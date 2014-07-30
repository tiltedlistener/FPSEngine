package entities;

public class Player {
	
	public int x;
	public int y;
	public float direction;
	public int paces;
	public ImageEntity image;
	
	public Player(int _x, int _y, float _direction, ImageEntity _image) {
		this.x = _x;
		this.y = _y;
		this.direction = _direction;
		this.paces = 0;
		this.image = _image;
	}
	
}
