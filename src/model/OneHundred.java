package model;

import gui.GamePanel;
import util.ImageLoader;

public class OneHundred extends Model{

	public OneHundred(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frame = iLoader.getOneHundred();
		this.FRAME_DELAY = 45;
		this.size = GamePanel.ENTITY_SIZE;
		this.drawX = (int)Math.round(x);
		this.drawY = (int)Math.round(y);
	}

}