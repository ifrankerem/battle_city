package powerup;

import engine.GameEngine;
import util.ImageLoader;

public class ShieldPowerUp extends TimedPowerUps {

	public ShieldPowerUp(double x, double y,ImageLoader iLoader,int time)
	{
		super(x, y, iLoader);
		this.effectDuration = time;
		this.frame = iLoader.getEntitySprite(7, 21);
	}

	public void action(GameEngine engine)
	{
		engine.setInvincible(true);
	}

	public void expireEffect(GameEngine engine)
	{
		engine.setInvincible(false);
	}
}