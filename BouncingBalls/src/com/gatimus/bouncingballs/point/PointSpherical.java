package com.gatimus.bouncingballs.point;

public class PointSpherical {
	
	public float rho;
	public float phi;
	public float theta;
	
	public PointSpherical(PointCartesian pointCartesian){
		this.rho = (float)Math.sqrt(Math.pow(pointCartesian.x, 2)+Math.pow(pointCartesian.y, 2)+Math.pow(pointCartesian.z, 2));
		this.phi = (float)Math.atan(pointCartesian.y/pointCartesian.x);
		this.theta = (float)Math.atan(Math.sqrt(Math.pow(pointCartesian.x, 2)+Math.pow(pointCartesian.y, 2))/pointCartesian.z);
	}
	
	public PointSpherical(PointCylindrical pointCylindrical) {
		this.rho = (float)Math.sqrt(Math.pow(pointCylindrical.r, 2)+Math.pow(pointCylindrical.h, 2));
		this.phi = pointCylindrical.theta;
		this.theta = (float)Math.atan(pointCylindrical.r/pointCylindrical.h);
	}

}
