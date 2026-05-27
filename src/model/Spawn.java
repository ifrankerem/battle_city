package model;

import java.awt.image.BufferedImage;
import java.awt.Graphics;


import gui.GamePanel;
import util.ImageLoader;

public class Spawn extends Explosion{

	public Spawn(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frames = new BufferedImage[4];
		this.frames[0] = iLoader.getEntitySprite2(6, 17);
		this.frames[1] = iLoader.getEntitySprite2(6, 18);
		this.frames[2] = iLoader.getEntitySprite2(6, 19);
		this.frames[3] = iLoader.getEntitySprite2(6, 20);
		this.FRAME_DELAY = 30;
	}



	public void draw(Graphics g)
	{
		if(!active)
			return;
		int drawX = (int)Math.round(x);
		int drawY = (int)Math.round(y);
		g.drawImage(frames[frameIndex], drawX, drawY, GamePanel.ENTITY_SIZE + 10, GamePanel.ENTITY_SIZE + 10, null);
	}

}