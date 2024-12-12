package utils;


public class LinearAlgebra {

    static DiscreetMaths dMaths = new DiscreetMaths();
    static Operations ops = new Operations();

    private double[][] minor(double[][] matrix, int row, int column) {
        double[][] minor = new double[matrix.length - 1][matrix[0].length - 1];
        int minorRow = 0;
        int minorColumn = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i == row) {
                continue;
            }
            minorColumn = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == column) {
                    continue;
                }
                minor[minorRow][minorColumn] = matrix[i][j];
                minorColumn++;
            }
            minorRow++;
        }
        return minor;
    }

    public double[][] transpose(double[][] matrix) {
        double[][] transpose = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < transpose.length; i++) {
            for (int j = 0; j < transpose[0].length; j++) {
                transpose[i][j] = matrix[j][i];
            }
        }
        return transpose;
    }

    public double[] getRow(double[][] matrix, int row) {
        double[] rowVector = new double[matrix.length];
        for (int i = 0; i < rowVector.length; i++) {
            rowVector[i] = matrix[i][row];
        }
        return rowVector;
    }

    public double[] getColumn(double[][] dataMatrix2, int column) {
        double[] columnVector = new double[dataMatrix2.length];
        for (int i = 0; i < columnVector.length; i++) {
            columnVector[i] = dataMatrix2[i][column];
        }
        return columnVector;
    }

    public double determinant(double[][] matrix) {
        int n = matrix.length;

    // Caso base para matriz 1x1
    if (n == 1) {
        return matrix[0][0];
    }

    // Caso base para matriz 2x2
    if (n == 2) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    double determinant = 0;

    // Expansión por cofactores en la primera fila
    for (int j = 0; j < n; j++) {
        determinant += matrix[0][j] * cofactorSign(0, j) * determinant(minor(matrix, 0, j));
    }

    return determinant;
    }

    // Calcula el signo del cofactor (-1)^(i+j)
    private int cofactorSign(int i, int j) {
        return (i + j) % 2 == 0 ? 1 : -1;
    }

    public double[][] inverse(double[][] matrix) {
        int n = matrix.length;

        // Calcular el determinante
        double det = determinant(matrix);
        if (det == 0) {
            throw new ArithmeticException("La matriz no tiene inversa (determinante = 0)");
        }

        // Matriz de cofactores
        double[][] cofactors = cofactorMatrix(matrix);

        // Transponer la matriz de cofactores (adjunta)
        double[][] adjoint = transpose(cofactors);

        // Dividir cada elemento por el determinante
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = adjoint[i][j] / det;
            }
        }

        return inverse;
    }

    // Método para calcular la matriz de cofactores
    public double[][] cofactorMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] cofactors = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactors[i][j] = Math.pow(-1, i + j) * determinant(minor(matrix, i, j));
            }
        }
        return cofactors;
    }


    public double[][] cramer(double[][] matrix, double[] vector) {
        double[][] cramer = new double[matrix.length][matrix[0].length];
        double determinant = determinant(matrix);
        double[][] temp = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < cramer.length; i++) {
            for (int j = 0; j < cramer[0].length; j++) {
                temp[i][j] = matrix[i][j];
            }
        }
        for (int i = 0; i < cramer.length; i++) {
            for (int j = 0; j < cramer[0].length; j++) {
                temp[j][i] = vector[j];
            }
            cramer[i][0] = determinant(temp) / determinant;
            for (int k = 0; k < cramer.length; k++) {
                for (int l = 0; l < cramer[0].length; l++) {
                    temp[k][l] = matrix[k][l];
                }
            }
        }
        return cramer;
    }

    public double[][] gaussJordan(double[][] matrix) {
        double[][] gaussJordan = new double[matrix.length][matrix[0].length];
        double[][] temp = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < gaussJordan.length; i++) {
            for (int j = 0; j < gaussJordan[0].length; j++) {
                temp[i][j] = matrix[i][j];
            }
        }
        for (int i = 0; i < gaussJordan.length; i++) {
            for (int j = 0; j < gaussJordan[0].length; j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < gaussJordan.length; k++) {
                    for (int l = 0; l < gaussJordan[0].length; l++) {
                        temp[k][l] = matrix[k][l];
                    }
                }
                for (int k = 0; k < gaussJordan[0].length; k++) {
                    temp[j][k] = matrix[j][k] * matrix[i][i] - matrix[i][k] * matrix[j][i];
                }
                for (int k = 0; k < gaussJordan.length; k++) {
                    for (int l = 0; l < gaussJordan[0].length; l++) {
                        matrix[k][l] = temp[k][l];
                    }
                }
            }
        }
        for (int i = 0; i < gaussJordan.length; i++) {
            for (int j = 0; j < gaussJordan[0].length; j++) {
                gaussJordan[i][j] = matrix[i][j] / matrix[i][i];
            }
        }
        return gaussJordan;
    }

    public double[] powVector(double[] vector, int power) {
        double[] powVector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            powVector[i] = Math.pow(vector[i], power);
        }
        return powVector;
    }

    public double[][] multiply(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;

        if (n != b.length) {
            throw new IllegalArgumentException("No se pueden multiplicar matrices de estas dimensiones.");
        }

        double[][] result = new double[m][p];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                double sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public double[] multiply(double[][] a, double[] b) {
        double[] result = new double[a.length];

        if (a[0].length != b.length) {
            System.out.println("No se puede multiplicar una matriz por un vector de diferente numero de columnas");
            return result;
        }

        int i = 0, j = 0;

        do {

            result[i] += a[i][j] * b[j];
            j++;

            if (j == b.length && i < a.length) {
                j = 0;
                i += 1;
                continue;
            }

        } while (j < a[0].length && i < a.length);
        
        return result;
    }

    public double[] resolveEquationSystem(double[][] a, double[] b, double[] y) {

        double[] result = new double[a.length];

        if (a[0].length != b.length) {
            System.out.println("No se puede multiplicar una matriz por un vector de diferente numero de columnas");
            return result;
        }

        int i = 0, j = 0;

        do {

            if (i == 0) {
                result[i] += y[j];
            }

            result[i] += a[i][j] * b[j];
            j++;

            if (j == b.length && i < a.length) {
                j = 0;
                i += 1;
                continue;
            }

        } while (j < b.length && i < a.length);
        
        return result;
    }


    public double[][] MLRNormalMatrix(double[][] matrix) {

        int normalSize = matrix[0].length;

        double[][] normal = new double[normalSize][normalSize];

        int i = 0;
        int k = 0;

        while (i < normal.length) {
            k = 0;
            while (k < normal.length) {
                

                if (i == k) {
                    normal[i][k] = i == 0 ? matrix.length : dMaths.RiemannSum(getColumn(matrix, k), getColumn(matrix, i));
                    k++;
                    continue;
                }

                normal[i][k] = dMaths.RiemannSum(getColumn(matrix, i), getColumn(matrix, k));

                k++;
            }
            i++;
        }

        return normal;
    }

    public double[][] adjustXMatrix(double[][] matrix) {
        double[][] xMatrix = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < xMatrix.length; i++) {
            for (int j = 0; j < xMatrix[0].length; j++) {
                if (j == 0) {
                    xMatrix[i][j] = 1;
                    continue;
                }
                xMatrix[i][j] = matrix[i][j - 1];
            }
        }

        return xMatrix;
    }

}