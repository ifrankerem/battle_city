package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.ImageLoader;
import gui.GamePanel;
import model.GameObject;
import java.awt.Rectangle;

public class Explosion extends GameObject {

	private BufferedImage[] frames;
	private int frameIndex;
	private int frameCounter;
	private boolean active;
	private static final int FRAME_DELAY = 8;

	public Explosion(double x, double y, ImageLoader iLoader)
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

		int drawX = (int)Math.round(x) - (GamePanel.ENTITY_SIZE - GamePanel.BULLET_SIZE) / 2;
		int drawY = (int)Math.round(y) - (GamePanel.ENTITY_SIZE - GamePanel.BULLET_SIZE) / 2;
		g.drawImage(frames[frameIndex], drawX, drawY, GamePanel.ENTITY_SIZE, GamePanel.ENTITY_SIZE, null);
	}

	public boolean isActive()
	{
		return active;
	}

	public void initHitbox()
	{
		this.hitbox = new Rectangle(0, 0, 0, 0);
	}

	public void drawHitbox(Graphics g)
	{
		// Explosion has no hitbox
	}
}
