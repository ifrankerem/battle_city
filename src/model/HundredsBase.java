package model;

import gui.GamePanel;
import util.ImageLoader;
import java.awt.Graphics;

public class HundredsBase extends Model{

	public HundredsBase(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.FRAME_DELAY = 120;
		this.size = GamePanel.ENTITY_SIZE;
		this.drawX = (int)Math.round(x);
		this.drawY = (int)Math.round(y);
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
