package powerup;

import engine.GameEngine;
import util.ImageLoader;

abstract public class TimedPowerUps extends PowerUp{

	protected int effectFrameCounter;
	protected int effectDuration;

	public TimedPowerUps(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.effectFrameCounter = 0;
		this.effectDuration = 1800;
	}

	public void activateEffect(GameEngine engine)
	{
		this.effectFrameCounter = 0;
		this.action(engine);
	}

	public boolean updateEffect(GameEngine engine)
	{
		effectFrameCounter++;
		if(effectFrameCounter >= effectDuration)
		{
			this.expireEffect(engine);
			return true;
		}
		return false;
	}

	abstract public void expireEffect(GameEngine engine);

}
