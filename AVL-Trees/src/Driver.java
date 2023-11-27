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
      buffer.append("\"scores\": {");
      buffer.append("\t\"Correctness\":").append((double) (numTests - failedTests) / numTests * 100)
         .append("} }");
      System.out.println(buffer.toString());
   }  

   private static void addToTree(MyAVLTree<Integer> tree, int ... values) {
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

   /** Gets the height of the tree
    */
    private static int getHeight(MyAVLTree.TreeNode<Integer> node) {
      if (node == null) {
         return 0;
      }
      return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }
   /**
    * Gets height of tree on left and right and checks the difference
    * @param testOutput
    * @param testCase
    * @param node
    * @return true if the tree is balanced, false otherwise.
    */
   private static boolean checkBalance(Map<String, String> testOutput, String testCase, MyAVLTree.TreeNode<Integer> root) {
      if (root == null) {
         return true;
      }
      int leftHeight = getHeight(root.left);
      int rightHeight = getHeight(root.right);
      int balanceFactor = leftHeight - rightHeight;
      if ((balanceFactor == 0) || (balanceFactor == -1) || (balanceFactor == 1)) {
         if (!checkBalance(testOutput, testCase, root.left) || !checkBalance(testOutput, testCase, root.right)) {
            return false;
         }
         testOutput.put(testCase, null);
         return true;
      }
      testOutput.put(testCase, "Balance factor is " + balanceFactor + " at node with value " + root.data);
      return false;
   }

   private static void checkSearch(Map<String, String> testOutput, String testCase, MyAVLTree<Integer> tree, int ...values) {
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

      MyAVLTree<Integer> tree = new MyAVLTree<>();
      // Insert elements in order. Check inorder, preorder, postorder, search.
      addToTree(tree, 1, 2, 3, 4, 5);
      checkBalance(testOutput, "Checking balance after adding nodes to tree in sorted order", tree.root);
      checkSearch(testOutput, "Adding in order, search", tree, 1, 2, 3, 4, 5);

      // Insert element in reverse order. Check inorder, preorder, post order, search
      addToTree(tree, 5, 4, 3, 2, 1);
      checkBalance(testOutput, "Checking balance after adding nodes to tree in reverse order", tree.root);
      checkSearch(testOutput, "Adding reverse order, search", tree, 1, 2, 3, 4, 5);

      // Insert elements in random order. Check inorder, preorder, postorder, search
      tree = new MyAVLTree<>();
      tree.insert(1);
      tree.insert(4);
      tree.insert(3);
      tree.insert(2);
      tree.insert(5);
      checkBalance(testOutput, "Checking balance after adding nodes to tree in random order", tree.root);
      checkSearch(testOutput, "Adding random order, search", tree, 1, 2, 3, 4, 5);
      System.out.println("Tree is " + tree);

      // Delete nodes and check.
      tree.delete(3);
      checkBalance(testOutput, "Checking balance after deleting one node from tree", tree.root);
      
      tree.delete(2);
      checkBalance(testOutput, "Checking balance after deleting two nodes from tree", tree.root);

      tree.delete(4);
      checkBalance(testOutput, "Checking balance after deleting three nodes from tree", tree.root);

      if (tree.search(2) || tree.search(3) || tree.search(4)) {
            testOutput.put("Searching after deleting", "Found value after deleting");
      } else if (!tree.search(1) || !tree.search(5)) {
            testOutput.put("Searching after deleting", "Could not find value that should exist");
      } else {
         testOutput.put("Deleting node(s)", null);
      }

      printJsonOutput(testOutput);
   }
}
