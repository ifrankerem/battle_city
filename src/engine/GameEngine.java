package engine;
import java.awt.Graphics;
import entity.PlayerTank;
import entity.Bullet;
import entity.EnemyTankBase;
import entity.EnemyTankGreen;
import entity.EnemyTankPink;
import entity.EnemyTankWhite;
import level.Level;
import model.Direction;
import util.Explosion;
import util.ImageLoader;
import util.Spawn;

import java.util.ArrayList;
import java.util.Iterator;
import entity.Tank;
import gui.GamePanel;
import gui.contentPanel;

public class GameEngine {

	private boolean isPaused;
	private InputManager iManager;
	private PlayerTank player;
	private ImageLoader iLoader;
	private Level[] levels;
	private int currentLevelindex;
	private ArrayList <Bullet> bullets;
	private ArrayList <Explosion> explosions;
	private ArrayList <EnemyTankBase> enemytanks;
	private ArrayList <Spawn> spawns;
	private static final int[][] EnemyBaseSpawnCords = {{0,0},{GamePanel.GAME_WIDTH/2,0},{GamePanel.GAME_WIDTH - GamePanel.ENTITY_SIZE,0}};
	private static final int[] PlayerBaseSpawnCords = {220,570};
	private static int maxEnemy = 5; //TODO DO 20 AT THE END OF DEVELOPMENT
	private int score;
	private static int active_enemy_nbr = 0;
	private static int player_max_health = 3;
	private static boolean isGameOver = false;

	public GameEngine(InputManager iManager, ImageLoader iLoader,Level[] levels)
	{
		this.iManager = iManager;
		this.iLoader = iLoader;
		this.bullets = new ArrayList<Bullet>();
		this.explosions = new ArrayList<Explosion>();
		this.spawns = new ArrayList<Spawn>();
		this.playerSpawn();
		this.enemytanks = new ArrayList<EnemyTankBase>();
		this.enemySpawn();
		this.isPaused = false;
		this.levels = levels;
		this.currentLevelindex = 13;
		this.score = 0;
	}


