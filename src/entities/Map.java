package entities;

import java.util.*;

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
		return 0;
	}
	
}
