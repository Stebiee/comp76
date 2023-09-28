import java.io.*;

public class TestFileStream {
    // almost all methods in I/O class throw IOException so decleration
    // is needed or using a try catch block
    public static void main(String[] args) throws IOException{
        try (
            // creating new output stream
            FileOutputStream output = new FileOutputStream("temp.dat");
        ){
            // outut values to the file
            for (int i = 1; i <= 10; i++) {
                output.write(i);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try(
            //creating new input stream for the file
            FileInputStream input = new FileInputStream("temp.dat");
        ){
            // Read values from the file
            int value;
            // a .read() input returns -1 when end of file is reached
            while ((value = input.read()) != -1) {
                System.out.println(value + " ");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
