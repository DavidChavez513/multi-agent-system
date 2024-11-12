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

    private double[][] dataForMultipleLinearRegression = {
        {1, 41.9, 29.1},
        {1, 43.4, 29.3},
        {1, 43.9, 29.5},
        {1, 44.5, 29.7},
        {1, 47.3, 29.9},
        {1, 47.5, 30.3},
        {1, 47.9, 30.5},
        {1, 50.2, 30.7},
        {1, 52.8, 30.8},
        {1, 53.2, 30.9},
        {1, 56.7, 31.5},
        {1, 57.0, 31.7},
        {1, 63.5, 31.9},
        {1, 65.3, 32.0},
        {1, 71.1, 32.1},
        {1, 77.0, 32.5},
        {1, 77.8, 32.9}
    };

    private double[] targetVectorY = {
        251.3,
        251.3,
        248.3,
        267.5,
        273.0,
        276.5,
        270.3,
        274.9,
        285.0,
        290.0,
        297.0,
        302.5,
        304.5,
        309.3,
        321.7,
        330.7,
        349.0,
    };

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


    public double[][] getDataForLinearRegression() {
        return dataMatrix;
    }


    public double[][] getDataForMultipleLinearRegression() {
        return dataForMultipleLinearRegression;
    }

    public double[] getTargetMLRegression() {
        return targetVectorY;
    }

    public double[][] getSigSigmaData() {
        return sixSigmaBeltData;
    }
}