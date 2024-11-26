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

    public double[] evaluateGeneration(double[][] generation, double[][] data) {
        double[] result = new double[generation.length];
        double[] hats = new double[data.length];

        int person = 0;

        double yMean = mean(lAlgebra.getColumn(data, data[0].length - 1));
        double sst = dMaths.RiemannSum(lAlgebra.getColumn(data, data[0].length - 1), yMean);

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

            double sse = dMaths.RiemannSumHats(lAlgebra.getColumn(data, data[0].length - 1), hats);

            double error = sse / sst;

            double rSquare = 1 - error;

            result[person] = rSquare;

            person++;
        }


        return result;
    }

    public double mean(double[] data) {
        double result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i];
        }

        return result / data.length;
    }

}
