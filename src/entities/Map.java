package entities;

import java.util.*;
import physics.*;

public class Map {

	public int size;
	public List<Integer> grid;
	public ImageEntity sky;
	public ImageEntity wallTexture;
	public int light;
	
	private Random rand = new Random();
	
	public Map(int size, ImageEntity _sky, ImageEntity _wall) {
		this.size = size;
		this.grid = new ArrayList<Integer>(size * size);
		this.sky = _sky;
		this.wallTexture = _wall;
		this.light = 0;
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
	
	public double cast(Point2D point, double angle, double range) {
		return 0;
	}
	
}
