package powerup;

import engine.GameEngine;
import util.ImageLoader;


public class StarPowerUp extends PowerUp{

	public StarPowerUp(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.frame = iLoader.getEntitySprite(7, 19);
	}


	public void action(GameEngine engine)
	{
		int counter = engine.incrementStarPowerCounter();
		if(counter == 1)
			engine.getPlayer().incrementBulletSpeed(2);
		else if(counter == 2)
			engine.getPlayer().incrementMaxBullets();
		else if(counter == 3)
			engine.getCurrentLevel().updateSteel();
	}
}
