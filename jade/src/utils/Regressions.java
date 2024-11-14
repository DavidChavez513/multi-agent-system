package utils;

import java.util.HashMap;
import java.util.Map;

public class Regressions {

    static DiscreetMaths dm = new DiscreetMaths();
    static LinearAlgebra la = new LinearAlgebra();
    static Operations ops = new Operations();

    public Map<String, Object> linearRegressionAnalysis() {

        Map<String, Object> calculatedData = new HashMap<>();


        double[][] data = new DataSet().getSigSigmaData();

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

        double[][] normalMatrix = la.MLRNormalMatrix(dataSet);

        double[] normalForTarget = la.multiply(normalMatrix, targetVector);

        double[] betas = la.resolveEquationSystem(normalMatrix, normalForTarget);

        // data.put("betas", betas);

        return data;
    }

    public void bestCurveToDataSet(int degreeRegression) {

        
    }

    public double[] predictions(int noPredicts, double[] betas, int degreeRegression) {

        return new double[1];
    }

    public Map<String, Object> fittingByMultipleLinearRegression() {

        Map<String, Object> fittingMap = new HashMap<>();


        return fittingMap;
    }

}