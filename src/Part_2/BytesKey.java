package Part_2;

import java.util.Arrays;

public final class BytesKey {
    // this class is used as a wrapper for a byte[] so that we can insert byte arrays into a hashmap using the Arrays. methods
    // inspired from https://www.baeldung.com/java-map-key-byte-array as a solution to hashing byte arrays

    private final byte[] array;

    public BytesKey(byte[] array) {
        this.array = array;
    }

    public byte[] getArray() {
        return array.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BytesKey bytesKey = (BytesKey) o;
        return Arrays.equals(array, bytesKey.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

}
