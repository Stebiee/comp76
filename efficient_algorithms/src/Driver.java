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

   public static void main(String[] args) {
      Map<String, String> testOutput = new HashMap<>();
      // TODO: Add test methods
      testOutput.put("Code compiles", null);
      // Print the output
      printJsonOutput(testOutput);

   }
}
