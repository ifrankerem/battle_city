package level;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import gui.GamePanel;
import tile.Base;
import tile.BrickWall;
import tile.Bush;
import tile.EmptyTile;
import tile.GrayRoad;
import tile.SteelWall;
import tile.Tile;
import tile.Water;
import util.ImageLoader;

public class LevelLoader {

	private Level levels[];
	private ImageLoader iLoader;

	public LevelLoader(ImageLoader iLoader)
	{
		this.iLoader = iLoader;
		this.levels = new Level[36];
	}	

	private String buildStagePath(int stageNumber) {
   		return String.format("/resources/levels_txt/stage_%02d.txt", stageNumber);
	}

	public void loadMaps()
	{
		try{
			for(int i = 0; i < 36 ; i++)
			{
				InputStream txStream = getClass().getResourceAsStream(buildStagePath(i));
				BufferedReader reader = new BufferedReader(new InputStreamReader(txStream));
				Tile tile[][] = new Tile[GamePanel.MAP_ROWS][GamePanel.MAP_COLS];
				for(int row = 0 ; row < GamePanel.MAP_ROWS ; row++)
				{
					String line = reader.readLine(); // get lines from txt 
					for(int col = 0; col < GamePanel.MAP_COLS; col++)
					{
						char code = line.charAt(col);
						switch (code){
							case '0':
							{
								tile[row][col] = new EmptyTile(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader);
								break;
							}
							case '1':
							{
								tile[row][col] = new BrickWall(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader);
								break;
							}
							case '2':
							{
								tile[row][col] = new SteelWall(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader);
								break;
							}
							case '3':
							{
								tile[row][col] = new Bush(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader);
								break;
							}
							case '4':
							{
								tile[row][col] = new Water(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader);
								break;
							}
							case '5':
							{
								boolean isTopLeft = (row == 24 && col == 12); //TODO this line for map construction part
								tile[row][col] = new Base(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader, isTopLeft);
								break;
							}
							case '6':
							{
								tile[row][col] = new GrayRoad(col * GamePanel.MAP_CELL_SIZE, row * GamePanel.MAP_CELL_SIZE, iLoader);
								break;
							}

						}
					}
				}
				levels[i] = new Level(tile);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error on level loading!", JOptionPane.WARNING_MESSAGE);
		}
	}

	public Level[] getLevels()
	{
		return levels;
	}
}

