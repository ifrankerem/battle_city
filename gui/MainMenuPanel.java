package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import engine.GameEngine;
import engine.GameLoop;

public class MainMenuPanel extends JPanel {

	private BufferedImage background;
	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;
	private GameEngine engine;
	private GamePanel gameWindow;
	private SidePanel sidePanelWindow;
	private GameLoop loop;

	private Rectangle newGameButton = new Rectangle(455, 520, 500, 85);
	private Rectangle mapEditorButton = new Rectangle(455, 610, 500, 85);
	private Rectangle optionsButton = new Rectangle(455, 690, 500, 85);
	private Rectangle highScoresButton = new Rectangle(455, 780, 500, 85);
	private Rectangle helpButton = new Rectangle(455, 870, 500, 85);
	private Rectangle aboutButton = new Rectangle(455, 955, 500, 85);
	private Rectangle exitButton = new Rectangle(455, 1035, 500, 85);

	public MainMenuPanel(CardLayout cards, JPanel rootPanel, MainFrame windowMain, GameEngine engine,
			GamePanel gameWindow, SidePanel sidePanelWindow)
	{
		this.background = loadImage("/resources/images/MainMenu.png");
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.engine = engine;
		this.gameWindow = gameWindow;
		this.sidePanelWindow = sidePanelWindow;
		this.setPreferredSize(new Dimension(GamePanel.GAME_WIDTH + SidePanel.SIDEPANEL_WIDTH + 30, GamePanel.GAME_HEIGHT + 20));
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				handleMousePressed(e.getX(), e.getY());
			}
		});
	}

	private BufferedImage loadImage(String path)
	{
		try {
			InputStream stream = getClass().getResourceAsStream(path);
			if(stream == null)
				throw new RuntimeException("Image not found: " + path);
			return ImageIO.read(stream);
		}
		catch(IOException e) {
			throw new RuntimeException("Could not load image: " + path, e);
		}
	}

	private void handleMousePressed(int x, int y)
	{
		int imageX = x * background.getWidth() / getWidth();
		int imageY = y * background.getHeight() / getHeight();

		if(newGameButton.contains(imageX, imageY))
			startNewGame();
		else if(mapEditorButton.contains(imageX, imageY))
			cards.show(rootPanel, "MAP_EDITOR");
		else if(optionsButton.contains(imageX, imageY))
			cards.show(rootPanel, "OPTIONS");
		else if(highScoresButton.contains(imageX, imageY))
			cards.show(rootPanel, "HIGH_SCORES");
		else if(helpButton.contains(imageX, imageY))
			cards.show(rootPanel, "HELP");
		else if(aboutButton.contains(imageX, imageY))
			cards.show(rootPanel, "ABOUT");
		else if(exitButton.contains(imageX, imageY))
			System.exit(0);
	}

	private void startNewGame()
	{
		engine.startNewGame();
		showGame();
	}

	public void showGame()
	{
		cards.show(rootPanel, "GAME");
		windowMain.pack();
		gameWindow.updatePauseControls();
		gameWindow.requestFocusInWindow();
		if(loop == null)
			loop = new GameLoop(engine, gameWindow, sidePanelWindow, cards, rootPanel, windowMain);
	}

	public void showMenu()
	{
		cards.show(rootPanel, "MENU");
		windowMain.pack();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}
}
