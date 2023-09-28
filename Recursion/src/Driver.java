import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Driver {
   /** Prints the json output for the autograder
    */   
    private static final void printJsonOutput(Map<String, String> testOutput) {
      int numTests = 0;
      int failedTests = 0;
      StringBuffer buffer = new StringBuffer();
      buffer.append("{\n");
      buffer.append("\t\"Test\" : {\n");
      for (Map.Entry<String, String> entry : testOutput.entrySet()) {
         numTests++;
         buffer.append("\t\t\"").append(entry.getKey()).append("\": {\n");
         buffer.append("\t\t\t\"passed\": ").append(entry.getValue() == null ? "true" : "false").append("\n");
         if (entry.getValue() != null) {
            failedTests++;
            buffer.append("\t\t\t\"hint\": \"").append(entry.getValue()).append("\"\n");
         }
         buffer.append("\t\t}\n");
      }
      buffer.append("\t}\n}\n");
      buffer.append("{\"scores\": { \"Correctness\":").append((double) (numTests - failedTests) / numTests * 100)
            .append("}}");
      System.out.println(buffer.toString());
   }  

   private static final void checkReversedString(Map<String, String> testOutput, String testCase, String stringToReverse, String reversedString) {
      if (Recursion.reverseString(stringToReverse).equals(reversedString)) {
         testOutput.put(testCase, null);
      } else {
         testOutput.put(testCase, "Expected " + reversedString + ", Got " + Recursion.reverseString(stringToReverse));
      }
   }

   private static final void checkPermutations(Map<String, String> testOutput, String testCase, String str, String... perms) {
      Set<String> result = Recursion.allPermutations(str);
      if (perms.length != result.size()) {
         testOutput.put(testCase, "List of permutations of \"" + str + "\" not of correct size. Expected: " + perms.length + ", Got " + result.size());
         return;
      }
      for (String expected : perms) {
         if (!result.contains(expected)) {
            testOutput.put(testCase, "Did not find \"" + expected + "\" in result set.");
            return;
         }
      }
      testOutput.put(testCase, null);
   }

   public static void main(String[] args) {
      Map<String, String> testOutput = new HashMap<String, String>();

      checkReversedString(testOutput, "Reversing empty string", "", "");
      checkReversedString(testOutput, "Reversing string with odd number of characters", "abcde", "edcba");
      checkReversedString(testOutput, "Reversing string with even number of characters", "abcd", "dcba");
      String longString = "asdlakjprio upq38rukasjd>AJdkJAdsklJadsfikjaiwerw8 y7r8923745  8923rihNAKSfnlAJKDSGLasyheri23QH4Y89PY  23489yuqw";
      checkReversedString(testOutput, "Reversing a long string", longString, (new StringBuffer(longString)).reverse().toString());

      checkPermutations(testOutput, "Permutations of empty string", "", "");
      checkPermutations(testOutput, "Permutation of string containing duplicate characters", "aba", "aba", "aab", "baa");
      checkPermutations(testOutput, "Permutations of one character string", "a", "a");
      checkPermutations(testOutput, "Permutations of two character string", "12", "12", "21");
      checkPermutations(testOutput, "Permutations of three character string", "def", "def", "dfe", "edf", "efd", "fde", "fed");

      printJsonOutput(testOutput);

   }
}
