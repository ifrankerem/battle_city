package util;

public enum GameState{

	PLAYING(1),
	PAUSED(2),
	MENU(3),
	GAME_OVER(4),
	LEVEL_COMPLETE(5);

	private final int num;

	GameState(int num)
	{
		this.num = num;
	}
}