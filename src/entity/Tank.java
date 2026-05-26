package entity;

import model.GameObject;
import util.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import level.Level;
import java.util.ArrayList;

import engine.GameEngine;
import gui.GamePanel;


import model.Direction;

abstract public class Tank extends GameObject{

	protected double speed;
	protected Direction dir;
	protected int health;
	protected Bullet bullet;
	protected int maxBulletCount;
	protected GameEngine engine;

	public Tank(double x, double y, double speed, Direction dir,ImageLoader iLoader,GameEngine engine)
	{
		super(x,y,iLoader);
		this.dir = dir;
		this.speed = speed;
		this.maxBulletCount = 1; //TODO if player gets bonux it will be 2
		this.engine = engine;
	}

	abstract public void updateDraw();
	abstract public void updateMovement(Level currentLevel,ArrayList <Bullet> bullets);

	public void draw(Graphics g)
	{
		this.updateDraw();
		this.updateHitbox();
		int drawX = (int)Math.round(this.x);
		int drawY = (int)Math.round(this.y);
		g.drawImage(this.getImage(),drawX,drawY,GamePanel.ENTITY_SIZE,GamePanel.ENTITY_SIZE,null);
		//this.drawHitbox(g);
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
	}


	public Bullet getBullet() {
		return bullet;
	}

	public int getMaxBulletCount() {
		return maxBulletCount;
	}

}