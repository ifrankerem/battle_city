package engine;
import java.awt.Graphics;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import entity.PlayerTank;
import entity.Bullet;
import entity.EnemyTankBase;
import entity.EnemyTankGreen;
import entity.EnemyTankPink;
import entity.EnemyTankWhite;
import level.Level;
import level.LevelLoader;
import model.AnimatedModel;
import model.BulletExplosion;
import model.FiveHundred;
import model.InvincibleModel;
import model.Spawn;
import model.TankExplosion;
import model.ThreeHundred;
import util.Direction;
import util.GameState;
import util.ImageLoader;
import util.ScoreManager;
import util.ScoreRecord;
import util.SoundLoader;
import util.HitType;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import gui.GamePanel;
import model.TwoHundred;
import powerup.BombPowerUp;
import powerup.ClockPowerUp;
import powerup.PowerUp;
import powerup.ShieldPowerUp;
import powerup.ShovelPowerUp;
import powerup.StarPowerUp;
import powerup.TankPowerUp;
import powerup.TimedPowerUps;
import model.Model;
import java.awt.Color;

public class GameEngine {

	public static final int MIN_PLAYER_LIVES = 1;
	public static final int MAX_PLAYER_LIVES = 9;
	public static final int MIN_START_LEVEL = 1;
	public static final int MAX_START_LEVEL = 37;
	private InputManager iManager;
	private PlayerTank player;
	private ImageLoader iLoader;
	private Level[] levels;
	private int currentLevelindex;
	private ArrayList <Bullet> bullets;
	private ArrayList <EnemyTankBase> enemytanks;
	private ArrayList <AnimatedModel> animatedModels;
	private ArrayList <Model> models;
	private ArrayList<PowerUp> powerUps;
	private ArrayList<TimedPowerUps> activeEffects;
	private static final int[][] ENEMY_SPAWN_COORDS = {{0,0},{GamePanel.GAME_WIDTH/2,0},{GamePanel.GAME_WIDTH - GamePanel.ENTITY_SIZE,0}};
	private static final int[] PLAYER_SPAWN_COORDS = {220,570};
	private static final int START_LEVEL_INDEX = 0;
	private static final int MAX_ENEMY_PER_LEVEL = 20;
	private static final int PLAYER_START_HEALTH = 3; 
	private static final int BONUS_SCORE = 500;
	private static final int MAX_STAR_POWER_LEVEL = 3;
	private static final int STAR_BULLET_SPEED_BONUS = 2;
	private int maxEnemy = MAX_ENEMY_PER_LEVEL; 
	private int score;
	private int active_enemy_nbr = 0;
	private int player_max_health = PLAYER_START_HEALTH;
	private GameState gameState;
	private int pinkTanknbr;
	private int whiteTanknbr;
	private int greenTanknbr;
	private int bonusNbr;
	private boolean isFrozen;
	private boolean isInvincible;
	private boolean powerUp13Spawned = false;
	private boolean powerUp7Spawned = false;
	private int powerUpStarMax = 3;
	private int starPowerCounter;
	private ScoreManager scoreManager;
	private boolean nameAsked;
	private boolean gameOverSoundPlayed;
	private boolean customLevelMode;
	private int levelCompleteCounter;
	private int stageLoadCounter;
	private static final int LEVEL_COMPLETE_DELAY = 180;
	private static final int STAGE_LOAD_DELAY = 120;
	private SoundLoader soundLoader;
	private int startingPlayerLives;
	private int startingLevelIndex;

