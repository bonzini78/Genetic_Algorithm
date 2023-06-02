package genetic_algorithm;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Agent> agents = new ArrayList<Agent>();
        int x = 50;
        int y = 20;
        int generations = 5000;
        int iterations = 50;
        int A = 10;
        int B = 5;
        int C = 0;
        int D = -10;

        while (agents.size() < x * y) {
            agents.add(new Agent());
        }

        Game game = new Game(x, y, iterations, generations, agents, A, B, C, D);
        game.playGame();
    }
}
