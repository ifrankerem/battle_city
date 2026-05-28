package engine;
import java.awt.Graphics;
import entity.PlayerTank;
import entity.Bullet;
import entity.EnemyTankBase;
import entity.EnemyTankGreen;
import entity.EnemyTankPink;
import entity.EnemyTankWhite;
import level.Level;
import model.AnimatedModel;
import model.BulletExplosion;
import model.OneHundred;
import model.Spawn;
import model.TankExplosion;
import model.ThreeHundred;
import util.Direction;
import util.GameState;
import util.ImageLoader;
import java.util.ArrayList;
import java.util.Iterator;
import gui.GamePanel;
import model.TwoHundred;
import model.Model;
import java.awt.Color;

public class GameEngine {

	private InputManager iManager;
	private PlayerTank player;
	private ImageLoader iLoader;
	private Level[] levels;
	private int currentLevelindex;
	private ArrayList <Bullet> bullets;
	private ArrayList <EnemyTankBase> enemytanks;
	private ArrayList <AnimatedModel> animatedModels;
	private ArrayList <Model> models;
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
		this.animatedModels = new ArrayList<AnimatedModel>();
		this.models = new ArrayList<Model>();
		this.bullets = new ArrayList<Bullet>();
		this.playerSpawn();
		this.enemytanks = new ArrayList<EnemyTankBase>();
		this.enemySpawn();
		this.levels = levels;
		this.currentLevelindex = 5;
		this.score = 0;
		this.gameState = GameState.MENU;
	}


	public void enemySpawn()
	{	
		if(active_enemy_nbr == 0 && maxEnemy == 0)
			this.gameState = GameState.LEVEL_COMPLETE;
		while(active_enemy_nbr < 4 && active_enemy_nbr >= 0)
		{
			int random = (int)(Math.random() * 3);
			animatedModels.add(new Spawn(EnemyBaseSpawnCords[random][0] - 5, EnemyBaseSpawnCords[random][1] - 5, iLoader));
			if(random == 0)
			{
				EnemyTankWhite enemy = new EnemyTankWhite(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				whiteTanknbr++;
				enemytanks.add(enemy);
			}
			else if(random == 1)
			{
				EnemyTankGreen enemy = new EnemyTankGreen(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				greenTanknbr++;
				enemytanks.add(enemy);
			}
			else
			{
				EnemyTankPink enemy = new EnemyTankPink(EnemyBaseSpawnCords[random][0],EnemyBaseSpawnCords[random][1] ,2,Direction.SOUTH,iLoader);
				pinkTanknbr++;
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
			animatedModels.add(new Spawn(PlayerBaseSpawnCords[0] - 5, PlayerBaseSpawnCords[1] - 5, iLoader));
			this.player = new PlayerTank(PlayerBaseSpawnCords[0], PlayerBaseSpawnCords[1], 3.0, Direction.NORTH, iManager,iLoader);
		}

	}

	public void update()
	{			
		if(levels[currentLevelindex].isbaseDestroyed())
			this.gameState = GameState.GAME_OVER;
		if(maxEnemy == 0 && active_enemy_nbr == 0)
			this.gameState = GameState.LEVEL_COMPLETE;
		else
			this.gameState = GameState.PLAYING;

		if(this.gameState == GameState.PLAYING)
		{
			this.updateAnimatedModels();
			this.updateModels();
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

	public void playerPowerUpCollision()
	{

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
				animatedModels.add(new BulletExplosion(bullet.getX(), bullet.getY(), iLoader));
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
				animatedModels.add(new TankExplosion(enemy.getX(), enemy.getY(), iLoader));
				if(enemy instanceof EnemyTankGreen)
					models.add(new OneHundred(enemy.getX(), enemy.getY(), iLoader));
				else if(enemy instanceof EnemyTankPink)
					models.add(new TwoHundred(enemy.getX(), enemy.getY(), iLoader));
				else if(enemy instanceof EnemyTankWhite)
					models.add(new ThreeHundred(enemy.getX(), enemy.getY(), iLoader));
				it_t.remove();
			}
		}
		if(player.get_isAlive() == false)
		{
			animatedModels.add(new TankExplosion(player.getX(), player.getY(), iLoader));
			this.playerSpawn();
			player_max_health--;
		}
	}

	public void updateAnimatedModels()
	{
		Iterator <AnimatedModel> it = animatedModels.iterator();
		while(it.hasNext())
		{
			AnimatedModel animatedModel = it.next();
			animatedModel.update();
			if(!animatedModel.isActive())
			{
				if(animatedModel instanceof Spawn)
				{
					player.setSpawn(true);
					for(EnemyTankBase enemy : enemytanks)
						enemy.setSpawn(true);
				}
				it.remove();
			}
		}
	}

	public void updateModels()
	{
		Iterator <Model> it = models.iterator();
		while(it.hasNext())
		{
			Model model = it.next();
			model.update();
			if(!model.isActive())
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
		else if(this.gameState == GameState.PLAYING)
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
			Iterator <Bullet> it = bullets.iterator();
			while(it.hasNext())
				it.next().draw(g);
			Iterator <AnimatedModel> eit = animatedModels.iterator();
			while(eit.hasNext())
				eit.next().draw(g);
			Iterator <Model> hit = models.iterator();
			while(hit.hasNext())
				hit.next().draw(g);
			levels[currentLevelindex].topdraw(g);
		}

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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		int imgWidth = iLoader.getPaused_textImage().getWidth();
		int imgHeight = iLoader.getPaused_textImage().getHeight();
		int targetWidth = 300;
		int targetHeight = (targetWidth * imgHeight) / imgWidth;
		g.drawImage(iLoader.getPaused_textImage(), (GamePanel.GAME_WIDTH - targetWidth) / 2, (GamePanel.GAME_HEIGHT - targetHeight) / 2, targetWidth, targetHeight, null);
		
	}

	private void drawNumber(Graphics g, int number, int x, int y, int digitW, int digitH)
	{
		String text = String.valueOf(number);

		for (int i = 0; i < text.length(); i++)
		{
			int digit = text.charAt(i) - '0';
			g.drawImage(iLoader.getDigit_white(digit), x + i * digitW, y, digitW, digitH, null);
		}
	}

	public void drawLevelComplete(Graphics g)
	{
		g.setColor(java.awt.Color.BLACK);
		g.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);

		int digitW = 20;
		int digitH = 20;

		int tsW = 150;
		int tsH = 37;
		String scoreStr = String.valueOf(score);
		int scoreW = scoreStr.length() * digitW;
		int tsTotalW = tsW + 20 + scoreW;
		int tsStartX = (GamePanel.GAME_WIDTH - tsTotalW) / 2 - 15;
		int topY = 90;
		
		g.drawImage(iLoader.getTotalScoreImage(), tsStartX, topY, tsW, tsH, null);
		this.drawNumber(g, score, tsStartX + tsW + 20, topY + (tsH - digitH) / 2, digitW, digitH);

		int stageW = 117;
		int stageH = 21;
		String levelStr = String.valueOf(this.currentLevelindex + 1);
		int stageTotalW = stageW + 15 + levelStr.length() * digitW;
		int stageStartX = (GamePanel.GAME_WIDTH - stageTotalW) / 2 - 15;
		int stageY = 160;
		
		g.drawImage(iLoader.getStageImage(), stageStartX, stageY, stageW, stageH, null);
		this.drawNumber(g, this.currentLevelindex + 1, stageStartX + stageW + 15, stageY, digitW, digitH);

		int startY = 260;
		int rowGap = 60;
		
		drawTankKillRow(g, whiteTanknbr, 500, 3, 8, startY);
		drawTankKillRow(g, greenTanknbr, 200, 11, 0, startY + rowGap);
		drawTankKillRow(g, pinkTanknbr, 300, 10, 8, startY + 2 * rowGap);

		int totalY = startY + 3 * rowGap + 20;
		
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(230, totalY - 10, 203, 4);

		g.drawImage(iLoader.getTotalImage(), 195, totalY, 90, 30, null);
		
		int totalTanks = whiteTanknbr + greenTanknbr + pinkTanknbr;
		String totalTanksStr = String.valueOf(totalTanks);
		int totalTanksX = 345 - (totalTanksStr.length() * digitW);
		this.drawNumber(g, totalTanks, totalTanksX, totalY + 5, digitW, digitH);
	}

	public void drawNextLevel(Graphics g)
	{

	}



	private void drawTankKillRow(Graphics g, int count, int baseScore, int spriteRow, int spriteCol, int y)
	{
		int digitW = 20;
		int digitH = 20;
		int ptsValue = count * baseScore;
		String ptsStr = String.valueOf(ptsValue);
		String countStr = String.valueOf(count);

		int ptsNumX = 220 - (ptsStr.length() * digitW); 
		int ptsImgX = 230;
		int countNumX = 345 - (countStr.length() * digitW);
		int arrowX = 350;
		int tankImgX = 385;

		this.drawNumber(g, ptsValue, ptsNumX, y + 10, digitW, digitH);

		g.drawImage(iLoader.getPtsImage(), ptsImgX, y + 5, 60, 30, null);

		this.drawNumber(g, count, countNumX, y + 10, digitW, digitH);

		g.drawImage(iLoader.getArrowImage(), arrowX, y + 15, 30, 15, null);

		g.drawImage(iLoader.getEntitySprite2(spriteRow, spriteCol), tankImgX, y, 48, 48, null);
	}

}