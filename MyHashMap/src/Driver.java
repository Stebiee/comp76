import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Driver {

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
    private static final void printJsonOutput(Map<String, String> testOutput) {
      int numTests = testOutput.size();
      StringBuffer buffer = new StringBuffer();
      buffer.append("{\n");
      int failedTests = printMap(testOutput, buffer, "Test");
      buffer.append("{");
      buffer.append("\"scores\": {");
      buffer.append("\t\"Correctness\":").append((double) (numTests - failedTests) / numTests * 100)
         .append("} }");
      System.out.println(buffer.toString());
   }  


   /**
    * Checks the size of the table, keySet, values, entrySet, containsKey and containsValue methods.
    * @param testOutput
    * @param testCase
    * @param sizeOfTable
    * @param expectedMap
    * @param actualMap
    */
   private static void check(Map<String, String> testOutput, String testCase, int sizeOfTable, Map<Integer, Integer> expectedMap, MyHashMap<Integer, Integer> actualMap) {
      if (actualMap.size() != sizeOfTable) {
         testOutput.put(testCase + ": Size of table", "Expected " + sizeOfTable + ", Got " + actualMap.size());
      }

      // Check the keys method.
      Set<Integer> expectedKeySet = expectedMap.keySet();
      Set<Integer> actualKeySet = actualMap.keySet();

      if (actualKeySet.size() != expectedKeySet.size()) {
         testOutput.put(testCase + ": Size of keySet", "Expected " + expectedKeySet.size() + ", Got " + actualKeySet.size());
      } else {
         testOutput.put(testCase + ": Size of keySet",  null);
         testOutput.put(testCase + ": keySet", null);
         testOutput.put(testCase  + ": containsKey", null);

         for (int key : expectedKeySet) {
            if (!actualKeySet.contains(key)) {
               testOutput.put(testCase + ": keySet", "Actual keySet does not contain " + key + " that is in expectedKeySet");
               break;
            }
            if (!actualMap.containsKey(key)) {
               testOutput.put(testCase  + ": containsKey", "containsKey(" + key + ") returned false.");
               break;
            }
         }
      }

      // Check the values() method.
      Collection<Integer> expectedValues = expectedMap.values();
      Collection<Integer> actualValues = actualMap.values();

      if (actualValues.size() != expectedValues.size()) {
         testOutput.put(testCase + ": Size of values", "Expected " + expectedValues.size() + ", Got " + actualValues.size());
      } else {
         testOutput.put(testCase + ": Size of values",  null);
         testOutput.put(testCase + ": values", null);
         testOutput.put(testCase  + ": containsValue", null);

         for (int value : expectedValues) {
            if (!actualValues.contains(value)) {
               testOutput.put(testCase + ": values", "Actual values does not contain " + value + " that is in expectedValues");
               break;
            }
            if (!actualMap.containsKey(value)) {
               testOutput.put(testCase  + ": containsValue", "containsValue(" + value + ") returned false.");
               break;
            }
         }
      }

      // Check the entries() method.
      Set<Map.Entry<Integer, Integer>> expectedEntries = expectedMap.entrySet();
      Set<Map.Entry<Integer, Integer>> actualEntries = actualMap.entrySet();

      if (actualEntries.size() != expectedEntries.size()) {
         testOutput.put(testCase + ": Size of entries", "Expected " + expectedEntries.size() + ", Got " + actualEntries.size());
      } else {
         testOutput.put(testCase + ": Size of entries",  null);
         testOutput.put(testCase + ": contains entry", null);

         for (Map.Entry<Integer, Integer> entry : expectedEntries) {
            if (!actualEntries.contains(entry)) {
               testOutput.put(testCase + ": entries", "Actual entries does not contain " + entry + " that is in expectedEntries");
               break;
            }
         }
      }


   }

   public static void main(String[] args) {
      Map<String, String> testOutput = new LinkedHashMap<>();

      // Test putting 2 elements with different hash values
      MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
      Map<Integer, Integer> expectedHashMap = new HashMap<>();
      myHashMap.put(1, 1);
      myHashMap.put(2, 2);
      expectedHashMap.put(1, 1);
      expectedHashMap.put(2, 2);
      check(testOutput, "Adding two entries. ", MyHashMap.INITIAL_TABLE_SIZE, expectedHashMap, myHashMap);

      // Test putting 5 elements with same hash value
      myHashMap = new MyHashMap<>();
      myHashMap.put(1 + MyHashMap.INITIAL_TABLE_SIZE, 1 + MyHashMap.INITIAL_TABLE_SIZE);
      myHashMap.put(1 + 2*MyHashMap.INITIAL_TABLE_SIZE, 1 + 2*MyHashMap.INITIAL_TABLE_SIZE);
      myHashMap.put(1 + 3*MyHashMap.INITIAL_TABLE_SIZE, 1 + 3*MyHashMap.INITIAL_TABLE_SIZE);
      myHashMap.put(1 + 4*MyHashMap.INITIAL_TABLE_SIZE, 1 + 4*MyHashMap.INITIAL_TABLE_SIZE);
      myHashMap.put(1 + 5*MyHashMap.INITIAL_TABLE_SIZE, 1 + 5*MyHashMap.INITIAL_TABLE_SIZE);
      expectedHashMap.clear();
      expectedHashMap.put(1 + MyHashMap.INITIAL_TABLE_SIZE, 1 + MyHashMap.INITIAL_TABLE_SIZE);
      expectedHashMap.put(1 + 2*MyHashMap.INITIAL_TABLE_SIZE, 1 + 2*MyHashMap.INITIAL_TABLE_SIZE);
      expectedHashMap.put(1 + 3*MyHashMap.INITIAL_TABLE_SIZE, 1 + 3*MyHashMap.INITIAL_TABLE_SIZE);
      expectedHashMap.put(1 + 4*MyHashMap.INITIAL_TABLE_SIZE, 1 + 4*MyHashMap.INITIAL_TABLE_SIZE);
      expectedHashMap.put(1 + 5*MyHashMap.INITIAL_TABLE_SIZE, 1 + 5*MyHashMap.INITIAL_TABLE_SIZE);

      check(testOutput, "Adding five entries with colliding hashes. ", MyHashMap.INITIAL_TABLE_SIZE * 2, expectedHashMap, myHashMap);


      // Test removing non-existent value.
      if (myHashMap.remove(1) != null) {
         testOutput.put("Removing non-existent value", "returned a value when it should have returned null");
      } else {
         testOutput.put("Removing non-existent value", null);
      }
      
      // Test removing 1 element.
      if (myHashMap.remove(1 + MyHashMap.INITIAL_TABLE_SIZE) != (1 + MyHashMap.INITIAL_TABLE_SIZE)) {
         testOutput.put("Removing value", "Failed to return old value");
      } else {
         testOutput.put("Removing value", null);
      }
      expectedHashMap.remove(1 + MyHashMap.INITIAL_TABLE_SIZE);
      check(testOutput, "Removing one element. ", MyHashMap.INITIAL_TABLE_SIZE * 2, expectedHashMap, myHashMap);
      testOutput.put("get after remove", null);
      if ((myHashMap.get(1 + MyHashMap.INITIAL_TABLE_SIZE) != null) || myHashMap.containsKey(1 + MyHashMap.INITIAL_TABLE_SIZE)) {
         testOutput.put("get after remove", "Did not return null");
      }

      // Test removing 2 element.
      myHashMap.remove(1 + 2*MyHashMap.INITIAL_TABLE_SIZE);
      expectedHashMap.remove(1 + 2*MyHashMap.INITIAL_TABLE_SIZE);
      check(testOutput, "Removing one element. ", MyHashMap.INITIAL_TABLE_SIZE * 2, expectedHashMap, myHashMap);
      if ((myHashMap.get(1 + 2*MyHashMap.INITIAL_TABLE_SIZE) != null) || myHashMap.containsKey(1 + 2*MyHashMap.INITIAL_TABLE_SIZE)) {
         testOutput.put("get after remove", "Did not return null");
      }

      // Test putAll with 5 elements and test.
      myHashMap = new MyHashMap<>();
      myHashMap.putAll(expectedHashMap);
      check(testOutput, "putAll", MyHashMap.INITIAL_TABLE_SIZE, expectedHashMap, myHashMap);

      // Test clear.
      myHashMap.clear();
      expectedHashMap.clear();
      check(testOutput, "clear", MyHashMap.INITIAL_TABLE_SIZE, expectedHashMap, myHashMap);

      // Test putting the same key twice and make sure we get the previous value
      myHashMap = new MyHashMap<>();
      myHashMap.put(1, 100);
      if (myHashMap.put(1, 200) != 100) {
         testOutput.put("putting same key twice", "does not return previous value");
      } else {
         testOutput.put("putting same key twice", null);
      }
      if (myHashMap.put(2, 100) != null) {
         testOutput.put("putting new key", "returns non-null");
      } else {
         testOutput.put("putting new key", null);
      }      

      // Test removing and putting the same key and make sure we get null value
      myHashMap.remove(1);
      if (myHashMap.put(1, 101) != null) {
         testOutput.put("remvoving and putting same key", "returns non-null");
      } else {
         testOutput.put("removing and putting same key", null);
      }      
      
      printJsonOutput(testOutput);
   }
}
