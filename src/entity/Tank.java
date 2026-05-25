package entity;

import model.GameObject;
import util.ImageLoader;
import java.awt.Graphics;
import gui.GamePanel;


import model.Direction;

public class Tank extends GameObject{

	protected double speed;
	protected Direction dir;
	protected int health;

	public Tank(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x,y,iLoader);
		this.dir = dir;
		this.speed = speed;
	}

	//TODO Will update for enemy sprite numbers!!
	public void updateDraw()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 0);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 1);
				break;
			}
			case Direction.SOUTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 4);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 5);
				break;
			}
			case Direction.EAST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 6);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 7);
				break;
			}
			case Direction.WEST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 2);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 3);
				break;
			}
	
		}
	}

	public void draw(Graphics g)
	{
		this.updateDraw();
		int drawX = (int)Math.round(this.x);
		int drawY = (int)Math.round(this.y);
		g.drawImage(this.getImage(),drawX,drawY,GamePanel.ENTITY_SIZE,GamePanel.ENTITY_SIZE,null);
	}
	
}