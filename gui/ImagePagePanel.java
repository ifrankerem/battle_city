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

public class ImagePagePanel extends JPanel {

	private BufferedImage background;
	private BufferedImage returnButtonImage;
	private Rectangle returnButtonBounds;
	private CardLayout cards;
	private JPanel rootPanel;
	private MainFrame windowMain;

	public ImagePagePanel(String imagePath, CardLayout cards, JPanel rootPanel, MainFrame windowMain)
	{
		this.background = loadOptionalImage(imagePath);
		this.returnButtonImage = loadImage("/resources/images/return_to_main_menu.png");
		this.cards = cards;
		this.rootPanel = rootPanel;
		this.windowMain = windowMain;
		this.returnButtonBounds = new Rectangle();
		this.setPreferredSize(new Dimension(GamePanel.GAME_WIDTH + SidePanel.SIDEPANEL_WIDTH + 30, GamePanel.GAME_HEIGHT + 20));
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				if(returnButtonBounds.contains(e.getX(), e.getY()))
				{
					ImagePagePanel.this.cards.show(ImagePagePanel.this.rootPanel, "MENU");
					ImagePagePanel.this.windowMain.pack();
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

	private BufferedImage loadOptionalImage(String path)
	{
		try {
			InputStream stream = getClass().getResourceAsStream(path);
			if(stream == null)
				return null;
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
		if(background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

		int buttonW = getWidth() / 2;
		int buttonH = buttonW * returnButtonImage.getHeight() / returnButtonImage.getWidth();
		int buttonX = (getWidth() - buttonW) / 2;
		int buttonY = getHeight() - buttonH - 30;
		returnButtonBounds.setBounds(buttonX, buttonY, buttonW, buttonH);
		g.drawImage(returnButtonImage, buttonX, buttonY, buttonW, buttonH, null);
	}
}
