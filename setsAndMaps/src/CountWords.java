import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/** 
 * class uses a hashmap to store occurences of words in a text file 
 * 
 * @author Esteban Madrigal 
 */
public class CountWords {
    private Map<String, Integer> wordMap = new HashMap<>();

    /**
     * on object creation populates hashMap with number of occurences
     * 
     * @param fileName path to text file
     * @throws FileNotFoundException
     */
    public CountWords(String fileName) throws FileNotFoundException{
        try (Scanner sc = new Scanner(new File(fileName))){
            sc.useDelimiter(Pattern.compile("[\\s,;.:?'\")(]+"));
            
            // only coninue if possible
            while (sc.hasNext()) {
                String word = sc.next().toLowerCase();

                // makes sure words start with letter
                word = word.replaceAll("[^a-z]", "");

                // Update word counts in the map
                if (!word.isEmpty()) {
                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    /** 
     * @parm word word to get occurences of
     * @return int number of occurences of passed word
     */
    public int getCount(String word) {
        word = word.toLowerCase();
        if (wordMap.containsKey(word)) {
            return wordMap.get(word);
        }

        return 0;
    }
}
