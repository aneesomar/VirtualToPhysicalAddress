import java.util.HashMap;
import java.util.Map;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Anees Omar
//OMRANE005
//OS1 Assignment
//This program reads a file of 8 byte hex values (virtual addresses) and converts them to physical addresses using a page table

public class OS1Assignment {

    // Convert hex to binary
    private static String hexToBinary(String hex) {
        int padding = 12;
        int decimal = Integer.parseInt(hex.substring(2), 16);
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(decimal));
        while (binary.length() < padding) {
            binary.insert(0, "0");
        }
        return binary.toString();
    }

    // Convert virtual address to physical address

    private static String convert(String virtualAddress) {

        int page_size = 128;

        Map<Integer, Integer> page_table = new HashMap<>();
        page_table.put(0, 2);
        page_table.put(1, 4);
        page_table.put(2, 1);
        page_table.put(3, 7);
        page_table.put(4, 3);
        page_table.put(5, 5);
        page_table.put(6, 6);
        page_table.put(7, 0);

        String binary = hexToBinary(virtualAddress);

        int virtualPageNum = Integer.parseInt((binary.substring(0, 5)), 2);

        int offset = Integer.parseInt((binary.substring(5, 12)), 2);

        int frameNum = page_table.get(virtualPageNum);

        int physicalAddress = (frameNum * page_size) + offset;

        return "0x" + Integer.toHexString(physicalAddress);
    }

    // Read 8 byte hex values from file

    private static Byte[] readValue(DataInputStream inputStream) throws IOException {
        Byte[] data = new Byte[8];
        for (int i = 7; i >= 0; i--) {
            data[i] = inputStream.readByte();
        }
        return data;
    }

    // Convert Byte array to printable string in hex format

    private static String makePrintableString(Byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%02X", data[i]));
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        String filename = args[0];
        String outputFilename = "output-OS1";
        String txtfile = "output-OS1.txt";

        // Read file and write to output file with physical addresses
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(filename));
                DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputFilename));
                BufferedWriter writer = new BufferedWriter(new FileWriter(txtfile))) {

            while (inputStream.available() > 0) {
                Byte[] data = readValue(inputStream);
                String virtualAddress = makePrintableString(data);
                String physicalAddress = convert("0x" + virtualAddress);

                System.out.println("Virtual Address: 0x" + virtualAddress);
                System.out.println("Physical Address: " + physicalAddress);

                long physicalAddressLong = Long.parseUnsignedLong(physicalAddress.substring(2), 16);
                outputStream.writeLong(Long.reverseBytes(physicalAddressLong));

                writer.write(physicalAddress);
                writer.newLine(); // to write each address on a new line
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

// Catch exceptions
