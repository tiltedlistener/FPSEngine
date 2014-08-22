package entities;

import java.util.*;
import physics.*;
import java.awt.Image;

public class Map {

	public int size;
	public int[] grid;
	public Image skybox;
	public Image wallTexture;
	public double light;
	public Step noWall = new Step();
	public Angle currentAngle = new Angle();
	public double currentRange = 0;
	
	private Random rand = new Random();
	
	public Map(int size, Image _sky, Image _wall) {
		this.size = size;
		this.grid = new int[1024];
		this.skybox = _sky;
		this.wallTexture = _wall;
		this.light = 0;
		
		this.noWall.length2 = Double.POSITIVE_INFINITY;
	}
	
	public void randomize() {
		for (int i=0; i < this.size * this.size; i++) {
			float result = rand.nextFloat();			
			// this.grid[i] = (result > 0.5) ? 1 : 0;
			this.grid[i] = 1;
		}
	}
	
	public double get(double x, double y) {
		int cleanX = (int)Math.floor(x);
		int cleanY = (int)Math.floor(y);
		if ((cleanX < 0 || cleanX > this.size - 1) || (cleanY < 0 || cleanY > this.size - 1)) return -1;
		return this.grid[cleanY * this.size + cleanX];
	}
	
	public void update(double seconds) {
		if (this.light > 0) {
			this.light = Math.max(this.light - 10 * seconds, 0);		
		} else if (Math.random() * 5 < seconds) {
			this.light = 2;
		}
	}
	
	/**
	 * Entry point for all ray work
	 * @param point this is the Point2D of the Player object
	 * @param angle Player's direction attribute
	 * @param range Camera object's range
	 * @return ray object
	 */
	public ArrayList<Ray> cast(Point2D point, double angle, double range) {
		this.currentAngle.sin = Math.sin(angle);
		this.currentAngle.cos = Math.cos(angle);
		this.currentRange = range;
		return this.ray(new Ray(point, 0, 0));
	}
	
	public ArrayList<Ray> ray(Ray origin) {
		Step stepX = this.stepCheck(this.currentAngle.sin, this.currentAngle.cos, origin.pos.x, origin.pos.y, false);
		
		// Flip the point here so we check towards the opposite (horizontal vs vertical) side [note the x/y are switched too]
		Step stepY = this.stepCheck(this.currentAngle.cos, this.currentAngle.sin, origin.pos.y, origin.pos.x, true);
		
		NextStep nextStep;
		// Here we see which way is closer and proceed with inspection
		if (stepX.length2 < stepY.length2) {
			nextStep = inspect(stepX, 1, 0, origin.distance, stepX.pos.y);
		} else {
			nextStep = inspect(stepY, 0, 1, origin.distance, stepY.pos.x);
		}
		
		ArrayList<Ray> finalGroup = new ArrayList<Ray>();
		finalGroup.add(origin);
		
		if (nextStep.distance > this.currentRange) {	
			return finalGroup;
		} else {
			ArrayList<Ray> otherGroup = this.ray(nextStep);
			finalGroup.addAll(otherGroup);
			return finalGroup;
		}
		
	}
	
	public Step stepCheck(double rise, double run, double x, double y, boolean inverted) {
		if (run == 0) return this.noWall;
		double dx;
		/**
		 * Imagine here we are in a box quadrant's center at 45 degrees facing SE
		 * So cos(315) is positive (looking at StepX), while sin(315) is negative with
		 * a slope of -1
		 * 
		 * But dx will be positive. Hence, we'll move down one, and over right one
		 */
		if (run > 0) {
			dx = Math.floor(x + 1) - x;
		} else {
			dx = Math.ceil(x - 1) - x;
		}
		double dy = dx * (rise / run);
		
		Step result = new Step();
		
		/**
		 * Length2 is rather a^2 + b^2 = c^2
		 * This is used only for comparative purposes since
		 * square roots are not fun to compute so fast
		 */
		result.length2 = dx * dx + dy * dy;
		
		/**
		 * Use inverted when we're swapping pos
		 */
		if (inverted) {
			result.pos.x = y + dy;
			result.pos.y = x + dx;
		} else {
			result.pos.y = y + dy;
			result.pos.x = x + dx;
		}
		
		return result;
	}
	
	public NextStep inspect(Step step, int shiftX, int shiftY, double distance, double offset) {
		double dx = 0;
		double dy = 0;
		if (this.currentAngle.cos < 0) dx = shiftX;
		if (this.currentAngle.sin < 0) dy = shiftY;
		
		/**
		 * In the PlayfulJS demo, he sets additional params on the step object
		 * Here we're going to extend it to NextStep
		 */
		NextStep nextStep = new NextStep(step);
		
		nextStep.height = this.get(step.pos.x - dx, step.pos.y - dy);
		nextStep.distance = distance + Math.sqrt(step.length2);
		
		nextStep.offset = offset - Math.floor(offset);

		return nextStep;
	}
	
	/**
	 * ANGLE: used to hold the current angle so its present throughout the cast process
	 * Playful JS uses nested functions to resolve this, but that's not present in Java
	 */
	public class Angle {
		public double cos = 0;
		public double sin = 0;
	}

}
