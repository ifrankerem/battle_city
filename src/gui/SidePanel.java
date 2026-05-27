package gui;

import javax.swing.JPanel;

import engine.GameEngine;
import util.ImageLoader;

import java.awt.Graphics;
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
	

	public SidePanel(GameEngine engine,ImageLoader iLoader)
	{
		this.setPreferredSize(new Dimension(SIDEPANEL_WIDTH,GamePanel.GAME_HEIGHT));
		setBackground(Color.decode("#636363"));
		this.iLoader = iLoader;
		this.engine = engine;
		this.enemyCounterImage = iLoader.getEnemyCounterImage();
		this.flagImage = iLoader.getFlagImage();
		this.playerHealthImage = iLoader.getPlayerHealthImage();
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

	private int getEnemyCounterEndY()
	{
		int totalEnemies = engine.getMaxEnemy() + engine.getActiveEnemyCount();
		int iconH = 20;
		int gapY = 4;
		int rows = (totalEnemies + 1) / 2; // ceiling division for 2 columns
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
		int startY = topBound + (bottomBound - topBound - sectionHeight) / 2;
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

		int startY = getFlagStartY();
		int startX = (SIDEPANEL_WIDTH - iconW) / 2;

		g.drawImage(flagImage, startX, startY, iconW, iconH, null);

		int levelNumber = engine.getCurrentLevelIndex() + 1;
		String levelStr = String.valueOf(levelNumber);
		int numWidth = levelStr.length() * digitW;
		int numX = startX + iconW - numWidth; // right-align with icon
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