package com.mobiledev.topimpamatricks.MatrixCalculation;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;

/**
 * Created by larspmayrand on 4/14/16.
 */
public class MatrixHelper {

    public static String classify(CDenseMatrix64F matrix) {
        if (isReal(matrix)) {
            if (matrix.numCols == 1) {
                if (matrix.numRows == 1) {
                    return "real number";
                }
                return "real vector";
            }
            return "real matrix";
        }
        if (matrix.numCols == 1) {
            if (matrix.numRows == 1) {
                return "complex number";
            }
            return "complex vector";
        }
        return "complex matrix";
    }

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
        if (!isReal(matrix)) throw new IllegalArgumentException("Matrix is not real");
        DenseMatrix64F real = new DenseMatrix64F(matrix.numRows, matrix.numCols);
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                real.set(r, c, matrix.getReal(r, c));
            }
        }
        return real;
    }

    public static Vector makeReal(Complex64F[] vector) {
        if (!isReal(vector)) throw new IllegalArgumentException("Vector ain't real");
        double[] realPart = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            realPart[i] = vector[i].real;
        }
        return new Vector(realPart);
    }

    public static com.mobiledev.topimpamatricks.Calculator.ComplexVector makeVector(CDenseMatrix64F matrix) {
        if (matrix.numCols != 1 && matrix.numRows != 1)
            throw new IllegalArgumentException("Matrix is not a vector!");
        if (matrix.numCols == 1) {
            Complex64F[] vector = new Complex64F[matrix.numRows];
            for (int r = 0; r < matrix.numRows; r++) {
                vector[r] = new Complex64F(matrix.getReal(r, 0), matrix.getImaginary(r, 0));
            }
            return new com.mobiledev.topimpamatricks.Calculator.ComplexVector(vector);
        }
        Complex64F[] vector = new Complex64F[matrix.numCols];
        for (int c = 0; c < matrix.numCols; c++) {
            vector[c] = new Complex64F(matrix.getReal(0, c), matrix.getImaginary(0, c));
        }
        return new com.mobiledev.topimpamatricks.Calculator.ComplexVector(vector);
    }

    public static Vector makeVector(DenseMatrix64F matrix) {
        if (matrix.numCols != 1 && matrix.numRows != 1)
            throw new IllegalArgumentException("matrix is not a vector");
        if (matrix.numCols == 1) {
            double[] components = new double[matrix.numRows];
            for (int r = 0; r < matrix.numRows; r++) {
                components[r] = matrix.get(r, 0);
            }
            return new Vector(components);
        }
        double[] components = new double[matrix.numCols];
        for (int c = 0; c < matrix.numCols; c++) {
            components[c] = matrix.get(0, c);
        }
        return new Vector(components);
    }

    public static boolean isReal(CDenseMatrix64F matrix) {
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.getImaginary(r, c) != 0) return false;
            }
        }
        return true;
    }

    public static boolean isReal(Complex64F[] vector) {
        for (Complex64F component : vector) {
            if (!component.isReal()) return false;
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
        if (n == 0) return false;
        for (int i = 2; i <= n / 2; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean isTwin(double n) {
        return isPrime(n) && (isPrime(n - 2) || isPrime(n + 2));
    }

    public static boolean isTotallyPositive(CDenseMatrix64F matrix) {
        return false;
    }

    public static boolean isTotallyPositive(DenseMatrix64F matrix) {
        return false;
    }

//    public static boolean isTotallyPositive(CDenseMatrix64F matrix) {
//        if (matrix.numCols != matrix.numRows) return false;
//        for (CDenseMatrix64F submatrix : MatrixHelper.submatrices(matrix)) {
//            if (CCommonOps.det(submatrix).getMagnitude() > 0) return false;
//        }
//        return true;
//    }

//    public static Complex64F[] minors(CDenseMatrix64F matrix64F) {
//
//    }
//
//    public static CDenseMatrix64F[] submatrices(CDenseMatrix64F matrix) {
//        // how many submatrices?
//        /**
//         * delete 1 column
//         * delete 1 row
//         * delete 1 column & 1 row
//         * delete 2 columns & 1 row
//         * delete 1 column and & rows
//         * ...
//         * where
//         */
//        for (int r = 0; r < matrix.numRows; r++) {
//            for (int c = 0; c < matrix.numCols; c++) {
//                if (r == c) {
//                    realPart += matrix.getReal(r, c);
//                    imaginaryPart += matrix.getImaginary(r, c);
//                }
//            }
//        }
//    }

}
