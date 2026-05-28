package powerup;

import gui.GamePanel;
import model.Model;
import util.ImageLoader;
import java.awt.Graphics;

public class PowerUp extends Model{


	public PowerUp(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
	}

	public void draw(Graphics g)
	{
		if(!active)
			return;
		int drawX = (int)Math.round(x) - (GamePanel.ENTITY_SIZE - GamePanel.BULLET_SIZE) / 2;
		int drawY = (int)Math.round(y) - (GamePanel.ENTITY_SIZE - GamePanel.BULLET_SIZE) / 2;
		g.drawImage(frame, drawX, drawY, GamePanel.ENTITY_SIZE, GamePanel.ENTITY_SIZE, null);
	}

}