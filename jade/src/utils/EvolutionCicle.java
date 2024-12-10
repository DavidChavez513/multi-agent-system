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
            if (new Random().nextBoolean())
                mutations[i] = (dad.getGenes()[i] + mom.getGenes()[i]) / genes.length;

            else
                mutations[i] = Math.random() * (dad.getGenes()[genValues] - 0 + 1) + 0;
        }

        return new Citizen(genes);
    }

    public void mutateTown() {
        for (Citizen citizen : townToEvolute) {
            if (Math.random() < MUTATION_RATE) {
                citizen.mutate();
            }
        }
    }

}