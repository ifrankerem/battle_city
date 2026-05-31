package tile;

import util.ImageLoader;

public class SteelWall extends Tile {

	public SteelWall(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "steelwall";
		this.isPassable = false;
		this.isDestroyable = false;
		this.image = iLoader.getCellSprite(2, 32);
	}

}