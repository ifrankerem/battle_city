package engine;
import java.awt.Graphics;
import entity.PlayerTank;
import entity.Bullet;
import entity.Explosion;
import level.Level;
import model.Direction;
import util.ImageLoader;
import java.util.ArrayList;
import java.util.Iterator;
import entity.Tank;

public class GameEngine {

	private boolean isPaused;
	private InputManager iManager;
	private PlayerTank player;
	private ImageLoader iLoader;
	private Level[] levels;
	private int currentLevelindex;
	private ArrayList <Bullet> bullets;
	private ArrayList <Explosion> explosions;

	public GameEngine(InputManager iManager, ImageLoader iLoader,Level[] levels)
	{
		this.iManager = iManager;
		this.iLoader = iLoader;
		this.bullets = new ArrayList<Bullet>();
		this.explosions = new ArrayList<Explosion>();
		this.player = new PlayerTank(220, 570, 3.0, Direction.NORTH, iManager,iLoader,this);
		this.isPaused = false;
		this.levels = levels;
		this.currentLevelindex = 32;
	}

	public void update()
	{			
		player.updateMovement(levels[currentLevelindex],bullets);
		this.updateBullets();
		Iterator <Bullet> it = bullets.iterator();
		while(it.hasNext())
		{
			it.next().updateMovement(levels[currentLevelindex]);
		}
		this.updateExplosions();
	}

	public int countBullets()
	{
		int count = 1;
		for(Bullet curr : bullets)
		{
			if(curr.getOwnerTank() instanceof Tank)
				count++;
		}
		return count;
	}

	public void updateBullets()
	{
		if(iManager.get_space())
		{
			if(this.countBullets() <= player.getMaxBulletCount())
				bullets.add(player.getBullet());
		}
		Iterator <Bullet> it = bullets.iterator();
		while(it.hasNext())
		{
			Bullet bullet = it.next();
			if(bullet.getActive() == false)
			{
				explosions.add(new Explosion(bullet.getX(), bullet.getY(), iLoader));
				it.remove();
			}
		}
	}

	public void updateExplosions()
	{
		Iterator <Explosion> it = explosions.iterator();
		while(it.hasNext())
		{
			Explosion explosion = it.next();
			explosion.update();
			if(!explosion.isActive())
				it.remove();
		}
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
		Iterator <Bullet> it = bullets.iterator();
		while(it.hasNext())
			it.next().draw(g);
		Iterator <Explosion> eit = explosions.iterator();
		while(eit.hasNext())
			eit.next().draw(g);
		levels[currentLevelindex].topdraw(g);
	}

}