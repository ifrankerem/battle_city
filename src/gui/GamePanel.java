package gui;

import javax.swing.JPanel;

import engine.GameEngine;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;


public class GamePanel extends JPanel {

    public static final int TILE_SIZE = 32;
    public static final int ROWS = 13;
    public static final int COLS = 13;

    public static final int WIDTH = COLS * TILE_SIZE;
    public static final int HEIGHT = ROWS * TILE_SIZE;	// 416x416
	private GameEngine engine;

    public GamePanel(GameEngine engine) {
		this.engine = engine;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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