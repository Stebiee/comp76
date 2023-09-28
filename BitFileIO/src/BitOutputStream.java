import java.io.*;

/**
 * This class writes a stream of bits to a file
 * 
 * @author Esteban Madrigal
 */
public class BitOutputStream {
    private FileOutputStream output;
    private int bits;// holds all bits
    private int numBits;// has the number of bits

    /**
     * creates a BitoutputStream to write bits to a file
     * @throws IOException
     */
    public BitOutputStream(File file) throws IOException{
        this.output = new FileOutputStream(file);
        // when object is created bits/numbits are empty
        this.bits = 0;
        this.numBits = 0;
    }

    /**
     * writes to file when enough bits are passed to create a byte
     * then clears var bits and numbits
     * @param bit a bit value '1' or '0'
     * @throws IOException
     */
    public void writeBit(char bit) throws IOException {
        if (bit != '0' && bit != '1') {
            throw new IllegalArgumentException("Invalid bit");
        }

        bits = (bits << 1) | (bit - '0');
        numBits++;

        if (numBits == 8) {
            output.write(bits);
            bits = 0;
            numBits = 0;
        }
    }

    /**
     * iterates through string values and writes them to the output stream
     * in groups of bytes
     * if not enough bits for a full byte fill in with '0' 
     * @param bitString a string of bits to be written to the output stream
     * @throws IOException
     */
    public void writeBits(String bitString) throws IOException{
        for (char bit : bitString.toCharArray()) {
            output.write(bit);
        }
    }

    /**
     * fills bits to size 8 then writes to the output stream
     * then closes the stream
     * @throws IOException
     */
    public void close() throws IOException {
        while (numBits > 0) {
            writeBit('0');
        }

        output.close();
    }
}