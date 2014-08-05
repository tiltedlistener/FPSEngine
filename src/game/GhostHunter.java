package game;

import entities.*;
import physics.*;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GhostHunter extends Game {

	private static final long serialVersionUID = 2;
	
	// Key Game Objects
	Player player;
	LinkedList<Sprite> map = new LinkedList<Sprite>();
	
	// Key States
	boolean keyDown, keyUp, keyLeft, keyRight, keyFire;	
	
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
			keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		case KeyEvent.VK_UP:
			keyUp = true;
			break;	
		case KeyEvent.VK_SPACE:
			keyFire = true;
			break;
		}
	}
	public void gameKeyUp(int k) {
		switch(k) {
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		case KeyEvent.VK_UP:
			keyUp = false;
			break;	
		case KeyEvent.VK_SPACE:
			keyFire = false;
			break;
		}
	}
	
}
