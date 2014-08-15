package entities;

import physics.Point2D;

public class Ray {
	public Point2D pos = new Point2D(0,0);
	public double height = 0;
	public double distance = 0;
	public double offset = 0;
	
	// In case there needs to be an empty obj
	public Ray() {}
	
	public Ray(Point2D point, double _height, double _distance) {
		pos.setAsSelf(point);
		height = _height;
		distance = _distance;
	}
}