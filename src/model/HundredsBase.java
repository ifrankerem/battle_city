package model;

import util.ImageLoader;
import java.awt.Graphics;

public class HundredsBase extends Model{

	public HundredsBase(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.FRAME_DELAY = 8;
	}

	public void initHitbox()
	{
		//just visual no hitboxes
	}

	public void drawHitbox(Graphics g)
	{
		//just visual no hitboxes
	}

}