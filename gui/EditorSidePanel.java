package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import engine.GameEngine;
import util.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class EditorSidePanel extends JPanel {
	
	public static final int SIDEPANEL_WIDTH = 80;

	private GameEngine engine;
	private ImageLoader iLoader;
	private JButton playButton;
	private BufferedImage[] tileImages;
	private JButton exitButton;
	

	public EditorSidePanel(GameEngine engine,ImageLoader iLoader,MapEditorPanel mapEditorPanel, MainMenuPanel mainMenuPanel)
	{
		this.setPreferredSize(new Dimension(SIDEPANEL_WIDTH,GamePanel.GAME_HEIGHT));
		setBackground(Color.decode("#636363"));
		setLayout(null); // absolute positioning for the button
		this.iLoader = iLoader;
		this.engine = engine;
		this.initTileImages();
		this.initplayButton(mapEditorPanel, mainMenuPanel);
		this.initExitButton(mainMenuPanel);
	}

	private void initTileImages()
	{
		this.tileImages = new BufferedImage[] {
			iLoader.getSprite(4, 20),
			iLoader.getCellSprite(0, 32),
			iLoader.getCellSprite(2, 32),
			iLoader.getCellSprite(4, 34),
			iLoader.getWaterSprite(3, 17),
			iLoader.getCellSprite(4, 36)
		};
	}

	private void initplayButton(MapEditorPanel mapEditorPanel, MainMenuPanel mainMenuPanel)
	{
		int btnSize = 70;
		Image scaledIcon = iLoader.getPlayImage().getScaledInstance(btnSize, btnSize, Image.SCALE_FAST);
		ImageIcon pauseIcon = new ImageIcon(scaledIcon); //Jbuton wants button
		playButton = new JButton(pauseIcon);
		int btnX = (SIDEPANEL_WIDTH - btnSize) / 2;
		int btnY = GamePanel.GAME_HEIGHT - 150;
		playButton.setBounds(btnX, btnY, btnSize, btnSize);
		playButton.setBorderPainted(false); //disable default borders
		playButton.setContentAreaFilled(false); //disable default background color
		playButton.setFocusPainted(false); // disable focusing for example with tab there will be black borders we dont want that
		playButton.setOpaque(false); //set absolute opaque
		playButton.setFocusable(false); // WASD can be interrupted by this so even if button pressed WASD will gonna work properly
		playButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //when get on the button cursor gonna change to hand cursor 
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				engine.playCustomLevel(mapEditorPanel.getEmptyLevel().copy());
				mainMenuPanel.showGame();
			}
		}); //do unpause and pause also set focus on gamepanel so player can play when unpaused
		add(playButton); //adding button to sidePanel
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

	private void drawTileControls(Graphics g)
	{
		int iconSize = 28;
		int digitW = 20;
		int digitH = 18;
		int rowGap = 26;
		int startY = 24;
		int iconX = 10;
		int numberX = 48;

		for(int i = 0; i < tileImages.length; i++)
		{
			int y = startY + i * (iconSize + rowGap);
			if(i == 0)
			{
				g.setColor(Color.BLACK);
				g.fillRect(iconX, y, iconSize, iconSize);
			}
			else
				g.drawImage(tileImages[i], iconX, y, iconSize, iconSize, null);
			this.drawNumber(g, i, numberX, y + (iconSize - digitH) / 2, digitW, digitH);
		}
	}

	private void initExitButton(MainMenuPanel mainMenuPanel)
	{
		int btnSize = 95;
		Image scaledIcon = iLoader.getExitPNG().getScaledInstance(btnSize, btnSize, Image.SCALE_FAST);
		ImageIcon pauseIcon = new ImageIcon(scaledIcon); //Jbuton wants button
		exitButton = new JButton(pauseIcon);
		int btnX = (SIDEPANEL_WIDTH - btnSize) / 2;
		int btnY = GamePanel.GAME_HEIGHT - (btnSize * 2) - 60;
		exitButton.setBounds(btnX, btnY, btnSize, btnSize);
		exitButton.setBorderPainted(false); //disable default borders
		exitButton.setContentAreaFilled(false); //disable default background color
		exitButton.setFocusPainted(false); // disable focusing for example with tab there will be black borders we dont want that
		exitButton.setOpaque(false); //set absolute opaque
		exitButton.setFocusable(false); // WASD can be interrupted by this so even if button pressed WASD will gonna work properly
		exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //when get on the button cursor gonna change to hand cursor
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				engine.exitToMenu();
				mainMenuPanel.showMenu();
			}
		}); //do unpause and pause also set focus on gamepanel so player can play when unpaused
		add(exitButton); //adding button to sidePanel
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.drawTileControls(g);
	}
}
