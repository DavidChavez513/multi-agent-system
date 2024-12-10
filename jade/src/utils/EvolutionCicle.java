package utils;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionCicle {

    public final double MUTATION_RATE = 0.01;
    public final double LAPS_ON_ROULETTE = 6;
    public final double CROSSOVER = 0.95;
    public final double FIT_OPTIMAL = 0.85;
    public final int NUMBER_OF_EVOLUTIONS = 300;
    public final int CITIZENS_ON_THE_TOWN = 100;

    private ArrayList<Citizen> townToEvolute;
    private double[][] dataset;

    public EvolutionCicle(ArrayList<Citizen> town, double[][] townToSearch) {
        this.townToEvolute = town;
        this.dataset = townToSearch;
    }

    public EvolutionCicle() {
    }

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

        while (winners.size() < 10) {

            Citizen citizenWins = townToEvolute.get(random.nextInt(townToEvolute.size()));

            winners.add(citizenWins);

        }

        return winners.stream().max((dad, mom) -> Double.compare(dad.getFitness(), mom.getFitness())).orElse(null);

    }

    public Citizen bestCitizenOnTheTown() {
        return townToEvolute.stream()
                .max((bestCitizen, bestCitizen2) -> Double.compare(bestCitizen.getFitness(), bestCitizen2.getFitness()))
                .orElse(null);
    }

    public Citizen crossover(Citizen dad, Citizen mom) {

        double[] genes = dad.getGenes();

        double[] mutations = new double[genes.length];

        int genValues = (int) (Math.random() * ((dad.getGenes().length - 1) - 0 + 1) + 0);

        for (int i = 0; i < genes.length; i++) {
            if (new Random().nextBoolean()) {
                mutations[i] = (dad.getGenes()[i] + mom.getGenes()[i]) / genes.length;
                shuffleChromosomes(dad, mom);
            } else {
                mutations[i] = Math.random() * (150 - 0 + 1) + 0;
            }
        }

        return new Citizen(mutations);
    }

    public void mutateTown() {
        for (Citizen citizen : townToEvolute) {
            if (Math.random() < MUTATION_RATE) {
                citizen.mutate();
            }
        }
    }

    public void shuffleChromosomes(Citizen child, Citizen parent) {

        for (int i = 0; i < parent.getGenes().length; i++) {
            shuffle(child.getGenes(), parent.getGenes());
        }

    }

    public void shuffle(double[] individual, double[] parent) {
        if (individual.length != parent.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        for (int i = individual.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            swap(individual, i, index);
            swap(parent, i, index);
        }
    }

    public void swap(double[] array, int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}