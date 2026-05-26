package entity;

import model.Direction;
import util.ImageLoader;

public class EnemyTankWhite extends EnemyTankBase{

	public EnemyTankWhite(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x, y, speed, dir, iLoader);
		this.health = 1;
	}


	public void updateDraw()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 0);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 1);
				break;
			}
			case Direction.SOUTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 4);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 5);
				break;
			}
			case Direction.EAST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 6);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 7);
				break;
			}
			case Direction.WEST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 2);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 3);
				break;
			}
		}
	}
	
}