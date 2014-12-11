package com.gatimus.bouncingballs.point;

import android.graphics.PointF;


public class PointCartesian extends PointF{
	
	public float z;

	public PointCartesian(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
	
	public PointCartesian(PointCylindrical pointCylindrical) {
		this.x = pointCylindrical.r * (float)Math.cos(pointCylindrical.theta);
		this.y = pointCylindrical.r * (float)Math.sin(pointCylindrical.theta);
		this.z = pointCylindrical.h;
	}
	
	public PointCartesian(PointSpherical pointSpherical) {
		this.x = pointSpherical.rho * (float)Math.sin(pointSpherical.theta) * (float)Math.cos(pointSpherical.phi);
		this.y = pointSpherical.rho * (float)Math.sin(pointSpherical.theta) * (float)Math.sin(pointSpherical.phi);
		this.z = pointSpherical.rho * (float)Math.cos(pointSpherical.theta);
	}

}
