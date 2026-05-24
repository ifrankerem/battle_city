package model;

public enum Direction{

	NORTH(1),
	WEST(2),
	EAST(3),
	SOUTH(4);

	private final int num;

	Direction(int num)
	{
		this.num = num;
	}
}