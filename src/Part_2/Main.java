package Part_2;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Main {

    public static void main(String[] args) throws IOException {
        String op = args[0];
        System.out.println(op);
        String filename = args[1];
        System.out.println(filename);
        long startTime = System.nanoTime();
        if (op.equalsIgnoreCase("c")) {
            int nValue = Integer.parseInt(args[2]);
            FileCompressor fc = new FileCompressor();
            byte[] inputByteArr = fc.readFile(filename);
            Encoder encoder = new Encoder(nValue);
            encoder.buildFreqMap(ByteBuffer.wrap(inputByteArr));
            encoder.buildTree();
            encoder.buildCodeMap(encoder.getRoot(), 0b0, 0);
            String outputFileName = "6543." + nValue + "." + filename + ".hc";
            FileUtils.writeHeader(outputFileName, encoder.charCodeMap, encoder.unitBytes);
            FileUtils.writeBody(filename, outputFileName, encoder.charCodeMap, encoder.unitBytes);
        } else {
            String outputFileName = "extracted" + filename.replace(".hc", "");
            FileUtils.decompress(filename, outputFileName);
            double ratio = ((double) new File(filename).length()) / (new File(outputFileName).length()) * 100;
            System.out.println("Compression Ratio: " + ratio);
        }

        System.out.println("Time taken = " + (System.nanoTime() - startTime) / 1000000 + " ms");

    }
}