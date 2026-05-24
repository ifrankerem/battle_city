package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {


	private static final int SPRITE_SIZE = 16;
	private BufferedImage spriteSheet;
	private BufferedImage[][] sprites; // sprites in array data structure so ı can reach easily
	
	
	public ImageLoader()
	{
		loadSpriteSheet("/resources/images/spritesheet1.png");
		splitSpriteSheet();
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

    private void splitSpriteSheet() {
        int columns = spriteSheet.getWidth() / SPRITE_SIZE;
        int rows = spriteSheet.getHeight() / SPRITE_SIZE;

        sprites = new BufferedImage[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * SPRITE_SIZE;
                int y = row * SPRITE_SIZE;

                sprites[row][col] = spriteSheet.getSubimage(
                    x,
                    y,
                    SPRITE_SIZE,
                    SPRITE_SIZE
                );
            }
        }
    }

    public BufferedImage getSprite(int row, int col) {
        return sprites[row][col];
    }

}
