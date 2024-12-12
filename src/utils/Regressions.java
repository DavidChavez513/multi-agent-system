package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Regressions {

    static DiscreetMaths dm = new DiscreetMaths();
    static LinearAlgebra la = new LinearAlgebra();
    static Operations ops = new Operations();

    public Map<String, Object> linearRegressionAnalysis(double[][] dataset) {

        Map<String, Object> calculatedData = new HashMap<>();

        double beta0 = ops.calculateB0(dataset);
        double beta1 = ops.calculateB1(dataset);

        double[] betas = {
                beta0,
                beta1
        };

        double[] hats = ops.yHat(dataset, betas);

        double[] error = ops.errors(dataset, hats);
        double[] errorsPercent = ops.errorPercent(dataset, error);

        double percentErrorGlobal = ops.generalErrorPercent(dataset, errorsPercent);

        calculatedData.put("betas", betas);
        calculatedData.put("errorPercent", percentErrorGlobal);
        calculatedData.put("hats", hats);
        calculatedData.put("error", error);
        calculatedData.put("errorPercentRow", beta0);

        System.out.println("Y = " + betas[0] + " + " + betas[1]
                + "X; Con Linear Regression el porcentaje de error es de: " + percentErrorGlobal);

        System.out.println(calculatedData.toString());

        

        return calculatedData;
    }

    public Map<String, Object> multipleLinearRegressionAnalysis(double[][] dataset) {

        Map<String, Object> data = new HashMap<>();

        double[] targetVector = la.getColumn(dataset, dataset[0].length - 1);

        dataset = la.adjustXMatrix(dataset);

        double[][] transposeData = la.transpose(dataset);

        double[][] transposeForData = la.multiply(transposeData, dataset);
        double[] transposeForTarget = la.multiply(transposeData, targetVector);

        double[][] inverseTransposeForData = la.inverse(transposeForData);

        double[] betas = la.multiply(inverseTransposeForData, transposeForTarget);

        System.out.println("=============================== Multiple Linear Regression =====================================");
        StringBuffer msg = new StringBuffer("Y = " + betas[0]);

        for (int i = 1; i < betas.length; i++) {
            msg.append(" + " + betas[i] + "x" + i);
        }

        System.out.println(msg);

        double rSquare = ops.calculateRSquare(dataset, betas);

        System.out.println("R^2: " + rSquare);

        System.out.println("=================================================================================================");

        // data.put("betas", betas);

        return data;
    }

    public void bestCurveToDataSet(int degreeRegression) {

    }

    public Map<String, Object> polynomialRegression(double[][] dataset, int degreeToFit) {

        Map<String, Object> data = new HashMap<>();


        double[][] xDataDegree = new double[dataset.length][degreeToFit + 1];
        double[] observedData = la.getColumn(dataset, 1);

        int degree = 0;

        while (degree < xDataDegree[0].length) {
            for (int i = 0; i < xDataDegree.length; i++) {

                xDataDegree[i][degree] = Math.pow(dataset[i][0], degree);
            }
            degree++;
        }

        double[][] xddTranspose = la.transpose(xDataDegree);

        double[][] transposeForXdd = la.multiply(xddTranspose, xDataDegree);

        double[][] inverseXddTranspose = la.inverse(transposeForXdd);
        
        double[] xddTransposeForObservedValues = la.multiply(xddTranspose, observedData);


        double[] betas = la.multiply(inverseXddTranspose, xddTransposeForObservedValues);

        double[] hats = ops.yHat(dataset, betas);

        double ssRes = ops.calculateSSRes(observedData, hats);
        double ssTotal = ops.calculateSSTotal(observedData, dm.mean(observedData));

        System.out.println("============== Polynomial Regression =============================");

        StringBuffer msg = new StringBuffer("Y=" + betas[0]);

        for (int i = 1; i < betas.length; i++) {
            msg.append(" + " + betas[i] + "x^" + i);
        }

        System.out.println(msg);

        double rSquare = 1 - (ssRes / ssTotal);

        System.out.println("R^2: " + rSquare);
        System.out.println("==========================================================================");



        return data;
    }

    private ArrayList<Citizen> createTown (int numberOfGenes) {

        ArrayList<Citizen> town = new ArrayList<Citizen>();
        
        while (town.size() < new EvolutionCicle().CITIZENS_ON_THE_TOWN) {

            double[] genes = new double[numberOfGenes];

            for (int i = 0; i < genes.length; i++) {
                genes[i] = Math.random() * (150 - 0 + 1) + 0;
            }

            town.add(new Citizen(genes));
        }


        return town;
    }

    public void geneticAlgorithm(double[][] matrix) {

        ArrayList<Citizen> town = createTown(matrix[0].length);

        EvolutionCicle evolution = new EvolutionCicle(town, matrix);

        int generation = 0;

        Citizen bestResult = null;
        double rSquare = 0;

        while (generation < evolution.NUMBER_OF_EVOLUTIONS) {
            evolution.evaluateCitizens();

            bestResult = evolution.bestCitizenOnTheTown();

            rSquare = ops.calculateRSquare(matrix, bestResult.getGenes());

            if (rSquare >= evolution.FIT_OPTIMAL) {
                System.out.println("Personaje optimo encontrado en la generacion: " + generation);
                break;
            }

            ArrayList<Citizen> newTown = new ArrayList<>();

            while (newTown.size() < evolution.CITIZENS_ON_THE_TOWN) {
                
                Citizen dad = evolution.rouletteForParents();
                Citizen mom = evolution.rouletteForParents();


                if (Math.random() < evolution.CROSSOVER) {
                    newTown.add(evolution.crossover(dad, mom));
                } else {
                    newTown.add(dad);
                }
            }

            evolution.mutateTown();
            evolution.setTown(newTown);
            generation++;
        }



        System.out.println("========== Genetic Algorithm Prediction =============");
        StringBuffer msg = new StringBuffer("Y=" + bestResult.getGenes()[0]);

        for (int i = 1; i < bestResult.getGenes().length; i++) {
            msg.append(" + " + bestResult.getGenes()[i] + "x^" + i);
        }

        System.out.println(msg);

        System.out.println("R^2: " + rSquare);

        System.out.println("============================================================");


    }
}