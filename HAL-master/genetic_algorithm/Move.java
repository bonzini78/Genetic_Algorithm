package genetic_algorithm;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private List<String> moves;

    public Move() {
        this.moves = new ArrayList<>();
        int permutations = (int) Math.pow(2, 6);
        for (int bits = 0; bits < permutations; bits++) {
            String permutation = createMoves(bits, 6);
            this.moves.add(permutation);
        }
    }

    public static String createMoves(int bits, int n) {
        String conversion = "";
        while (n-- > 0) {
            int bit = bits & 1;
            if (bit == 0){
                conversion += 0;
            }
            else{
                conversion += 1;
            }
            bits >>= 1;
        }
        return conversion;
    }

    public int getIndex(String gene) {
        int index = this.moves.indexOf(gene);
        return index;
    }
}
