package model;

import util.ImageLoader;

public class FiveHundred extends OneHundred{

	public FiveHundred(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frame = iLoader.getFiveHundred();
		this.FRAME_DELAY = 30;
	}

}

