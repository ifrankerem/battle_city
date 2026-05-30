package powerup;

import java.util.Iterator;

import engine.GameEngine;
import entity.EnemyTankBase;
import util.ImageLoader;

public class BombPowerUp extends PowerUp{


	public BombPowerUp(double x, double y,ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.frame = iLoader.getEntitySprite(7, 20);
	}

	public void action(GameEngine engine)
	{
		Iterator<EnemyTankBase> it_t = engine.getEnemytanks().iterator();
		while(it_t.hasNext())
		{
			EnemyTankBase enemy = it_t.next();
			if(enemy.get_isAlive())
			{
				enemy.setAlive(false);
				engine.incrementScore(enemy.getScore());
				engine.decrementActiveEnemyCount();
			}
		}

	}
}
