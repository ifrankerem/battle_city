package entity;

import java.util.ArrayList;

import gui.GamePanel;
import util.Direction;
import util.ImageLoader;
import level.Level;

public abstract class EnemyTankBase extends Tank{

	protected int directionCounter;
	protected int score;
	protected boolean canMove;
	public EnemyTankBase(double x, double y, double speed, Direction dir,ImageLoader iLoader)
	{
		super(x, y, speed, dir, iLoader);
		this.health = 1;
		this.directionCounter = 0;
		this.canMove = true;
	}


	public void randomDir()
	{
		int random = (int)(Math.random() * 100);
		if(random < 40)
			this.dir = Direction.SOUTH; //by %40 chance going down
		else if(random < 60)
			this.dir = Direction.NORTH;
		else if(random < 80)
			this.dir = Direction.EAST;
		else
			this.dir = Direction.WEST;
	}

	public void enemyAI()
	{
		directionCounter++;
		if(directionCounter >= 60) //per frame
		{
			this.randomDir();
			this.directionCounter = 0;
			this.shoot();
		}
	}

	public int getScore() {
		return score;
	}

	public void updateMovement(Level currentLevel,ArrayList <Bullet> bullets)
	{
		if(this.isSpawn && this.canMove)
		{
			double nextX = x;
			double nextY = y;
			if(this.dir == Direction.NORTH)
				nextY -= speed;
			else if(this.dir == Direction.SOUTH)
				nextY += speed;
			else if(dir == Direction.EAST)
				nextX += speed;
			else if(dir == Direction.WEST)
				nextX -= speed;
			if(nextY >= 0 && (nextY <= GamePanel.GAME_HEIGHT - this.hitbox.height) && (nextX >= 0 && (nextX <= GamePanel.GAME_WIDTH - this.hitbox.width)) && currentLevel.hitboxControl(nextX, nextY, this.hitbox.width, this.hitbox.height))
			{
				this.y = nextY;
				this.x = nextX;
			}
			else
				this.randomDir();
			this.enemyAI();
			//updateMovement func calls 60 times in a second so in every 10 frame i reset the counter so animation will be smooth. the animation will change in every 10 frame
			this.SpriteCounter++;
			if(this.SpriteCounter > 10)
			{
				if(this.SpriteNumber == 1)
					this.SpriteNumber = 2;
				else if(this.SpriteNumber == 2)
					this.SpriteNumber = 1;
				this.SpriteCounter = 0;
			}
			this.updateHitbox();
		}
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

}
