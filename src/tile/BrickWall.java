package tile;

import util.ImageLoader;

public class BrickWall extends Tile {

	private int health;

	public BrickWall(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "brick";
		this.isPassable = false;
		this.isDestroyable = true;
		this.health = 1;
		this.image = iLoader.getCellSprite(0, 32);
	}

}