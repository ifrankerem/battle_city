package tile;

import util.ImageLoader;

public class Bush extends Tile {

	public Bush(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "bush";
		this.isPassable = true;
		this.isDestroyable = false;
		this.image = iLoader.getbush();
	}

}