import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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

   private static void checkDoesNotThrowException(Map<String, String> testOutput, 
      String testCase, Runnable r) {
      try {
         r.run();
      } catch (Exception re) {
         testOutput.put(testCase, "Threw an exception");
      }
   }

   private static void checkListContents(Map<String, String> testOutput, 
      String testCase, List list, Object... args) {
         int index = 0;
         if (list.size() != args.length) {
            testOutput.put(testCase, "Expected size " + args.length + ", Got " + list.size());
            return;
         }
         for (Object o : list) {
            if (!o.equals(args[index])) {
               StringBuffer buff = new StringBuffer("Expected [");
               for (Object toPrint : args) {
                  buff.append(toPrint.toString());
                  buff.append(", ");
               }
               buff.append("]. Got [");
               for (Object toPrint : list) {
                  buff.append(toPrint.toString());
                  buff.append(", ");
               }
               buff.append("] at index " + index);
               buff.append(". Actual Value: " + o + ", Expected value: " + args[index]);
               testOutput.put(testCase, buff.toString());
               return;
            }
            index++;
         }
         testOutput.put(testCase, null);
      }


   /**
    * Prints the json output for the given map.
    * @param testOutput
    * @return The number of failed tests.
    */
    private static final int printMap(Map<String, String> testOutput, StringBuffer buffer, String tag) {
      int failedTests = 0;
      buffer.append("\t\"");
      buffer.append(tag + "\" : {\n");
      for (Map.Entry<String, String> entry : testOutput.entrySet()) {
         buffer.append("\t\t\"").append(entry.getKey()).append("\": {\n");
         buffer.append("\t\t\t\"passed\": ").append(entry.getValue() == null ? "true" : "false").append("\n");
         if (entry.getValue() != null) {
            failedTests++;
            buffer.append("\t\t\t\"hint\": \"").append(entry.getValue()).append("\"\n");
         }
         buffer.append("\t\t}\n");
      }
      buffer.append("\t}\n}\n");
      return failedTests;
    }


   /** Prints the json output for the autograder
    */   
    private static final void printJsonOutput(Map<String, String> testOutput, Map<String, String> extraCredit) {
      int numTests = testOutput.size();
      int numExtraTests = extraCredit.size();
      StringBuffer buffer = new StringBuffer();
      buffer.append("{\n");
      int failedTests = printMap(testOutput, buffer, "Test");
      int failedExtraTests = printMap(extraCredit, buffer, "ExtraCredit");
      buffer.append("{\n");
      buffer.append("\"scores\": {\n");
      buffer.append("\t\"Correctness\":").append((double) (numTests - failedTests) / numTests * 100)
            .append(", \n")
            .append("\t\"Extra Credit\":").append((double) (numExtraTests - failedExtraTests) / numExtraTests * 100)
         .append("\n}");
      System.out.println(buffer.toString());
   }  

   public static void main(String[] args) {
      Map<String, String> testOutput = new HashMap<>();

      // Create an empty list and check isEmpty.
      MyDoubleLinkedList<Integer> list = new MyDoubleLinkedList<>();
      if (!list.isEmpty() || (list.size() != 0)) {
         testOutput.put("Empty List Check", "Size/isEmpty is incorrect");
      }

      // Add some elements to it and check size.
      list.add(1);
      list.add(2);
      list.add(3);
      if (list.size() != 3) {
         testOutput.put("Size check", "Size incorrect for list with elements.");
      } else {
         testOutput.put("Size check", null);
      }
      // Check contains on an object that exists.
      checkDoesNotThrowException(testOutput, "contains", () -> {
         if (!list.contains(1) || !list.contains(2) || !list.contains(3) || list.contains(4) || list.contains("Foo")) {
            testOutput.put("contains", "Returned incorrect value.");
         } else {
            testOutput.put("contains", null);
         }
      });
      // Check toArray
      if (Arrays.equals(list.toArray(), new Object[]{1, 2, 3})) {
         testOutput.put("toArray", null);
      } else {
         testOutput.put("toArray", "Got " + list.toArray() + ". Expected [1, 2, 3]");
      }
      
      // Add an element, verify that the list contains it and has the right size.
      if (list.add(4) == false) {
         testOutput.put("add", "Method returned false.");
      } else {
         checkListContents(testOutput, "add", list, 1, 2, 3, 4);
      }
      // Remove an element that does not exist, check return value and size
      if (list.remove(Integer.valueOf(5))) {
         testOutput.put("remove non existent element", "Method returned true.");
      } else {
         checkListContents(testOutput, "remove non existent element", list, 1, 2, 3, 4);
      }

      // Remove an element that exists in the list, check return value and size.
      if (list.remove(Integer.valueOf(3)) == false) {
         testOutput.put("remove", "Method returned false.");
      } else {
         checkListContents(testOutput, "add", list, 1, 2, 4);
      }
      // Check containsAll with an ArrayList that has all elements in our list
      ArrayList<Integer> testList = new ArrayList<>();
      Collections.addAll(testList, 1, 2, 4);
      if (!list.containsAll(testList)) {
         testOutput.put("containsAll has all elements", "containsAll returned false instead of true");
      } else {
         testOutput.put("containsAll has all elements", null);
      }
       
      // Check contains all with an ArrayList that has some element that is not in our list.
      testList.add(5);
      if (list.containsAll(testList)) {
         testOutput.put("containsAll has non-existent elements", "containsAll returned true instead of false");
      } else {
         testOutput.put("containsAll has non-existent elements", null);
      }

      // Call addAll with the ArrayList, check containsAll again and size.
      list.addAll(testList);
      checkListContents(testOutput, "addAll", list, 1, 2, 4, 1, 2, 4, 5);

      // Call clear to verify the list is empty (isEmpty and size)
      list.clear();
      if (list.isEmpty() && list.size() == 0) {
         testOutput.put("clear", null);
      } else {
         testOutput.put("clear", "size/isEmpty incorrect after clear.");
      }

      // Reset the List with some elements. Call get on each index and verify the value
      list.addAll(testList);
      if (list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 4 && list.get(3) == 5) {
         testOutput.put("get(index)", null);
      } else {
         testOutput.put("get(index)", "Gets failed to return correct value");
      }
      // Call set at a particular index and then iterate to verify list is right.
      list.set(2, 3);
      list.set(3, 4);
      checkListContents(testOutput, "set", list, 1, 2, 3, 4);

      // Call add at 0 index, verify its correct. Call add at size() index and verify its correct. Call add at an intermediate value and verify
      list.add(0, 0);
      checkListContents(testOutput, "add at 0", list, 0, 1, 2, 3, 4);

      // Call remove at 0 index and verify its correct, Call remove at size() and verify its correct, call remove somewher ein middle and verify
      list.remove(0);
      checkListContents(testOutput, "remove at 0", list, 1, 2, 3, 4);
   
      // Call indexOf and verify the right value (list should have multiple identical values in the middle)
      list.clear();
      Collections.addAll(list, 1, 2, 3, 4, 3, 2, 1);
      if (list.indexOf(1) == 0 && list.indexOf(2) == 1 && list.indexOf(3) == 2) {
         testOutput.put("indexOf", null);
      } else {
         testOutput.put("indexOf", "Returned incorrect values");
      }

      // Call listIterator and verify each element is in the right order.
      ListIterator<Integer> listIter = list.listIterator();
      int[] expected = new int[]{1, 2, 3, 4, 3, 2, 1};
      int index = 0;
      // Assume success. If it fails the loop will set the failure code.
      testOutput.put("listIterator", null);
      while (listIter.hasNext()) {
         Integer actualValue = listIter.next();
         if (expected[index] != actualValue) {
            testOutput.put("listIterator", "Iteration failed at index " + index + ". Expected " + expected[index] + ", Got " + actualValue);
            break;
         }
         index++;
      }

      // Now for extra credits.
      Map<String, String> extraCreditTestOutput = new HashMap<>();
      // Extra Credit: Call addAll at a specific index and check values.
      testList.clear();
      Collections.addAll(testList, 5, 4);
      list.addAll(4, testList);

      checkListContents(extraCreditTestOutput, "addAll at specific index", list, 1, 2, 3, 4, 5, 4, 3, 2, 1);
      // Extra Credit: Call removeAll with the ArrayList and verify that the list doesnt have any of those values.
      list.removeAll(testList);
      checkListContents(extraCreditTestOutput, "addAll at specific index", list, 1, 2, 3, 3, 2, 1);

      // Extra Credit: Call retainAll with an ArrayList with some elements and verify that only those elements are present.
      testList.clear();
      Collections.addAll(testList, 2, 3, 4);
      list.retainAll(testList);
      checkListContents(extraCreditTestOutput, "retainAll", list, 2, 3, 3, 2);
      // Extra Credit: Call lastIndexOf and verify the right value (list should have multiple identical values in the middle)
      if (list.lastIndexOf(3) == 2) {
         extraCreditTestOutput.put("lastIndexOf", null);
      } else {
         extraCreditTestOutput.put("lastIndexOf", "Expected 2, Got "+ list.lastIndexOf(3));         
      }
      // Extra Credit: Call listIterator at a specific index and verify
      listIter = list.listIterator(2);
      expected = new int[]{3, 2};
      index = 0;
      testOutput.put("listIterator at index", null);
      while (listIter.hasNext()) {
         Integer actualValue = listIter.next();
         if (expected[index] != actualValue) {
            testOutput.put("listIterator at index", "Iteration failed at index " + index + ". Expected " + expected[index] + ", Got " + actualValue);
            break;
         }
         index++;
      }
      
      
      printJsonOutput(testOutput, extraCreditTestOutput);
   }
}
