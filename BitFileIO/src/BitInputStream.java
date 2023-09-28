import java.io.*;

/**
 * This class reads a stream of bits from a file
 * 
 * @author Esteban Madrigal
 */
public class BitInputStream {
    private FileInputStream input;
    private int bits; // holds bits
    private int numBits; // holds the number of bits

    /**
     * creates a new BitInputStream to read bits from a file
     */
    public BitInputStream(File file) throws FileNotFoundException {
        this.input = new FileInputStream(file);

        // initializes varaibles to zero
        bits = 0;
        numBits = 0;
    }

    /**
     * @return the number of bits in a file
     * @throws IOException
     */
    public int available() throws IOException {
        return numBits + (8 * input.available());
    }

    /**
     * reads the current bit in the file then moves to the next position
     * if at 0 restarts at the position 8 of a new byte
     * @return bit char with 1 or 0
     * @throws IOException
     */
    public char readBit() throws IOException {
        if (numBits == 0) {
            bits = input.read();

            if (bits == -1) {
                return '0';
            }

            numBits = 8;
        }
        
        char bit = ((bits >> (numBits - 1)) & 1) == 1 ? '1' : '0';
        numBits--;
        return bit;
    }

    /**
     * reads the remainder of the file
     * @return bitString builds a string by appending bits as they're read
     * @throws IOException
     */
    public String readBits() throws IOException {
        StringBuilder bitString = new StringBuilder();
        char bit;
        while ((bit = readBit()) != '0') {
            bitString.append(bit);
        }
        return bitString.toString();
    }

    /**
     * closes the IO stream
     * @throws IOException
     */
    public void close() throws IOException {
        input.close();
    }
}