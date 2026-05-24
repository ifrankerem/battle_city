package model;

import java.awt.image.BufferedImage;
import util.ImageLoader;

abstract public class GameObject {

	protected double x;
	protected double y;
	protected ImageLoader iLoader;
	protected BufferedImage image;
	protected int SpriteCounter;
	protected int SpriteNumber;


	public GameObject(double x, double y,ImageLoader iLoader)
	{
		this.x = x;
		this.y = y;
		this.iLoader = iLoader;
		this.SpriteCounter = 0;
		this.SpriteNumber = 1;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}