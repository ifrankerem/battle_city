import java.awt.BorderLayout;
import javax.swing.JFrame;
import engine.GameEngine;
import engine.GameLoop;
import engine.InputManager;
import gui.GamePanel;
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
		contentPanel contentP = new contentPanel();
		GamePanel gameWindow = new GamePanel(engine);
		contentP.add(gameWindow,BorderLayout.CENTER);
		SidePanel sidePanelWindow = new SidePanel();
		contentP.add(sidePanelWindow,BorderLayout.EAST);
		windowMain.add(contentP);
		windowMain.pack(); //auto setSize according to that ı gave windows
		windowMain.setVisible(true);
		gameWindow.requestFocus();
		GameLoop loop = new GameLoop(engine,gameWindow);
	}	
}
	