	public GameEngine(InputManager iManager, ImageLoader iLoader,Level[] levels)
	{
		this.iManager = iManager;
		this.iLoader = iLoader;
		this.animatedModels = new ArrayList<AnimatedModel>();
		this.models = new ArrayList<Model>();
		this.bullets = new ArrayList<Bullet>();
		this.powerUps = new ArrayList<PowerUp>();
		this.activeEffects = new ArrayList<TimedPowerUps>();
		this.enemytanks = new ArrayList<EnemyTankBase>();
		this.levels = levels;
		this.currentLevelindex = START_LEVEL_INDEX;
		this.score = 0;
		this.scoreManager = new ScoreManager();
		this.nameAsked = false;
		this.gameOverSoundPlayed = false;
		this.customLevelMode = false;
		this.soundLoader = new SoundLoader();
		this.startingPlayerLives = PLAYER_START_HEALTH;
		this.startingLevelIndex = START_LEVEL_INDEX;
		this.startNewGame();
	}

	public synchronized void startNewGame()
	{
		iManager.reset();
		LevelLoader levelLoader = new LevelLoader(iLoader);
		levelLoader.loadMaps();
		this.levels = levelLoader.getLevels();
		this.resetLevelRuntime();
		this.starPowerCounter = 0;
		player_max_health = this.startingPlayerLives;
		this.currentLevelindex = this.startingLevelIndex;
		this.score = 0;
		this.nameAsked = false;
		this.customLevelMode = false;
		this.gameState = GameState.PLAYING;
		this.playerSpawn();
		this.enemySpawn();
	}

	private void resetLevelRuntime()
	{
		this.animatedModels.clear();
		this.models.clear();
		this.bullets.clear();
		this.powerUps.clear();
		this.activeEffects.clear();
		this.enemytanks = new ArrayList<EnemyTankBase>();
		maxEnemy = MAX_ENEMY_PER_LEVEL;
		active_enemy_nbr = 0;
		pinkTanknbr = 0;
		whiteTanknbr = 0;
		greenTanknbr = 0;
		bonusNbr = 0;
		powerUpStarMax = 3;
		this.isFrozen = false;
		this.isInvincible = false;
		this.powerUp13Spawned = false;
		this.powerUp7Spawned = false;
		this.levelCompleteCounter = 0;
		this.stageLoadCounter = 0;
		this.gameOverSoundPlayed = false;
	}

	private void prepareNextStage()
	{
		this.currentLevelindex++;
		if(this.currentLevelindex >= levels.length)
		{
			this.gameState = GameState.GAME_OVER;
			return;
		}

		this.resetLevelRuntime();
		this.applyStarPowerToCurrentLevel();
		this.playerSpawn();
		this.enemySpawn();
		this.gameState = GameState.STAGE_LOAD;
	}

	public void enemySpawn()
	{	
		if(active_enemy_nbr == 0 && maxEnemy == 0)
			this.gameState = GameState.LEVEL_COMPLETE;
		while(active_enemy_nbr < 4 && active_enemy_nbr >= 0 && maxEnemy > 0)
		{
			int random = (int)(Math.random() * 100);
			int randomCord = (int)(Math.random() * 3);
			animatedModels.add(new Spawn(ENEMY_SPAWN_COORDS[randomCord][0] - 5, ENEMY_SPAWN_COORDS[randomCord][1] - 5, iLoader,false));
			if(random <= 10)
			{
				EnemyTankWhite enemy = new EnemyTankWhite(ENEMY_SPAWN_COORDS[randomCord][0],ENEMY_SPAWN_COORDS[randomCord][1] ,2,Direction.SOUTH,iLoader);
				this.whiteTanknbr++;
				enemytanks.add(enemy);
			}
			else if(random <= 70)
			{
				EnemyTankGreen enemy = new EnemyTankGreen(ENEMY_SPAWN_COORDS[randomCord][0],ENEMY_SPAWN_COORDS[randomCord][1] ,2,Direction.SOUTH,iLoader);
				this.greenTanknbr++;
				enemytanks.add(enemy);
			}
			else
			{
				EnemyTankPink enemy = new EnemyTankPink(ENEMY_SPAWN_COORDS[randomCord][0],ENEMY_SPAWN_COORDS[randomCord][1] ,2,Direction.SOUTH,iLoader);
				this.pinkTanknbr++;
				enemytanks.add(enemy);
			}
			active_enemy_nbr++;
			maxEnemy--;
		}
	}

