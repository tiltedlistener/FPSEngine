package game;

import entities.*;
import physics.*;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GhostHunter extends Game {

	private static final long serialVersionUID = 2;
	
	// Key Game Objects
	private Player player;
	private LinkedList<Sprite> map = new LinkedList<Sprite>();
	
	// Sprite Types
	final static int PLAYER = 0;
	
	// Key States
	public ControlState controls;
	
	public GhostHunter() {
		super("Ghost Hunter", 1024, 576);
	
		// Setup our player
		player = new Player(this, graphics(), this.screenWidth, this.screenHeight);
		
		// Add our sprites to the list
		sprites().add(player);
		
		// Begin game
		start();
	}
	
	public void generateWalls() {
		
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
