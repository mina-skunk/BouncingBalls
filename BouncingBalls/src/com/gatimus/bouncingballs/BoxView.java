package com.gatimus.bouncingballs;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.media.audiofx.Visualizer;
import android.media.audiofx.Visualizer.OnDataCaptureListener;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gatimus.bouncingballs.point.PointCartesian;
import com.gatimus.bouncingballs.point.PointCylindrical;

public class BoxView extends View implements OnDataCaptureListener{

	private static final int FRAME_TIME = 17;
	private Handler handler;
	private Runnable run;
	private PointF zeroPosition;
	private PointF viewSize;
	private Paint bg;
	private Paint color;
	private ArrayList<PointCartesian> points;
	private ArrayList<PointCartesian> newPoints;
	private String displayX = "0";
	private String displayY = "0";
	private Visualizer mVisualizer;
	float ig = 0;
	float[] mPoints;
	
    public BoxView(Context context) {
         super(context);
         handler = new Handler();
         bg = new Paint();
         bg.setStyle(Paint.Style.FILL);
         bg.setColor(Color.WHITE);
         color = new Paint();
         color.setStyle(Paint.Style.STROKE);
         color.setColor(Color.RED);
         
         points = new ArrayList<PointCartesian>();
         points.add(new PointCartesian(-150, -150, 150));
         points.add(new PointCartesian(150, -150, 150));
         points.add(new PointCartesian(150, 150, 150));
         points.add(new PointCartesian(-150, 150, 150));
         points.add(new PointCartesian(-150, -150, -150));
         points.add(new PointCartesian(150, -150, -150));
         points.add(new PointCartesian(150, 150, -150));
         points.add(new PointCartesian(-150, 150, -150));
         newPoints = points;
         
         mVisualizer = new Visualizer(0);
 		mVisualizer.setDataCaptureListener(this, Visualizer.getMaxCaptureRate() / 2, true, true);
 		mVisualizer.setEnabled(true);
         
         run = new Runnable(){
			@Override
			public void run() {
				//byte[] waveform = aLis.getWaveform();
				//byte[] fft = aLis.getFFT();
				//float fy = waveform[0];
				//float fx = fft[0];
				//newPoints = cylindricalTheta(points, fx, fy);
				
				
				Log.v("Update:", "Position");
				invalidate();
			} //run()
         }; //run
    } //constructor

    @Override
    protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       canvas.drawPaint(bg);
       Paint paint = new Paint();
       paint.setStyle(Paint.Style.STROKE);
       float[] pts = new float[16];
       int i = 0;
       
       for(PointCartesian point : newPoints){

    	   pts[i] = point.x*((float)Math.pow(3, point.y/1000))+zeroPosition.x;
    	   pts[++i] = point.z*((float)Math.pow(3, point.y/1000))+zeroPosition.y;
    	   i++;
       }
       Path path = new Path();
       path.setFillType(Path.FillType.WINDING);
       path.moveTo(pts[0], pts[1]);
       path.lineTo(pts[2], pts[3]);
       path.lineTo(pts[4], pts[5]);
       path.lineTo(pts[6], pts[7]);
       path.lineTo(pts[0], pts[1]);
       path.lineTo(pts[8], pts[9]);
       path.lineTo(pts[10], pts[11]);
       path.lineTo(pts[12], pts[13]);
       path.lineTo(pts[14], pts[15]);
       path.lineTo(pts[8], pts[9]);
       path.moveTo(pts[2], pts[3]);
       path.lineTo(pts[10], pts[11]);
       path.moveTo(pts[4], pts[5]);
       path.lineTo(pts[12], pts[13]);
       path.moveTo(pts[6], pts[7]);
       path.lineTo(pts[14], pts[15]);
       canvas.drawPath(path, color);
       
       canvas.drawLines(mPoints, color);
	   
       canvas.drawText(displayX, 5, 10, color);
       canvas.drawText(displayY, 5, 20, color);
       //Log.v("Canvas:", "Draw");
       //handler.postDelayed(run, FRAME_TIME);
   }
    
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	viewSize = new PointF(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    	zeroPosition = new PointF(viewSize.x/2, viewSize.y /2);
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	Log.v("Canvas:", "Measure");
    }
    
    public ArrayList<PointCartesian> cylindricalTheta(ArrayList<PointCartesian> list, float amountX, float amountY){
    	ArrayList<PointCartesian> rList = new ArrayList<PointCartesian>();
    	for(PointCartesian point : list){
    		PointCylindrical cPoint = new PointCylindrical(point);
    		cPoint.theta = (float)cPoint.theta + amountX;
    		cPoint.r = (float)cPoint.r + amountY;
    		rList.add(new PointCartesian(cPoint));
    	}
    	return rList;
    }

    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	float amountX = e.getX() - zeroPosition.x;
    	float amountY = e.getY() - zeroPosition.y;
    	//float amountX = 0;
    	//float amountY = 0;
    	displayX = String.valueOf(amountX);
    	displayY = String.valueOf(amountY);
    	if(e.getAction() == MotionEvent.ACTION_MOVE || e.getAction() == MotionEvent.ACTION_DOWN){
    		amountX = amountX/100*-1;
    		amountY = amountY*-1;
    		//newPoints = cylindricalTheta(points, amountX, amountY);
    	}
    	invalidate();
    	//Log.v("Update:", "Position");
    	return true;
    }

	@Override
	public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
		
			ArrayList<PointCartesian> rList = new ArrayList<PointCartesian>();
			int i = 0;
			for(PointCartesian point : points){
				PointCylindrical cPoint = new PointCylindrical(point);
				cPoint.theta = (float)cPoint.theta + ig;
				cPoint.r = (float)cPoint.r + waveform[i]/2;
				rList.add(new PointCartesian(cPoint));
				i++;
			}
			newPoints = rList;
			ig = ig +0.05F;
			//invalidate();
			
	}

	@Override
	public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
		mPoints = new float[fft.length * 4];
		for (int i = 0; i < fft.length / 2; i++) {
	        mPoints[i * 4] = i * 8;
	        mPoints[i * 4 + 1] = 0;
	        mPoints[i * 4 + 2] = i * 8;
	        byte rfk = fft[2 * i];
	        byte ifk = fft[2 * i + 1];
	        float magnitude = (float) (rfk * rfk + ifk * ifk);
	        int dbValue = (int) (10 * Math.log10(magnitude));
	        mPoints[i * 4 + 3] = (float) (dbValue * 7);
	    }       
		invalidate();
	    
    	
	}
    
    
    
}