	public void enemySpawn()
	{	
		while(active_enemy_nbr < 3 && active_enemy_nbr >= 0)
		{
			int random = (int)(Math.random() * 3);
			spawns.add(new Spawn(EnemyBaseSpawnCords[random][0] - 5, EnemyBaseSpawnCords[random][1] - 5, iLoader));
			if(random == 0)
			{
				EnemyTankWhite enemy = new EnemyTankWhite(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				enemytanks.add(enemy);
			}
			else if(random == 1)
			{
				EnemyTankGreen enemy = new EnemyTankGreen(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				enemytanks.add(enemy);
			}
			else
			{
				EnemyTankPink enemy = new EnemyTankPink(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				enemytanks.add(enemy);
			}
			active_enemy_nbr++;
			maxEnemy--;
		}
	}

	public void playerSpawn()
	{
		if(player_max_health > 0)
		{
			if(player_max_health == 0)
				this.isGameOver = true; //TODO when game is over. UI gonna change
			spawns.add(new Spawn(PlayerBaseSpawnCords[0] - 5, PlayerBaseSpawnCords[1] - 5, iLoader));
			this.player = new PlayerTank(PlayerBaseSpawnCords[0], PlayerBaseSpawnCords[1], 3.0, Direction.NORTH, iManager,iLoader);
		}

	}

	public void update()
	{			
		this.updateSpawn();
		this.updateTanks();
		if(maxEnemy != 0)
			this.enemySpawn();
		player.updateMovement(levels[currentLevelindex],bullets);
		Iterator <EnemyTankBase> it_t = enemytanks.iterator();
		while(it_t.hasNext())
			it_t.next().updateMovement(levels[currentLevelindex],bullets);
		this.updateBullets();
		Iterator <Bullet> it = bullets.iterator();
		while(it.hasNext())
			it.next().updateMovement(levels[currentLevelindex]);
		this.updateExplosions();
	}

	public void updateSpawn()
	{
		Iterator <Spawn> it = spawns.iterator();
		while(it.hasNext())
		{
			Spawn spawn = it.next();
			spawn.update();
			if(!spawn.isActive())
				{
					it.remove();
					player.setSpawn(true);
					Iterator <EnemyTankBase> eit = enemytanks.iterator();
					while(eit.hasNext())
						eit.next().setSpawn(true);
				}
		}
	}


	public void playerBulletCollision()
	{
		for(Bullet bullet : bullets)
		{
			if(bullet.getActive() && bullet.getOwnerTank() instanceof PlayerTank)
			{
				for(EnemyTankBase curr : enemytanks)
				{
					if(bullet.getHitbox().intersects(curr.getHitbox()) && curr.get_isAlive())
					{
						curr.getDamage();
						bullet.setActive(false);
						if(curr.get_isAlive() == false)
						{
							score += 200;
							active_enemy_nbr--;
						}
						break; // Stop checking this bullet against other enemies
					}
				}
			}
		}
	}

	public void enemyBulletCollision()
	{
		for(Bullet bullet : bullets)
		{
			if(bullet.getActive() && bullet.getOwnerTank() instanceof EnemyTankBase)
			{
				if(bullet.getHitbox().intersects(player.getHitbox()) && player.get_isAlive())
				{
					player.getDamage();
					bullet.setActive(false);
					break;
				}
			}
		}
	}

	public void bulletBulletCollision()
	{
		for(int i = 0; i < bullets.size(); i++)
		{
			Bullet first = bullets.get(i);
			if(first.getActive() == false)
				continue;
			for(int j = i+1; j < bullets.size(); j++)
			{
				Bullet second = bullets.get(j);
				if(second.getActive() == false)
					continue;
				if(first.getHitbox().intersects(second.getHitbox()))
				{
					bullets.remove(first);
					bullets.remove(second);
					break;
				}
			}
		}
	}


	public void updateBullets()
	{
		if(iManager.get_space())
		{
			Bullet playerBullet = player.getBullet();
			if(playerBullet != null && !bullets.contains(playerBullet) && player.countBullets(bullets) < player.getMaxBulletCount())
				bullets.add(playerBullet);
		}
		Iterator <EnemyTankBase> it_t = enemytanks.iterator();
		while(it_t.hasNext())
		{
			EnemyTankBase enemy = it_t.next();
			Bullet enemyBullet = enemy.getBullet();
			if(enemyBullet != null && !bullets.contains(enemyBullet) && enemy.countBullets(bullets) < enemy.getMaxBulletCount())
				bullets.add(enemyBullet);
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

	public void updateTanks()
	{
		this.playerBulletCollision();
		this.enemyBulletCollision();
		this.bulletBulletCollision();
		Iterator <EnemyTankBase> it_t = enemytanks.iterator();
		while(it_t.hasNext())
		{
			EnemyTankBase enemy = it_t.next();
			if(enemy.get_isAlive() == false)
			{
				explosions.add(new Explosion(enemy.getX(), enemy.getY(), iLoader)); //TODO Different explosion
				it_t.remove();
			}
		}
		if(player.get_isAlive() == false)
		{
			explosions.add(new Explosion(player.getX(), player.getY(), iLoader)); //TODO Different explosion
			this.playerSpawn();
			player_max_health--;
			System.out.println(player_max_health);
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
		if(player.getSpawn())
			player.draw(g);
		Iterator <EnemyTankBase> it_t = enemytanks.iterator();
		while(it_t.hasNext())
		{
			EnemyTankBase curr = it_t.next();
			if(curr.getSpawn())
				curr.draw(g);
		}
		Iterator <Spawn> sit = spawns.iterator();
		while(sit.hasNext())
			sit.next().draw(g);
		Iterator <Bullet> it = bullets.iterator();
		while(it.hasNext())
			it.next().draw(g);
		Iterator <Explosion> eit = explosions.iterator();
		while(eit.hasNext())
			eit.next().draw(g);
		levels[currentLevelindex].topdraw(g);
	}

}