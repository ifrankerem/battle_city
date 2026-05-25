package tile;

import util.ImageLoader;

public class SteelWall extends Tile {


	private int health = 1;

	public SteelWall(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "steelwall";
		this.isPassable = false;
		this.isDestroyable = true; //TODO if player is 3 level !! 
		this.image = iLoader.getCellSprite(2, 32);
	}

}