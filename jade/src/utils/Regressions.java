package utils;

import java.util.HashMap;
import java.util.Map;

public class Regressions {

    static DiscreetMaths dm = new DiscreetMaths();
    static LinearAlgebra la = new LinearAlgebra();
    static Operations ops = new Operations();
    static GeneticProccess genProcess = new GeneticProccess(100, 0.95);

    public Map<String, Object> linearRegressionAnalysis() {

        Map<String, Object> calculatedData = new HashMap<>();


        double[][] data = new DataSet().getBennetonCase();

        double beta0 = ops.calculateB0(data);
        double beta1 = ops.calculateB1(data);

        double[] betas = {
            beta0,
            beta1
        };

        double[] hats = ops.yHat(data, betas);

        double[] error = ops.errors(data, hats);
        double[] errorsPercent = ops.errorPercent(data, error);

        double percentErrorGlobal = ops.generalErrorPercent(data, errorsPercent);

        calculatedData.put("beta0", beta0);
        calculatedData.put("beta1", beta1);
        calculatedData.put("errorPercent", percentErrorGlobal);
        calculatedData.put("hats", hats);
        calculatedData.put("error", error);
        calculatedData.put("errorPercentRow", beta0);
        
        System.out.println("Y = " + betas[0] + " + " + betas[1] + "X; Con Linear Regression el porcentaje de error es de: " + percentErrorGlobal);

        return calculatedData;
    }

    public Map<String, Object> multipleLinearRegressionAnalysis() {

        Map<String, Object> data = new HashMap<>();

        double[][] dataSet = new DataSet().getDataForMultipleLinearRegression();
        double[] targetVector = la.getColumn(dataSet, dataSet[0].length - 1);

        dataSet = la.adjustXMatrix(dataSet);

        double[][] transposeData = la.transpose(dataSet);

        double[][] transposeForData = la.multiply(transposeData, dataSet);
        double[] transposeForTarget = la.multiply(transposeData, targetVector);

        double[][] inverseTransposeForData = la.inverse(transposeForData);

        double[] betas = la.multiply(inverseTransposeForData, transposeForTarget);

        double[] hats = ops.yHatMLR(dataSet, betas);

        StringBuffer msg = new StringBuffer("Y = " + betas[0]);

        for (int i = 1; i < betas.length; i++) {
            msg.append(" + " + betas[i] + "x" + i);
        }

        System.out.println(msg);

        // data.put("betas", betas);

        return data;
    }

    public void bestCurveToDataSet(int degreeRegression) {

        
    }

    public Map<String, Object> polynomialLinearRegression() {

        Map<String, Object> data = new HashMap<>();

        

        return data;
    }

    public double[] predictions(int noPredicts, double[] betas, int degreeRegression) {

        return new double[1];
    }

    public Map<String, Object> fittingByMultipleLinearRegression() {

        Map<String, Object> fittingMap = new HashMap<>();


        return fittingMap;
    }

    public void geneticAlgorithm() {

        double[][] matrix = new DataSet().getSigSigmaData();

        double[][] people = new double[100][matrix[0].length];

        people = dm.generatePeople(people, 150, 0);

        boolean isOptimalSolution = false;

        while (!isOptimalSolution) {
            double[] evaluatePeople = genProcess.evaluateGeneration(people, matrix);

            people = genProcess.crossover(people);

            

            



            System.out.println("Control Los");

            // people = nextGeneration;

        }


        System.out.println("Control Log");
    }
}