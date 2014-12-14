package com.gatimus.bouncingballs;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.gatimus.bouncingballs.point.PointCartesian;
import com.gatimus.bouncingballs.point.PointCylindrical;
import com.gatimus.bouncingballs.point.PointSpherical;

public class Main extends Activity {
	
	private static final String TAG = "Main:";
	private Resources res;
	private FragmentManager fragMan;
	private DialogFragment about;
	private DialogFragment help;
	private View view;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Create");
		super.onCreate(savedInstanceState);
		view = new MyView(this);
		setContentView(new DView(this));
		res = getApplicationContext().getResources();
		fragMan = this.getFragmentManager();
		about = new About();
		help = new Help();
	} //onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "Create Options Menu");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} //onCreateOptionsMenu

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, item.getTitle().toString() + " Option Selected");
		int id = item.getItemId();
		switch(id){
		case R.id.action_settings :
			Intent intent = new Intent(Main.this, Settings.class);
            startActivity(intent);
			break;
		case R.id.action_about :
			about.show(fragMan, res.getString(R.string.action_about));
			break;
		case R.id.action_help :
			help.show(fragMan, res.getString(R.string.action_help));
			break;
		case R.id.action_quit : System.exit(0);
			break;
		default : 
			break;
		}
		return super.onOptionsItemSelected(item);
	} //onOptionsItemSelected
	
	public class MyView extends View {
		
		private static final int FRAME_TIME = 17;
		private Handler handler;
		private Runnable run;
		private PointF position;
		private PointF viewSize;
		private float radius;
		private int deltaY;
		private int deltaX;
		private int deltaZ;
		private Paint bg;
		private Paint color;
		private Paint shadow;
		
        public MyView(Context context) {
             super(context);
             handler = new Handler();
             radius = 75;
             deltaY = 5;
             deltaX = 5;
             deltaZ = 1;
             bg = new Paint();
             bg.setStyle(Paint.Style.FILL);
             bg.setColor(Color.WHITE);
             color = new Paint();
             color.setStyle(Paint.Style.FILL);
             color.setColor(Color.RED);
             shadow = new Paint();
             shadow.setStyle(Paint.Style.FILL);
             shadow.setColor(Color.LTGRAY);
             run = new Runnable(){
				@Override
				public void run() {
					if(radius >= 100 || radius <= 50){
						deltaZ = deltaZ * -1;
					}
					if(position.y + radius >= viewSize.y || position.y - radius <= 2){
						deltaY = deltaY * -1;
					}
					if(position.x + radius >= viewSize.x || position.x - radius <= 2){
						deltaX = deltaX * -1;
					}
					position.y = position.y + deltaY;
					position.x = position.x + deltaX;
					radius = radius + deltaZ;
					Log.v("Update:", "Position");
					invalidate();
				} //run()
             }; //run
        } //constructor

        @Override
        protected void onDraw(Canvas canvas) {
           super.onDraw(canvas);
           canvas.drawPaint(bg);
           canvas.drawCircle(position.x+radius-(radius/(float)Math.PI), position.y+radius-(radius/(float)Math.PI), 50-(radius-49), shadow);
           canvas.drawCircle(position.x, position.y, radius, color);
           setLayerType(View.LAYER_TYPE_SOFTWARE, null);
           canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 6, new float[]{10,10,10,50,50,10}, 0, null, 0, new int[]{Color.RED,Color.RED,Color.RED,0xFF000000, 0xFF000000, 0xFF000000} , 0, null, 0, 0, new Paint());
           Log.v("Canvas:", "Draw");
           handler.postDelayed(run, FRAME_TIME);
       }
        
        @Override 
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        	viewSize = new PointF(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        	position = new PointF(viewSize.x/2, viewSize.y /2);
        	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        
    } //inner class
	
public class DView extends View {
		
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
		
        public DView(Context context) {
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
             
          
             
             run = new Runnable(){
				@Override
				public void run() {
					
					//points = cylindricalTheta(points, 0.01F);
					
					
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
           int it = 0;
           for(PointCartesian point : newPoints){
        	   int r = (int)point.x + (int)zeroPosition.x;
        	   int g = (int)point.y + (int)zeroPosition.y;
        	   int b = (int)point.z + 150;
        	   r = r/((int)viewSize.x/255);
        	   g = g/((int)viewSize.y/255);
        	   b = b/(300/255);
        	   paint.setColor(Color.rgb(r, g, b));
        	   canvas.drawCircle(
        			   point.x*((float)Math.pow(3, point.y/1000))+zeroPosition.x,
        			   point.z*((float)Math.pow(3, point.y/1000))+zeroPosition.y,
        			   10*((float)Math.pow(3, point.y/1000)),
        			   paint
			   );
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
           

		   
           canvas.drawText(displayX, 5, 10, color);
           canvas.drawText(displayY, 5, 20, color);
           Log.v("Canvas:", "Draw");
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
        		newPoints = cylindricalTheta(points, amountX, amountY);
        	}
        	invalidate();
        	Log.v("Update:", "Position");
        	return true;
        }
        
        
    } //inner class


	
} //class
