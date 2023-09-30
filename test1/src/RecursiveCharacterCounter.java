
/**
 * this class implements two public methods,
 * charCount uses recursion to find the occurences of a character in a string
 * substringCount uses recursion to find the occurences 
 * of a substring in the passed string
 * @author Esteban Madrigal
 */
public class RecursiveCharacterCounter {
    /**
     * method uses recursion to count the number of occurences 
     * a character appears in a string relying on a helper method 
     * which doesnt use substring calls
     * @param str string to be looked through
     * @param c character to be looking for
     * @return amount of chars found
     */
    public static int charCount(String str, char c) {
        // current index being compared against char c
        int index = 0;
        // makes string and char lower case for accurate comparison
        c = Character.toLowerCase(c);
        str = str.toLowerCase();
        
        return charCountHelper(str, c, index);
    }

    /**
     * helper method which recursivley counts the number of chars
     * in passed str, first checks str at index 0 calls method again
     * with index + 1 continuously until index >= str length
     * @param str
     * @param c
     * @param index
     * @return
     */
    private static int charCountHelper(String str, char c, int index) {
        // base case: makes sure index is in range of str
        if (index >= str.length()) {
            return 0;
        }
        // compares char at index with char to find
        if(str.charAt(index) == c){
            return 1 + charCountHelper(str, c, index + 1);
        }
        // no chars were found, continue looking
        return charCountHelper(str, c, index + 1);
    }

    /**
     * runs basic checks for parameters being empty
     * or substring length > str length before calling helper method
     * @returns number of occurences str has of substring
     */
    public static int substringCount(String str, String subString) {

        // first makes sure either parameter holds elements
        if(str.length() == 0 || subString.length() == 0){
            return 0;
        }else if(subString.length() > str.length()){
            // substring is greater than str
            // impossible for str to contain subString
            return 0;
        }

        // converts parameters to lowercase then character arrays
        str = str.toLowerCase();
        subString = subString.toLowerCase();
        char[] strChars = str.toCharArray();
        char[] subChars = subString.toCharArray();

        return substringCountHelper(strChars, subChars, 0, 0);
    }

    /**
     * uses recursion to increment through every element of strChars array
     * if an element equals the first element of subChars, the method then compares
     * the following elements, while incrementing subIndex, which stores 
     * how many elements in a row have been equal to eachother,
     * when elements are inequal, subindex is reset to 0,
     * and strIndex with any method call is increased by 1
     * @param str array of string to be compared
     * @param sub array in which strChars should be compared against
     * @param strIndex increments by one every recursive call
     * @param subIndex increments by one when elements equal eachother
     * @return amount of occurences strchars has of subChars
     */
    private static int substringCountHelper(char[] str,char[] sub,
                int strIndex, int subIndex){
        
        // base case: returns 0 if index >= than strChars length
        if (strIndex >= str.length || subIndex >= sub.length){
            return 0;
        }else if(str[strIndex] == sub[subIndex]){
            // both elements matched
            if(subIndex + 1 == sub.length){
                // subString length equals subindex
                return 1 + substringCountHelper(str, sub,
                    strIndex + 1, 0);
            }
            // subIndex still low, continue with next element
            return substringCountHelper(str, sub,
                strIndex + 1, subIndex + 1);
        }else if(str[strIndex] == sub[0]){
            // str doesnt match sub at given index
            // but str element matches the first in sub
            return substringCountHelper(str, sub,
                strIndex + 1, 1);
        }

        // elements dont match reset subindex to 0, step strIndex
        return substringCountHelper(str, sub,
                strIndex + 1, 0);
    }
}