	public void playerSpawn()
	{
		if(player_max_health <= 0)
			this.gameState = GameState.GAME_OVER;
		if(player_max_health > 0)
		{
			animatedModels.add(new Spawn(PLAYER_SPAWN_COORDS[0] - 5, PLAYER_SPAWN_COORDS[1] - 5, iLoader, true));
			this.player = new PlayerTank(PLAYER_SPAWN_COORDS[0], PLAYER_SPAWN_COORDS[1], 3.0, Direction.NORTH, iManager,iLoader);
			this.applyStarPowerToPlayer();
		}

	}

	private void applyStarPowerToPlayer()
	{
		if(this.starPowerCounter >= 1)
			this.player.incrementBulletSpeed(STAR_BULLET_SPEED_BONUS);
		if(this.starPowerCounter >= 2)
			this.player.incrementMaxBullets();
	}

	private void applyStarPowerToCurrentLevel()
	{
		if(this.starPowerCounter >= 3)
			this.getCurrentLevel().updateSteel();
	}

	public void update()
	{
		boolean shouldAskName;

		synchronized(this)
		{
			shouldAskName = updateGameObjects();
		}

		if(shouldAskName)
			this.askNameAndSaveScore();
	}

	private boolean updateGameObjects()
	{			
		if(this.gameState == GameState.GAME_OVER)
		{
			return this.updateGameOverState();
		}
		if(this.gameState == GameState.LEVEL_COMPLETE)
		{
			this.levelCompleteCounter++;
			if(this.levelCompleteCounter >= LEVEL_COMPLETE_DELAY)
			{
				if(this.customLevelMode)
				{
					this.gameState = GameState.GAME_OVER;
					return this.updateGameOverState();
				}
				else
				{
					this.prepareNextStage();
					if(this.gameState == GameState.GAME_OVER)
						return this.updateGameOverState();
				}
			}
			return false;
		}

		if(this.gameState == GameState.STAGE_LOAD)
		{
			this.stageLoadCounter++;
			if(this.stageLoadCounter >= STAGE_LOAD_DELAY)
				this.gameState = GameState.PLAYING;
			return false;
		}

		if(levels[currentLevelindex].isbaseDestroyed())
			this.gameState = GameState.GAME_OVER;
		else if(maxEnemy == 0 && active_enemy_nbr == 0)
		{
			this.gameState = GameState.LEVEL_COMPLETE;
			this.levelCompleteCounter = 0;
		}
		else
			this.gameState = GameState.PLAYING;

		if(this.gameState == GameState.GAME_OVER && !this.nameAsked)
		{
			return this.updateGameOverState();
		}

		if(this.gameState == GameState.PLAYING)
		{
			this.updateModels();
			this.updatePowerUps();
			this.updateActiveEffects();
			this.updateTanks();
			this.playerPowerUpCollision();
			this.ManagePowerUps();
			if(maxEnemy != 0)
			{
				this.enemySpawn();
			}
			player.updateMovement(levels[currentLevelindex],bullets);
			if(this.isFrozen == false)
			{
				Iterator <EnemyTankBase> it_t = enemytanks.iterator();
				while(it_t.hasNext())
				{
					EnemyTankBase enemy = it_t.next();
					if(enemy.get_isAlive())
						enemy.updateMovement(levels[currentLevelindex],bullets);
				}
			}
			this.updateBullets();
			Iterator <Bullet> it = bullets.iterator();
			while(it.hasNext())
				it.next().updateMovement(levels[currentLevelindex]);
			this.updateAnimatedModels();
		}

		if(this.gameState == GameState.GAME_OVER)
			return this.updateGameOverState();

		return false;
	}

