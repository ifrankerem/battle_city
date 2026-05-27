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
import model.Explosion;
import model.GameState;
import model.OneHundred;
import model.Spawn;
import model.TankExplosion;
import model.ThreeHundred;
import util.ImageLoader;
import model.GameObject;
import java.util.ArrayList;
import java.util.Iterator;
import entity.Tank;
import gui.GamePanel;
import gui.contentPanel;
import model.TwoHundred;
import model.FiveHundred;

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
	private ArrayList <TankExplosion> tankExplosions;
	private ArrayList <OneHundred> oneHundreds;
	private ArrayList <TwoHundred> twoHundreds;
	private ArrayList <ThreeHundred> threeHundreds;
	private ArrayList <FiveHundred> fiveHundreds;
	private static final int[][] EnemyBaseSpawnCords = {{0,0},{GamePanel.GAME_WIDTH/2,0},{GamePanel.GAME_WIDTH - GamePanel.ENTITY_SIZE,0}};
	private static final int[] PlayerBaseSpawnCords = {220,570};
	private static int maxEnemy = 5; //TODO DO 20 AT THE END OF DEVELOPMENT
	private int score;
	private static int active_enemy_nbr = 0;
	private static int player_max_health = 3;
	private GameState gameState;
	private static int pinkTanknbr;
	private static int whiteTanknbr;
	private static int greenTanknbr;

	public GameEngine(InputManager iManager, ImageLoader iLoader,Level[] levels)
	{
		this.iManager = iManager;
		this.iLoader = iLoader;
		this.bullets = new ArrayList<Bullet>();
		this.explosions = new ArrayList<Explosion>();
		this.tankExplosions = new ArrayList<TankExplosion>();
		this.spawns = new ArrayList<Spawn>();
		this.oneHundreds = new ArrayList<OneHundred>();
		this.twoHundreds = new ArrayList<TwoHundred>();
		this.threeHundreds = new ArrayList<ThreeHundred>();
		this.fiveHundreds = new ArrayList<FiveHundred>();
		this.playerSpawn();
		this.enemytanks = new ArrayList<EnemyTankBase>();
		this.enemySpawn();
		this.isPaused = false;
		this.levels = levels;
		this.currentLevelindex = 5;
		this.score = 0;
		this.gameState = GameState.MENU;
	}


	public void enemySpawn()
	{	
		if(active_enemy_nbr == 0 && maxEnemy == 0)
			this.gameState = GameState.LEVEL_COMPLETE;
		while(active_enemy_nbr < 3 && active_enemy_nbr >= 0)
		{
			int random = (int)(Math.random() * 3);
			spawns.add(new Spawn(EnemyBaseSpawnCords[random][0] - 5, EnemyBaseSpawnCords[random][1] - 5, iLoader));
			if(random == 0)
			{
				EnemyTankWhite enemy = new EnemyTankWhite(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				this.whiteTanknbr++;
				enemytanks.add(enemy);
			}
			else if(random == 1)
			{
				EnemyTankGreen enemy = new EnemyTankGreen(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				this.greenTanknbr++;
				enemytanks.add(enemy);
			}
			else
			{
				EnemyTankPink enemy = new EnemyTankPink(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				this.pinkTanknbr++;
				enemytanks.add(enemy);
			}
			active_enemy_nbr++;
			maxEnemy--;
		}
	}

	public void playerSpawn()
	{
		if(player_max_health == 0)
			this.gameState = GameState.GAME_OVER; //TODO when game is over. UI gonna change
		if(player_max_health > 0)
		{
			spawns.add(new Spawn(PlayerBaseSpawnCords[0] - 5, PlayerBaseSpawnCords[1] - 5, iLoader));
			this.player = new PlayerTank(PlayerBaseSpawnCords[0], PlayerBaseSpawnCords[1], 3.0, Direction.NORTH, iManager,iLoader);
		}

	}

	public void update()
	{			
		if(levels[currentLevelindex].isbaseDestroyed())
			this.gameState = GameState.GAME_OVER;
		if(maxEnemy == 0 && active_enemy_nbr == 0)
			this.gameState = GameState.LEVEL_COMPLETE;
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
		this.updateTankExplosions();
		this.updateOneHundred();
		this.updateTwoHundred();
		this.updateThreeHundred();
		this.updateFiveHundred();
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
							score += curr.getScore();
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
				tankExplosions.add(new TankExplosion(enemy.getX(), enemy.getY(), iLoader));
				if(enemy instanceof EnemyTankGreen)
					oneHundreds.add(new OneHundred(enemy.getX(), enemy.getY(), iLoader));
				else if(enemy instanceof EnemyTankPink)
					twoHundreds.add(new TwoHundred(enemy.getX(), enemy.getY(), iLoader));
				else if(enemy instanceof EnemyTankWhite)
					threeHundreds.add(new ThreeHundred(enemy.getX(), enemy.getY(), iLoader));
				it_t.remove();
			}
		}
		if(player.get_isAlive() == false)
		{
			tankExplosions.add(new TankExplosion(player.getX(), player.getY(), iLoader));
			this.playerSpawn();
			player_max_health--;
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


	public void updateTankExplosions()
	{
		Iterator <TankExplosion> it = tankExplosions.iterator();
		while(it.hasNext())
		{
			TankExplosion tankExplosions = it.next();
			tankExplosions.update();
			if(!tankExplosions.isActive())
				it.remove();
		}
	}

	public void updateOneHundred()
	{
		Iterator <OneHundred> it = oneHundreds.iterator();
		while(it.hasNext())
		{
			OneHundred oneHundred = it.next();
			oneHundred.update();
			if(!oneHundred.isActive())
				it.remove();
		}
	}
	
	public void updateTwoHundred()
	{
		Iterator <TwoHundred> it = twoHundreds.iterator();
		while(it.hasNext())
		{
			TwoHundred twoHundred = it.next();
			twoHundred.update();
			if(!twoHundred.isActive())
				it.remove();
		}
	}

	public void updateFiveHundred()
	{
		Iterator <FiveHundred> it = fiveHundreds.iterator();
		while(it.hasNext())
		{
			FiveHundred fiveHundred = it.next();
			fiveHundred.update();
			if(!fiveHundred.isActive())
				it.remove();
		}
	}
	public void updateThreeHundred()
	{
		Iterator <ThreeHundred> it = threeHundreds.iterator();
		while(it.hasNext())
		{
			ThreeHundred threeHundred = it.next();
			threeHundred.update();
			if(!threeHundred.isActive())
				it.remove();
		}
	}


	public boolean get_isPaused()
	{
		return(gameState == GameState.PAUSED);
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

	public int getMaxEnemy()
	{
		return maxEnemy;
	}

	public int getActiveEnemyCount()
	{
		return active_enemy_nbr;
	}

	public int getCurrentLevelIndex()
	{
		return currentLevelindex;
	}

	public int getPlayerMaxHealth()
	{
		return player_max_health;
	}

	public void draw(Graphics g)
	{
		if(this.gameState == GameState.GAME_OVER)
			this.drawOver(g);
		else if(this.gameState == GameState.PAUSED)
			this.drawPause(g);
		if(this.gameState == GameState.LEVEL_COMPLETE)
			this.drawLevelComplete(g);
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
		Iterator <TankExplosion> tit = tankExplosions.iterator();
		while(tit.hasNext())
			tit.next().draw(g);
		this.drawHundred(g);
		levels[currentLevelindex].topdraw(g);
	}

	public void drawHundred(Graphics g)
	{
		Iterator <OneHundred> iterator = oneHundreds.iterator();
		while(iterator.hasNext())
			iterator.next().draw(g);	
		Iterator <TwoHundred> iterator2 = twoHundreds.iterator();
		while(iterator2.hasNext())
			iterator2.next().draw(g);	
		Iterator <ThreeHundred> iterator3 = threeHundreds.iterator();
		while(iterator3.hasNext())
			iterator3.next().draw(g);	
		Iterator <FiveHundred> iterator4 = fiveHundreds.iterator();
		while(iterator4.hasNext())
			iterator4.next().draw(g);	

	}

	public void togglePause()
	{
		if(this.gameState == GameState.PAUSED)
			this.gameState = GameState.PLAYING;
		else if(this.gameState == GameState.PLAYING)
			this.gameState = GameState.PAUSED;
	}

	public void drawOver(Graphics g)
	{

	}

	public void drawPause(Graphics g)
	{

	}

	private void drawNumber(Graphics g, int number, int x, int y, int digitW, int digitH)
	{
		String text = String.valueOf(number);

		for (int i = 0; i < text.length(); i++)
		{
			int digit = text.charAt(i) - '0';
			g.drawImage(iLoader.getDigit(digit), x + i * digitW, y, digitW, digitH, null);
		}
	}

	public void drawLevelComplete(Graphics g)
	{
		// Draw black background
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);

		int digitW = 20;
		int digitH = 20;

		// 1. TOTAL SCORE
		g.drawImage(iLoader.getTotalScoreImage(), 170, 50, 150, 37, null);
		this.drawNumber(g, score, 340, 60, digitW, digitH);

		// 2. STAGE NUMBER
		int stageW = 117;
		int stageH = 21;
		g.drawImage(iLoader.getStageImage(), 220, 120, stageW, stageH, null);
		this.drawNumber(g, this.currentLevelindex + 1, 220 + stageW + 15, 120, digitW, digitH);

		// 3. TANKS KILLED ROWS
		int startY = 220;
		int rowGap = 60;
		
		// White tank (500 pts)
		drawTankKillRow(g, whiteTanknbr, 500, 3, 8, startY);
		// Green tank (200 pts)
		drawTankKillRow(g, greenTanknbr, 200, 11, 0, startY + rowGap);
		// Pink tank (300 pts)
		drawTankKillRow(g, pinkTanknbr, 300, 10, 8, startY + 2 * rowGap);

		// 4. TOTAL
		int totalY = startY + 3 * rowGap + 20;
		
		// Draw white line above the total count
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(250, totalY - 10, 140, 4);

		// Draw TOTAL text image
		g.drawImage(iLoader.getTotalImage(), 160, totalY, 90, 30, null);
		
		// Draw total tanks number (aligned with the counts column)
		int totalTanks = whiteTanknbr + greenTanknbr + pinkTanknbr;
		String totalTanksStr = String.valueOf(totalTanks);
		int totalTanksX = 290 - (totalTanksStr.length() * digitW);
		this.drawNumber(g, totalTanks, totalTanksX, totalY + 5, digitW, digitH);
	}

	private void drawTankKillRow(Graphics g, int count, int baseScore, int spriteRow, int spriteCol, int y)
	{
		int digitW = 20;
		int digitH = 20;
		int ptsValue = count * baseScore;
		String ptsStr = String.valueOf(ptsValue);
		String countStr = String.valueOf(count);

		// Calculate X positions to align nicely
		int ptsNumX = 180 - (ptsStr.length() * digitW); // right aligned
		int ptsImgX = 190;
		int countNumX = 290 - (countStr.length() * digitW); // right aligned
		int arrowX = 300;
		int tankImgX = 340;

		// Draw Points Value
		this.drawNumber(g, ptsValue, ptsNumX, y + 10, digitW, digitH);

		// Draw PTS Image
		g.drawImage(iLoader.getPtsImage(), ptsImgX, y + 5, 60, 30, null);

		// Draw Count Number
		this.drawNumber(g, count, countNumX, y + 10, digitW, digitH);

		// Draw Arrow Image
		g.drawImage(iLoader.getArrowImage(), arrowX, y + 15, 30, 15, null);

		// Draw Tank Sprite
		g.drawImage(iLoader.getEntitySprite2(spriteRow, spriteCol), tankImgX, y, 48, 48, null);
	}

}