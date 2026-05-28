package model;

import util.ImageLoader;

public class TwoHundred extends HundredsBase{

	public TwoHundred(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.frame = iLoader.getTwoHundred();
	}


}
