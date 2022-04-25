package Part_2;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class FileCompressor {


    public byte[] readFile(String fileName) throws IOException {

        FileInputStream fin = new FileInputStream(fileName);
        BufferedInputStream bin = new BufferedInputStream(fin);
//        System.out.println("Number of remaining bytes:" + bin.available());
        int bytesRead, offset = 0, maxReadLen = 4096, testBounds = 0;
        byte[] inputByteArr = new byte[bin.available()];
        while (bin.available() > 0) {
//            System.out.println(offset + "\t" + bin.available() + " " + maxReadLen);
            if (bin.available() < maxReadLen) {
                maxReadLen = bin.available();
            }
//            System.out.println(offset + "\t" + bin.available() + " " + maxReadLen);
            bytesRead = bin.read(inputByteArr, offset, maxReadLen);
            if ((testBounds = inputByteArr.length - (offset + maxReadLen)) > maxReadLen) {
//                System.out.println(testBounds);
                offset += bytesRead;
            } else {
                offset += bytesRead;
                maxReadLen = testBounds;
            }
        }

        return inputByteArr;
    }


}
