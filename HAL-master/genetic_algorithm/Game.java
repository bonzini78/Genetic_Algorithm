package genetic_algorithm;

import HAL.Gui.GridWindow;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Game {

    private int x, y, population, iterations, generations, A, B, C, D;
    List<Agent> agents;
    Grid grid;
    Move move;

    public Game(int x, int y, int iterations, int generations, List<Agent> agents, int A, int B, int C, int D) {
        this.x = x;
        this.y = y;
        this.population = x * y;
        this.iterations = iterations;
        this.generations = generations;
        this.agents = agents;
        this.grid = new Grid(x, y);
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.move = new Move();
    }

    public void playGame() {
        GridWindow win = new GridWindow(this.x, this.y, 10);
        win.TickPause(10);
        List<Integer> best = new ArrayList<>();
        List<Integer> worst = new ArrayList<>();
        List<Integer> average = new ArrayList<>();
        List<Integer> bestBits = new ArrayList<>();
        List<Integer> worstBits = new ArrayList<>();
        for (int i = 0; i < this.generations ; i++) {
            for (int j = 0; j < this.agents.size(); j++) {
                this.grid.NewAgentSQ(j).equals(this.agents.get(j));
            }
            for (Agent agent : this.grid) {
                this.grid.DrawModel(win);
                List<Agent> neighbours = new ArrayList<>();
                this.grid.GetAgentsHood((ArrayList<Agent>) neighbours, this.grid.getDivHood(), agent.Xsq(), agent.Ysq());
                for (Agent neighbour: neighbours) {
                    playAgainst(agent, neighbour);
                }
            }
            for (Agent agent : this.grid) {
                this.grid.DrawModel(win);
                agent.calculateFitness();
                agent.updateProbability();
                agent.renderAgent();
            }
            List<Agent> oldGeneration = new ArrayList<>(this.grid.AllAgents());
            double max = oldGeneration.get(0).getFitness();
            double min = oldGeneration.get(0).getFitness();
            BitSet bestB = new BitSet();
            BitSet worstB = new BitSet();
            int countBest = 0;
            int countWorst = 0;
            int countAverage = 0;
            for (int j = 0; j < oldGeneration.size(); j++) {
                switch (oldGeneration.get(j).getColour()) {
                    case -16711936:
                        countBest++;
                        break;
                    case -65536:
                        countWorst++;
                        break;
                    case -256:
                        countAverage++;
                        break;
                }
                double tempMax = oldGeneration.get(j).getFitness();
                double tempMin = oldGeneration.get(j).getFitness();
                if (max < tempMax) {
                    max = tempMax;
                    bestB = oldGeneration.get(j).getChromosome();
                }
                if (min > tempMin) {
                    min = tempMin;
                    worstB = oldGeneration.get(j).getChromosome();
                }
            }
            best.add(countBest);
            worst.add(countWorst);
            average.add(countAverage);
            bestBits.add(bestB.cardinality());
            worstBits.add(worstB.cardinality());
            Reproduce reproduce = new Reproduce(oldGeneration, this.population);
            updateAgents(reproduce.getOffspring());
            this.grid.ResetHard();
        }
        Chart chart = new Chart(best, worst, average);
        BitChart bitChart = new BitChart(bestBits, worstBits);
        chart.setVisible(true);
        bitChart.setVisible(true);
    }

    public void updateAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public void playAgainst(Agent a, Agent b) {
        StringBits historyA = new StringBits();
        StringBits historyB = new StringBits();
        boolean moveA, moveB;

        //Play game for i iterations
        for (int i = 0; i < this.iterations; i++) {
            moveA = a.getNextMove(i, historyA.toString(), this.move);
            moveB = b.getNextMove(i, historyB.toString(), this.move);

            //Update scores
            //CC
            if (moveA && moveB) {
                a.updateScore(B);
                b.updateScore(B);
            }
            //CD
            else if (moveA) {
                a.updateScore(D);
                b.updateScore(A);
            }
            //DC
            else if (moveB) {
                a.updateScore(A);
                b.updateScore(D);
            }
            //DD
            else {
                a.updateScore(C);
                b.updateScore(C);
            }

            //Update histories
            if(moveA)
            {
                historyA.set(i*2);
                historyB.set((i*2)+1);
            }
            else
            {
                historyA.clear(i*2);
                historyB.clear((i*2)+1);
            }
            if(moveB)
            {
                historyA.set((i*2)+1);
                historyB.set((i*2));
            }
            else
            {
                historyA.clear((i*2)+1);
                historyB.clear((i*2));
            }
            a.updateGamesPlayed();
            b.updateGamesPlayed();
        }
    }
}
