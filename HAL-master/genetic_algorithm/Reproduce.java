package genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.BitSet;

public class Reproduce {

    private List<Agent> offspring;

    public Reproduce(List<Agent> parents, int population) {
        this.offspring = new ArrayList<>();
        this.offspring = getNewGeneration(parents, population);
    }

    public List<Agent> getNewGeneration(List<Agent> parents, int population) {
        int newPopulation = 0;
        while (newPopulation < population * 1) {
            double random = Math.random();
            double cumulativeProb = 0.0;
            int count = 0;
            List<Agent> breeders = new ArrayList<>();
            for (Agent agent : parents) {
                cumulativeProb += agent.getProbability();
                if (random <= cumulativeProb && count < 2) {
                    breeders.add(agent);
                    count++;
                    newPopulation++;
                }
                if (count == 2) {
                    breed(breeders);
                    Collections.shuffle(parents);
                    break;
                }
            }
        }
        while (newPopulation < population) {
            offspring.add(new Agent());
            newPopulation++;
        }
        return this.offspring;
    }

    public void breed(List<Agent> breeders) {
        Random random = new Random();
        int crossover = random.nextInt(67);

        BitSet a = breeders.get(0).getChromosome();
        BitSet b = breeders.get(1).getChromosome();

        BitSet c = new BitSet(67);
        BitSet d = new BitSet(67);

        for (int i = 0; i < crossover; i++) {
            if (a.get(i))
                d.set(i);
            if (b.get(i))
                c.set(i);
        }
        for (int i = crossover; i < 67; i++) {
            if (a.get(i))
                c.set(i);
            if (b.get(i))
                d.set(i);
        }

        offspring.add(new Agent(c));
        offspring.add(new Agent(d));
    }

    public List<Agent> getOffspring() {
        return this.offspring;
    }
}