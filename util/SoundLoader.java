package util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;


public class SoundLoader {
	private boolean soundEnabled;
	private Clip shootSound[];
	private int shootSoundIndex;
	private Clip tankExplosionSound[];
	private int tankExplosionSoundIndex;
	private Clip bigTankSound[];
	private int bigTankSoundIndex;
	private Clip brickBreakSound[];
	private int brickBreakSoundIndex;
	private Clip steelHitSound[];
	private int steelHitSoundIndex;
	private Clip powerUpAppearedSound[];
	private int powerUpAppearedSoundIndex;
	private Clip powerUpObtainedSound[];
	private int powerUpObtainedSoundIndex;
	private Clip pauseSound;
	private Clip gameOverSound;

	public SoundLoader()
	{
		this.soundEnabled = true;
		this.shootSound = new Clip[5];
		this.tankExplosionSound = new Clip[5];
		this.bigTankSound = new Clip[5];
		this.brickBreakSound = new Clip[5];
		this.steelHitSound = new Clip[5];
		this.powerUpAppearedSound = new Clip[5];
		this.powerUpObtainedSound = new Clip[5];
		this.pauseSound = loadSound("/resources/sounds/pause.wav");
		this.gameOverSound = loadSound("/resources/sounds/game_over.wav");
		for(int i = 0; i < shootSound.length; i++)
		{
			shootSound[i] = loadSound("/resources/sounds/bulletshoot.wav");
			tankExplosionSound[i] = loadSound("/resources/sounds/tankExplosionSound.wav");
			bigTankSound[i] = loadSound("/resources/sounds/bigTankSound.wav");
			brickBreakSound[i] = loadSound("/resources/sounds/brickbreak.wav");
			steelHitSound[i] = loadSound("/resources/sounds/steelhit.wav");
			powerUpAppearedSound[i] = loadSound("/resources/sounds/power_up_appeared.wav");
			powerUpObtainedSound[i] = loadSound("/resources/sounds/power_up_obtained.wav");
		}
		this.shootSoundIndex = 0;
		this.tankExplosionSoundIndex = 0;
		this.bigTankSoundIndex = 0;
		this.brickBreakSoundIndex = 0;
		this.steelHitSoundIndex = 0;
		this.powerUpAppearedSoundIndex = 0;
		this.powerUpObtainedSoundIndex = 0;
	}

	private Clip loadSound(String path)
	{
		try {
			InputStream stream = getClass().getResourceAsStream(path);
			if(stream == null)
				return null;

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(stream));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			return clip;
		}
		catch(Exception e) {
			return null;
		}
	}

	public void setSoundEnabled(boolean soundEnabled)
	{
		this.soundEnabled = soundEnabled;
		if(!soundEnabled)
			this.stopAllSounds();
	}

	public boolean isSoundEnabled()
	{
		return soundEnabled;
	}

	private void stopClip(Clip clip)
	{
		if(clip != null && clip.isRunning())
			clip.stop();
	}

	private void stopClipArray(Clip[] clips)
	{
		if(clips == null)
			return;

		for(Clip clip : clips)
			stopClip(clip);
	}

	private void stopAllSounds()
	{
		stopClipArray(shootSound);
		stopClipArray(tankExplosionSound);
		stopClipArray(bigTankSound);
		stopClipArray(brickBreakSound);
		stopClipArray(steelHitSound);
		stopClipArray(powerUpAppearedSound);
		stopClipArray(powerUpObtainedSound);
		stopClip(pauseSound);
		stopClip(gameOverSound);
	}

	public void playShoot()
	{
		if(!soundEnabled || shootSound == null)
			return;

		Clip clip = shootSound[shootSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		shootSoundIndex++;
		if(shootSoundIndex >= shootSound.length)
			shootSoundIndex = 0;
	}

	public void playTankExplosion()
	{
		if(!soundEnabled || tankExplosionSound == null)
			return;

		Clip clip = tankExplosionSound[tankExplosionSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		tankExplosionSoundIndex++;
		if(tankExplosionSoundIndex >= tankExplosionSound.length)
			tankExplosionSoundIndex = 0;
	}

	public void playBigTankSound()
	{
		if(!soundEnabled || bigTankSound == null)
			return;

		Clip clip = bigTankSound[bigTankSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		bigTankSoundIndex++;
		if(bigTankSoundIndex >= bigTankSound.length)
			bigTankSoundIndex = 0;
	}

	public void playBrickBreak()
	{
		if(!soundEnabled || brickBreakSound == null)
			return;

		Clip clip = brickBreakSound[brickBreakSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		brickBreakSoundIndex++;
		if(brickBreakSoundIndex >= brickBreakSound.length)
			brickBreakSoundIndex = 0;
	}

	public void playSteelHit()
	{
		if(!soundEnabled || steelHitSound == null)
			return;

		Clip clip = steelHitSound[steelHitSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		steelHitSoundIndex++;
		if(steelHitSoundIndex >= steelHitSound.length)
			steelHitSoundIndex = 0;
	}

	public void playPowerUpAppeared()
	{
		if(!soundEnabled || powerUpAppearedSound == null)
			return;

		Clip clip = powerUpAppearedSound[powerUpAppearedSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		powerUpAppearedSoundIndex++;
		if(powerUpAppearedSoundIndex >= powerUpAppearedSound.length)
			powerUpAppearedSoundIndex = 0;
	}

	public void playPowerUpObtained()
	{
		if(!soundEnabled || powerUpObtainedSound == null)
			return;

		Clip clip = powerUpObtainedSound[powerUpObtainedSoundIndex];
		if(clip != null)
		{
			if(clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}

		powerUpObtainedSoundIndex++;
		if(powerUpObtainedSoundIndex >= powerUpObtainedSound.length)
			powerUpObtainedSoundIndex = 0;
	}

	public void playPause()
	{
		if(!soundEnabled || pauseSound == null)
			return;

		if(pauseSound.isRunning())
			pauseSound.stop();
		pauseSound.setFramePosition(0);
		pauseSound.start();
	}

	public void playGameOver()
	{
		if(!soundEnabled || gameOverSound == null)
			return;

		if(gameOverSound.isRunning())
			gameOverSound.stop();
		gameOverSound.setFramePosition(0);
		gameOverSound.start();
	}

}
