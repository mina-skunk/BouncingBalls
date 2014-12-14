package com.gatimus.bouncingballs.point;

public class PointCylindrical {
	
	public float r;
	public float theta;
	public float h;
	
	public PointCylindrical(float r, float theta, float h){
		this.r = r;
		this.theta = theta;
		this.h = h;
	}
	
	public PointCylindrical(PointCartesian pointCartesian){
		this.r = (float)Math.sqrt(Math.pow(pointCartesian.x, 2)+Math.pow(pointCartesian.y, 2));
		if(pointCartesian.x==0 && pointCartesian.y==0){
			this.theta = 0;
		} else if(pointCartesian.x >= 0){
			this.theta = (float)Math.asin(pointCartesian.y/this.r);
		} else if(pointCartesian.x < 0){
			this.theta = (float)-(Math.asin(pointCartesian.y/this.r)+Math.PI);
		}
		this.h = pointCartesian.z;
	}
	
	public PointCylindrical(PointSpherical pointSpherical) {
		this.r = pointSpherical.rho * (float)Math.sin(pointSpherical.phi);
		this.theta = pointSpherical.theta;
		this.h = pointSpherical.rho * (float)Math.cos(pointSpherical.phi);
	}

}
