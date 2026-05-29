package entity;

import model.GameObject;
import util.Direction;
import util.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import level.Level;
import java.util.ArrayList;
import gui.GamePanel;

abstract public class Tank extends GameObject{

	protected double speed;
	protected Direction dir;
	protected int health;
	protected Bullet bullet;
	protected int maxBulletCount;
	protected int bulletCount;
	protected int bulletSpeedBonus;
	protected boolean isSpawn;
	protected boolean isAlive;

	public Tank(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x,y,iLoader);
		this.dir = dir;
		this.speed = speed;
		this.maxBulletCount = 1;
		this.bulletCount = 0;
		this.bulletSpeedBonus = 0;
		this.isSpawn = false;
		this.isAlive = true;
	}

	abstract public void updateDraw();
	abstract public void updateMovement(Level currentLevel,ArrayList <Bullet> bullets);

	public void draw(Graphics g)
	{
		if(this.isAlive == true)
		{
		this.updateDraw();
		int drawX = (int)Math.round(this.x);
		int drawY = (int)Math.round(this.y);
		g.drawImage(this.getImage(),drawX,drawY,GamePanel.ENTITY_SIZE,GamePanel.ENTITY_SIZE,null);
		//this.drawHitbox(g);
		}

	}

	public int countBullets(ArrayList <Bullet> bullets)
	{
		this.bulletCount = 0;
		for(Bullet curr : bullets)
		{
			if(curr.getOwnerTank() == this) // every tank should update its own
				this.bulletCount++;
		}
		return this.bulletCount;
	}

	public void initHitbox()
	{
		this.hitbox = new Rectangle((int)this.x,(int)this.y,GamePanel.ENTITY_SIZE, GamePanel.ENTITY_SIZE);
	}

	public void drawHitbox(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
	
	public void updateHitbox()
	{
		hitbox.x = (int)this.x;
		hitbox.y = (int)this.y;
	}

	public void shoot()
	{
		this.bullet = new Bullet(this.x + 13, this.y + 13, iLoader, this);
		if(this.bulletSpeedBonus > 0)
		this.bullet.incrementSpeed(this.bulletSpeedBonus);
	}


	public Bullet getBullet() {
		return bullet;
	}

	public int getMaxBulletCount() {
		return maxBulletCount;
	}

	public boolean getSpawn()
	{
		return isSpawn;
	}

	public void setSpawn(boolean value)
	{
		this.isSpawn = value;
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}

	public void getDamage()
	{
		this.health--;
		if(this.health == 0)
			this.isAlive = false;
	}

	public boolean get_isAlive()
	{
		return isAlive;
	}

	public int getHealth() {
		return health;
	}

	public void incrementBulletSpeed(int value)
	{
		this.bulletSpeedBonus += value;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

}
