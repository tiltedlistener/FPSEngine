package entities;

import java.awt.Graphics2D;
import javax.swing.JFrame;

import physics.Point2D;

public class Player extends Sprite {
	
	private final double CIRCLE = Math.PI * 2;
	
	final static int TYPE = 0;
	
	private Map map;	
	private Point2D playerPos;
	private double direction = 0;
	public int paces = 0;

	public Player(JFrame _frame, Graphics2D _g2d, int screenWidth, int screenHeight) {
		super(_frame, _g2d);
		setAlive(true);
		load("images/shotgun.png");
		setPosition(new Point2D((screenWidth - imageWidth())/2, screenHeight - imageHeight() + 20));
	}
	
	public void setMap(Map _map) {
		this.map = _map;
	}
	
	public void setPos(double x, double y) {
		this.playerPos.x = x;
		this.playerPos.y = y;
	}
	
	public void setDirection(double dir) {
		this.direction = dir;
	}
	
	public void rotate(double angle) {
		this.direction = (this.direction + angle + CIRCLE) % (CIRCLE);
	}
	
	public void walk(int distance) {
        double dx = Math.cos(this.direction) * distance;
        double dy = Math.sin(this.direction) * distance;
        if (this.map.get(this.playerPos.x + dx, this.playerPos.y) <= 0) this.playerPos.x += dx;
        if (this.map.get(this.playerPos.x, this.playerPos.y + dy) <= 0) this.playerPos.y += dy;
        this.paces += distance;
	}
	
	public void update() {
		
	}
	
	public void updatePos() {
		/** if (controls.left) this.rotate(-Math.PI * seconds);
        if (controls.right) this.rotate(Math.PI * seconds);
        if (controls.forward) this.walk(3 * seconds);
        if (controls.backward) this.walk(-3 * seconds);
        **/
	}
	
	public int type() {
		return TYPE;
	}
      
      
}
