package game;

import entities.*;
import physics.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GhostHunter extends Game {

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
	public ControlState controls;
	
	// Camera properties
	public double resolution = 320;
	public double spacing;
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
		player = new Player(15.3, -1.2, Math.PI * 0.3, load("images/knife_hand.png"));
		map = new Map(32, load("images/deathvalley_panorama.png"), load("images/wall_texture.png"));
		
		// Begin game
		start();
	}
	
	/**
	 * Utility for Media methods
	 * Contrary to Harbour - our FPS engine doesn't consider sprites as self-contained. Instead,
	 * they hold position, direction, and reference to their image, but they are not responsible for 
	 * drawing themselves. This engine is. 
	 */
	public Image load(String filename) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.getImage(filename);	
	}
	
	/**
	 * Game Loop methods
	 */	
	public void gameUpdate() {
		updateSprites();
	}
	
	public void gameDraw(double interpolation) {
		this.drawSky(this.player.direction, this.map.skybox, this.map.light);
		this.drawColumns();
		this.drawWeapon(this.player.weapon, this.player.paces);
	}
	
	public void drawSky(double direction, Image sky, double light) {
		double width = sky.getWidth(this) * (this.screenHeight / sky.getHeight(this)) * 2;
        double left = (direction / CIRCLE) * -width;
        graphics().drawImage(sky, (int)left, 0, (int)width, this.screenHeight, this);
        
        if (left < width - this.screenWidth) {
        	graphics().drawImage(sky, (int)(left+width), 0, (int)width, this.screenHeight, this);
        }
	}
	
	public void drawColumns() {
		
	}
	
	public void drawWeapon(Image weapon, int paces) {
		
	}
	
	/**
	 * Sprite State Changes
	 */
	public void spriteCollision(Sprite spr1, Sprite spr2) {
		
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
	
}
