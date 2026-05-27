package model;

import java.awt.image.BufferedImage;
import java.awt.Graphics;


import gui.GamePanel;
import util.ImageLoader;

public class TankExplosion extends Explosion{

	public TankExplosion(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frames = new BufferedImage[2];
		this.frames[0] = iLoader.getTankboom1();
		this.frames[1] = iLoader.getTankboom2();
		this.FRAME_DELAY = 30;
	}

	public void draw(Graphics g)
	{
		if(!active)
			return;
		int drawX = (int)Math.round(x);
		int drawY = (int)Math.round(y);
		g.drawImage(frames[frameIndex], drawX - GamePanel.ENTITY_SIZE / 2, drawY - GamePanel.ENTITY_SIZE / 2, GamePanel.ENTITY_SIZE * 2, GamePanel.ENTITY_SIZE * 2, null);
	}

}