package entity;

import engine.InputManager;
import gui.GamePanel;
import util.Direction;
import util.ImageLoader;
import java.util.ArrayList;
import level.Level;

public class PlayerTank extends Tank{


	private InputManager inputs;
	private int shootCoolDown;
	private int shootCoolDownMax;

	public PlayerTank(double x, double y, double speed, Direction dir,InputManager inputs, ImageLoader iLoader)
	{
		super(x,y,speed,dir,iLoader);
		this.inputs = inputs;
		this.health = 1;
		this.shootCoolDown = 0;
		this.shootCoolDownMax = 20;
	}

	public void updateMovement(Level currentLevel,ArrayList <Bullet> bullets)
	{
		double nextX = x;
			double nextY = y;
			this.updateShootCoolDown();
			if(this.isSpawn)
			{
			if(inputs.get_up() || inputs.get_down() || inputs.get_left() || inputs.get_right()) // there will be no animation while player standing still
			{			
					if(inputs.get_up())
					{
						dir = Direction.NORTH;
						nextY -= speed;
					}
					else if(inputs.get_down())
					{
						dir = Direction.SOUTH;
						nextY += speed;
					}
					else if(inputs.get_right())
					{
						dir = Direction.EAST;
						nextX += speed;
					}
					else if(inputs.get_left())
					{
						dir = Direction.WEST;
						nextX -= speed;
					}
				if(nextY >= 0 && (nextY <= GamePanel.GAME_HEIGHT - this.hitbox.height) && (nextX >= 0 && (nextX <= GamePanel.GAME_WIDTH - this.hitbox.width)) && currentLevel.hitboxControl(nextX, nextY, this.hitbox.width, this.hitbox.height))
				{
					this.y = nextY;
					this.x = nextX;
				}
				else
				{
					this.tryAssist(nextX,nextY, this.hitbox.width, this.hitbox.height,currentLevel);
		
				}
					
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
		}
				if(inputs.get_space() && this.canShoot() && this.countBullets(bullets) < this.maxBulletCount)
			{
				this.shoot();
				this.resetShootCoolDown();
				
			}
			this.updateHitbox();

		}
	}


	public void tryAssist(double nextX, double nextY, int width ,int height,Level currentLevel)
	{
		int leftCol = (int)nextX / GamePanel.MAP_CELL_SIZE;
		int rightCol = ((int)nextX + width - 1) / GamePanel.MAP_CELL_SIZE;
		int topRow = (int)nextY / GamePanel.MAP_CELL_SIZE;
		int bottomRow = ((int)nextY + height -1) / GamePanel.MAP_CELL_SIZE;

		if(leftCol < 0 || rightCol >= GamePanel.MAP_COLS || topRow < 0 || bottomRow >= GamePanel.MAP_ROWS)
			return;

		for(int row = topRow; row <= bottomRow; row++)
		{
			for(int col = leftCol; col <= rightCol; col++)
			{
				if(currentLevel.getTiles()[row][col].getisPassable())
					continue;

				int hitLeft = (int)nextX; //player nextX and nextY hitboexes
				int hitRight = hitLeft + width;
				int hitTop = (int)nextY;
				int hitBottom = hitTop + height;
				int tileLeft = col * GamePanel.MAP_CELL_SIZE;
				int tileRight = tileLeft + GamePanel.MAP_CELL_SIZE;
				int tileTop = row * GamePanel.MAP_CELL_SIZE;
				int tileBottom = tileTop + GamePanel.MAP_CELL_SIZE;
				int overlapX = Math.min(hitRight, tileRight) - Math.max(hitLeft, tileLeft); //number of pixels which is overlapping 
				int overlapY = Math.min(hitBottom, tileBottom) - Math.max(hitTop, tileTop);	//number of pixels which is overlapping 
				int maxAssist = (int)(GamePanel.MAP_CELL_SIZE * 0.4);

				if(overlapX <= 0 || overlapY <= 0)
					continue;

				if((dir == Direction.EAST || dir == Direction.WEST) && overlapY <= maxAssist)
				{
					int assistY;
					if(hitTop < tileTop)
						assistY = -1;
					else
						assistY = 1;
					if(applyAssist(nextX, nextY, assistY, true, maxAssist, width, height, currentLevel))
						return;
				}
				else if((dir == Direction.NORTH || dir == Direction.SOUTH) && overlapX <= maxAssist)
				{
					int assistX;
					if(hitLeft < tileLeft)
						assistX = -1;
					else
						assistX = 1;
					if(applyAssist(nextX, nextY, assistX, false, maxAssist, width, height, currentLevel))
						return;
				}
			}
		}
	}

	private boolean applyAssist(double nextX, double nextY, int assistDirection, boolean assistY, int maxAssist, int width, int height, Level currentLevel)
	{
		for(int assist = 1; assist <= maxAssist; assist++) // 1->2->3->4... it doesnt directly up the pixels!
		{
			double assistedX;
			double assistedY;

			if(assistY)
			{
				assistedX = nextX;
				assistedY = nextY + (assist * assistDirection);
			}
			else
			{
				assistedX = nextX + (assist * assistDirection);
				assistedY = nextY;
			}

			if(canMoveTo(assistedX, assistedY, width, height, currentLevel))
			{
				this.x = assistedX;
				this.y = assistedY;
				return true;
			}
		}
		return false;
	}

	private boolean canMoveTo(double nextX, double nextY, int width, int height, Level currentLevel)
	{
		return nextY >= 0 && nextY <= GamePanel.GAME_HEIGHT - height && nextX >= 0 && nextX <= GamePanel.GAME_WIDTH - width && currentLevel.hitboxControl(nextX, nextY, width, height);
	}


	public void updateShootCoolDown()
	{
		if(this.shootCoolDown > 0)
			shootCoolDown--;
	}

	public boolean canShoot()
	{
		return this.shootCoolDown <= 0;
	}

	public void resetShootCoolDown()
	{
		this.shootCoolDown = this.shootCoolDownMax;
	}

	public void updateDraw()
	{
		switch (this.dir) {
			case Direction.NORTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 0);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 1);
				break;
			}
			case Direction.SOUTH:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 4);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 5);
				break;
			}
			case Direction.EAST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 6);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 7);
				break;
			}
			case Direction.WEST:
			{
				if(this.SpriteNumber == 1)
					this.image = iLoader.getSprite(0, 2);
				if(this.SpriteNumber == 2)
					this.image = iLoader.getSprite(0, 3);
				break;
			}
		}
	}


	public void incrementHealth()
	{
		this.health++;
	}

	public void incrementMaxBullets()
	{
		this.maxBulletCount++;
	}



	
	
}
