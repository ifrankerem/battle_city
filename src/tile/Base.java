package tile;

import gui.GamePanel;
import util.ImageLoader;
import java.awt.Graphics;

public class Base extends Tile {

	private static final int BASE_DRAW_COL = 12;
	private static final int BASE_DRAW_ROW = 24;

	public Base(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "base";
		this.isPassable = false;
		this.isDestroyable = false;
		this.image = iLoader.getEntitySprite2(2, 19);

	}
	
	public void draw(Graphics g) {
		if (this.image != null) {
			int drawX = (int)Math.round(this.x);
			int drawY = (int)Math.round(this.y);
			if(drawX == BASE_DRAW_COL * GamePanel.MAP_CELL_SIZE && drawY == BASE_DRAW_ROW * GamePanel.MAP_CELL_SIZE) //base always in the same place
				g.drawImage(this.image, drawX, drawY, 48, 48, null);
			//this.drawHitbox(g);
		}
	}


	
}
