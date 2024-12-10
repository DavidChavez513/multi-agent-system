package utils;

public class Operations {
    LinearAlgebra lAlgebra = new LinearAlgebra();
    DiscreetMaths dMaths = new DiscreetMaths();


    public double calculateB0(double[][] matrix) {

        if (matrix[0].length == 2) {
            double sumX = dMaths.RiemannSum(lAlgebra.getColumn(matrix, 0));
            double sumY = dMaths.RiemannSum(lAlgebra.getColumn(matrix, 1));
            
            double sumXY = dMaths.multiplyXForY(matrix);
            double sumXPow2 = dMaths.RiemannSum(lAlgebra.powVector(lAlgebra.getColumn(matrix, 0), 2));
            return (sumY * sumXPow2 - sumX * sumXY) / (matrix.length * sumXPow2 - sumX * sumX);
        }
        return 0;
    }

    public double calculateB1(double[][] matrix) {

        if (matrix[0].length == 2) {
            double sumY = dMaths.RiemannSum(lAlgebra.getColumn(matrix, 1));
            double sumX = dMaths.RiemannSum(lAlgebra.getColumn(matrix, 0));
            double sumXY = dMaths.multiplyXForY(matrix);
            double sumX2 = dMaths.RiemannSum(lAlgebra.powVector(lAlgebra.getColumn(matrix, 0), 2));
            
            return (matrix[0].length * sumXY - sumX * sumY) / (matrix[0].length * sumX2 - sumX * sumX);
        }

        return 0;
    }

    public double[] yHat(double[][] matrix, double[] betas) {
        double[] hats = new double[matrix.length];

        for (int i = 0; i < hats.length; i++) {
            hats[i] = betas[0] + betas[1] * matrix[i][0];
        }

        return hats;
    }

    public double[] yHatMLR(double[][] matrix, double[] betas) {

        double[] hats = new double[matrix.length];

        for (int i = 0; i < hats.length; i++) {

            double yHat = betas[0];
            for (int j = 1; j < betas.length; j++) {
                yHat += betas[j] * matrix[i][j];
            }

            hats[i] = yHat;
        }

        return hats;
    }

    public double[] errors(double[][] matrix, double[] hats) {

        double[] error = new double[matrix.length];

        for (int i = 0; i < error.length; i++) {
            error[i] = matrix[i][1] - hats[i];
        }

        return error;

    }

    public double[] errorPercent(double[][] matrix, double[] error) {
        double[] errors = dMaths.errorPercentForRow(error, lAlgebra.getColumn(matrix, 1));

        for (int i = 0; i < errors.length; i++) {
            errors[i] = errors[i] * 100;
        }

        return errors;
    }

    public double generalErrorPercent(double[][] matrix, double[] error) {
        double sumPercentError = dMaths.RiemannSumAbs(dMaths.errorPercentForRow(error, lAlgebra.getColumn(matrix, 1)));

        return (sumPercentError / matrix.length) * 100;
    }

    public int forth(int iterator, int arrSize) { return (iterator + 1) % arrSize; }

    public void swap(double[] array, int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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

    public double calculateSSRes (double[] observed, double[] hats) {
        if (observed.length != hats.length) {
            System.out.println(" Los datos no son comparables ");
            return 0;
        }

        double res = 0;

        for (int i = 0; i < hats.length; i++) {
            res += Math.pow((observed[i] - hats[i]), 2);
        }

        return res;
    }


    public double calculateSSTotal(double[] observed, double mean) {
        double res = 0;

        for (int i = 0; i < observed.length; i++) {
            res += Math.pow((observed[i] - mean), 2);
        }

        return res;
    }
}
