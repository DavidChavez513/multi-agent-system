package utils;

import java.util.HashMap;
import java.util.Map;

public class Regressions {


    static DiscretMaths dm = new DiscretMaths();
    static LinearAlgebra la = new LinearAlgebra();

    private class Linear {

        private DataSet dataSet;
        private int[][] dataMatrix;
        private double error = 0;
        private double beta0 = 0;
        private double beta1 = 0;
        private double[] yHat;
        private int[] y;
        private int[] x;
    
        public Linear(DataSet _data) {
            this.dataSet = _data;
            this.dataMatrix = dataSet.getDataForLinearRegression();
            this.yHat = new double[dataMatrix[0].length];
            this.y = new int[dataMatrix[0].length];
            this.x = new int[dataMatrix[0].length];
        }

        public void calculateBeta0() {
            double sumY = dm.RiemmanSum(la.getColumn(dataMatrix, 1));
            double sumX = dm.RiemmanSum(la.getColumn(dataMatrix, 0));
            double sumXY = dm.multiply(dataMatrix);
            double sumX2 = la.powVector(la.getColumn(dataMatrix, 0), 2);

            beta0 = (sumY * sumX2 - sumX * sumXY) / (dataMatrix[0].length * sumX2 - sumX * sumX);
        }

        public int[][] getDataMatrix() {
            return dataMatrix;
        }

        public void calculateBeta1() {
            double sumY = dm.RiemmanSum(la.getColumn(dataMatrix, 1));
            double sumX = dm.RiemmanSum(la.getColumn(dataMatrix, 0));
            double sumXY = dm.multiply(dataMatrix);
            double sumX2 = la.powVector(la.getColumn(dataMatrix, 0), 2);

            beta1 = (dataMatrix[0].length * sumXY - sumX * sumY) / (dataMatrix[0].length * sumX2 - sumX * sumX);
        }

        public void calculateYHat() {
            for (int i = 0; i < dataMatrix[0].length; i++) {
                yHat[i] = beta0 + beta1 * dataMatrix[0][i];
            }
        }

        public void calculateError() {
            for (int i = 0; i < dataMatrix[0].length; i++) {
                error += (dataMatrix[1][i] - yHat[i]) * (dataMatrix[1][i] - yHat[i]);
            }
        }

        public void calculateY() {
            for (int i = 0; i < dataMatrix[0].length; i++) {
                y[i] = dataMatrix[1][i];
            }
        }

        public void calculateX() {
            for (int i = 0; i < dataMatrix[0].length; i++) {
                x[i] = dataMatrix[0][i];
            }
        }

        public double getBeta0() {
            return beta0;
        }

        public double getBeta1() {
            return beta1;
        }

        public double[] getyHat() {
            return yHat;
        }

        public int[] getY() {
            return y;
        }

        public int[] getX() {
            return x;
        }

        public double getError() {
            return error;
        }

        @Override
        public String toString() {

            System.out.println("Y = " + beta0 + " + " + beta1 + "X");
            System.out.println("Error: " + error);

            System.out.println("============ TABLA DE RESULTADOS ============");

            System.out.println("| X\t| Y\t| YHat\t| Error");

            for (int i = 0; i < dataMatrix[0].length; i++) {
                System.out.println("| " + dataMatrix[0][i] + "\t| " + dataMatrix[1][i] + "\t| " + yHat[i] + "\t| "
                        + (dataMatrix[1][i] - yHat[i]));
            }

            return "";

        }

    }

    private class MultipleLinear {

        private double[] coefficients;
        private double[][] dataMatrix;
        private double[] targetVector;

        public MultipleLinear(double[][] _data, double[] _vector) {
            this.coefficients = new double[_data[0].length];
            this.dataMatrix = _data;
            this.targetVector = _vector;
        }

        public void fit() {
            double[][] transposeMatrix = la.transpose(dataMatrix);
            double[][] xTx = la.multiply(transposeMatrix, dataMatrix);
            double[][] inverseX_XT = la.inverse(xTx);
            double[] x_yT = la.multiply(transposeMatrix, targetVector);

            this.coefficients = la.multiply(inverseX_XT, x_yT);
        }
    }

    private class Polynomial {

    }

    public Map<String, Object> fitByLinearRegression() {

        System.out.println("Is an log information");

        DataSet test = new DataSet();

        System.out.println(test.getDataForLinearRegression());

        Linear lr = new Linear(test);
        lr.calculateBeta0();
        lr.calculateBeta1();
        lr.calculateError();
        lr.calculateYHat();

        lr.toString();

        Map<String, Object> betasMap = new HashMap<>();

        betasMap.put("beta_0", lr.getBeta0());
        betasMap.put("beta_1", lr.getBeta1());
        betasMap.put("error", lr.getError());
        betasMap.put("Y_hat", lr.getyHat());

        return betasMap;

    }

    public double[] predictionsLinearRegression(int noPredicts, Linear lr) {

        double[] predictions = new double[noPredicts];
        for (int i = 0; i < noPredicts; i++) {
            predictions[i] = lr.getBeta0() + lr.getBeta1() * (lr.getDataMatrix()[0][8] + ((i + 1) * 2));
        }
        return predictions;
    }

    public Map<String, Object> fittingByMultipleLinearRegression() {

        Map<String, Object> fittingMap = new HashMap<>();

        return fittingMap;
    }

}