package level;

import gui.GamePanel;
import tile.Base;
import tile.BrickWall;
import tile.Bush;
import tile.EmptyTile;
import tile.SteelWall;
import tile.Tile;
import tile.Water;

import java.awt.Graphics;


public class Level {

	private Tile[][] tiles;
	int[][] baseWallPositions = {{23, 11}, {23, 12}, {23, 13}, {23, 14},{24, 11},{24, 14},{25, 11},{25, 14}};

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

	public void updateSteel()
	{
		for(int row = 0; row < GamePanel.MAP_ROWS; row++)
		{
			for(int col = 0; col < GamePanel.MAP_COLS; col++)
			{
				if(tiles[row][col] instanceof SteelWall)
					tiles[row][col].setDestroyable(true);
			}	
		}
	}

	public void baseProtection(boolean value)
	{
		if(value == true)
		{
			for(int i = 0; i < 8 ; i++)
			{
				int row = baseWallPositions[i][0];
				int col = baseWallPositions[i][1];
				tiles[row][col] = new SteelWall(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, tiles[row][col].getImageLoader()); 
			}
		}
		else 
		{
			for(int i = 0; i < 8 ; i++)
			{
				int row = baseWallPositions[i][0];
				int col = baseWallPositions[i][1];
				tiles[row][col] = new BrickWall(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, tiles[row][col].getImageLoader()); 
			}
		}
	}
}
