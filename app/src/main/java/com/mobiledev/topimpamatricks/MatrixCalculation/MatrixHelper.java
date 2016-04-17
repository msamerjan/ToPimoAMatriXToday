package com.mobiledev.topimpamatricks.MatrixCalculation;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;

/**
 * Created by maiaphoebedylansamerjan on 4/17/16.
 */
public class MatrixHelper {
    public static CDenseMatrix64F makeComplex(DenseMatrix64F matrix) {
        CDenseMatrix64F complex = new CDenseMatrix64F(matrix.numRows, matrix.numCols);
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                complex.setReal(r, c, matrix.get(r, c));
                complex.setImaginary(r, c, 0);
            }
        }
        return complex;

    }

    public static DenseMatrix64F makeReal(CDenseMatrix64F matrix) {
        DenseMatrix64F real = new DenseMatrix64F(matrix.numRows, matrix.numCols);
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                real.set(r, c, matrix.getReal(r, c));
            }
        }
        return real;
    }

    public static Complex64F[] makeVector(CDenseMatrix64F matrix) {
        if (matrix.numCols != 1 && matrix.numRows != 1) throw new IllegalArgumentException();
        if (matrix.numCols == 1) {
            Complex64F[] vector = new Complex64F[matrix.numRows];
            for (int r = 0; r < matrix.numCols; r ++) {
                vector[r] = new Complex64F(matrix.getReal(r, 0), matrix.getImaginary(r, 0));
            }
            return vector;
        }
        Complex64F[] vector = new Complex64F[matrix.numCols];
        for (int c = 0; c < matrix.numCols; c ++) {
            vector[c] = new Complex64F(matrix.getReal(0, c), matrix.getImaginary(0, c));
        }
        return vector;
    }

    public static double[] makeVector(DenseMatrix64F matrix) {
        if (matrix.numCols != 1 && matrix.numRows != 1) throw new IllegalArgumentException();
        if (matrix.numCols == 1) {
            double[] vector = new double[matrix.numRows];
            for (int r = 0; r < matrix.numCols; r ++) {
                vector[r] = matrix.get(r, 0);
            }
            return vector;
        }
        double[] vector = new double[matrix.numCols];
        for (int c = 0; c < matrix.numCols; c ++) {
            vector[c] = matrix.get(0, c);
        }
        return vector;
    }


    public static boolean isReal(CDenseMatrix64F matrix) {
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.getImaginary(r, c) != 0) return false;
            }
        }
        return true;
    }

    public static boolean isSquare(CDenseMatrix64F matrix) {
        return matrix.numCols == matrix.numRows;
    }

    public static Complex64F trace(CDenseMatrix64F matrix) {
        double realPart = 0;
        double imaginaryPart = 0;
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (r == c) {
                    realPart += matrix.getReal(r, c);
                    imaginaryPart += matrix.getImaginary(r, c);
                }
            }
        }
        return new Complex64F(realPart, imaginaryPart);
    }

    public static boolean isPrime(double n) {
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public static boolean isTwin(double n) {
        return isPrime(n) && (isPrime(n-2) || isPrime(n+2));
    }

    public static double length(double[] vector) {
        double length = 0;
        for (double entry: vector) {
            length += entry*entry;
        }
        return Math.sqrt(length);
    }

}
