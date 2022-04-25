package Part_2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class FileUtils {

    public static void writeHeader(String outputFile, HashMap<BytesKey, String> codesTable, int unitBytes) throws IOException {
        File file = new File(outputFile);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(String.valueOf(unitBytes));
        fileWriter.write("▐");
        for (BytesKey bytes : codesTable.keySet()) {
            String byteRep = codesTable.get(bytes);
            String strRep = new String(bytes.getArray());
            byteRep = strRep  + byteRep;

            fileWriter.write(byteRep);
            fileWriter.write("▐");
        }
        fileWriter.write("▀");
        fileWriter.close();
    }

    public static void writeBody(String inputFile, String outputFile, HashMap<BytesKey, String> codesTable, int unitBytes) throws IOException {
        File file = new File(inputFile);
        StringBuilder fileCoded = new StringBuilder();
        RandomAccessFile data = new RandomAccessFile(file, "r");
        byte[] rBytes = new byte[unitBytes];
        for (int i = 0, len = (int) (data.length() / unitBytes); i < len; i++) {
            data.readFully(rBytes);
            BytesKey bytesKey = new BytesKey(rBytes);
            String byteRep = codesTable.get(bytesKey);
            fileCoded.append(byteRep);
        }
        //
        String base64 = binaryToText(fileCoded.toString());
        FileWriter fileWriter = new FileWriter(new File(outputFile), true);
        fileWriter.write(base64);
        fileWriter.close();
    }

    public static void decompress(String inputFile, String outputFile) throws IOException {
        File fileInput = new File(inputFile);
        File fileOutput = new File(outputFile);
        HashMap<String, BytesKey> codesTable = new HashMap<>();
        //
        Scanner scanner = new Scanner(fileInput);
        scanner.useDelimiter("▀");
        //
        boolean flag = false;
        while(scanner.hasNext()) {
            String rawCodes = scanner.next();
            if (flag) continue;
            String[] codes = rawCodes.split("▐");
            int unitBytes = Integer.parseInt(codes[0]);
            for (int i = 1; i < codes.length; i++) {
                String stringRep = codes[i].substring(0, unitBytes);
                byte[] bytes = stringRep.getBytes();
                BytesKey key = new BytesKey(bytes);
                String bytesRep = codes[i].substring(unitBytes);
                codesTable.put(bytesRep, key);
            }
            flag = true;
        }
        String fileContent = new String(Files.readAllBytes(Paths.get(inputFile)));
        String content = fileContent.split("▀")[1];
        content = TextToBinary(content);

        String coded = content;
        String readValue = "";
        StringBuilder decoded = new StringBuilder();
        for ( int i = 0; i < coded.length(); i++) {
            readValue = readValue + coded.charAt(i);

            if (codesTable.containsKey(readValue)) {
                BytesKey c = codesTable.get(readValue);
                String x = new String(c.getArray(), StandardCharsets.UTF_8);

                decoded.append(x);
                readValue = "";
            }
        }
        FileWriter fileWriter = new FileWriter(fileOutput);
        fileWriter.write(decoded.toString());
        fileWriter.close();
    }

    public static String binaryToText(String binary) {
        int mod = 0;
        int pieceNo = (int) (binary.length() / 8);
        StringBuilder text = new StringBuilder();

        for (int p = 0; p <= pieceNo; p++) {

            String piece = "";
            try {
                piece = binary.substring(p * 8, (p + 1) * 8);
            } catch (java.lang.StringIndexOutOfBoundsException e) {
                piece = binary.substring(p * 8);
                while (piece.length() < 8) {
                    piece = "0" + piece;
                    mod++;
                }
            }

            int binValue = Integer.parseInt(piece, 2);
            char binCharacter = (char) binValue;

            text.append(binCharacter);
        }

        return mod + text.toString();
    }

    public static String TextToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        int mod = Integer.parseInt(text.substring(0,1));
        text = text.substring(1);

        for (int c = 0; c < text.length(); c++) {
            int n = (int) text.charAt(c);
            String binString = Integer.toBinaryString(n);

            while (binString.length() < 8) {
                binString = "0" + binString;
            }
            if ( c == text.length()-1 ) {
                binString = binString.substring(mod);
            }
            binary.append(binString);
        }
        return binary.toString();
    }
}
