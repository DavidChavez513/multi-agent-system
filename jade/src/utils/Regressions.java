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

        double[][] transposeData = la.transpose(dataSet);

        double[][] transposeXForX = la.multiply(transposeData, dataSet);

        double[][] inverseProductXTX = la.inverse(transposeXForX);

        double[] transposeXForY = la.multiply(transposeData, new DataSet().getTargetMLRegression());

        double[] betas = la.multiply(inverseProductXTX, transposeXForY);

        double[] hats = ops.yHatMLR(dataSet, betas);

        double[] error = ops.errors(dataSet, hats);

        double percentErrorGlobal = ops.generalErrorPercent(dataSet, error);

        StringBuffer msg = new StringBuffer("Y = " + betas[0]);

        for (int i = 1; i < betas.length; i++) {
            msg.append( betas[i] + "X"+ i + " + ");
        }

        System.out.println(msg + " Error: " + percentErrorGlobal);

        data.put("betas", betas);

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