package tile;

import java.awt.Graphics;
import java.awt.Rectangle;
import gui.GamePanel;
import model.GameObject;
import util.ImageLoader;
import java.awt.Color;


abstract public class Tile extends GameObject {

	protected boolean isPassable;
	protected boolean isDestroyable;
	protected String name;
	
	public Tile(double x, double y, ImageLoader iLoader)
	{
		super(x,y,iLoader);
	}


	public void draw(Graphics g)
	{
		int drawX = (int)Math.round(this.x);
		int drawY = (int)Math.round(this.y);
		g.drawImage(this.getImage(),drawX,drawY,GamePanel.MAP_CELL_SIZE,GamePanel.MAP_CELL_SIZE,null);
		//this.drawHitbox(g);
	}

	public void updateAnimation()
	{
		//This for water
	}

	public void initHitbox()
	{
		this.hitbox = new Rectangle((int)this.x,(int)this.y,GamePanel.MAP_CELL_SIZE, GamePanel.MAP_CELL_SIZE);
	}

	public void drawHitbox(Graphics g)
	{
		g.setColor(Color.PINK);
		g.drawRect(hitbox.x , hitbox.y, hitbox.width, hitbox.height);
	}

	public boolean getisPassable()
	{
		return isPassable;
	}

	public String toString()
	{
		return this.name;
	}
	
}