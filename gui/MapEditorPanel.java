package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import level.Level;
import level.LevelLoader;
import util.ImageLoader;
import util.TileEnum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MapEditorPanel extends JPanel implements KeyListener{

	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;
	private Level emptyLevel;
	private TileEnum selectedTileType;

	public MapEditorPanel(CardLayout cards, JPanel rootPanel, MainFrame windowMain, ImageLoader iLoader)
	{
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.selectedTileType = TileEnum.EMPTY;
		this.emptyLevel = new LevelLoader(iLoader).loadMap("/resources/levels_txt/empty_level.txt");
		this.setPreferredSize(new Dimension(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				requestFocusInWindow();
			}
		});

		addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			requestFocusInWindow();
			handleEditorClick(e);
		}
		});
	}

		public void keyPressed(KeyEvent e)
	{
		//System.out.println("KEY PRESSED: " + e.getKeyCode() + " (" + KeyEvent.getKeyText(e.getKeyCode()) + ")");
		switch (e.getKeyCode()) {
			case KeyEvent.VK_0 :
				{
					this.selectedTileType = TileEnum.EMPTY;
					//System.out.println("UP = true");
					break;
				}
			case KeyEvent.VK_1:
				{
					this.selectedTileType = TileEnum.BRICK;
					//System.out.println("DOWN = true");
					break;
				}
			case KeyEvent.VK_2:
				{
					this.selectedTileType = TileEnum.STEEL;
					//System.out.println("LEFT = true");
					break;
				}
			case KeyEvent.VK_3:
				{
					this.selectedTileType = TileEnum.BUSH;
					//System.out.println("RIGHT = true");
					break;
				}
			case KeyEvent.VK_4:
				{
					this.selectedTileType = TileEnum.WATER;
					//System.out.println("SPACE = true");
					break;
				}
			case KeyEvent.VK_5:
				{
					this.selectedTileType = TileEnum.GRAYROAD;
					break;
				}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		//dont needed
	}

	public void keyTyped(KeyEvent e)
	{
		//dont needed
	}


	public void handleEditorClick(MouseEvent e) 
	{
		if(emptyLevel == null)
			return;

		int col = e.getX() / GamePanel.MAP_CELL_SIZE;
		int row = e.getY() / GamePanel.MAP_CELL_SIZE;

		if (!emptyLevel.isInsideMap(row, col))
			return;

		if (e.getButton() == MouseEvent.BUTTON1) {
			emptyLevel.setTile(row, col, selectedTileType);
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			emptyLevel.setTile(row, col, selectedTileType);
		}
		repaint();
	}



	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(emptyLevel != null)
		{
			emptyLevel.draw(g);
			emptyLevel.topdraw(g);
		}
	}

	public Level getEmptyLevel() {
		return emptyLevel;
	}

}
