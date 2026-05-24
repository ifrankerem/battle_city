import java.awt.BorderLayout;
import javax.swing.JFrame;
import engine.GameEngine;
import engine.GameLoop;
import engine.InputManager;
import gui.GamePanel;
import gui.MainFrame;
import gui.SidePanel;
import util.ImageLoader;

public class Main {

	public static void main(String[] args) {
		InputManager iManager = new InputManager();
		ImageLoader iLoader = new ImageLoader();
		GameEngine engine = new GameEngine(iManager,iLoader);
		MainFrame windowMain = new MainFrame();
		windowMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowMain.setResizable(false);
		GamePanel gameWindow = new GamePanel(engine);
		windowMain.add(gameWindow,BorderLayout.CENTER);
		SidePanel sidePanelWindow = new SidePanel();
		windowMain.add(sidePanelWindow,BorderLayout.EAST);
		windowMain.pack(); //auto setSize according to that ı gave windows
		windowMain.setVisible(true);
		gameWindow.requestFocus();
		GameLoop loop = new GameLoop(engine,gameWindow);
	}	
}
	