package tile;

import util.ImageLoader;
import java.awt.Graphics;

public class Base extends Tile {

	private int health;

	public Base(double x, double y, ImageLoader iLoader, boolean isTopLeft)
	{
		super(x, y, iLoader);
		this.isPassable = false;
		this.isDestroyable = false;
		this.health = 1;
		if (isTopLeft) {
			this.image = iLoader.getEntitySprite(2, 19);
		} else {
			this.image = null;
		}
	}

	public void onHit()
	{

	}
	
	public void draw(Graphics g) {
		if (this.image != null) {
			int drawX = (int)Math.round(this.x);
			int drawY = (int)Math.round(this.y);
			g.drawImage(this.image, drawX, drawY, gui.GamePanel.ENTITY_SIZE, gui.GamePanel.ENTITY_SIZE, null);
		}
	}
	
}