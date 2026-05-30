package model;

import util.ImageLoader;

public class Explosion extends AnimatedModel {

	public Explosion(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.FRAME_DELAY = 8;
	}

}
