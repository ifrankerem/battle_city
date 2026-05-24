package gui;

import javax.swing.JPanel;
import java.awt.Dimension;

public class SidePanel extends JPanel {
	
	public static final int SIDEPANEL_WIDTH = 160;


	public SidePanel()
	{
		this.setPreferredSize(new Dimension(160,GamePanel.WIDTH));
	}
}