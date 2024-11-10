package utils;

public class DataSet {
    private int[][] dataMatrix = {{1, 2, 3,4,5,6,7,8,9}, {4, 8, 12,16,20,24,28,32,36}};

    public int[][] getDataForLinearRegression() {
        return dataMatrix;
    }


    private double[][] dataForMultipleLinearRegression = {};


    public double[][] getDataForMultipleLinearRegression() {
        return dataForMultipleLinearRegression;
    }

}