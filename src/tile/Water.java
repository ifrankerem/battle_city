package tile;

import util.ImageLoader;

public class Water extends Tile {

	public Water(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.isPassable = false;
		this.isDestroyable = false;
		this.image = iLoader.getCellSprite(6, 34);
	}


	public void onHit()
	{

	}
}