package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ScoreManager {

    private static final String FILE_NAME = "scores.csv";

    public void saveScore(ScoreRecord record) {
        boolean fileExists = new File(FILE_NAME).exists();

		//true is important it addes to csv file not overwrites it
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true)))
		{

            if (!fileExists) {
                writer.println("Name,Date,Time,Score");
            }

            writer.println(record.toCsvLine());

        } catch (IOException e) {
            System.err.println("Score could not be saved: " + e.getMessage());
        }
    }

	public ArrayList<ScoreRecord> loadScores() 
	{
        ArrayList<ScoreRecord> scores = new ArrayList<ScoreRecord>();
        File file = new File(FILE_NAME);

        if (!file.exists())
            return scores;

        try 
		{
            Scanner scanner = new Scanner(file);

            if (scanner.hasNextLine())
                scanner.nextLine(); // skip header
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ScoreRecord record = parseLine(line);
                if (record != null)
                    scores.add(record);
            }
            scanner.close();
        }
		catch (FileNotFoundException e) 
		{
            System.err.println("Score file could not be found.");
        }
        Collections.sort(scores);
        return scores;
    }

	public ScoreRecord parseLine(String line) 
	{
        String[] parts = line.split(",");

        if (parts.length != 4)
            return null;

        try {
            String name = parts[0];
            String date = parts[1];
            String time = parts[2];
            int score = Integer.parseInt(parts[3]);

            return new ScoreRecord(name, date, time, score);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public ArrayList<ScoreRecord> getTopScores(int limit) 
	{
        ArrayList<ScoreRecord> scores = loadScores();
        ArrayList<ScoreRecord> topScores = new ArrayList<ScoreRecord>();

        for (int i = 0; i < scores.size() && i < limit; i++) 
            topScores.add(scores.get(i));
        return topScores;
    }
}
