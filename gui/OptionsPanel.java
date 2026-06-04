package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import engine.GameEngine;
import util.ScoreManager;

public class OptionsPanel extends JPanel {

	private BufferedImage background;
	private BufferedImage returnButtonImage;
	private Rectangle returnButtonBounds;
	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;
	private GameEngine engine;
	private ScoreManager scoreManager;

	private Rectangle soundButton = new Rectangle(130, 350, 1000, 100);
	private Rectangle soundDisplayBounds = new Rectangle(850, 340, 120, 100);
	private Rectangle livesMinusButton = new Rectangle(690, 485, 100, 100);
	private Rectangle livesValueButton = new Rectangle(850, 485, 120, 100);
	private Rectangle livesPlusButton = new Rectangle(1030, 485, 100, 100);
	private Rectangle levelMinusButton = new Rectangle(690, 635, 100, 100);
	private Rectangle levelValueButton = new Rectangle(850, 635, 120, 100);
	private Rectangle levelPlusButton = new Rectangle(1030, 635, 100, 100);
	private Rectangle resetScoresButton = new Rectangle(145, 785, 735, 100);

	public OptionsPanel(CardLayout cards, JPanel rootPanel, MainFrame windowMain, GameEngine engine)
	{
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.engine = engine;
		this.scoreManager = new ScoreManager();
		this.background = loadImage("/resources/images/Options.png");
		this.returnButtonImage = loadImage("/resources/images/return_to_main_menu.png");
		this.returnButtonBounds = new Rectangle();
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
		if(returnButtonBounds.contains(x, y))
		{
			cards.show(rootPanel, "MENU");
			windowMain.pack();
			return;
		}

		int imageX = x * background.getWidth() / getWidth();
		int imageY = y * background.getHeight() / getHeight();

		if(soundButton.contains(imageX, imageY))
		{
			engine.toggleSound();
			repaint();
		}
		else if(livesMinusButton.contains(imageX, imageY))
			updatePlayerLives(engine.getStartingPlayerLives() - 1);
		else if(livesPlusButton.contains(imageX, imageY))
			updatePlayerLives(engine.getStartingPlayerLives() + 1);
		else if(livesValueButton.contains(imageX, imageY))
			promptPlayerLives();
		else if(levelMinusButton.contains(imageX, imageY))
			updateStartLevel(engine.getStartLevel() - 1);
		else if(levelPlusButton.contains(imageX, imageY))
			updateStartLevel(engine.getStartLevel() + 1);
		else if(levelValueButton.contains(imageX, imageY))
			promptStartLevel();
		else if(resetScoresButton.contains(imageX, imageY))
			resetHighScores();
	}

	private void updatePlayerLives(int lives)
	{
		try {
			engine.setStartingPlayerLives(lives);
			repaint();
		}
		catch(IllegalArgumentException e) {
			showInvalidOption(e.getMessage());
		}
	}

	private void updateStartLevel(int startLevel)
	{
		try {
			engine.setStartLevel(startLevel);
			repaint();
		}
		catch(IllegalArgumentException e) {
			showInvalidOption(e.getMessage());
		}
	}

	private void promptPlayerLives()
	{
		String input = JOptionPane.showInputDialog(this, "Enter player lives (" + GameEngine.MIN_PLAYER_LIVES + "-" + GameEngine.MAX_PLAYER_LIVES + "):", engine.getStartingPlayerLives());
		if(input == null)
			return;

		try {
			engine.setStartingPlayerLives(Integer.parseInt(input.trim()));
			repaint();
		}
		catch(NumberFormatException e) {
			showInvalidOption("Player lives must be a number between " + GameEngine.MIN_PLAYER_LIVES + " and " + GameEngine.MAX_PLAYER_LIVES + ".");
		}
		catch(IllegalArgumentException e) {
			showInvalidOption(e.getMessage());
		}
	}

	private void promptStartLevel()
	{
		String input = JOptionPane.showInputDialog(this, "Enter start level (" + GameEngine.MIN_START_LEVEL + "-" + GameEngine.MAX_START_LEVEL + "):", engine.getStartLevel());
		if(input == null)
			return;

		try {
			engine.setStartLevel(Integer.parseInt(input.trim()));
			repaint();
		}
		catch(NumberFormatException e) {
			showInvalidOption("Start level must be a number between " + GameEngine.MIN_START_LEVEL + " and " + GameEngine.MAX_START_LEVEL + ".");
		}
		catch(IllegalArgumentException e) {
			showInvalidOption(e.getMessage());
		}
	}

	private void resetHighScores()
	{
		int choice = JOptionPane.showConfirmDialog(this, "Reset all high scores?", "Reset High Scores", JOptionPane.YES_NO_OPTION);
		if(choice != JOptionPane.YES_OPTION)
			return;

		if(scoreManager.clearScores())
			JOptionPane.showMessageDialog(this, "High scores reset.", "Options", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(this, "High scores could not be reset.", "Options", JOptionPane.WARNING_MESSAGE);
	}

	private void showInvalidOption(String message)
	{
		JOptionPane.showMessageDialog(this, message, "Invalid Option", JOptionPane.WARNING_MESSAGE);
	}

	private int scaleX(int imageX)
	{
		return imageX * getWidth() / background.getWidth();
	}

	private int scaleY(int imageY)
	{
		return imageY * getHeight() / background.getHeight();
	}

	private Rectangle scaleRectangle(Rectangle imageRectangle)
	{
		return new Rectangle(scaleX(imageRectangle.x), scaleY(imageRectangle.y), scaleX(imageRectangle.width), scaleY(imageRectangle.height));
	}

	private void drawCenteredText(Graphics g, String text, Rectangle bounds)
	{
		FontMetrics metrics = g.getFontMetrics();
		int textX = bounds.x + (bounds.width - metrics.stringWidth(text)) / 2;
		int textY = bounds.y + (bounds.height - metrics.getHeight()) / 2 + metrics.getAscent();
		g.drawString(text, textX, textY);
	}

	private void drawOptionValues(Graphics g)
	{
		String soundText = "OFF";
		if(engine.isSoundEnabled())
			soundText = "ON";

		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, Math.max(18, scaleY(52))));

		drawCenteredText(g, soundText, scaleRectangle(soundDisplayBounds));
		drawCenteredText(g, "<", scaleRectangle(livesMinusButton));
		drawCenteredText(g, String.valueOf(engine.getStartingPlayerLives()), scaleRectangle(livesValueButton));
		drawCenteredText(g, ">", scaleRectangle(livesPlusButton));
		drawCenteredText(g, "<", scaleRectangle(levelMinusButton));
		drawCenteredText(g, String.valueOf(engine.getStartLevel()), scaleRectangle(levelValueButton));
		drawCenteredText(g, ">", scaleRectangle(levelPlusButton));
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		drawOptionValues(g);

		int buttonW = getWidth() / 2;
		int buttonH = buttonW * returnButtonImage.getHeight() / returnButtonImage.getWidth();
		int buttonX = (getWidth() - buttonW) / 2;
		int buttonY = getHeight() - buttonH - 65;
		returnButtonBounds.setBounds(buttonX, buttonY, buttonW, buttonH);
		g.drawImage(returnButtonImage, buttonX, buttonY, buttonW, buttonH, null);
	}
}
