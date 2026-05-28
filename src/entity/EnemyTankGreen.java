package entity;

import util.Direction;
import util.ImageLoader;

public class EnemyTankGreen extends EnemyTankBase{

	public EnemyTankGreen(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x, y, speed, dir, iLoader);
		this.health = 1;
		this.score = 200;
	}


	public void updateDraw()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(11, 0);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(11, 1);
				break;
			}
			case Direction.SOUTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(11, 4);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(11, 5);
				break;
			}
			case Direction.EAST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(11, 6);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(11, 7);
				break;
			}
			case Direction.WEST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(11, 2);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(11, 3);
				break;
			}
		}
	}
	
}