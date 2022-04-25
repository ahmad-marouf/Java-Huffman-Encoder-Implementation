package Part_2;

import java.nio.ByteBuffer;

public class Node {
    private BytesKey data;
    private int frequency;
    private Node left;
    private Node right;

    public Node(BytesKey data, int frequency, Node left, Node right) {
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public int getFrequency() { return frequency; }

    public BytesKey getData() { return data; }

    public Node getLeft() { return left; }

    public Node getRight() { return right; }

}
