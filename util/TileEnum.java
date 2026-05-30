package util;

public enum TileEnum{

	EMPTY(0),
	BRICK(1),
	STEEL(2),
	BUSH(3),
	WATER(4),
	GRAYROAD(5);



	private final int num;

	TileEnum(int num)
	{
		this.num = num;
	}
}