package powerup;

import engine.GameEngine;
import util.ImageLoader;

public class ShovelPowerUp extends TimedPowerUps {

	public ShovelPowerUp(double x, double y , ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.effectDuration = 3600; // 1 mins
		this.frame = iLoader.getEntitySprite(7, 18);
	}

	public void action(GameEngine engine)
	{
		engine.getCurrentLevel().baseProtection(true);
	}

	public void expireEffect(GameEngine engine)
	{
		engine.getCurrentLevel().baseProtection(false);
	}


}
