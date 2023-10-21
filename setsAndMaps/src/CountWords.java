import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
            // split words on whitespace and punctuation marks
            sc.useDelimiter("[\\s,;.:?'\")(]+");
            
            // only coninue if there are words to look through
            while (sc.hasNext()) {
                // go to next word, make sure its lowercase for comparison
                String word = sc.next().toLowerCase();

                // makes sure words start with letter
                word = word.replaceAll("[^a-z]", "");

                // add word to hashMap if not empty
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
        // make param lowercase for comparison
        word = word.toLowerCase();

        // if word exists in hashMap return its value
        if (wordMap.containsKey(word)) {
            return wordMap.get(word);
        }

        // word doesnt exist in hashMap value is 0
        return 0;
    }
}
