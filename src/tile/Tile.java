package tile;

import java.awt.Graphics;

import gui.GamePanel;
import model.GameObject;
import util.ImageLoader;

abstract public class Tile extends GameObject {

	protected boolean isPassable;
	protected boolean isDestroyable;
	
	public Tile(double x, double y, ImageLoader iLoader)
	{
		super(x,y,iLoader);
	}


	abstract void onHit();
	public void draw(Graphics g)
	{
		int drawX = (int)Math.round(this.x);
		int drawY = (int)Math.round(this.y);
		g.drawImage(this.getImage(),drawX,drawY,GamePanel.MAP_CELL_SIZE,GamePanel.MAP_CELL_SIZE,null);
	}
}