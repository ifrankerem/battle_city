package tile;

import util.ImageLoader;

public class EmptyTile extends Tile {

	public EmptyTile(double x, double y, ImageLoader iLoader)
	{
		super(x, y, iLoader);
		this.name = "empty";
		this.isPassable = true;
		this.isDestroyable = false;
		this.image = iLoader.getSprite(4, 20);
	}

}