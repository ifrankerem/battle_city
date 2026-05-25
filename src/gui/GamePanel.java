package gui;

import javax.swing.JPanel;

import engine.GameEngine;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;


public class GamePanel extends JPanel {

	public static final int MAP_CELL_SIZE = 24;
	public static final int MAP_ROWS = 26;
	public static final int MAP_COLS = 26;

	public static final int GAME_WIDTH = MAP_COLS * MAP_CELL_SIZE;
	public static final int GAME_HEIGHT = MAP_ROWS * MAP_CELL_SIZE;

	public static final int ENTITY_SIZE = 36;
	private GameEngine engine;

    public GamePanel(GameEngine engine) {
		this.engine = engine;
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		this.setDoubleBuffered(true); // improve performance
		this.setBackground(Color.BLACK);
		this.addKeyListener(engine.getiManager());
		this.setFocusable(true); // "focus" to receive key inputs
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		engine.draw(g);
	}
}