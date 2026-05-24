package entity;
import java.awt.font.GlyphMetrics;
import java.security.DrbgParameters.NextBytes;

import engine.InputManager;
import gui.GamePanel;
import model.Direction;
import util.ImageLoader;

public class PlayerTank extends Tank{


	private InputManager inputs;

	public PlayerTank(double x, double y, double speed, Direction dir,InputManager inputs, ImageLoader iLoader)
	{
		super(x,y,speed,dir,iLoader);
		this.inputs = inputs;
	}

	public void updateMovement()
	{
		double nextX = x;
		double nextY = y;
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
				if(nextY >= 0 && nextY <= GamePanel.HEIGHT - GamePanel.TILE_SIZE)
					this.y = nextY;
				if(nextX >= 0 && nextX <= GamePanel.WIDTH - GamePanel.TILE_SIZE)
					this.x = nextX;
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



	
	
}