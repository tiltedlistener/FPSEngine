package game;

import entities.*;
import physics.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GhostHunter extends Game {

	private static final long serialVersionUID = 2;
	
	// Key Game Objects
	private Player player;
	private Map map;
	
	// Sprite Types
	final static int PLAYER = 0;
	final static int ENEMEY = 1;
	final static int BULLET = 2;
	
	// Key States
	public ControlState controls;
	
	public GhostHunter() {
		super("Ghost Hunter", 1024, 576);
	
		// Setup our main components
		player = new Player(15.3, -1.2, Math.PI * 0.3, load("images/shotgun.png"));
		map = new Map(32, load("images/sky.png"), load("images/wall.png"));
		
		// Begin game
		start();
	}
	
	public void generateWalls() {
		
	}
	
	
	/**
	 * Utility for Media methods
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
