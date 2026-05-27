package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {


	private static final int SPRITE_SIZE = 16;
	private BufferedImage spriteSheet;
	private BufferedImage spriteSheet2;
	private BufferedImage[][] sprites; // sprites in array data structure so ı can reach easily
	private BufferedImage bullet_north;
	private BufferedImage bullet_east;
	private BufferedImage bullet_west;
	private BufferedImage bullet_south;
	private BufferedImage boom1;
	private BufferedImage boom2;
	private BufferedImage boom3;
	private BufferedImage tankboom1;
	private BufferedImage tankboom2;

	public ImageLoader()
	{
		this.spriteSheet = loadImage("/resources/images/spritesheet1.png");
		this.spriteSheet2 = loadImage("/resources/images/spritesheet2.png");
		this.bullet_north = loadImage("/resources/images/bullet_north.png");
		this.bullet_east = loadImage("/resources/images/bullet_east.png");
		this.bullet_west = loadImage("/resources/images/bullet_west.png");
		this.bullet_south = loadImage("/resources/images/bullet_south.png");
		this.boom1 = loadImage("/resources/images/boom1.png");
		this.boom2 = loadImage("/resources/images/boom2.png");
		this.boom3 = loadImage("/resources/images/boom3.png");
		this.tankboom1 = loadImage("/resources/images/tankboom1.png");
		this.tankboom2 = loadImage("/resources/images/tankboom2.png");
	}

	
	public BufferedImage loadImage(String path) {
		try {
			InputStream stream = getClass().getResourceAsStream(path);

			if (stream == null) {
				throw new RuntimeException("Image not found: " + path);
			}

			return ImageIO.read(stream);
		}
		catch (IOException e) {
			throw new RuntimeException("Could not load image: " + path, e);
		}
	}

    public BufferedImage getWaterSprite(int row, int col) {
        int x = col * SPRITE_SIZE;
        int y = row * SPRITE_SIZE;
        return spriteSheet.getSubimage(x, y, SPRITE_SIZE-1, SPRITE_SIZE-1);
    }

    public BufferedImage getEntitySprite(int row, int col) {
        int x = col * SPRITE_SIZE;
        int y = row * SPRITE_SIZE;
        return spriteSheet2.getSubimage(x, y, SPRITE_SIZE-1, SPRITE_SIZE-1);
    }

    public BufferedImage getCellSprite(int row, int col) {
        int x = col * 8;
        int y = row * 8;
        return spriteSheet2.getSubimage(x, y, 8, 8);
    }


    public BufferedImage getEntitySprite2(int row, int col) {
        int x = col * SPRITE_SIZE;
        int y = row * SPRITE_SIZE;
        return spriteSheet2.getSubimage(x, y, SPRITE_SIZE-1, SPRITE_SIZE-1);
    }

	public BufferedImage getSprite(int row, int col) {
        return getEntitySprite(row, col);
    }

	public BufferedImage getBullet_east() {
		return bullet_east;
	}

	public BufferedImage getBullet_north() {
		return bullet_north;
	}

	public BufferedImage getBullet_south() {
		return bullet_south;

	}public BufferedImage getBullet_west() {
		return bullet_west;
	}

	public BufferedImage getBoom1() {
		return boom1;
	}
	public BufferedImage getBoom2() {
		return boom2;
	}
	public BufferedImage getBoom3() {
		return boom3;
	}

	public BufferedImage getTankboom1() {
		return tankboom1;
	}
	public BufferedImage getTankboom2() {
		return tankboom2;
	}


}
