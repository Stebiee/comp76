import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

   private static final void checkOrder(Map<String, String> testOutput, String testCase, List<Point> result, Point... points) {
      for (int i = 0; i < points.length; i++) {
         if (!result.get(i).equals(points[i])) {
            testOutput.put(testCase, "Element[" + i +"]: Expected " + points[i] + ", Got " + result.get(i));
            return;
         }
      }
      // All checked out.
      testOutput.put(testCase, null);
   }
   
   public static void main(String[] args) {
      Map<String, String> testOutput = new HashMap<String, String>();
      // 1. Create a list of points and sort with X coordinate and check
      Point p1 = new Point(1, 1);
      Point p2 = new Point(2, 2);
      Point p3 = new Point(3, 3);
      Point p4 = new Point(4, 4);
      Point p5 = new Point(5, 5);
      ArrayList<Point> list = new ArrayList<>();
      list.add(p3);
      list.add(p4);
      list.add(p1);      
      list.add(p5);
      list.add(p2);
      list.sort(null);
      checkOrder(testOutput, "Comparing on X coordinate", list, p1, p2, p3, p4, p5);

      // 2. Sort with y cooridinate and check
      list.clear();
      list.add(p3);
      list.add(p4);
      list.add(p1);      
      list.add(p5);
      list.add(p2);
      list.sort(new Point.CompareY());
      checkOrder(testOutput, "Comparing on Y coordinate", list, p1, p2, p3, p4, p5);
      // 3. Create a set of points with same x coordinates and sort with X and check
      p1 = new Point(0, 1);
      p2 = new Point(0, 2);
      p3 = new Point(0, 3);
      p4 = new Point(0, 4);
      p5 = new Point(0, 5);
      list.clear();
      list.add(p3);
      list.add(p4);
      list.add(p1);      
      list.add(p5);
      list.add(p2);
      list.sort(null);
      checkOrder(testOutput, "Comparing when X coordinate is same", list, p1, p2, p3, p4, p5);

      // 4. Create a set of points with same y coordinates and call CompareY and check
      p1 = new Point(1, 0);
      p2 = new Point(2, 0);
      p3 = new Point(3, 0);
      p4 = new Point(4, 0);
      p5 = new Point(5, 0);
      list.clear();
      list.add(p3);
      list.add(p4);
      list.add(p1);      
      list.add(p5);
      list.add(p2);
      list.sort(new Point.CompareY());
      checkOrder(testOutput, "Comparing X when Y coordinate is same", list, p1, p2, p3, p4, p5);
   
      // Print the output
      printJsonOutput(testOutput);

   }
}
