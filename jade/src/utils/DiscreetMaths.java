package utils;

public class DiscreetMaths {
    
    public double RiemannSum(double[] data) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum;
    }

    public double RiemannSum(double[] data, double mean) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i] - mean, 2);
        }
        return sum;
    }

    public double RiemannSumHats(double[] data, double[] hats) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i] - hats[i], 2);
        }
        return sum;
    }

    public double RiemannSumAbs(double[] data) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.abs(data[i]);
        }
        return sum;
    }

    public double RiemannSum(double[] data, double[] vectorMultiply) {
        double res = 0;

        for (int i = 0; i < vectorMultiply.length; i++) {
            res += data[i] * vectorMultiply[i];
        }

        return res;
    }


    public double multiplyXForY(double[][] dataMatrix) {
        double sum = 0;
        for (int i = 0; i < dataMatrix.length; i++) {
            sum += dataMatrix[i][0] * dataMatrix[i][1];
        }
        return sum;
    }
    
    public int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n-1);
    }
    
    public double mean(double[] vector){
        double sumVector = 0;

        for (int i = 0; i < vector.length; i++) {
            sumVector += vector[i];
        }

        return sumVector / vector.length;
    }

    public int nCr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n-r));
    }

    public int nPr(int n, int r) {
        return factorial(n) / factorial(n-r);
    }

    public double[] errorPercentForRow(double[] data, double[] hats) {

        double[] percents = new double[data.length];

        for (int i = 0; i < percents.length; i++) {
            percents[i] = Math.abs(data[i] / hats[i]);
        }

        return percents;
    }

    public double[][] generatePeople(double[][] matrix, int max, int min) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = Math.random() * (max - min + 1) + min;
            }
        }

        return matrix;
    }

}