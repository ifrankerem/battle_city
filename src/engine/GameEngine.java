package engine;
import java.awt.Graphics;
import entity.PlayerTank;
import level.Level;
import model.Direction;
import util.ImageLoader;

public class GameEngine {

	private boolean isPaused;
	private InputManager iManager;
	private PlayerTank player;
	private ImageLoader iLoader;
	private Level[] levels;
	private int currentLevelindex;

	public GameEngine(InputManager iManager, ImageLoader iLoader,Level[] levels)
	{
		this.iManager = iManager;
		this.iLoader = iLoader;
		this.player = new PlayerTank(208, 568, 3.0, Direction.NORTH, iManager,iLoader);
		this.isPaused = false;
		this.levels = levels;
		this.currentLevelindex = 5;
	}

	public void update()
	{			
		player.updateMovement(levels[currentLevelindex]);
	}

	public boolean get_isPaused()
	{
		return(isPaused);
	}
	public PlayerTank getPlayer()
	{
		return player;
	}

	public InputManager getiManager() {
		return iManager;
	}

	public Level getCurrentLevel()
	{
		return levels[currentLevelindex];
	}

	public void draw(Graphics g)
	{
		levels[currentLevelindex].draw(g);
		player.draw(g);
		//enemytank.draw(g);
		//bullet.draw(g);
		levels[currentLevelindex].topdraw(g);
	}

}