package tile;

import util.ImageLoader;
import java.awt.image.BufferedImage;


public class Water extends Tile {

	private BufferedImage frame1;
	private BufferedImage frame2;

	public Water(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.isPassable = false;
		this.name = "water";
		this.isDestroyable = false;
		this.frame1 = iLoader.getWaterSprite(3, 17);
		this.frame2 = iLoader.getWaterSprite(3, 16);
		this.image = this.frame1;
	}

	public void updateAnimation()
	{
		this.SpriteCounter++;
		if(this.SpriteCounter > 60)
		{
			if(this.SpriteNumber == 1) {
				this.SpriteNumber = 2;
				this.image = this.frame2;
			}
			else if(this.SpriteNumber == 2) {
				this.SpriteNumber = 1;
				this.image = this.frame1;
			}
			this.SpriteCounter = 0;
		}
	}
}