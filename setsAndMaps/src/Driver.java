import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Driver {

   @FunctionalInterface
   public interface ThrowingConsumer<E extends Exception> {
      void accept() throws E;
   }

   static <T> Runnable throwingConsumerWrapper(
         ThrowingConsumer<Exception> throwingConsumer) {

      return () -> {
         try {
            throwingConsumer.accept();
         } catch (Exception ex) {
            throw new RuntimeException(ex);
         }
      };
   }

   private static void checkThrowsException(Map<String, String> testOutput, String testCase, Runnable r) {
      try {
         r.run();
         testOutput.put(testCase, "Should throw exception.");
      } catch (Exception re) {
         testOutput.put(testCase, null);
      }
   }

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

   private static void checkCount(Map<String, String> testOutput, CountWords cw, String key, Integer value) {
      String testCase = "Checking count of " + key;
      try {
         if (cw.getCount(key) != value) {
            testOutput.put(testCase, "Expected " + value + ", Got " + cw.getCount(key));
         } else {
            testOutput.put(testCase, null);
         }
      } catch (Exception ex) {
         testOutput.put(testCase, "getCount threw an unexpected exception");
      }
   }

   public static void main(String[] args){
      Map<String, String> testOutput = new HashMap<>();
      // Create one with a non-existent file. Should throw exception.
      // Check to make sure that all words return the appropriate count.
      // Check that words that dont start with a letter return a 0 count.
      checkThrowsException(testOutput, "Creating CountWords with non-existent files should throw exception",
                        throwingConsumerWrapper(() -> new CountWords(null)));
      checkThrowsException(testOutput, "Creating CountWords with non-existent files should throw exception",
                        throwingConsumerWrapper(() -> new CountWords("/tmp/non-existent-file")));

      CountWords cw = new CountWords("E:\\aaaJava\\setsAndMaps\\src\\hamlet.txt");
      Map<String, Integer> expectedCounts = new HashMap<String, Integer>();
      expectedCounts.put("horatio", 158);
      expectedCounts.put("FORTINBRAS", 21);
      expectedCounts.put("mouth", 6);
      expectedCounts.put("thanks", 10);
      expectedCounts.put("and", 966);
      expectedCounts.put("foo", 0);
      expectedCounts.put("mou", 0);
      expectedCounts.put("bernardo?", 0);
      expectedCounts.put("france", 5);
      
      for (Map.Entry<String, Integer> entry : expectedCounts.entrySet()) {
         checkCount(testOutput, cw, entry.getKey(), entry.getValue());
      }
      // Print the output
      printJsonOutput(testOutput);

   }
}
