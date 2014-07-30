package entities;

import java.util.*;

public class Map {

	public int size;
	public ArrayList<ImageEntity> grid;
	public ImageEntity sky;
	public ImageEntity wallTexture;
	public int light;
	
	public Map(int size, ImageEntity _sky, ImageEntity _wall) {
		this.size = size;
		this.grid = new ArrayList<ImageEntity>(size * size);
		this.sky = _sky;
		this.wallTexture = _wall;
		this.light = 0;
	}
	
}