	private boolean updateGameOverState()
	{
		if(!this.gameOverSoundPlayed)
		{
			soundLoader.playGameOver();
			this.gameOverSoundPlayed = true;
		}

		if(this.customLevelMode)
			return false;

		if(!this.nameAsked)
		{
			this.nameAsked = true;
			return true;
		}

		return false;
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
						if(curr instanceof EnemyTankWhite && (curr.getHealth() == 2 || curr.getHealth() == 3))
							soundLoader.playBigTankSound();
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
					if(isInvincible == false)
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
		Iterator<PowerUp> it = powerUps.iterator();
		while(it.hasNext())
		{
			PowerUp powerUp = it.next();
			if(!powerUp.getActive())
				continue;
			if(powerUp.getHitbox().intersects(player.getHitbox()))
			{
				this.addBonusScore(powerUp);
				if(powerUp instanceof TimedPowerUps) {
					TimedPowerUps timedPowerUp = (TimedPowerUps)powerUp;
					Iterator<TimedPowerUps> effectIt = activeEffects.iterator();
					while(effectIt.hasNext())
					{
						TimedPowerUps activeEffect = effectIt.next();
						if(activeEffect.getClass() == timedPowerUp.getClass())
						{
							effectIt.remove();
							if(activeEffect instanceof ShieldPowerUp)
								this.removeInvincibleModels();
						}
					}
					timedPowerUp.activateEffect(this);
					if(timedPowerUp instanceof ShieldPowerUp)
						animatedModels.add(new InvincibleModel(player.getX(), player.getY(), iLoader,this));
					activeEffects.add(timedPowerUp);
				}
				else {
					powerUp.action(this);
				}
				it.remove();
				break;
			}
		}
	}

	private void addBonusScore(PowerUp powerUp)
	{
		this.bonusNbr++;
		this.score += BONUS_SCORE;
		this.models.add(new FiveHundred(powerUp.getX(), powerUp.getY(), iLoader));
		this.soundLoader.playPowerUpObtained();
	}

