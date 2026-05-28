package entity;

import model.GameObject;
import util.Direction;
import util.ImageLoader;
import java.awt.Graphics;
import java.awt.Color;
import gui.GamePanel;

import java.awt.Rectangle;
import level.Level;


public class Bullet extends GameObject {


	private Direction dir;
	private int speed;
	private Tank ownerTank;
	private boolean active;
	
	public Bullet(double x, double y, ImageLoader iLoader, Tank ownerTank)
	{
		super(x, y, iLoader);
		this.ownerTank = ownerTank;
		this.dir = ownerTank.dir;
		this.active = true;
		this.speed = 7; //TODO speed will update when bonus added
		this.initImage();
	}

	public void initImage()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				this.image = iLoader.getBullet_north();
				break;
			}
			case Direction.SOUTH:
			{
				this.image = iLoader.getBullet_south();
				break;
			}
			case Direction.EAST:
			{
				this.image = iLoader.getBullet_east();
				break;
			}
			case Direction.WEST:
			{
				this.image = iLoader.getBullet_west();
				break;
			}
		}
	}

	public void updateMovement(Level currentLevel)
	{
		double nextX = x;
		double nextY = y;
		if(this.dir == Direction.NORTH)
		{
			nextY -= speed;
		}
		else if(this.dir == Direction.EAST)
			nextX += speed;
		else if(this.dir == Direction.WEST)
			nextX -= speed;
		else if(this.dir == Direction.SOUTH)
			nextY += this.speed;
		if(nextY >= 0 && (nextY <= GamePanel.GAME_HEIGHT - this.hitbox.height) && (nextX >= 0 && (nextX <= GamePanel.GAME_WIDTH - this.hitbox.width)) && currentLevel.hitboxControlBullet(nextX, nextY, this.hitbox.width, this.hitbox.height))
		{
			this.y = nextY;
			this.x = nextX;
		}
		else
			this.active = false;
	}

	public void draw(Graphics g)
	{
		if(!this.active)
			return;

		int drawX = (int)Math.round(this.x);
		int drawY = (int)Math.round(this.y);

		this.updateHitbox();
		g.drawImage(this.getImage(),drawX,drawY,GamePanel.BULLET_SIZE,GamePanel.BULLET_SIZE,null);
		//this.drawHitbox(g);
	}

	public void updateHitbox()
	{
		hitbox.x = (int)this.x;
		hitbox.y = (int)this.y;
	}

	public void initHitbox()
	{
		this.hitbox = new Rectangle((int)this.x,(int)this.y,GamePanel.BULLET_SIZE, GamePanel.BULLET_SIZE);
	}


	public void drawHitbox(Graphics g)
	{
		g.setColor(Color.PINK);
		g.drawRect(hitbox.x , hitbox.y, hitbox.width, hitbox.height);
	}

	public Tank getOwnerTank() {
		return ownerTank;
	}

	public boolean getActive()
	{
		return active;
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}

	public void setActive(boolean value)
	{
		this.active = value;
	}
}