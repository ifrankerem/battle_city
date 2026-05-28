package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gui.GamePanel;
import util.ImageLoader;

public class AnimatedModel extends GameObject {

	protected BufferedImage[] frames;
	protected int frameIndex;
	protected int frameCounter;
	protected boolean active;
	protected int FRAME_DELAY = 8;
	protected int size;
	protected int drawX;
	protected int drawY;

	public AnimatedModel(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frames = new BufferedImage[3];
		this.frames[0] = iLoader.getBoom1();
		this.frames[1] = iLoader.getBoom2();
		this.frames[2] = iLoader.getBoom3();
	}

	public void update()
	{
		if(!active)
			return;

		frameCounter++;
		if(frameCounter >= FRAME_DELAY)
		{
			frameCounter = 0;
			frameIndex++;
			if(frameIndex >= frames.length)
			{
				active = false;
			}
		}
	}

	public void draw(Graphics g)
	{
		if(!active)
			return;
		g.drawImage(this.frames[frameIndex], this.drawX, this.drawY, this.size, this.size, null);
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
