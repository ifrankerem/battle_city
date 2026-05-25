package level;

import gui.GamePanel;
import tile.Tile;
import tile.Water;

import java.awt.Graphics;


public class Level {

	private Tile[][] tiles;

	public Level(Tile[][] tiles)
	{
		this.tiles = tiles;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void draw(Graphics g)
	{
		for(int row = 0; row < GamePanel.MAP_ROWS; row++)
		{
			for(int col = 0; col < GamePanel.MAP_COLS; col++)
			{
				tiles[row][col].draw(g);
			}	
		}
	}

	public void updateAnimation()
	{
		for(int row = 0; row < GamePanel.MAP_ROWS; row++)
		{
			for(int col = 0; col < GamePanel.MAP_COLS; col++)
			{
				tiles[row][col].updateAnimation();
			}	
		}
	}

}