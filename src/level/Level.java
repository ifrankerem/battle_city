package level;

import gui.GamePanel;
import tile.Base;
import tile.Bush;
import tile.EmptyTile;
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
				if(!(tiles[row][col] instanceof Bush))
					tiles[row][col].draw(g);
			}	
		}
	}

	public void topdraw(Graphics g)
	{
		for(int row = 0; row < GamePanel.MAP_ROWS; row++)
		{
			for(int col = 0; col < GamePanel.MAP_COLS; col++)
			{
				if((tiles[row][col] instanceof Bush))
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
				if(tiles[row][col] instanceof Water)
					tiles[row][col].updateAnimation();
			}	
		}
	}

	public boolean hitboxControl(double nextX, double nextY, int width ,int height)
	{
		int leftCol = (int)nextX / GamePanel.MAP_CELL_SIZE;
		int rightCol = ((int)nextX + width - 1) / GamePanel.MAP_CELL_SIZE;
		int topRow = (int)nextY / GamePanel.MAP_CELL_SIZE;
		int bottomRow = ((int)nextY + height -1) / GamePanel.MAP_CELL_SIZE;
		// division for taking (x,y) cordinates
		//System.out.println("leftCol: " + leftCol + " rightCol: " + rightCol + " topRow: " + topRow + " bottomRow: " + bottomRow);

		for(int row = topRow; row <= bottomRow ; row++)
		{
			for(int col = leftCol; col <= rightCol; col++)
			{
				// System.out.println("row:" + row + " col: " + col);
				// System.out.println(tiles[row][col]);
				if(!tiles[row][col].getisPassable())
					return false;					
			}
		}
		return true;
	}



	public boolean hitboxControlBullet(double nextX, double nextY, int width ,int height)
	{
		int leftCol = (int)nextX / GamePanel.MAP_CELL_SIZE;
		int rightCol = ((int)nextX + width - 1) / GamePanel.MAP_CELL_SIZE;
		int topRow = (int)nextY / GamePanel.MAP_CELL_SIZE;
		int bottomRow = ((int)nextY + height -1) / GamePanel.MAP_CELL_SIZE;
		// division for taking (x,y) cordinates
		//System.out.println("leftCol: " + leftCol + " rightCol: " + rightCol + " topRow: " + topRow + " bottomRow: " + bottomRow);

		for(int row = topRow; row <= bottomRow ; row++)
		{
			for(int col = leftCol; col <= rightCol; col++)
			{
				// System.out.println("row:" + row + " col: " + col);
				// System.out.println(tiles[row][col]);
				if(!tiles[row][col].getisPassable())
					{
						tiles[row][col].setHitted(true);
						if(tiles[row][col].getDestroyable() == true)
							tiles[row][col] = new EmptyTile(tiles[row][col].getX(),tiles[row][col].getY(),tiles[row][col].getImageLoader());
						return false;
					}					
			}
		}
		return true;
	}

	public boolean isbaseDestroyed()
	{
		for(int row = 0; row < GamePanel.MAP_ROWS; row++)
		{
			for(int col = 0; col < GamePanel.MAP_COLS; col++)
			{
				if(tiles[row][col] instanceof Base)
				{
					if(tiles[row][col].getisHitted() == true)
					{
						return true;
					}
						
				}
			}	
		}
		return false;
	}
}
