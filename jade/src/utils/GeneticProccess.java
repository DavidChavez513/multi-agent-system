package utils;

public class GeneticProccess {

    private int peoplePerGeneration;
    private double crossoverRate;

    static DiscreetMaths dMaths = new DiscreetMaths();
    static LinearAlgebra lAlgebra = new LinearAlgebra();
    static Operations ops = new Operations();

    public GeneticProccess(int people, double crsOvr) {
        this.peoplePerGeneration = people;
        this.crossoverRate = crsOvr;
    }

    public double[] combinationChromosomes(double[] individual, double[] parent) {
        
        for (int i = 0; i < parent.length; i++) {
            ops.shuffle(individual, parent);
        }

        return individual;

    }

    public double[][] crossover(double[][] lastGeneration) {

        double[][] nextGeneration = new double[this.peoplePerGeneration][lastGeneration[0].length];

        double[] maxAndMinValue = dMaths.maxAndMinGenOnParent(lastGeneration[(int) (Math.random() * this.peoplePerGeneration)]);

        nextGeneration = dMaths.generatePeople(nextGeneration, maxAndMinValue[0], maxAndMinValue[1]);

        int i = 0;

        for (double[] individual : lastGeneration) {
            
            if (this.crossoverRate < Math.random()) {
                nextGeneration[i] = individual;
                continue;
            }

            double[] parent = selectParents(lastGeneration);
            nextGeneration[i] = combinationChromosomes(individual, parent);
            i++;
        }

        return nextGeneration;
    }

    public double[] selectParents(double[][] generation) {

        int survivor = (int) ((Math.random() * 100));
        return generation[survivor];
        
    }

    public double[] evaluateGeneration(double[][] generation, double[][] data) {
        double[] result = new double[generation.length];
        double[] hats = new double[data.length];

        int person = 0;

        double[] dataObserved = lAlgebra.getColumn(data, data[0].length - 1);

        double yMean = dMaths.mean(dataObserved);
        double sst = dMaths.RiemannSum(dataObserved, yMean);

        while (person < generation.length) {

            for (int i = 0; i < hats.length; i++) {
                for (int j = 0; j < data[0].length; j++) {

                    if (j == 0) {
                        hats[i] = generation[person][j];
                        continue;
                    }
                    hats[i] += generation[person][j] * data[i][j - 1];
                }
            }

            double sse = dMaths.RiemannSumHats(dataObserved, hats);

            double error = sse / sst;

            double rSquare = 1 - error;

            result[person] = rSquare;

            person++;
        }

        return result;
    }

}