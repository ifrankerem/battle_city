package model;

import java.awt.image.BufferedImage;
import java.awt.Graphics;


import gui.GamePanel;
import util.ImageLoader;

public class OneHundred extends Explosion{

	protected BufferedImage frame;

	public OneHundred(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frame = iLoader.getOneHundred();
		this.FRAME_DELAY = 45;
	}

	public void draw(Graphics g)
	{
		if(!active)
			return;
		int drawX = (int)Math.round(x);
		int drawY = (int)Math.round(y);
		g.drawImage(frame, drawX, drawY, GamePanel.ENTITY_SIZE, GamePanel.ENTITY_SIZE, null);

	}

}