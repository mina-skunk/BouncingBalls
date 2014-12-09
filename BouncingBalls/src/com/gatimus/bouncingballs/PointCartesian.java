package com.gatimus.bouncingballs;

import android.graphics.Point;
import android.graphics.PointF;

public class PointCartesian extends PointF {
	
	public float z;

	public PointCartesian() {
		// TODO Auto-generated constructor stub
	}

	public PointCartesian(Point p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public PointCartesian(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}

}
