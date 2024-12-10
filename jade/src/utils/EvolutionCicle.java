package utils;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionCicle {

    public final double MUTATION_RATE = 0.01;
    public final double LAPS_ON_ROULETTE = 6;
    public final double CROSSOVER = 0.95;
    public final double FIT_OPTIMAL = 0.90;
    public final int NUMBER_OF_EVOLUTIONS = 250;
    public final int CITIZENS_ON_THE_TOWN = 100;

    private ArrayList<Citizen> townToEvolute;
    private double[][] dataset;

    public EvolutionCicle(ArrayList<Citizen> town, double[][] townToSearch) {
        this.townToEvolute = town;
        this.dataset = townToSearch;
    }

    public EvolutionCicle() {}

    public void setTown(ArrayList<Citizen> town) {
        this.townToEvolute = town;
    }

    public void evaluateCitizens() {
        for (Citizen citizen : townToEvolute) {
            citizen.fitness(this.dataset);
        }
    }


    public Citizen rouletteForParents() {

        Random random = new Random();

        ArrayList<Citizen> winners = new ArrayList<>();

        for (int i = 0; i < LAPS_ON_ROULETTE; i++) {
            winners.add(townToEvolute.get(random.nextInt(townToEvolute.size())));
        }

        return winners.stream().max((dad, mom) -> Double.compare(dad.getFitness(), mom.getFitness())).orElse(null);

    }

    public Citizen bestCitizenOnTheTown() {
        return townToEvolute.stream().max((bestCitizen, bestCitizen2) -> Double.compare(bestCitizen.getFitness(), bestCitizen2.getFitness())).orElse(null);
    }

    public ArrayList<Citizen> crossover(Citizen dad, Citizen mom) {

        Random rand = new Random();
        ArrayList<Citizen> sons = new ArrayList<>();

        double[] genes = dad.getGenes();

        double[] mutations = new double[genes.length];

        while (sons.size() < CITIZENS_ON_THE_TOWN) {
            
            for (int i = 0; i < genes.length; i++) {
                if ( Math.random() > CROSSOVER) mutations[i] = (dad.getGenes()[i] + mom.getGenes()[i]) / genes.length;
    
                else mutations[i] = rand.nextDouble() * 50 - 150;
            }

            sons.add(new Citizen(mutations));
        }

        return sons;
    }


    public void mutateTown() {
        for (Citizen citizen : townToEvolute) {
            if (Math.random() < MUTATION_RATE) {
                citizen.mutate();
            }
        }
    }

}