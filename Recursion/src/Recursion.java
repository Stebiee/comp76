/**
 * This class has 2 different methods, 
 * one which reverses a given string and 
 * another which outputs all possible combinations of those characters
 * @author Esteban Madrigal
 */
import java.util.*;

public class Recursion {

    /**
     * reverses passed string using helper method
     * @param str String to reverse
     * @return final reveresed String
     */
    public static String reverseString(String str) {
        // call the helper method with initial highIndex as the last character
        return reverseString(str, str.length() - 1);
    }

    /**
     * grabs the character at highIndex then recursively calls itself 
     * with highIndex - 1 resulting in 2nd to last character being returned etc.
     * 
     * @param str String passed will get reversed
     * @param highIndex index of character at tail of String
     * @return final reversed String
     */
    private static String reverseString(String str, int highIndex) {
        // base case: ends recursion when last character has been reversed
        if (highIndex < 0) {
            return "";
        }

        // recursive: walks index backwards from tail of String
        return str.charAt(highIndex) + reverseString(str, highIndex - 1);
    }

    /**
     * returns the permutations for passed String
     *
     * @param value String to find all permutations for
     * @return HashSet of created permutations
     */
    public static Set<String> allPermutations(String value) {
        // creating HashSet to pass into recursive helper function
        Set<String> permutations = new HashSet<>();
        allPermutations("", value, permutations);

        return permutations;
    }

    /**
     * uses a loop to move a character from s2 to s1 and
     * recursively invokes it with new s1 and s2
     *
     * @param s1 String to be added to
     * @param s2 String used to add to s1
     * @param permutations HashSet of created permutations
     */
    private static void allPermutations(String s1, String s2, Set<String> permutations) {
        // base: if s2 is empty then stop recursion and add s1 to Hashset permutations
        if (s2.isEmpty()) {
            permutations.add(s1);
        } else {
            // recursion loop
            for (int i = 0; i <= s1.length(); i++) {
                // newS1 = (s1 before s2) + (s2 character at index 0) + (s1 after s2)
                String newS1 = s1.substring(0, i) + s2.charAt(0) + s1.substring(i);
                // removes character at index 0 from s2 
                String newS2 = s2.substring(1);

                allPermutations(newS1, newS2, permutations);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(Recursion.reverseString("123"));
        System.out.println(Recursion.allPermutations("123"));
    }
}