package powerup;

import gui.GamePanel;
import model.Model;
import util.ImageLoader;
import java.awt.Rectangle;
import engine.GameEngine;
import java.awt.Color;
import java.awt.Graphics;


abstract public class PowerUp extends Model{

	protected Rectangle hitbox; 

	public PowerUp(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.active = true;
		this.frameIndex = 0;
		this.frameCounter = 0;
		this.FRAME_DELAY = 1800;
		this.size = GamePanel.ENTITY_SIZE;
		this.drawX = (int)Math.round(x);
		this.drawY = (int)Math.round(y);
		this.initHitbox();
	}

	public void initHitbox()
	{
		this.hitbox = new Rectangle((int)this.x,(int)this.y,GamePanel.ENTITY_SIZE , GamePanel.ENTITY_SIZE);
	}

	public void drawHitbox(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}

	public Rectangle getHitbox()
	{
		return hitbox;
	}

	public boolean updateEffect(GameEngine engine)
	{
		//this is for timed effects
		return false;
	}

	abstract public void action(GameEngine engine);

}