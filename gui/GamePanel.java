package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import engine.GameEngine;
import util.ImageLoader;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GamePanel extends JPanel {

	public static final int MAP_CELL_SIZE = 24;
	public static final int MAP_ROWS = 26;
	public static final int MAP_COLS = 26;

	public static final int GAME_WIDTH = MAP_COLS * MAP_CELL_SIZE;
	public static final int GAME_HEIGHT = MAP_ROWS * MAP_CELL_SIZE;

	public static final int ENTITY_SIZE = 36;
	public static final int BULLET_SIZE = 8;
	private GameEngine engine;
	private JButton returnButton;
	private Runnable returnToMenuAction;

	public GamePanel(GameEngine engine, ImageLoader iLoader) {
		this.engine = engine;
		this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		this.setDoubleBuffered(true); // improve performance
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		this.addKeyListener(engine.getiManager());
		this.setFocusable(true); // "focus" to receive key inputs
		this.initReturnButton(iLoader);
	}

	private void initReturnButton(ImageLoader iLoader)
	{
		int iconW = 408;
		int iconH = iconW * iLoader.getReturnToMainMenuImage().getHeight() / iLoader.getReturnToMainMenuImage().getWidth();
		int btnW = iconW;
		int btnH = 42;
		Image scaledIcon = iLoader.getReturnToMainMenuImage().getScaledInstance(iconW, iconH, Image.SCALE_FAST);
		returnButton = new JButton(new ImageIcon(scaledIcon));
		returnButton.setBounds((GAME_WIDTH - btnW) / 2, (GAME_HEIGHT / 2) + 78, btnW, btnH);
		returnButton.setBorderPainted(false);
		returnButton.setContentAreaFilled(false);
		returnButton.setFocusPainted(false);
		returnButton.setOpaque(false);
		returnButton.setFocusable(false);
		returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		returnButton.setVisible(false);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(returnToMenuAction != null)
				{
					returnButton.setVisible(false);
					returnToMenuAction.run();
				}
			}
		});
		add(returnButton);
	}

	public void setReturnToMenuAction(Runnable returnToMenuAction)
	{
		this.returnToMenuAction = returnToMenuAction;
		this.updatePauseControls();
	}

	public void updatePauseControls()
	{
		boolean shouldShowButton = returnToMenuAction != null && engine.get_isPaused();
		if(returnButton.isVisible() != shouldShowButton)
			returnButton.setVisible(shouldShowButton);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		engine.draw(g);
		this.updatePauseControls();
	}
}
