package entities;

import java.awt.Graphics2D;
import javax.swing.JFrame;

import physics.Point2D;

public class Player extends Sprite {

	public Player(JFrame _frame, Graphics2D _g2d, int screenWidth, int screenHeight) {
		super(_frame, _g2d);
		setAlive(true);
		load("images/shotgun.png");
		setPosition(new Point2D((screenWidth - imageWidth())/2, screenHeight - imageHeight() + 20));
	}
	
}
