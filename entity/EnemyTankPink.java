package entity;

import util.Direction;
import util.ImageLoader;

public class EnemyTankPink extends EnemyTankBase{

	public EnemyTankPink(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x, y, 3.2, dir, iLoader);
		this.health = 1;
		this.score = 300;
	}


	public void updateDraw()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(10, 8);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(10, 9);
				break;
			}
			case Direction.SOUTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(10, 12);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(10, 13);
				break;
			}
			case Direction.EAST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(10, 14);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(10, 15);
				break;
			}
			case Direction.WEST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(10, 10);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(10, 11);
				break;
			}
		}
	}
	
}