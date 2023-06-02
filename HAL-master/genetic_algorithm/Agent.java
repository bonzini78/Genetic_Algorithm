package genetic_algorithm;

import HAL.GridsAndAgents.AgentSQ2Dunstackable;
import HAL.Util;

import java.util.BitSet;
import java.util.Random;

public class Agent extends AgentSQ2Dunstackable<Grid>{

    private BitSet chromosome;
    private int score, colour, gamesPlayed;
    private double fitness, probability;

    public Agent() {
        this.score = 0;
        this.colour = Util.WHITE;
        this.chromosome = new BitSet(67);
        Random rand = new Random();
        for (int i = 0; i < 67; i++) {
            if(rand.nextBoolean()){
                chromosome.set(i);
            }
            else
                chromosome.clear(i);
        }
        this.gamesPlayed = 0;
    }

    public Agent(BitSet chromosome) {
        this.score = 0;
        this.colour = Util.WHITE;
        this.chromosome = chromosome;
        this.gamesPlayed = 0;
    }

    public int getScore(){
        return this.score;
    }

    public void updateScore(int s){
        this.score += s;
    }

    public void updateGamesPlayed(){
        this.gamesPlayed ++;
    }

    public void calculateFitness() {
        this.fitness = ((double) this.score) / this.gamesPlayed;
    }

    public double getFitness() {
        return this.fitness;
    }
    public BitSet getChromosome(){
        return  this.chromosome;
    }

    public int getColour() {
        return this.colour;
    }

    public double getProbability() {
        return probability;
    }

    public void updateProbability() {
        if (this.fitness >= 0 && this.fitness <= 1.5) {
            this.probability = 0;
        } else if (this.fitness > 1.5 && this.fitness <= 3.5) {
            this.probability = 0;
        }
        else if (this.fitness > 3.5) {
            this.probability = 1;
        }
    }

    public boolean getNextMove(int iteration, String history, Move move) {
        switch(iteration) {
            case 0:
                return chromosome.get(64);
            case 1:
                return chromosome.get(65);
            case 2:
                return chromosome.get(67);
            default:
                int x = move.getIndex(history);
                return chromosome.get(x);
        }
    }

    public void renderAgent() {
        if (this.fitness >= 0 && this.fitness <= 1.5) {
            this.colour = Util.RED;
        } else if (this.fitness > 1.5 && this.fitness <= 3.5) {
            this.colour = Util.YELLOW;
        }
        else if (this.fitness > 3.5){
            this.colour = Util.GREEN;
        }
    }
}

