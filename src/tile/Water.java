package tile;

import util.ImageLoader;


public class Water extends Tile {

	public Water(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.isPassable = false;
		this.name = "water";
		this.isDestroyable = false;
	}

	public void updateAnimation()
	{
		this.SpriteCounter++;
		if(this.SpriteCounter > 60)
		{
			if(this.SpriteNumber == 1)
				this.SpriteNumber = 2;
			else if(this.SpriteNumber == 2)
				this.SpriteNumber = 1;
			this.SpriteCounter = 0;
		}
		if(this.SpriteNumber == 1)
			this.image = iLoader.getEntitySprite(3, 17);
		else if(this.SpriteNumber == 2)
			this.image = iLoader.getEntitySprite(3,16);
	}
}