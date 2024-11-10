package utils;

public class DiscretMaths {
    
    public double RiemmanSum(double[] data) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum;
    }

    public double multiply(int[][] dataMatrix) {
        double sum = 0;
        for (int i = 0; i < dataMatrix[0].length; i++) {
            sum += dataMatrix[0][i] * dataMatrix[1][i];
        }
        return sum;
    }
    
    public int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n-1);
    }
    
    public int nCr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n-r));
    }

    public int nPr(int n, int r) {
        return factorial(n) / factorial(n-r);
    }

}