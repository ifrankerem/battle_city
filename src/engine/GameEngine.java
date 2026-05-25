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
		this.player = new PlayerTank(100, 100, 3.0, Direction.NORTH, iManager,iLoader); //TODO x and y will be applied as map info but not ready!
		this.isPaused = false;
		this.levels = levels;
		this.currentLevelindex = 3;
	}

	public void update()
	{			
		player.updateMovement();
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

	public void draw(Graphics g)
	{
		levels[currentLevelindex].draw(g);
		player.draw(g);
	}

}