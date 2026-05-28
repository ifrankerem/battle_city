package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.ImageLoader;

public class Model extends GameObject {

	protected BufferedImage frame;
	protected int frameIndex;
	protected int frameCounter;
	protected boolean active;
	protected int FRAME_DELAY = 8;
	protected int size;
	protected int drawX;
	protected int drawY;

	public Model(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
	}

	public void update()
	{
		if(!active)
			return;

		frameCounter++;
		if(frameCounter >= FRAME_DELAY)
		{
			active = false;
		}
	}

	public void draw(Graphics g)
	{
		if(!active)
			return;
		g.drawImage(this.frame, this.drawX, this.drawY, this.size, this.size, null);
	}

	public boolean isActive()
	{
		return active;
	}

	public void initHitbox()
	{
		//this.hitbox = new Rectangle(0, 0, 0, 0);
	}

	public void drawHitbox(Graphics g)
	{
		// Model has no hitbox
	}
}
