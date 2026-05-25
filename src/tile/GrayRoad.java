package tile;

import util.ImageLoader;

public class GrayRoad extends Tile {

	public GrayRoad(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.isPassable = true;
		this.isDestroyable = false;
		this.image = iLoader.getSprite(3, 19);
	}


	public void onHit()
	{

	}
}