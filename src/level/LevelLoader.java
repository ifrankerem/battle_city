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

	private static final int LEVEL_COUNT = 37;
	private Level levels[];
	private ImageLoader iLoader;

	public LevelLoader(ImageLoader iLoader)
	{
		this.iLoader = iLoader;
		this.levels = new Level[LEVEL_COUNT];
	}	

	private String buildStagePath(int stageNumber) {
   		return String.format("/resources/levels_txt/stage_%02d.txt", stageNumber);
	}

	public Level loadMap(String path)
	{
		try{
			InputStream txStream = getClass().getResourceAsStream(path);
			if(txStream == null)
				throw new RuntimeException("Level file not found: " + path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(txStream));
			Tile tile[][] = new Tile[GamePanel.MAP_ROWS][GamePanel.MAP_COLS];
			for(int row = 0 ; row < GamePanel.MAP_ROWS ; row++)
			{
				String line = reader.readLine(); // get lines from txt
				if(line == null)
					throw new RuntimeException("Missing row " + row + " in " + path);
				if(line.length() < GamePanel.MAP_COLS)
					throw new RuntimeException("Row " + row + " is too short in " + path);
				for(int col = 0; col < GamePanel.MAP_COLS; col++)
				{
					char code = line.charAt(col);
					tile[row][col] = createTile(code, row, col, path);
				}
			}
			reader.close();
			return new Level(tile);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error on level loading!", JOptionPane.WARNING_MESSAGE);
			throw new RuntimeException(e);
		}
	}

	private Tile createTile(char code, int row, int col, String path)
	{
		int x = col * GamePanel.MAP_CELL_SIZE;
		int y = row * GamePanel.MAP_CELL_SIZE;

		switch (code){
			case '0':
				return new EmptyTile(x, y, iLoader);
			case '1':
				return new BrickWall(x, y, iLoader);
			case '2':
				return new SteelWall(x, y, iLoader);
			case '3':
				return new Bush(x, y, iLoader);
			case '4':
				return new Water(x, y, iLoader);
			case '5':
				return new Base(x, y, iLoader);
			case '6':
				return new GrayRoad(x, y, iLoader);
			default:
				throw new RuntimeException("Unknown tile code '" + code + "' at row " + row + ", col " + col + " in " + path);
		}
	}

	public void loadMaps()
	{
		for(int i = 0; i < LEVEL_COUNT ; i++)
		{
			levels[i] = loadMap(buildStagePath(i));
		}
	}

	public Level[] getLevels()
	{
		return levels;
	}
}
