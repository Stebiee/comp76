import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    public static void main(String[] args) throws Exception {
        // to write data to a file BufferedWriter can be used
        // which needs an argument for where text is sent
        // FileWriter contains that location
        // this can throw an IOException so a try catch is used as saftey
        //forgetting to close the writer causes nothingg to be written
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write("testing");
            writer.write("\nwriting to new line");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // BufferedReader is used to read data from txt file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line;

            //creating a while loop which reads every line from file
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
            
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }
}
