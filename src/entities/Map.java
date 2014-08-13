package entities;

import java.util.*;
import physics.*;

public class Map {

	public int size;
	public List<Integer> grid;
	public ImageEntity sky;
	public ImageEntity wallTexture;
	public int light;
	public WallPos noWall;
	public WallPosBuilder wallBuild = new WallPosBuilder();
	
	private Random rand = new Random();
	
	public Map(int size, ImageEntity _sky, ImageEntity _wall) {
		this.size = size;
		this.grid = new ArrayList<Integer>(size * size);
		this.sky = _sky;
		this.wallTexture = _wall;
		this.light = 0;
		
		wallBuild.create();
		wallBuild.setLength(Double.POSITIVE_INFINITY);
		this.noWall = wallBuild.get();
	}
	
	public void randomize() {
		for (int i=0; i < this.size * this.size; i++) {
			float result = rand.nextFloat();
			
			int input;
			if (result > 0.3)
				input = 1;
			else 
				input = 0;
			this.grid.set(i, input);
		}
	}
	
	public double get(double x, double y) {
		int cleanX = (int)Math.floor(x);
		int cleanY = (int)Math.floor(y);
		
		if (cleanX < 0 || cleanX > this.size - 1 || cleanY < 0 || cleanY > this.size - 1) return -1;
		return this.grid.get(cleanY * this.size + cleanX);
	}
	
	public ArrayList<Origin> cast(Point2D point, double angle, double range) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		return this.ray(new Origin(point.x, point.y), sin, cos, range);
	}
	
	public ArrayList<Origin> ray(Origin origin, double sin, double cos, double range) {
		WallPos stepX = this.step(sin, cos, origin.x, origin.y, false);
		WallPos stepY = this.step(cos, sin, origin.y, origin.x, true);
		Origin nextStep;
		
		if (stepX.length2 < stepY.length2) {
			nextStep = inspect(stepX, 1, 0, origin.distance, stepX.y);
		} else {
			nextStep = inspect(stepY, 0, 1, origin.distance, stepY.x);
		}
		
		ArrayList<Origin> finalGroup = new ArrayList<Origin>();
		finalGroup.add(origin);
		if (nextStep.distance <= range) {	
			ArrayList<Origin> otherGroup = this.ray(nextStep, sin, cos, range);
			finalGroup.addAll(otherGroup);
		} 
		return finalGroup;
	}

	public WallPos step(double rise, double run, double x, double y, boolean inverted) {
		if (run == 0) return this.noWall;
		
		double dx;
		if (run > 0) {
			dx = Math.floor(x + 1) - x;
		} else {
			dx = Math.ceil(x - 1) - x;
		}
		
		double dy = dx * (rise / run);
		
		this.wallBuild.create();
		this.wallBuild.setLength(dx * dx + dy * dy);
		
		if (inverted) {
			this.wallBuild.setX(y + dy);
			this.wallBuild.setY(x + dx);
		} else {
			this.wallBuild.setY(y + dy);
			this.wallBuild.setX(x + dx);
		}

		return this.wallBuild.get();
	}
	
	public Origin inspect(WallPos step, int shiftX, int shiftY, double distance, double offset) {
		
		return new Origin(0,0);
	}
	
	
	/**
	 * NESTED WALL CLASS
	 */
	public class WallPosBuilder {
		
		private WallPos wall;
		
		public void create() {
			this.wall = new WallPos();
		}
		
		public void setLength(double length) {
			wall.length2 = length;
		}
		
		public void setX(double x) {
			this.wall.x = x;
		}
		
		public void setY(double y) {
			this.wall.y = y;
		}
		
		public WallPos get() {
			return this.wall;
		}
	}
	
	public class WallPos {
		public double length2;
		public double x;
		public double y;
	}
	
	/**
	 * NESTED ORIGIN CLASS
	 */
	public class Origin {
		public double x; 
		public double y;
		public double height = 0;
		public double distance = 0;
		
		public Origin(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