	private void removeInvincibleModels()
	{
		Iterator<AnimatedModel> iterator = animatedModels.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next() instanceof InvincibleModel)
				iterator.remove();
		}
	}


	private void removeShieldEffects(boolean removeInvincibleModels)
	{
		Iterator<TimedPowerUps> iterator = activeEffects.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next() instanceof ShieldPowerUp)
				iterator.remove();
		}
		if(removeInvincibleModels)
			this.removeInvincibleModels();
		this.isInvincible = false;
	}

	public void updateActiveEffects()
	{
		Iterator<TimedPowerUps> it = activeEffects.iterator();
		while (it.hasNext()) {
			TimedPowerUps effect = it.next();
			if (effect.updateEffect(this)) {
				if(effect instanceof ShieldPowerUp)
					this.removeInvincibleModels();
				it.remove();
			}
		}
	}

	public void setFrozen(boolean value)
	{
		this.isFrozen = value;
	}
	

	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;

	}


	public void ManagePowerUps()
	{
		int remainingEnemies = maxEnemy + active_enemy_nbr;
		if(remainingEnemies == 13 && !powerUp13Spawned)
		{
			if(Math.random() < 0.5)
				spawnPowerUps();
			powerUp13Spawned = true;
		}
		else if(remainingEnemies == 7 && !powerUp7Spawned)
		{
			if(Math.random() < 0.5)
				spawnPowerUps();
			powerUp7Spawned = true;
		}
	}

	public void spawnPowerUps()
	{
		int spawnRate = (int)(Math.random() * 100);
		// Spawn inside the visible game area
		int randomX = (int)(Math.random() * (GamePanel.GAME_WIDTH - 60)) + 30;
		int randomY = (int)(Math.random() * (GamePanel.GAME_HEIGHT - 60)) + 30;
		PowerUp powerUp = null;
		if(spawnRate <= 16)
			powerUp = new ClockPowerUp(randomX, randomY, iLoader);
		else if(spawnRate <= 32)
			powerUp = new BombPowerUp(randomX, randomY, iLoader);
		else if(spawnRate <= 48)
			powerUp = new ShieldPowerUp(randomX, randomY, iLoader,1800);
		else if(spawnRate <= 64)
			powerUp = new ShovelPowerUp(randomX, randomY, iLoader);
		else if(spawnRate <= 80) 
			powerUp = new TankPowerUp(randomX, randomY, iLoader);
		else if(powerUpStarMax <= 3 && powerUpStarMax > 0)
		{
			powerUpStarMax--;
			powerUp = new StarPowerUp(randomX, randomY, iLoader);

		}

		if(powerUp != null)
		{
			powerUps.add(powerUp);
			soundLoader.playPowerUpAppeared();
		}
	}

	public void updatePowerUps()
	{
		Iterator<PowerUp> it = powerUps.iterator();
		while (it.hasNext()) {
			PowerUp powerUp = it.next();
			powerUp.update();
			if (!powerUp.getActive()) {
				it.remove();
			}
		}
	}

	public void updateBullets()
	{
		if(iManager.get_space())
		{
			Bullet playerBullet = player.getBullet();
			if(playerBullet != null && playerBullet.getActive() && !bullets.contains(playerBullet) && player.countBullets(bullets) < player.getMaxBulletCount())
			{
				bullets.add(playerBullet);
				soundLoader.playShoot();
			}
		}
		Iterator <EnemyTankBase> it_t = enemytanks.iterator();
		while(it_t.hasNext())
		{
			EnemyTankBase enemy = it_t.next();
			if(!enemy.get_isAlive())
				continue;
			Bullet enemyBullet = enemy.getBullet();
			if(enemyBullet != null && enemyBullet.getActive() && !bullets.contains(enemyBullet) && enemy.countBullets(bullets) < enemy.getMaxBulletCount())
				bullets.add(enemyBullet);
		}
		Iterator <Bullet> it = bullets.iterator();
		while(it.hasNext())
		{
			Bullet bullet = it.next();
			if(bullet.getActive() == false)
			{
				this.playBulletHitSound(bullet);
				animatedModels.add(new BulletExplosion(bullet.getX(), bullet.getY(), iLoader));
				it.remove();
			}
		}
	}

	private void playBulletHitSound(Bullet bullet)
	{
		if(!(bullet.getOwnerTank() instanceof PlayerTank))
			return;

		if(bullet.getHitType() == HitType.BRICK)
			soundLoader.playBrickBreak();
		else if(bullet.getHitType() == HitType.STEEL)
			soundLoader.playSteelHit();
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
					models.add(new TwoHundred(enemy.getX(), enemy.getY(), iLoader));
				else if(enemy instanceof EnemyTankPink)
					models.add(new ThreeHundred(enemy.getX(), enemy.getY(), iLoader));
				else if(enemy instanceof EnemyTankWhite)
					models.add(new FiveHundred(enemy.getX(), enemy.getY(), iLoader));
				soundLoader.playTankExplosion();
				it_t.remove();
			}
		}
		if(player.get_isAlive() == false)
		{
			animatedModels.add(new TankExplosion(player.getX(), player.getY(), iLoader));
			soundLoader.playTankExplosion();
			iManager.reset();
			player_max_health--;
			this.playerSpawn();
		}
	}

	public void updateAnimatedModels()
	{
		AnimatedModel pendingAnimatedModel = null;
		boolean pendingRemoveInvincibleModels = false;
		Iterator <AnimatedModel> it = animatedModels.iterator();
		while(it.hasNext())
		{
			AnimatedModel animatedModel = it.next();
			animatedModel.update();
			if(!animatedModel.isActive())
			{
				if(animatedModel instanceof Spawn)
				{
					Spawn spawn = (Spawn)animatedModel;
					if(spawn.isPlayerSpawn())
					{
						player.setSpawn(true);
						this.removeShieldEffects(false);
						pendingRemoveInvincibleModels = true;
						ShieldPowerUp spawnShield = new ShieldPowerUp(player.getX(), player.getY(), iLoader, 240);
						spawnShield.activateEffect(this);
						activeEffects.add(spawnShield);
						pendingAnimatedModel = new InvincibleModel(player.getX(), player.getY(), iLoader, this);
					}
					for(EnemyTankBase enemy : enemytanks)
						enemy.setSpawn(true);
				}
				it.remove();
			}
		}
		if(pendingRemoveInvincibleModels)
			this.removeInvincibleModels();
		if(pendingAnimatedModel != null)
			animatedModels.add(pendingAnimatedModel);
	}

	public void updateModels()
	{
		Iterator <Model> it = models.iterator();
		while(it.hasNext())
		{
			Model model = it.next();
			model.update();
			if(!model.getActive())
				it.remove();
		}
	}

	public synchronized boolean get_isPaused()
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

	public synchronized Level getCurrentLevel()
	{
		return levels[currentLevelindex];
	}

	public synchronized void updateCurrentLevelAnimation()
	{
		levels[currentLevelindex].updateAnimation();
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

	public void incrementPlayerMaxHealth()
	{
		player_max_health++;
	}

	public int incrementStarPowerCounter()
	{
		if(this.starPowerCounter < MAX_STAR_POWER_LEVEL)
			this.starPowerCounter++;
		return this.starPowerCounter;
	}

	public void incrementScore(int value)
	{
		score += value;
	}

	public void decrementActiveEnemyCount()
	{
		active_enemy_nbr--;
	}

	public synchronized void draw(Graphics g)
	{
		if(this.gameState == GameState.PAUSED)
			this.drawPause(g);
		else if(this.gameState == GameState.LEVEL_COMPLETE)
			this.drawLevelComplete(g);
		else if(this.gameState == GameState.STAGE_LOAD)
			this.drawStageLoad(g);
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
			Iterator <PowerUp> pit = powerUps.iterator();
			while(pit.hasNext())
				pit.next().draw(g);
			levels[currentLevelindex].topdraw(g);
			Iterator <Model> hit = models.iterator();
			while(hit.hasNext())
				hit.next().draw(g);
		}
	}

	public synchronized GameState getGameState()
	{
		return gameState;
	}

	public synchronized void togglePause()
	{
		if(this.gameState == GameState.PAUSED)
		{
			this.gameState = GameState.PLAYING;
			soundLoader.playPause();
		}
		else if(this.gameState == GameState.PLAYING)
		{
			this.gameState = GameState.PAUSED;
			soundLoader.playPause();
		}
	}

	public synchronized void exitToMenu()
	{
		iManager.reset();
		this.gameState = GameState.PAUSED;
	}

	public synchronized void setSoundEnabled(boolean soundEnabled)
	{
		this.soundLoader.setSoundEnabled(soundEnabled);
	}

	public synchronized boolean isSoundEnabled()
	{
		return this.soundLoader.isSoundEnabled();
	}

	public synchronized void toggleSound()
	{
		this.setSoundEnabled(!this.isSoundEnabled());
	}

	public synchronized int getStartingPlayerLives()
	{
		return this.startingPlayerLives;
	}

	public synchronized void setStartingPlayerLives(int startingPlayerLives)
	{
		if(startingPlayerLives < MIN_PLAYER_LIVES || startingPlayerLives > MAX_PLAYER_LIVES)
			throw new IllegalArgumentException("Player lives must be between " + MIN_PLAYER_LIVES + " and " + MAX_PLAYER_LIVES + ".");
		this.startingPlayerLives = startingPlayerLives;
	}

	public synchronized int getStartLevel()
	{
		return this.startingLevelIndex + 1;
	}

	public synchronized void setStartLevel(int startLevel)
	{
		if(startLevel < MIN_START_LEVEL || startLevel > MAX_START_LEVEL)
			throw new IllegalArgumentException("Start level must be between " + MIN_START_LEVEL + " and " + MAX_START_LEVEL + ".");
		this.startingLevelIndex = startLevel - 1;
	}

	public void askNameAndSaveScore() 
	{
		String name = JOptionPane.showInputDialog(null,"Enter your name:","Game Over",JOptionPane.PLAIN_MESSAGE);
		if (name == null || name.trim().isEmpty())
			name = "Unknown";

		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		ScoreRecord record = new ScoreRecord(name.trim(),currentDate.toString(),currentTime.format(timeFormatter),score);
		scoreManager.saveScore(record);
	}

	public synchronized void playCustomLevel(Level customLevel)
	{
		iManager.reset();
		this.levels = new Level[] { customLevel };
		this.resetLevelRuntime();
		this.currentLevelindex = 0;
		this.starPowerCounter = 0;
		this.player_max_health = this.startingPlayerLives;
		this.score = 0;
		this.nameAsked = false;
		this.customLevelMode = true;
		this.gameState = GameState.PLAYING;
		this.playerSpawn();
		this.enemySpawn();
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

	private void drawStageLoadNumber(Graphics g, int number, int x, int y, int digitW, int digitH)
	{
		String text = String.valueOf(number);

		for (int i = 0; i < text.length(); i++)
		{
			int digit = text.charAt(i) - '0';
			g.drawImage(iLoader.getDigit(digit), x + i * digitW, y, digitW, digitH, null);
		}
	}

	public void drawStageLoad(Graphics g)
	{
		g.setColor(Color.decode("#636363"));
		g.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);

		int stageW = 156;
		int stageH = 28;
		int digitW = 32;
		int digitH = 28;
		int gap = 20;
		int stageNumber = this.currentLevelindex + 1;
		String stageNumberText = String.valueOf(stageNumber);
		int totalW = stageW + gap + stageNumberText.length() * digitW;
		int startX = (GamePanel.GAME_WIDTH - totalW) / 2;
		int startY = (GamePanel.GAME_HEIGHT - stageH) / 2;

		g.drawImage(iLoader.getStageLoadImage(), startX, startY, stageW, stageH, null);
		this.drawStageLoadNumber(g, stageNumber, startX + stageW + gap, startY, digitW, digitH);
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
		drawBonusRow(g, bonusNbr, startY + 3 * rowGap);

		int totalY = startY + 4 * rowGap + 20;
		
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(230, totalY - 10, 203, 4);

		g.drawImage(iLoader.getTotalImage(), 195, totalY, 90, 30, null);
		
		int totalTanks = whiteTanknbr + greenTanknbr + pinkTanknbr + bonusNbr;
		String totalTanksStr = String.valueOf(totalTanks);
		int totalTanksX = 345 - (totalTanksStr.length() * digitW);
		this.drawNumber(g, totalTanks, totalTanksX, totalY + 5, digitW, digitH);
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ArrayList<EnemyTankBase> getEnemytanks() {
		return enemytanks;
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

	private void drawBonusRow(Graphics g, int count, int y)
	{
		int digitW = 20;
		int digitH = 20;
		int ptsValue = count * BONUS_SCORE;
		String ptsStr = String.valueOf(ptsValue);
		String countStr = String.valueOf(count);

		int ptsNumX = 220 - (ptsStr.length() * digitW); 
		int ptsImgX = 230;
		int countNumX = 345 - (countStr.length() * digitW);
		int arrowX = 350;
		int bonusImgX = 385;

		this.drawNumber(g, ptsValue, ptsNumX, y + 10, digitW, digitH);
		g.drawImage(iLoader.getPtsImage(), ptsImgX, y + 5, 60, 30, null);
		this.drawNumber(g, count, countNumX, y + 10, digitW, digitH);
		g.drawImage(iLoader.getArrowImage(), arrowX, y + 15, 30, 15, null);
		g.drawImage(iLoader.getBonusesImage(), bonusImgX, y + 2, 110, 37, null);
	}

}
