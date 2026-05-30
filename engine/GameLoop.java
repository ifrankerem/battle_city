package engine;

import java.awt.CardLayout;
import javax.swing.JPanel;
import gui.GamePanel;
import gui.MainFrame;
import gui.SidePanel;
import util.GameState;

public class GameLoop implements Runnable{


	private Thread game;
	private GameEngine engine;
	private GamePanel gamePanel;
	private SidePanel sidePanel;
	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;
	private boolean gameOverCardShown;
	public static final int FPS = 60;


	public GameLoop(GameEngine engine, GamePanel panel, SidePanel sidePanel, CardLayout cards, JPanel rootPanel, MainFrame windowMain)
	{
		this.engine = engine;
		this.gamePanel = panel;
		this.sidePanel = sidePanel;
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.gameOverCardShown = false;
		game = new Thread(this);
		game.start();
	}

	public void run()
	{
		long drawInterval = 1_000_000_000L/FPS; // 1 second / frame
		long nextDrawTime = System.nanoTime() + drawInterval;
		//int frameCounter = 0;
		while(game != null)
		{
			if(!engine.get_isPaused())
			{
				engine.update();
				engine.updateCurrentLevelAnimation();
			}
			if(engine.getGameState() == GameState.GAME_OVER && !gameOverCardShown)
			{
				gameOverCardShown = true;
				cards.show(rootPanel, "GAME_OVER");
				windowMain.pack();
			}
			else if(engine.getGameState() == GameState.PLAYING)
			{
				gameOverCardShown = false;
			}
			gamePanel.repaint();
			sidePanel.repaint();
			// frameCounter++;
			// if(System.nanoTime() - lastCheckTıme >= 1000000000)
			// {
			// 	System.out.println("FPS: " + frameCounter);
			// 	frameCounter = 0;
			// 	lastCheckTıme = System.nanoTime();
			// }
			long remainingTime = nextDrawTime - System.nanoTime();
			try{
				if(remainingTime > 0)
					Thread.sleep(remainingTime / 1000000);
				else
					nextDrawTime = System.nanoTime();
			}
			catch(InterruptedException e)
			{
				//ı set it to 0 so there is no catch error here
			}
			nextDrawTime = nextDrawTime + drawInterval;
		}
	}
	
}
