package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {


	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;
	public boolean space;


	public InputManager()
	{
		this.up = false;
		this.down = false;
		this.right = false;
		this.left = false;
		this.space = false;
	}

	public void keyPressed(KeyEvent e)
	{
		//System.out.println("KEY PRESSED: " + e.getKeyCode() + " (" + KeyEvent.getKeyText(e.getKeyCode()) + ")");
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W :
				{
					this.up = true;
					//System.out.println("UP = true");
					break;
				}
			case KeyEvent.VK_S:
				{
					this.down = true;
					//System.out.println("DOWN = true");
					break;
				}
			case KeyEvent.VK_A:
				{
					this.left = true;
					//System.out.println("LEFT = true");
					break;
				}
			case KeyEvent.VK_D:
				{
					this.right = true;
					//System.out.println("RIGHT = true");
					break;
				}
			case KeyEvent.VK_SPACE:
				{
					this.space = true;
					//System.out.println("SPACE = true");
					break;
				}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
				switch (e.getKeyCode()) {
			case KeyEvent.VK_W :
				{
					this.up = false;
					break;
				}
			case KeyEvent.VK_S:
				{
					this.down = false;
					break;
				}
			case KeyEvent.VK_A:
				{
					this.left = false;
					break;
				}
			case KeyEvent.VK_D:
				{
					this.right = false;
					break;
				}
			case KeyEvent.VK_SPACE:
				{
					this.space = false;
					break;
				}
		}
	}

	public void keyTyped(KeyEvent e)
	{
		//dont needed rn
	}
}