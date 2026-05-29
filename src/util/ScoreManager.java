package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
}