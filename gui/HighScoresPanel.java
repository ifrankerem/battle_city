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
import java.util.ArrayList;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import util.ScoreManager;
import util.ScoreRecord;

public class HighScoresPanel extends JPanel {

	private BufferedImage background;
	private BufferedImage returnButtonImage;
	private Rectangle returnButtonBounds;
	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;
	private ScoreManager scoreManager;
    private ArrayList<ScoreRecord> topScores;

	public HighScoresPanel(CardLayout cards, JPanel rootPanel, MainFrame windowMain)
	{
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.background = loadImage("/resources/images/HighScores.png");
		this.returnButtonImage = loadImage("/resources/images/return_to_main_menu.png");
		this.returnButtonBounds = new Rectangle();
		this.setPreferredSize(new Dimension(GamePanel.GAME_WIDTH + SidePanel.SIDEPANEL_WIDTH + 30, GamePanel.GAME_HEIGHT + 20));
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				if(returnButtonBounds.contains(e.getX(), e.getY()))
				{
					HighScoresPanel.this.cards.show(HighScoresPanel.this.rootPanel, "MENU");
					HighScoresPanel.this.windowMain.pack();
				}
			}
			});
			this.scoreManager = new ScoreManager();
			this.refreshScores();
		}

		private void refreshScores()
		{
			this.topScores = scoreManager.getTopScores(10);
		}

		public void setVisible(boolean visible)
		{
			super.setVisible(visible);
			if(visible)
			{
				this.refreshScores();
				this.repaint();
			}
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

	private void drawScoreTable(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 18));
        g.setColor(Color.WHITE);

        int startX = 130;
        int startY = 190;
        int rowHeight = 32;

        g.drawString("RANK", startX, startY);
        g.drawString("NAME", startX + 80, startY);
        g.drawString("SCORE", startX + 260, startY);
        g.drawString("DATE", startX + 380, startY);

        for (int i = 0; i < topScores.size(); i++) {
            ScoreRecord record = topScores.get(i);

            int y = startY + rowHeight * (i + 1);

            g.drawString(String.valueOf(i + 1), startX, y);
            g.drawString(record.getName(), startX + 80, y);
            g.drawString(String.valueOf(record.getScore()), startX + 260, y);
            g.drawString(record.getDate(), startX + 380, y);
        }
    }

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        drawScoreTable(g);
		int buttonW = getWidth() / 2;
		int buttonH = buttonW * returnButtonImage.getHeight() / returnButtonImage.getWidth();
		int buttonX = (getWidth() - buttonW) / 2;
		int buttonY = getHeight() - buttonH - 30;
		returnButtonBounds.setBounds(buttonX, buttonY, buttonW, buttonH);
		g.drawImage(returnButtonImage, buttonX, buttonY, buttonW, buttonH, null);
	}
}
