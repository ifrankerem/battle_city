package engine;

import gui.GamePanel;

public class GameLoop implements Runnable{


	private Thread game;
	private GameEngine engine;
	private GamePanel panel;
	public static final int FPS = 60;


	public GameLoop(GameEngine engine, GamePanel panel)
	{
		this.engine = engine;
		this.panel = panel;
		game = new Thread(this);
		game.start();
	}

	public void run()
	{
		double drawInterval = 1000000000.0/FPS; // 1 second / frame
		double nextDrawTıme = System.nanoTime() + drawInterval;
		double lastCheckTıme = System.nanoTime();
		int frameCounter = 0;
		while(game != null)
		{
			engine.update();
			engine.getCurrentLevel().updateAnimation();
			panel.repaint();
			frameCounter++;
			if(System.nanoTime() - lastCheckTıme >= 1000000000)
			{
				System.out.println("FPS: " + frameCounter);
				frameCounter = 0;
				lastCheckTıme = System.nanoTime();
			}
			double remainingTime = nextDrawTıme - System.nanoTime();
			if(remainingTime < 0)
				remainingTime = 0; //there is no time left so set to 0
			try{
				game.sleep((long)(remainingTime / 1000000)); // takes long so casting it
			}
			catch(InterruptedException e)
			{
				//ı set it to 0 so there is no catch error here
			}
			nextDrawTıme = nextDrawTıme + drawInterval;
		}
	}
	
}