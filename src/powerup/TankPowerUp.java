package powerup;


import engine.GameEngine;
import util.ImageLoader;

public class TankPowerUp extends PowerUp{

	public TankPowerUp(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.frame = iLoader.getEntitySprite(7, 16);
	}

	public void action(GameEngine engine)
	{
		engine.incrementPlayerMaxHealth();
	}
}