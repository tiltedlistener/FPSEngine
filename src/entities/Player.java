package entities;

import physics.*;
import game.*;
import java.awt.Image;

public class Player {

	private final double CIRCLE = Math.PI * 2;
	
	public Image weapon;
	public Point2D pos;
	public double direction;
	public int paces = 0;
	
	public Player(double x, double y, double _direction, Image _entity) {
		pos = new Point2D(x,y);
		direction = _direction;
		weapon = _entity;
	}
	
	public Image getImage() {
		return weapon;
	}
	
	public void rotate(double angle) {
		this.direction = (this.direction + angle + CIRCLE) % (CIRCLE);
	}
	
	public void walk(double distance, Map map) {
        double dx = Math.cos(this.direction) * distance;
        double dy = Math.sin(this.direction) * distance;
        if (map.get(this.pos.x + dx, this.pos.y) <= 0) this.pos.x += dx;
        if (map.get(this.pos.x, this.pos.y + dy) <= 0) this.pos.y += dy;
        this.paces += distance;
	}
	
	public void update(ControlState controls, double seconds, Map map) {
		if (controls.keyLeft) this.rotate(-Math.PI * seconds);
        if (controls.keyRight) this.rotate(Math.PI * seconds);
        if (controls.keyUp) this.walk(3 * seconds, map);
        if (controls.keyDown) this.walk(-3 * seconds, map);
	}

}
