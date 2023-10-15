import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Assignment for Sets and Maps Module.
 * You need to implement all the TODOs.
 * @author Esteban Madrigal
 */
public class CountWords {
    private Map<String, Integer> wordMap = new HashMap<>();

    public CountWords(String fileName){
        try {
            Scanner sc = new Scanner(new File(fileName));
            
            while (sc.hasNextLine()) {
                String line = sc.nextLine().toLowerCase();
                String[] words = line.split(" "); // Split line into words based on whitespace

                for (String word : words) {
                    // Remove punctuation and convert to lowercase for consistent counting
                    word = word.replaceAll("[^a-zA-Z]", "");
                    
                    // Update word counts in the map
                    if (!word.isEmpty()) {
                        wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
            sc.close();
        }catch (FileNotFoundException e) {
            
        }
    }

    public int getCount(String word) {
        word = word.toLowerCase();
        if (wordMap.containsKey(word)){
            return wordMap.get(word);
        }

        return 0;
    }
}
