package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Game engine controls routines, gamestate, and abstract format borrowed from for the sprite and rendering format from
 * JS Harbour - http://jharbour.com/wordpress/portfolio/beginning-java-se-6-game-programming-3rd-ed/
 * 
 * Loop structure from Eli Delvanthal
 * 
 * @author tiltedlistener
 */
abstract class Game extends JFrame implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1;
	
	// Mode
	private boolean DEBUG = false;
	
	// Game loop
	private Thread gameLoop;
	protected float interpolation;
	private int fps = 60;
	private int frameCount = 0;
	
	// Graphics rendering
	private Graphics2D g2d;
	public Graphics2D graphics() { return g2d; }
	
	private BufferedImage backBuffer;
	protected int screenWidth, screenHeight;
	
	// Game State
	boolean paused = false;
	
	// Abstract Methods
	abstract void gameUpdate();
	abstract void gameDraw(double interpolation);
	abstract void gameKeyDown(int keyCode);
	abstract void gameKeyUp(int keyCode);
	
	public Game(String name, int width, int height) {
		super(name);
		this.screenWidth = width;
		this.screenHeight = height;
		
		// Graphics Setup
		backBuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		// Controls
		addKeyListener(this);
	}
	
	/**
	 * LOOP
	 */	
	public void start() {		
		// Show the screen
		setSize(this.screenWidth, this.screenHeight);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Fire in the hole
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		
		/**
		 * Credit to Eli Delvanthal for the loop structure
		 * http://www.java-gaming.org/index.php?topic=24220.0
		 */
		final double GAME_HERTZ = 30.0;
		
		// Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		
		// At the very most we will update the game this many times before a new render.
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		
		//We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();
		  
		// If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		
		//Simple way of finding FPS.
	    int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		
		while(t == gameLoop) {
			double now = System.nanoTime();
			int updateCount = 0;
			 
			if (!paused) {
				//Do as many game updates as we need to, potentially playing catchup.
				while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER ) {
					gameUpdate();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}

				//If for some reason an update takes forever, we don't want to do an insane number of catchups.
				//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
				if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				   lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				//Render. To do so, we need to calculate interpolation for a smooth render.
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
				render(interpolation);
				lastRenderTime = now;

				//Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
				   fps = frameCount;
				  
				   if (DEBUG) {
					   System.out.println("FPS: " + fps);
				   }
				   
				   frameCount = 0;
				   lastSecondTime = thisSecond;
				}

				//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
				while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				   Thread.yield();

				   //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				   //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				   //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
				   try {Thread.sleep(1);} catch(Exception e) {} 

				   now = System.nanoTime();
				} 
			
			}			
		}
	}
		
	public void stop() {
		gameLoop = null;
	}
	
	/**
	 * GAME STATE
	 */
	public void pauseGame() {
		paused = true;
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	/**
	 * RENDER
	 */
	public void render(float interpolation) {
		setInterpolation(interpolation);
		repaint();
	}
	
	
	public void setInterpolation(float interp) {
	       interpolation = interp;
	}
	
	public void paint(Graphics g) {
		g2d.clearRect(0, 0, this.screenWidth, this.screenHeight);		
		
		// Draw individual components
		gameDraw(interpolation);
		
		g.drawImage(backBuffer, 0, 0, this);
	}	
	
	/**
	 * KEYLISTENERS
	 */
	public void keyTyped(KeyEvent k) {}
	public void keyPressed(KeyEvent k) {
		gameKeyDown(k.getKeyCode());
	}
	public void keyReleased(KeyEvent k) {
		gameKeyUp(k.getKeyCode());
	}
		
}
