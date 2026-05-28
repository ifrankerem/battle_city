package model;

import java.awt.image.BufferedImage;
import engine.GameEngine;
import gui.GamePanel;
import util.ImageLoader;

public class InvincibleModel extends AnimatedModel {

	private GameEngine engine;

	public InvincibleModel(double x, double y, ImageLoader iLoader,GameEngine engine)
	{
		super(x, y, iLoader);
		this.engine = engine;
		this.frames = new BufferedImage[2];
		this.frames[0] = iLoader.getEntitySprite2(9, 16);
		this.frames[1] = iLoader.getEntitySprite2(9, 17);
		this.FRAME_DELAY = 2;
		this.drawX = (int)Math.round(x) - GamePanel.ENTITY_SIZE / 2;
		this.drawY = (int)Math.round(y) - GamePanel.ENTITY_SIZE / 2;
		this.size = GamePanel.ENTITY_SIZE;
	}

	public void update()
	{
		this.drawX = (int)engine.getPlayer().getX();
		this.drawY = (int)engine.getPlayer().getY();
		if(!active)
			return;

		frameCounter++;
		if(frameCounter >= FRAME_DELAY)
		{
			frameCounter = 0;
			frameIndex = (frameIndex + 1) % frames.length; //loop till removing from the list 1->0->1->0
		}
	}

}
