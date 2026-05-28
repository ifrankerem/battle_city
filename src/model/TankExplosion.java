package model;

import java.awt.image.BufferedImage;
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
		this.drawX = (int)Math.round(x) - GamePanel.ENTITY_SIZE / 2;
		this.drawY = (int)Math.round(y) - GamePanel.ENTITY_SIZE / 2;
		this.size = GamePanel.ENTITY_SIZE *2;
	}

}