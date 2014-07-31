package game;

import java.awt.event.KeyEvent;

public class GhostHunter extends Game {

	private static final long serialVersionUID = 2;
	
	// Key States
	boolean keyDown, keyUp, keyLeft, keyRight, keyFire;	
	
	public GhostHunter() {
		super("Ghost Hunter", 1024, 768);
	}
	
	public void gamePaint(float interpolation) {
		
	}
	
	public void gameUpdate() {
		
	}
	
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
