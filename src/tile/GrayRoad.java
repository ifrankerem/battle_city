package tile;

import util.ImageLoader;

public class GrayRoad extends Tile {

	public GrayRoad(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "gray";
		this.isPassable = true;
		this.isDestroyable = false;
		this.image = iLoader.getCellSprite(4, 36);
	}

}