package Part_2;

import java.nio.ByteBuffer;
import java.util.*;

public class Encoder {
    public int unitBytes;
    private Node root = null;
    private HashMap<BytesKey, Integer> charFreqMap = new HashMap<BytesKey, Integer>();
    public HashMap<BytesKey, String> charCodeMap = new HashMap<BytesKey, String>();

    public Encoder(int unitBytes) { this.unitBytes = unitBytes; }

    public Node getRoot() { return root; }

    //-----------------------------------------------------------------------------------------------

    public void buildFreqMap(ByteBuffer sourceBytes) {


        int maxLen = unitBytes, check = 0;

        while (sourceBytes.remaining() > 0) {
            byte[] byteArr = new byte[maxLen];
            sourceBytes.get(byteArr, 0, maxLen);
            BytesKey byteArrWrapped = new BytesKey(byteArr);
//            System.out.println("Read characters\t" + new String(byteArr)  + "\tRemaining Bytes:" + sourceBytes.remaining());
            charFreqMap.put(byteArrWrapped , charFreqMap.getOrDefault(byteArrWrapped, 0) + 1);
//            System.out.println("Frequency: " + charFreqMap.get(byteArrWrapped));

            if ( (check = sourceBytes.remaining()) < maxLen) {
                maxLen = check;
            }
        }


//        System.out.println(charFreqMap.size());
    }

    public void buildTree() {

        PriorityQueue<Node> nByteEntries = new PriorityQueue<Node>(charFreqMap.size(), new NodeComparator());

        // create nodes for every n bytes
        for (Map.Entry<BytesKey, Integer> set : charFreqMap.entrySet()) {
            nByteEntries.add(new Node(set.getKey(), set.getValue(), null, null));
        }
        // build tree
        while (nByteEntries.size() > 1) {

            Node left = nByteEntries.remove();
            Node right = nByteEntries.remove();

            Node f = new Node(null, left.getFrequency() + right.getFrequency(), left, right);
            root = f;

            nByteEntries.add(f);
        }

//        System.out.println(root.getFrequency());
    }

    public void buildCodeMap(Node root, Integer s, int bits) {
        BytesKey data = root.getData();
        if (root.getLeft() == null && root.getRight() == null && data != null) {
            charCodeMap.put(data, String.format("%" + bits + "s", Integer.toBinaryString(s)).replace(' ', '0'));
            return;
        }

        buildCodeMap(root.getLeft(), (s<<1), bits + 1);
        buildCodeMap(root.getRight(), (s<<1) + 0b1, bits + 1);

    }

    public StringBuilder encodedText(ByteBuffer sourceBytes) {
        StringBuilder encodedText = new StringBuilder();

        int maxLen = unitBytes, check = 0;

        while (sourceBytes.remaining() > 0) {
            byte[] byteArr = new byte[maxLen];
            sourceBytes.get(byteArr, 0, maxLen);
//            System.out.println("Read characters\t" + new String(byteArr)  + "\tRemaining Bytes:" + sourceBytes.remaining());
            encodedText.append(charCodeMap.get(new BytesKey(byteArr)));
//            System.out.println(charCodeMap.get(new BytesKey(byteArr)));
            if ((check = sourceBytes.remaining()) < maxLen) {
//                byteArr = new byte[maxLen];
                maxLen = check;
            }
        }
        return encodedText;
    }

    public void printCodeMap () {
        String byteAsString;
        for (Map.Entry<BytesKey, String> set: charCodeMap.entrySet()) {
            String s = new String(set.getKey().getArray());
            System.out.println(s + ":" + set.getValue());
        }
    }

}