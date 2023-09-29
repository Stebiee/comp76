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
        int strIndex = 0;
        int subIndex = 0;
        // amount of elements compared equally in a row
        int serially = 0;

        // first makes sure either parameter holds elements
        if(str.length() == 0 || subString.length() == 0){
            return 0;
        }else if(subString.length() > str.length()){
            // substring is greater than str
            // impossible for str to contain it
            return 0;
        }

        // converts parameters to char arrays
        char[] strChars = str.toCharArray();
        char[] subChars = subString.toCharArray();

        return substringCountHelper(strChars, subChars, serially, strIndex, subIndex);
    }

    /**
     * uses recursion to increment through every element of strChars array
     * if an element equals the first element of subChars, the method then compares
     * the following elements, while incrementing serially, which stores 
     * how many elements in a row have been equal to eachother,
     * when elements are inequal, serially and subindex are reset to 0,
     * and strIndex with any method call is increased by 1
     * @param strChars array of string to be compared
     * @param subChars array in which strChars should be compared against
     * @param serially how many elements equal eachother in a row
     * @param strIndex increments by one every recursive call
     * @param subIndex increments by one when elements equal eachother
     * @return amount of occurences strchars has of subChars
     */
    private static int substringCountHelper(char[] strChars,char[] subChars,
                int serially, int strIndex, int subIndex){
        // base case: returns 0 if index >= than strChars length
        if (strIndex >= strChars.length){
            return 0;
        }else if(strChars[strIndex] == subChars[subIndex]){
            // both elements matched
            if(serially + 1 == subChars.length){
                // subSize matches serially
                return 1 + substringCountHelper(strChars, subChars,
                    0, strIndex + 1, 0);
            }
            // serially still low, continue with next element
            return substringCountHelper(strChars, subChars,
                serially + 1, strIndex + 1, subIndex + 1);
        }

        // elements dont match reset serially and subindex to 0, step strIndex
        return substringCountHelper(strChars, subChars,
                0, strIndex + 1, 0);
    }
}
