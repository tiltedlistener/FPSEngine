package game;

import entities.*;

import java.util.ArrayList;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class GhostHunter extends Game {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GhostHunter();
	}
	
	private static final long serialVersionUID = 2;
	
	private final double CIRCLE = Math.PI * 2;
	
	// Key Game Objects
	private Player player;
	private Map map;
	
	// Sprite Types
	final static int PLAYER = 0;
	final static int ENEMEY = 1;
	final static int BULLET = 2;
	
	// Key States
	public ControlState controls = new ControlState();
	
	// Camera properties
	public double resolution = 320;			// The number of columns we've divided the screen into.
	public double spacing;					// This is how wide those columns will be. We need to know the screenWidth to calculate
	
	/**
	 * Explanation on Focal Length: the 50mm lens is often considered most close to the human eye
	 * which I found was highly debated, however, 50mm results in roughly 46 degrees of view, roughly
	 * equivalent to 0.8 Radians. http://olympuszuiko.wordpress.com/2007/09/05/does-a-50mm-normal-lens-really-see-what-the-eye-sees/
	 * 
	 *  Regardless, it's essentially how zoomed in we are. 0.8 looks and feels good for our engine. Though you could make drunk mode by varying this. 
	 */
	public double focalLength = 0.8;
	public double range = 14;
	public double lightRange = 5;
	public double scale;
	
	public GhostHunter() {
		super("Ghost Hunter", 1024, 576);
	
		// Setup camera properties
		this.spacing = this.screenWidth / this.resolution;
		this.scale = (this.screenWidth + this.screenHeight) / 1200;
		
		// Setup our main components
		player = new Player(-1, -1, 0, load("images/knife_hand.png"));
		map = new Map(2, load("images/deathvalley_panorama.jpg"), load("images/wall_texture.jpg"));
		map.randomize();
		
		// Begin game
		start();
	}
	
	/**
	 * Utility for Media methods
	 * Contrary to Harbour - our FPS engine doesn't consider sprites as self-contained. Instead,
	 * they hold position, direction, and reference to their image, but they are not responsible for 
	 * drawing themselves. This engine is as I don't want the sprites to worry about projection.
	 */
	public Image load(String filename) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.getImage(filename);	
	}
	
	/**
	 * Game Loop methods
	 */	
	public void gameUpdate() {
		map.update(interpolation);
		player.update(controls, interpolation/20, map);
	}
	
	public void gameDraw(double interpolation) {
		this.drawSky(this.player.direction, this.map.skybox, this.map.light);
		this.drawColumns();
		this.drawWeapon(this.player.weapon, this.player.paces);
	}

	public void drawSky(double direction, Image sky, double light) {
		int width = (int)(sky.getWidth(this) * ((double)this.screenHeight / (double)sky.getHeight(this)) * 2);		
        int left =(int)((direction / CIRCLE) * -width);
        graphics().drawImage(sky, left, 0, width, this.screenHeight, this);
        
        if (left < width - this.screenWidth) {
            graphics().drawImage(sky, left + width, 0, width, this.screenHeight, this);
        }
	}
	
	public void drawColumns() {
		for (int column = 0; column < this.resolution; column++){ 	
			/**
			 * UNDERSTAND: these two calculations will return degrees varying from -32 to 32 degrees
			 * Or roughly 64 degrees of view, which is roughly what the standard FOV for console games
			 * is. http://en.wikipedia.org/wiki/Field_of_view_in_video_games
			 * 
			 * 0.5 is just convenient to write as opposed to trying to hit 60 degrees exactly. 
			 */
			double x = column / this.resolution - 0.5;
			double angle = Math.atan2(x, this.focalLength);
						
			ArrayList<Ray> ray = this.map.cast(player.pos, player.direction + angle, this.range);
			this.drawColumn(column, ray, angle);
		}
	}
	
	public void drawColumn(int column, ArrayList<Ray> ray, double angle) {
		Image texture = map.wallTexture;
		double left = (int)Math.floor(column * this.spacing);
		double width = (int)Math.ceil(this.spacing);
		int hit = -1;
		
		// Make sure we got a hit
		while (hit++ < ray.size() && ray.get(hit).height <= 0 ) {
			// We need this here rather than PlayfulJS because we'll throw errors 
			// rather than just move on with our lives like normal people. 
			if (ray.size()-1 < hit+1) {
				return;
			}
		}
		
		for (int s = ray.size() - 1; s >=0; s--) {
			Ray step = ray.get(s);
			
			if (s == hit) {				
				int textureX = (int)Math.floor(texture.getWidth(this) * step.offset);
				Wall wall = this.project(step.height, angle, step.distance); 
					
				/**
				graphics().drawImage(texture, 
						(int)left, (int)wall.top, 
						(int)(width), (int)wall.height, 
						textureX, 0,
						1, texture.getHeight(this),
						this);
				**/	
				
				graphics().setColor(Color.orange);
				graphics().fillRect((int)left, (int)wall.top, (int)(width), (int)wall.height);
			}
		}
	}
	
	public Wall project(double height, double angle, double distance) {
		double z = distance * Math.cos(angle);
		double wallHeight = this.screenHeight * height / z;
		double bottom = this.screenHeight / 2 * (1 + 1/z);
		return new Wall(bottom - wallHeight, wallHeight);
	}
	
	public void drawWeapon(Image weapon, int paces) {
		
	}
	
	/**
	 * KEY EVENTS
	 */
	public void gameKeyDown(int k) {
		switch(k) {
		case KeyEvent.VK_LEFT:
			controls.keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			controls.keyRight = true;
			break;
		case KeyEvent.VK_UP:
			controls.keyUp = true;
			break;	
		case KeyEvent.VK_DOWN:
			controls.keyDown = true;
			break;			
		case KeyEvent.VK_SPACE:
			controls.keyFire = true;
			break;
		}
	}
	public void gameKeyUp(int k) {
		switch(k) {
		case KeyEvent.VK_LEFT:
			controls.keyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			controls.keyRight = false;
			break;
		case KeyEvent.VK_UP:
			controls.keyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			controls.keyDown = false;
			break;			
		case KeyEvent.VK_SPACE:
			controls.keyFire = false;
			break;
		}
	}
	
	/**
	 * WALL DATA OBJECT
	 * Not truly a wall. This is a data object that PlayfulJS builds on the fly.
	 * I suppose everything is a data object if you think about it. Even ourselves. 
	 */
	public class Wall {
		public double top = 0;
		public double height = 0;
		
		public Wall() {}
		
		public Wall(double _top, double _height) {
			top = _top;
			height = _height;
		}
	}
	
}
