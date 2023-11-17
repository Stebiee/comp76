import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Function;

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
      buffer.append("{\n");
      buffer.append("\"scores\": {\n");
      buffer.append("\t\"Correctness\":").append((double) (numTests - failedTests) / numTests * 100)
         .append("\n}");
      System.out.println(buffer.toString());
   }  

   private static void addToTree(MyBinarySearchTree<Integer> tree, int ... values) {
      for (int value : values) {
         tree.insert(value);
      }
   }

   private static void checkOrder(Map<String, String> testOutput, String testCase, List<Integer> list, int ...values) {
      int index = 0;
      for (int val : list) {
         if (val != values[index]) {
            testOutput.put(testCase, "Expected " + values[index] + ", Got " + val);  
            return;
         }
         index++;
      }
      testOutput.put(testCase, null);
   }

   private static void checkSearch(Map<String, String> testOutput, String testCase, MyBinarySearchTree<Integer> tree, int ...values) {
      for (int value : values) {
         if (!tree.search(value)) {
            testOutput.put(testCase, "Could not find " + value);
            return;
         }
      }

      if (tree.search(100)) {
         testOutput.put(testCase, "search for 100 returned true.");
      } else {
         testOutput.put(testCase, null);
      }
   }

   public static void main(String[] args) {
      Map<String, String> testOutput = new LinkedHashMap<>();

      List<Integer> list = new ArrayList<>();
      Function<Integer, Void> addToList = (a) -> {
         list.add(a);
         return null;
      };

      MyBinarySearchTree<Integer> tree = new MyBinarySearchTree<>();
      // Insert elements in order. Check inorder, preorder, postorder, search.
      addToTree(tree, 1, 2, 3, 4, 5);
      list.clear();
      tree.inorder(addToList);
      checkOrder(testOutput, "Adding in order, inorder traversal", list, 1, 2, 3, 4, 5);
      list.clear();
      tree.preorder(addToList);
      checkOrder(testOutput, "Adding in order, preorder traversal", list, 1, 2, 3, 4, 5);
      list.clear();
      tree.postorder(addToList);
      checkOrder(testOutput, "Adding in order, postorder traversal", list, 5, 4, 3, 2, 1);
      checkSearch(testOutput, "Adding in order, search", tree, 1, 2, 3, 4, 5);

      // Insert element in reverse order. Check inorder, preorder, post order, search
      tree = new MyBinarySearchTree<>();
      addToTree(tree, 5, 4, 3, 2, 1);
      list.clear();
      tree.inorder(addToList);
      checkOrder(testOutput, "Adding reverse order, inorder traversal", list, 1, 2, 3, 4, 5);
      list.clear();
      tree.preorder(addToList);
      checkOrder(testOutput, "Adding reverse order, preorder traversal", list, 5, 4, 3, 2, 1);
      list.clear();
      tree.postorder(addToList);
      checkOrder(testOutput, "Adding reverse order, postorder traversal", list, 1, 2, 3, 4, 5);
      checkSearch(testOutput, "Adding reverse order, search", tree, 1, 2, 3, 4, 5);


      // Insert elements in random order. Check inorder, preorder, postorder, search
      tree = new MyBinarySearchTree<>();
      tree.insert(1);
      tree.insert(4);
      tree.insert(3);
      tree.insert(2);
      tree.insert(5);

      list.clear();
      tree.inorder(addToList);
      checkOrder(testOutput, "Adding random order, inorder traversal", list, 1, 2, 3, 4, 5);
      list.clear();
      tree.preorder(addToList);
      checkOrder(testOutput, "Adding random order, preorder traversal", list, 1, 4, 3, 2, 5);
      list.clear();
      tree.postorder(addToList);
      checkOrder(testOutput, "Adding random order, postorder traversal", list, 2, 3, 5, 4, 1);
      checkSearch(testOutput, "Adding random order, search", tree, 1, 2, 3, 4, 5);

      printJsonOutput(testOutput);
   }
}
