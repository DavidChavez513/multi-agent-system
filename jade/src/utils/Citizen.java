package utils;

import java.util.Arrays;
import java.util.Random;

public class Citizen {

    private static LinearAlgebra la = new LinearAlgebra();
    private static DiscreetMaths dMaths = new DiscreetMaths();
    private static Operations ops = new Operations();

    private double[] genes;
    private double fitness;

    public Citizen(double[] _genes) {
        this.genes = _genes;
        this.fitness = 0;
    }

    public Citizen(Citizen parent) {
        this.genes = parent.getGenes();
    }

    public double[] getGenes() {
        return this.genes;
    }

    public double getFitness() {
        return this.fitness;
    }

    public void fitness(double[][] dataset) {

        double[] observedData = la.getColumn(dataset, 1);

        double observedMean = dMaths.mean(la.getColumn(dataset, 1));
        double ssTotal = Arrays.stream(observedData).map(fitness -> Math.pow(fitness - observedMean, 2)).sum();
        double ssRes = 0;

        int observedRow = 0;
        int it = 0;

        double hat = 0;

        do {
            
            if (it == this.genes.length) {
                it = 0;
                ssRes += Math.pow(observedData[observedRow] - hat, 2);
                continue;
            }

            if (it == 0) {
                hat = genes[it];
                it++;
                continue;
            }

            hat += genes[it] * dataset[observedRow][0];
            it++;
            observedRow++;
        } while (observedRow < dataset.length);


        this.fitness = ssTotal > 0 ? 1 - (ssRes / ssTotal) : 0;

    }

    public void mutate() {

        Random random = new Random();

        for (int i = 0; i < genes.length; i++) {
            if (random.nextBoolean()) {
                genes[i] = Math.random() * (250 - 0 + 1) + 0;
            }
        }

        
    }

}
