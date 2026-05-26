package tile;

import util.ImageLoader;
import java.awt.Graphics;

import gui.GamePanel;

public class Base extends Tile {

	private int health;

	public Base(double x, double y, ImageLoader iLoader, boolean isTopLeft)
	{
		super(x, y, iLoader);
		this.name = "base";
		this.isPassable = false;
		this.isDestroyable = true;
		this.health = 1;
		if (isTopLeft) {
			this.image = iLoader.getEntitySprite2(2, 19);
		} else {
			this.image = null;
		}
	}
	
	public void draw(Graphics g) {
		if (this.image != null) {
			int drawX = (int)Math.round(this.x);
			int drawY = (int)Math.round(this.y);
			g.drawImage(this.image, drawX, drawY, 48, 48, null);
			//this.drawHitbox(g);
		}
	}
	
}