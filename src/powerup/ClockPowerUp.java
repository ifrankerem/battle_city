package powerup;

import engine.GameEngine;
import util.ImageLoader;

public class ClockPowerUp extends TimedPowerUps{


	public ClockPowerUp(double x, double y,ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.effectDuration = 900; // 15 sec
		this.frame = iLoader.getEntitySprite(7, 17);
	}

	public void action(GameEngine engine)
	{
		engine.setFrozen(true);
	}

	public void expireEffect(GameEngine engine)
	{
		engine.setFrozen(false);
	}
}
