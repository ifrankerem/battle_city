package powerup;

import engine.GameEngine;
import util.ImageLoader;

public class ShieldPowerUp extends TimedPowerUps {

	public ShieldPowerUp(double x, double y,ImageLoader iLoader)
	{
		super(x, y, iLoader);
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