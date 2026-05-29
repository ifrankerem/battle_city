package util;

public class ScoreRecord {
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
}