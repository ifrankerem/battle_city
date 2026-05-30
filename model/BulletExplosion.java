package model;

import java.awt.image.BufferedImage;
import gui.GamePanel;
import util.ImageLoader;

public class BulletExplosion extends Explosion {

	public BulletExplosion(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frames = new BufferedImage[3];
		this.frames[0] = iLoader.getBoom1();
		this.frames[1] = iLoader.getBoom2();
		this.frames[2] = iLoader.getBoom3();
		this.drawX = (int)Math.round(x) - (GamePanel.ENTITY_SIZE - GamePanel.BULLET_SIZE) / 2;
		this.drawY = (int)Math.round(y) - (GamePanel.ENTITY_SIZE - GamePanel.BULLET_SIZE) / 2;
		this.size = GamePanel.ENTITY_SIZE;
	}

}
