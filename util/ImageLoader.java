package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {


	private static final int SPRITE_SIZE = 16;
	private BufferedImage spriteSheet;
	private BufferedImage spriteSheet2;
	private BufferedImage[][] waterSprites;
	private BufferedImage[][] entitySprites;
	private BufferedImage[][] cellSprites;
	private BufferedImage bullet_north;
	private BufferedImage bullet_east;
	private BufferedImage bullet_west;
	private BufferedImage bullet_south;
	private BufferedImage boom1;
	private BufferedImage boom2;
	private BufferedImage boom3;
	private BufferedImage tankboom1;
	private BufferedImage tankboom2;
	private BufferedImage[] digits;
	private BufferedImage[] digits_white;
	private BufferedImage digits_text_white;
	private BufferedImage enemyCounterImage;
	private BufferedImage flagImage;
	private BufferedImage playerHealthImage;
	private BufferedImage digits_text;
	private BufferedImage pauseImage;
	private BufferedImage stageImage;
	private BufferedImage oneHundred;
	private BufferedImage twoHundred;
	private BufferedImage threeHundred;
	private BufferedImage fiveHundred;
	private BufferedImage arrowImage;
	private BufferedImage ptsImage;
	private BufferedImage totalImage;
	private BufferedImage totalScoreImage;
	private BufferedImage paused_textImage;
	private BufferedImage stageLoadImage;
	private BufferedImage playImage;
	private BufferedImage resetImage;
	private BufferedImage bonusesImage;
	private BufferedImage returnToMainMenuImage;
	private BufferedImage exitPNG;

	public ImageLoader()
	{
		this.spriteSheet = loadImage("/resources/images/spritesheet1.png");
		this.spriteSheet2 = loadImage("/resources/images/spritesheet2.png");
		this.loadSpriteCaches();
		this.bullet_north = loadImage("/resources/images/bullet_north.png");
		this.bullet_east = loadImage("/resources/images/bullet_east.png");
		this.bullet_west = loadImage("/resources/images/bullet_west.png");
		this.bullet_south = loadImage("/resources/images/bullet_south.png");
		this.boom1 = loadImage("/resources/images/boom1.png");
		this.boom2 = loadImage("/resources/images/boom2.png");
		this.boom3 = loadImage("/resources/images/boom3.png");
		this.tankboom1 = loadImage("/resources/images/tankboom1.png");
		this.tankboom2 = loadImage("/resources/images/tankboom2.png");
		this.digits = new BufferedImage[10];
		this.digits_white = new BufferedImage[10];
		this.digits_text = loadImage("/resources/images/numbers_text.png");
		this.digits_text_white = loadImage("/resources/images/numbers_text_black.png");
		this.enemyCounterImage = loadImage("/resources/images/enemy_counter.png");
		this.flagImage = loadImage("/resources/images/flag.png");
		this.playerHealthImage = loadImage("/resources/images/player_health.png");
		this.pauseImage = loadImage("/resources/images/pause.png");
		this.loadDigits();
		this.loadDigits_white();
		this.stageImage = loadImage("/resources/images/stage_text.png");
		this.fiveHundred = loadImage("/resources/images/fiveHundred.png");
		this.oneHundred = loadImage("/resources/images/oneHundred.png");
		this.twoHundred = loadImage("/resources/images/twoHundred.png");
		this.threeHundred = loadImage("/resources/images/threeHundred.png");
		this.arrowImage = loadImage("/resources/images/arrow.png");
		this.ptsImage = loadImage("/resources/images/pts.png");
		this.totalImage = loadImage("/resources/images/total.png");
		this.totalScoreImage = loadImage("/resources/images/total_score.png");
		this.paused_textImage = loadImage("/resources/images/pause2.png");
		this.stageLoadImage = loadImage("/resources/images/stageLoad.png");
		this.playImage = loadImage("/resources/images/play.png");
		this.resetImage = loadOptionalImage("/resources/images/reset.png");
		this.bonusesImage = loadImage("/resources/images/bonuses.png");
		this.returnToMainMenuImage = loadImage("/resources/images/return_to_main_menu.png");
		this.exitPNG = loadImage("/resources/images/exitPNG.png");
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

	private BufferedImage loadOptionalImage(String path) {
		try {
			InputStream stream = getClass().getResourceAsStream(path);

			if (stream == null) {
				return null;
			}

			return ImageIO.read(stream);
		}
		catch (IOException e) {
			throw new RuntimeException("Could not load image: " + path, e);
		}
	}

	public void loadSpriteCaches()
	{
		this.waterSprites = loadSpriteSheet(spriteSheet, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE - 1, SPRITE_SIZE - 1);
		this.entitySprites = loadSpriteSheet(spriteSheet2, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE - 1, SPRITE_SIZE - 1);
		this.cellSprites = loadSpriteSheet(spriteSheet2, 8, 8, 8, 8);
	}

	private BufferedImage[][] loadSpriteSheet(BufferedImage sheet, int cellWidth, int cellHeight, int spriteWidth, int spriteHeight)
	{
		int rowCount = sheet.getHeight() / cellHeight;
		int colCount = sheet.getWidth() / cellWidth;
		BufferedImage[][] sprites = new BufferedImage[rowCount][colCount];

		for(int row = 0; row < rowCount; row++)
		{
			for(int col = 0; col < colCount; col++)
			{
				int x = col * cellWidth;
				int y = row * cellHeight;
				sprites[row][col] = sheet.getSubimage(x, y, spriteWidth, spriteHeight);
			}
		}
		return sprites;
	}

	public void loadDigits()
	{
		// numbers_text.png is 41x17 with 1px border on all sides and 1px separator row
		// Layout: [1px border][7px digits][1px sep][7px digits][1px border] = 17 rows
		//         [1px border][8px * 5 digits] = 41 cols
		// Digits 0-4 on top (y=1), digits 5-9 on bottom (y=9), each 8x7
		int cellWidth = 8;
		int cellHeight = 7;

		for(int i = 0; i < 10; i++) {
			int col = i % 5;
			int row = i / 5;
			int x = 1 + col * cellWidth;
			int y = 1 + row * (cellHeight + 1); // +1 for separator row
			this.digits[i] = digits_text.getSubimage(x, y, cellWidth, cellHeight);
		}
	}

	public void loadDigits_white()
	{
		// numbers_text.png is 41x17 with 1px border on all sides and 1px separator row
		// Layout: [1px border][7px digits][1px sep][7px digits][1px border] = 17 rows
		//         [1px border][8px * 5 digits] = 41 cols
		// Digits 0-4 on top (y=1), digits 5-9 on bottom (y=9), each 8x7
		int cellWidth = 8;
		int cellHeight = 7;

		for(int i = 0; i < 10; i++) {
			int col = i % 5;
			int row = i / 5;
			int x = 1 + col * cellWidth;
			int y = 1 + row * (cellHeight + 1); // +1 for separator row
			this.digits_white[i] = digits_text_white.getSubimage(x, y, cellWidth, cellHeight);
		}
	}

    public BufferedImage getWaterSprite(int row, int col) {
        return waterSprites[row][col];
    }

    public BufferedImage getEntitySprite(int row, int col) {
        return entitySprites[row][col];
    }

    public BufferedImage getCellSprite(int row, int col) {
        return cellSprites[row][col];
    }

    public BufferedImage getEntitySprite2(int row, int col) {
        return entitySprites[row][col];
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

	public BufferedImage getEnemyCounterImage() {
		return enemyCounterImage;
	}

	public BufferedImage getFlagImage() {
		return flagImage;
	}

	public BufferedImage getPlayerHealthImage() {
		return playerHealthImage;
	}

	public BufferedImage getDigit(int index) {
		return digits[index];
	}
	
	public BufferedImage getDigit_white(int index) {
		return digits_white[index];
	}


	public BufferedImage getPauseImage() {
		return pauseImage;
	}
	public BufferedImage getStageImage() {
		return stageImage;
	}

	public BufferedImage getTwoHundred() {
		return twoHundred;
	}

	public BufferedImage getThreeHundred() {
		return threeHundred;
	}

	public BufferedImage getFiveHundred() {
		return fiveHundred;
	}

	public BufferedImage getOneHundred() {
		return oneHundred;
	}

	public BufferedImage getArrowImage() {
		return arrowImage;
	}

	public BufferedImage getPtsImage() {
		return ptsImage;
	}

	public BufferedImage getTotalImage() {
		return totalImage;
	}

	public BufferedImage getTotalScoreImage() {
		return totalScoreImage;
	}

	public BufferedImage getPaused_textImage() {
		return paused_textImage;
	}

	public BufferedImage getStageLoadImage() {
		return stageLoadImage;
	}

	public BufferedImage getPlayImage() {
		return playImage;
	}

	public BufferedImage getResetImage() {
		return resetImage;
	}

	public BufferedImage getBonusesImage() {
		return bonusesImage;
	}

	public BufferedImage getReturnToMainMenuImage() {
		return returnToMainMenuImage;
	}

	public BufferedImage getExitPNG() {
		return exitPNG;
	}
}
