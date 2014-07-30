package game;

import entities.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1;
	
	// Game loop
	private Thread gameLoop;
	private int desiredFrameRate;
	
	// Graphics rendering
	private BufferedImage backBuffer;
	private Graphics2D g2d;
	private int screenWidth, screenHeight;
	
	
	// Key States
	boolean keyDown, keyUp, keyLeft, keyRight, keyFire;	
	
	public Game() {
		// Static vars
		super("Ghost Hunter");
		this.screenWidth = 1024;
		this.screenHeight = 1024;
		this.desiredFrameRate = 60;
		
		// Graphics Setup
		backBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		// Controls
		addKeyListener(this);
		
		// Begin game
		start();
	}
	
	/**
	 * LOOP
	 */	
	public void start() {		
		// Show the screen
		setSize(this.screenWidth, this.screenHeight);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Fire in the hole
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		while(t == gameLoop) {
			try {
				Thread.sleep(1000/desiredFrameRate);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			repaint();
		}
	}
	
	public void stop() {
		gameLoop = null;
	}
	
	/**
	 * RENDER
	 */
	public void paint(Graphics g) {
		g2d.clearRect(0, 0, this.screenWidth, this.screenHeight);
		g.drawImage(backBuffer, 0, 0, this);
	}	
	
	/**
	 * KEYLISTENERS
	 */
	public void keyTyped(KeyEvent k) {}
	public void keyPressed(KeyEvent k) {
		switch(k.getKeyCode()) {
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
	public void keyReleased(KeyEvent k) {
		switch(k.getKeyCode()) {
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
