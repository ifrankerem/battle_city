package entity;

import model.Direction;
import util.ImageLoader;

public class EnemyTankWhite extends EnemyTankBase{

	public EnemyTankWhite(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x, y, speed, dir, iLoader);
		this.health = 3;
	}


	public void updateDraw()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(3, 8);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(3, 9);
				break;
			}
			case Direction.SOUTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(3, 12);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(3, 13);
				break;
			}
			case Direction.EAST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(3, 14);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(3, 15);
				break;
			}
			case Direction.WEST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getEntitySprite2(3, 10);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getEntitySprite2(3, 11);
				break;
			}
		}
	}
	
}