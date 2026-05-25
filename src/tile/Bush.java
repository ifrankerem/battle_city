package tile;

import util.ImageLoader;

public class Bush extends Tile {

	public Bush(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.isPassable = true;
		this.isDestroyable = false;
		this.image = iLoader.getCellSprite(4, 34);
	}


	public void onHit()
	{

	}
}