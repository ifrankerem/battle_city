package util;

public class ScoreRecord implements Comparable<ScoreRecord>{
    private String name;
    private String date;
    private String time;
    private int score;

    public ScoreRecord(String name, String date, String time, int score) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.score = score;
    }

    public String toCsvLine() {
        return name + "," + date + "," + time + "," + score;
    }

	public int compareTo(ScoreRecord other)
	{
		return other.getScore() - this.getScore(); //highest to lowest
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
	public int getScore() {
		return score;
	}
}