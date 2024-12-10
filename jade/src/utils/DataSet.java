package utils;

import java.util.HashMap;
import java.util.Map;

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
        {41.9, 29.1, 251.3},
        {43.4, 29.3, 251.3},
        {43.9, 29.5, 248.3},
        {44.5, 29.7, 267.5},
        {47.3, 29.9, 273.0},
        {47.5, 30.3, 276.5},
        {47.9, 30.5, 270.3},
        {50.2, 30.7, 274.9},
        {52.8, 30.8, 285.0},
        {53.2, 30.9, 290.0},
        {56.7, 31.5, 297.0},
        {57.0, 31.7, 302.5},
        {63.5, 31.9, 304.5},
        {65.3, 32.0, 309.3},
        {71.1, 32.1, 321.7},
        {77.0, 32.5, 330.7},
        {77.8, 32.9, 349.0}
    };

    private double[][] sixSigmaBeltData = {
        {108, 95},
        {115, 96},
        {106, 95},
        {97, 97},
        {95, 93},
        {91, 94},
        {97, 95},
        {83, 93},
        {78, 86},
        {54, 73},
        {67, 80},
        {56, 65},
        {53, 69},
        {61, 77},
        {115, 96},
        {81, 87},
        {78, 89},
        {30, 60},
        {45, 63},
        {99, 95},
        {32, 61},
        {25, 55},
        {28, 56},
        {90, 94},
        {89, 93}
    };

    public double[][] bennetonCase = {
        {23, 651},
        {26, 762},
        {30, 856},
        {34, 1063},
        {43, 1190},
        {48, 1298},
        {52, 1421},
        {57, 1440},
        {58, 1518},
    };

    public double[][] examplePolynomialRegression = {
        {0, 10},
        {1, 15},
        {2, 35},
        {3, 60},
        {4, 100},
    };

    public double[][] getDataForLinearRegression() {
        return dataMatrix;
    }


    public double[][] getDataForMultipleLinearRegression() {
        return dataForMultipleLinearRegression;
    }

    public double[][] getSigSigmaData() {
        return sixSigmaBeltData;
    }

    public double[][] getBennetonCase() {
        return bennetonCase;
    }

    public double[][] getExamplePolynomialRegression() {
        return examplePolynomialRegression;
    }


    public Map<String, Object> dataSets() {

        Map<String, Object> directoryMap = new HashMap<>(); 


        directoryMap.put("simple-linear-regression", getBennetonCase());
        directoryMap.put("multiple-linear-regression", getDataForMultipleLinearRegression());
        directoryMap.put("polynomial-regression", getSigSigmaData());


        return directoryMap;
    }

}