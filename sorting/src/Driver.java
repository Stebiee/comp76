import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

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

   private static void check(Map<String, String> testOutput, String testCase, ArrayList<Integer> actual, ArrayList<Integer> expected) {
      if (actual.equals(expected)) {
         testOutput.put(testCase, null);
      } else {
         testOutput.put(testCase, testCase);
      }
   }

   static final Comparator<Integer> comparator = new Comparator<>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };


    @SuppressWarnings("unchecked")
   public static void main(String[] args) {
      Map<String, String> testOutput = new HashMap<>();

      ArrayList<Integer> list = new ArrayList<>();
      for (int i = 0; i < 10000; i++) {
         list.add((new Random()).nextInt());
      }
      ArrayList<Integer> defaultSortedList = (ArrayList<Integer>)list.clone();
      defaultSortedList.sort(comparator);
      ArrayList<Integer> list2 = (ArrayList<Integer>)list.clone();
      Sorting.mergeSort(list2);
      check(testOutput, "Merge Sort", list2, defaultSortedList);

      list2 = (ArrayList<Integer>)list.clone();
      Sorting.quickSort(list2);
      check(testOutput, "Quick Sort", list2, defaultSortedList);

      list2 = (ArrayList<Integer>)list.clone();
      Sorting.selectionSort(list2);
      check(testOutput, "Selection Sort", list2, defaultSortedList);

      list2 = (ArrayList<Integer>)list.clone();
      Sorting.radixSort(list2);
      check(testOutput, "Radix Sort", list2, defaultSortedList);

      list2 = (ArrayList<Integer>)list.clone();
      Sorting.heapSort(list2);
      check(testOutput, "Heap Sort", list2, defaultSortedList);

      Sorting.mainHelper();
      // Print the output
      printJsonOutput(testOutput);

   }
}

