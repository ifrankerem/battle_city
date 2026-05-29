import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.GameEngine;
import engine.InputManager;
import gui.GameOverPanel;
import gui.GamePanel;
import gui.HighScoresPanel;
import gui.ImagePagePanel;
import gui.MainMenuPanel;
import gui.MainFrame;
import gui.SidePanel;
import gui.contentPanel;
import level.LevelLoader;
import util.ImageLoader;

public class Main {

	public static void main(String[] args) {
		InputManager iManager = new InputManager();
		ImageLoader iLoader = new ImageLoader();
		LevelLoader levelLoader = new LevelLoader(iLoader);
		levelLoader.loadMaps();
		GameEngine engine = new GameEngine(iManager, iLoader, levelLoader.getLevels());
		MainFrame windowMain = new MainFrame();
		windowMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowMain.setResizable(false);
		CardLayout cards = new CardLayout();
		JPanel rootPanel = new JPanel(cards);
		contentPanel contentP = new contentPanel();
		GamePanel gameWindow = new GamePanel(engine);
		contentP.add(gameWindow,BorderLayout.CENTER);
		SidePanel sidePanelWindow = new SidePanel(engine,iLoader,gameWindow);
		contentP.add(sidePanelWindow,BorderLayout.EAST);
		MainMenuPanel mainMenuPanel = new MainMenuPanel(cards, rootPanel, windowMain, engine, gameWindow, sidePanelWindow);
		rootPanel.add(mainMenuPanel, "MENU");
		rootPanel.add(contentP, "GAME");
		rootPanel.add(new GameOverPanel(cards, rootPanel, windowMain, engine), "GAME_OVER");
		rootPanel.add(new ImagePagePanel("/resources/images/MapEditor.png", cards, rootPanel, windowMain), "MAP_EDITOR");
		rootPanel.add(new HighScoresPanel(cards, rootPanel, windowMain, engine), "HIGH_SCORES");
		rootPanel.add(new ImagePagePanel("/resources/images/Help.png", cards, rootPanel, windowMain), "HELP");
		rootPanel.add(new ImagePagePanel("/resources/images/About.png", cards, rootPanel, windowMain), "ABOUT");
		windowMain.add(rootPanel);
		windowMain.pack(); //auto setSize according to that ı gave windows
		windowMain.setVisible(true);
	}	
}
	
