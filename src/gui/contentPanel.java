package gui;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;


public class contentPanel extends JPanel {



	public contentPanel()
	{
        setLayout(new BorderLayout(10, 0)); // GamePanel ile SidePanel arası 10 px
        setBackground(Color.GRAY);

        setBorder(new EmptyBorder(10, 10, 10, 10)); // top, left, bottom, right

	}

}