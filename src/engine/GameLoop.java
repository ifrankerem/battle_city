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
		double drawInterval = 1_000_000_000.0/FPS; // 1 second / frame
		double nextDrawTıme = System.nanoTime() + drawInterval;
		double lastCheckTıme = System.nanoTime();
		//int frameCounter = 0;
		while(game != null)
		{
			if(!engine.get_isPaused())
			{
				engine.update();
				engine.getCurrentLevel().updateAnimation();
			}
			if(engine.getGameState() == GameState.GAME_OVER && !gameOverCardShown)
			{
				cards.show(rootPanel, "GAME_OVER");
				windowMain.pack();
				gameOverCardShown = true;
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
