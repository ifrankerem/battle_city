package gui;

import java.awt.CardLayout;
import java.awt.Color;
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

public class GameOverPanel extends JPanel {

	private BufferedImage gameOverImage;
	private BufferedImage returnButtonImage;
	private Rectangle returnButtonBounds;
	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;
	private GameEngine engine;

	public GameOverPanel(CardLayout cards, JPanel rootPanel, MainFrame windowMain, GameEngine engine)
	{
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.engine = engine;
		this.gameOverImage = loadImage("/resources/images/gameover.png");
		this.returnButtonImage = loadImage("/resources/images/return_to_main_menu.png");
		this.returnButtonBounds = new Rectangle();
		this.setPreferredSize(new Dimension(GamePanel.GAME_WIDTH + SidePanel.SIDEPANEL_WIDTH + 30, GamePanel.GAME_HEIGHT + 20));
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				if(returnButtonBounds.contains(e.getX(), e.getY()))
				{
					GameOverPanel.this.engine.getiManager().reset();
					GameOverPanel.this.cards.show(GameOverPanel.this.rootPanel, "MENU");
					GameOverPanel.this.windowMain.pack();
				}
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

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		int gameOverW = getWidth() / 2;
		int gameOverH = gameOverW * gameOverImage.getHeight() / gameOverImage.getWidth();
		int gameOverX = (getWidth() - gameOverW) / 2;
		int gameOverY = (getHeight() - gameOverH) / 2 - 80;
		g.drawImage(gameOverImage, gameOverX, gameOverY, gameOverW, gameOverH, null);

		int buttonW = getWidth() / 2;
		int buttonH = buttonW * returnButtonImage.getHeight() / returnButtonImage.getWidth();
		int buttonX = (getWidth() - buttonW) / 2;
		int buttonY = gameOverY + gameOverH + 50;
		returnButtonBounds.setBounds(buttonX, buttonY, buttonW, buttonH);
		g.drawImage(returnButtonImage, buttonX, buttonY, buttonW, buttonH, null);
	}
}
