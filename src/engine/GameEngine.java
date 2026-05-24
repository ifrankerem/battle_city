package engine;
import java.awt.Graphics;

import entity.PlayerTank;
import model.Direction;
import util.ImageLoader;

public class GameEngine {

	private boolean isPaused;
	private InputManager iManager;
	private PlayerTank player;
	private ImageLoader iLoader;

	public GameEngine(InputManager iManager, ImageLoader iLoader)
	{
		this.iManager = iManager;
		this.iLoader = iLoader;
		this.player = new PlayerTank(100, 100, 3.0, Direction.NORTH, iManager,iLoader); //TODO x and y will be applied as map info but not ready!
		this.isPaused = false;
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
		player.draw(g);
	}

}