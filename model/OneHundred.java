package model;

import util.ImageLoader;

public class OneHundred extends HundredsBase{

	public OneHundred(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frame = iLoader.getOneHundred();
	}

}
