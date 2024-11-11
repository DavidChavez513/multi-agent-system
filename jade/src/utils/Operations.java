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
}
