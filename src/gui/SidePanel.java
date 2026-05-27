package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import engine.GameEngine;
import util.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class SidePanel extends JPanel {
	
	public static final int SIDEPANEL_WIDTH = 80;

	private GameEngine engine;
	private BufferedImage enemyCounterImage;
	private BufferedImage flagImage;
	private BufferedImage playerHealthImage;
	private ImageLoader iLoader;
	private JButton pauseButton;
	

	public SidePanel(GameEngine engine,ImageLoader iLoader,GamePanel gamePanel)
	{
		this.setPreferredSize(new Dimension(SIDEPANEL_WIDTH,GamePanel.GAME_HEIGHT));
		setBackground(Color.decode("#636363"));
		setLayout(null); // absolute positioning for the button
		this.iLoader = iLoader;
		this.engine = engine;
		this.enemyCounterImage = iLoader.getEnemyCounterImage();
		this.flagImage = iLoader.getFlagImage();
		this.playerHealthImage = iLoader.getPlayerHealthImage();
		this.initPauseButton(gamePanel);
	}

	private void initPauseButton(GamePanel gamePanel)
	{
		int btnSize = 96;
		Image scaledIcon = iLoader.getPauseImage().getScaledInstance(btnSize, btnSize, Image.SCALE_FAST); //makin png scaled to 96x96
		ImageIcon pauseIcon = new ImageIcon(scaledIcon); //Jbuton wants button
		pauseButton = new JButton(pauseIcon);
		int btnX = (SIDEPANEL_WIDTH - btnSize) / 2;
		int btnY = GamePanel.GAME_HEIGHT - 130;
		pauseButton.setBounds(btnX, btnY, btnSize, btnSize);
		pauseButton.setBorderPainted(false); //disable default borders
		pauseButton.setContentAreaFilled(false); //disable default background color
		pauseButton.setFocusPainted(false); // disable focusing for example with tab there will be black borders we dont want that
		pauseButton.setOpaque(false); //set absolute opaque
		pauseButton.setFocusable(false); // WASD can be interrupted by this so even if button pressed WASD will gonna work properly
		pauseButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //when get on the button cursor gonna change to hand cursor 
		pauseButton.addActionListener(e -> {engine.togglePause();gamePanel.requestFocusInWindow();}); //do unpause and pause also set focus on gamepanel so player can play when unpaused
		add(pauseButton); //adding button to sidePanel
	}

	private void drawNumber(Graphics g, int number, int x, int y, int digitW, int digitH)
	{
		String text = String.valueOf(number);

		for (int i = 0; i < text.length(); i++)
		{
			int digit = text.charAt(i) - '0';
			g.drawImage(iLoader.getDigit(digit), x + i * digitW, y, digitW, digitH, null);
		}
	}

	private void drawEnemyCounter(Graphics g)
	{
		int totalEnemies = engine.getMaxEnemy() + engine.getActiveEnemyCount();

		int iconW = 20;
		int iconH = 20;
		int gapX = 4;
		int gapY = 4;
		int cols = 2;
		int gridWidth = cols * iconW + (cols - 1) * gapX;
		int startX = (SIDEPANEL_WIDTH - gridWidth) / 2;
		int startY = 20;

		for (int i = 0; i < totalEnemies; i++)
		{
			int col = i % cols;
			int row = i / cols;
			int x = startX + col * (iconW + gapX);
			int y = startY + row * (iconH + gapY);
			g.drawImage(enemyCounterImage, x, y, iconW, iconH, null);
		}
	}

	/**
	 * Returns the Y position after the enemy counter section ends.
	 */
	private int getEnemyCounterEndY()
	{
		int totalEnemies = engine.getMaxEnemy() + engine.getActiveEnemyCount();
		int iconH = 20;
		int gapY = 4;
		int rows = (totalEnemies + 1) / 2;
		return 20 + rows * (iconH + gapY);
	}

	private void drawPlayerInfo(Graphics g)
	{
		int iconW = 48;
		int iconH = 28;
		int digitW = 20;
		int digitH = 18;
		int sectionHeight = iconH + 4 + digitH;

		int topBound = getEnemyCounterEndY();
		int bottomBound = getFlagStartY();
		int startY = (topBound + (bottomBound - topBound - sectionHeight) / 2) - 80;
		int startX = (SIDEPANEL_WIDTH - iconW) / 2;

		g.drawImage(playerHealthImage, startX, startY, iconW, iconH, null);

		int lives = engine.getPlayerMaxHealth();
		String livesStr = String.valueOf(lives);
		int numWidth = livesStr.length() * digitW;
		int numX = startX + iconW - numWidth;
		this.drawNumber(g, lives, numX, startY + iconH + 4, digitW, digitH);
	}

	private int getFlagStartY()
	{
		int iconH = 44;
		int digitH = 18;
		int sectionHeight = iconH + 4 + digitH;
		return GamePanel.GAME_HEIGHT - sectionHeight - 20;
	}

	private void drawFlag(Graphics g)
	{
		int iconW = 48;
		int iconH = 44;
		int digitW = 20;
		int digitH = 18;

		int startY = getFlagStartY() - 150;
		int startX = (SIDEPANEL_WIDTH - iconW) / 2;

		g.drawImage(flagImage, startX, startY, iconW, iconH, null);

		int levelNumber = engine.getCurrentLevelIndex() + 1;
		String levelStr = String.valueOf(levelNumber);
		int numWidth = levelStr.length() * digitW;
		int numX = startX + iconW - numWidth;
		this.drawNumber(g, levelNumber, numX, startY + iconH + 4, digitW, digitH);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.drawEnemyCounter(g);
		this.drawPlayerInfo(g);
		this.drawFlag(g);
	}
}
