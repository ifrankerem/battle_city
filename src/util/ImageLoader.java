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
	
	
	public ImageLoader()
	{
		loadSpriteSheet("/resources/images/spritesheet1.png");
		loadSpriteSheet2("/resources/images/spritesheet2.png");
	}

	private void loadSpriteSheet(String path) {
        try {
            InputStream input = getClass().getResourceAsStream(path);

            if (input == null) {
                throw new IllegalArgumentException("Sprite sheet not found: " + path);
            }

            spriteSheet = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void loadSpriteSheet2(String path) {
        try {
            InputStream input = getClass().getResourceAsStream(path);

            if (input == null) {
                throw new IllegalArgumentException("Sprite sheet not found: " + path);
            }

            spriteSheet2 = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
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




}
