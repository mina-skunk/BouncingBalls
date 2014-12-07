package com.gatimus.bouncingballs;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;

public class Bouncing extends SurfaceView implements Runnable{
	
	private static final String TAG = "Bouncing:";
	private Canvas canvas;
	
	public Bouncing(Context context) {
		super(context);
		Log.i(TAG, "Create");
		canvas = this.getHolder().lockCanvas();
	} //constructor

	@Override
	public void run() {
		while(!Thread.interrupted()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Log.e(TAG, e.toString());
			}
			this.draw(canvas);
		}
	} //run
	
}
