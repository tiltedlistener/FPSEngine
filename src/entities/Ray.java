package entities;

import physics.Point2D;

public class Ray {
	public Point2D pos = new Point2D(0,0);
	public double height = 0;
	public double distance = 0;
	public double offset = 0;
	public double length2 = 0;
	public double shading = 0;	
	
	// In case there needs to be an empty obj
	public Ray() {}
	
	public Ray(Point2D point, double _height, double _distance) {
		pos.setAsSelf(point);
		height = _height;
		distance = _distance;
	}
	
	public Ray(Point2D point, double _height, double _distance, double _offset, double _length2, double _shading) {
		pos.setAsSelf(point);
		height = _height;
		distance = _distance;
		offset = _offset;
		length2 = _length2;
		shading = _shading;
	}
	
	public Ray clone() {
		return new Ray(pos, height, distance, offset, length2, shading);
	}
}