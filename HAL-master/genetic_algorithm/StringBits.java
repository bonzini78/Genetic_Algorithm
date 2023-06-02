package genetic_algorithm;

import java.util.BitSet;
import java.util.stream.IntStream;

public class StringBits extends BitSet{
    private final int bits;

    public StringBits() {
        this.bits = 6;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(bits);
        IntStream.range(0, bits).mapToObj(i -> get(i) ? '1' : '0').forEach(buffer::append);
        return buffer.toString();
    }
}
