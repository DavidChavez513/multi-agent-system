package utils;

public class DataSet {
    private double[][] dataMatrix = {
        {1, 2},
        {2, 4},
        {3, 6},
        {4, 8},
        {5, 10},
        {6, 12},
        {7, 14},
        {8, 16},
        {9, 18}
    };
    

    public double[][] getDataForLinearRegression() {
        return dataMatrix;
    }


    private double[][] dataForMultipleLinearRegression = {};


    public double[][] getDataForMultipleLinearRegression() {
        return dataForMultipleLinearRegression;
    }

    private double[][] sixSigmaBeltData = {
        {108, 95},
        {115, 96},
        {106, 95},
        {97, 97},
        {95, 93},
        {91, 94},
        {97, 93},
        {83, 92},
        {83, 86},
        {78, 86},
        {54, 73},
        {67, 80},
        {56, 65},
        {53, 69},
        {61, 77},
        {115, 96},
        {81, 87},
        {78, 89},
        {30, 80},
        {45, 63},
        {99, 95},
        {32, 61},
        {25, 55},
        {28, 56},
        {50, 94},
        {90, 94},
        {89, 93}
    };

    public double[][] getSigSigmaData() {
        return sixSigmaBeltData;
    }

}