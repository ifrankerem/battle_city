package level;

import gui.GamePanel;
import entity.Bullet;
import entity.PlayerTank;
import tile.Base;
import tile.BrickWall;
import tile.Bush;
import tile.EmptyTile;
import tile.GrayRoad;
import tile.SteelWall;
import tile.Tile;
import tile.Water;
import util.HitType;
import util.TileEnum;
import java.awt.Graphics;

public class Level {

	private Tile[][] tiles;
	private static final int[][] ENEMY_SPAWN_COORDS = {{0, 0}, {GamePanel.GAME_WIDTH / 2, 0}, {GamePanel.GAME_WIDTH - GamePanel.ENTITY_SIZE, 0}};
	private static final int[] PLAYER_SPAWN_COORDS = {220, 570};
	private HitType lastBulletHitType;
	int[][] baseWallPositions = {{23, 11}, {23, 12}, {23, 13}, {23, 14},{24, 11},{24, 14},{25, 11},{25, 14}};

	public Level(Tile[][] tiles)
	{
		this.tiles = tiles;
		this.lastBulletHitType = HitType.NONE;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Level copy()
	{
		Tile[][] copiedTiles = new Tile[GamePanel.MAP_ROWS][GamePanel.MAP_COLS];

		for(int row = 0; row < GamePanel.MAP_ROWS; row++)
		{
			for(int col = 0; col < GamePanel.MAP_COLS; col++)
			{
				copiedTiles[row][col] = copyTile(tiles[row][col]);
			}
		}

		return new Level(copiedTiles);
	}

	private Tile copyTile(Tile tile)
	{
		double x = tile.getX();
		double y = tile.getY();

		if(tile instanceof Base)
			return new Base(x, y, tile.getImageLoader());
		if(tile instanceof BrickWall)
			return new BrickWall(x, y, tile.getImageLoader());
		if(tile instanceof Bush)
			return new Bush(x, y, tile.getImageLoader());
		if(tile instanceof GrayRoad)
			return new GrayRoad(x, y, tile.getImageLoader());
		if(tile instanceof SteelWall)
			return new SteelWall(x, y, tile.getImageLoader());
		if(tile instanceof Water)
			return new Water(x, y, tile.getImageLoader());

		return new EmptyTile(x, y, tile.getImageLoader());
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

		for(int row = topRow; row <= bottomRow; row++)
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

	public boolean hitboxControlBullet(double nextX, double nextY, int width ,int height, Bullet bullet)
	{
		lastBulletHitType = HitType.NONE;
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
						Tile hitTile = tiles[row][col];
						lastBulletHitType = getBulletHitType(hitTile);
						hitTile.setHitted(true);
						if(canBulletDestroyTile(hitTile, bullet))
							tiles[row][col] = new EmptyTile(hitTile.getX(), hitTile.getY(), hitTile.getImageLoader());
						return false;
					}					
				}
		}
		return true;
	}

	private boolean canBulletDestroyTile(Tile tile, Bullet bullet)
	{
		if(tile instanceof SteelWall)
			return tile.getDestroyable() && bullet.getOwnerTank() instanceof PlayerTank;

		return tile.getDestroyable();
	}

	private HitType getBulletHitType(Tile tile)
	{
		if(tile instanceof BrickWall)
			return HitType.BRICK;
		if(tile instanceof SteelWall)
			return HitType.STEEL;

		return HitType.NONE;
	}

	public HitType getLastBulletHitType()
	{
		return lastBulletHitType;
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

	public boolean isInsideMap(int row, int col)
	{
		if(row < 0 || row >= GamePanel.MAP_ROWS || col < 0 || col >= GamePanel.MAP_COLS)
			return false;

		if((row == 24 || row == 25) && (col == 12 || col == 13)) // base is always in these
			return false;

		for(int i = 0; i < ENEMY_SPAWN_COORDS.length; i++)
		{
			if(isSpawnCell(row, col, ENEMY_SPAWN_COORDS[i][0], ENEMY_SPAWN_COORDS[i][1]))
				return false;
		}

		if(isSpawnCell(row, col, PLAYER_SPAWN_COORDS[0], PLAYER_SPAWN_COORDS[1]))
			return false;

		return true;
	}

	private boolean isSpawnCell(int row, int col, int spawnX, int spawnY)
	{
		int spawnCol = spawnX / GamePanel.MAP_CELL_SIZE;
		int spawnRow = spawnY / GamePanel.MAP_CELL_SIZE;
		int spawnCellSize = (int)Math.ceil((double)GamePanel.ENTITY_SIZE / GamePanel.MAP_CELL_SIZE);
		return row >= spawnRow && row < spawnRow + spawnCellSize && col >= spawnCol && col < spawnCol + spawnCellSize;
	}

	public void setTile(int row, int col, TileEnum tile)
	{
		int x = col * GamePanel.MAP_CELL_SIZE;
		int y = row * GamePanel.MAP_CELL_SIZE;

		switch (tile) {
			case EMPTY:
			{
				this.tiles[row][col] = new EmptyTile(x, y, tiles[row][col].getImageLoader());
				break;
			}
			case BRICK:
			{
				this.tiles[row][col] = new BrickWall(x, y, tiles[row][col].getImageLoader());
				break;
			}
			case STEEL:
			{
				this.tiles[row][col] = new SteelWall(x, y, tiles[row][col].getImageLoader());
				break;
			}
			case BUSH:
			{
				this.tiles[row][col] = new Bush(x, y, tiles[row][col].getImageLoader());
				break;
			}
			case WATER:
			{
				this.tiles[row][col] = new Water(x, y, tiles[row][col].getImageLoader());
				break;
			}
			case GRAYROAD:
			{
				this.tiles[row][col] = new GrayRoad(x, y, tiles[row][col].getImageLoader());
				break;
			}
		}
	}
}
