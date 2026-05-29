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
		if(inputs.up || inputs.down || inputs.left || inputs.right) // there will be no animation while player standing still
		{			
				if(inputs.up)
				{
					dir = Direction.NORTH;
					nextY -= speed;
				}
				else if(inputs.down)
				{
					dir = Direction.SOUTH;
					nextY += speed;
				}
				else if(inputs.right)
				{
					dir = Direction.EAST;
					nextX += speed;
				}
				else if(inputs.left)
				{
					dir = Direction.WEST;
					nextX -= speed;
				}
				if(nextY >= 0 && (nextY <= GamePanel.GAME_HEIGHT - this.hitbox.height) && (nextX >= 0 && (nextX <= GamePanel.GAME_WIDTH - this.hitbox.width)) && currentLevel.hitboxControl(nextX, nextY, this.hitbox.width, this.hitbox.height))
				{
					this.y = nextY;
					this.x = nextX;
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
			if(inputs.space && this.canShoot() && this.countBullets(bullets) < this.maxBulletCount)
			{
				this.shoot();
				this.resetShootCoolDown();
			}
			this.updateHitbox();

		}
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
