package com.mobiledev.topimpamatricks;

import com.mobiledev.topimpamatricks.MatrixCalculation.Detail;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CMatrixFeatures;
import org.ejml.ops.MatrixFeatures;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by maiaphoebedylansamerjan on 4/14/16.
 */
public class MatrixRecyclerViewHelper {
    public static boolean isReal(CDenseMatrix64F matrix) {
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.getImaginary(r, c) != 0) return false;
            }
        }
        return true;
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

    public static boolean isSquare(CDenseMatrix64F matrix) {
        return matrix.numCols == matrix.numRows;
    }

    public static Detail[] getDetails(CDenseMatrix64F matrix) {
        if (MatrixRecyclerViewHelper.isReal(matrix)) {
            return MatrixRecyclerViewHelper.getDetailsReal(MatrixRecyclerViewHelper.makeReal(matrix));
        }

        Detail[] details = new Detail[6];
        details[0] = new Detail("Trace", FormatHelper.complexToString(trace(matrix)));
        details[1] = new Detail("Identity", FormatHelper.booleanToString(CMatrixFeatures.isIdentity(matrix, 1e-8)));
        details[2] = new Detail("Positive definite", FormatHelper.booleanToString(CMatrixFeatures.isPositiveDefinite(matrix)));
        details[3] = new Detail("Unitary", FormatHelper.booleanToString(CMatrixFeatures.isUnitary(matrix, 1e-8)));
        details[4] = new Detail("Square", FormatHelper.booleanToString(MatrixRecyclerViewHelper.isSquare(matrix)));
        details[5] = new Detail("Hermitian", FormatHelper.booleanToString(CMatrixFeatures.isHermitian(matrix, 1e-8)));
        return details;
    }

    private static Detail[] getDetailsReal(DenseMatrix64F matrix) {
        if (MatrixFeatures.isVector(matrix)) {
            return getDetailsVector(matrix);
        }
        SimpleMatrix simple = SimpleMatrix.wrap(matrix);
        String eigenvalues = "";
        String eigenvectors = "";
        int numEigenvalues = simple.eig().getNumberOfEigenvalues();
        for (int i = 0; i < numEigenvalues; i++) {
            if (i < numEigenvalues - 1) {
                eigenvalues += "\\lambda_" + i + " = " + simple.eig().getEigenvalue(i);
                eigenvectors += "v_" + i + " = " + simple.eig().getEigenVector(i);
            } else {
                eigenvalues += "\\lambda_" + i + " = " + simple.eig().getEigenvalue(i) + ", ";
                eigenvectors += "v_" + i + " = " + simple.eig().getEigenVector(i) + ", ";
            }
        }

        Detail[] details = new Detail[18];
        details[0] = new Detail("Eigenvalues", eigenvalues);
        details[1] = new Detail("Eigenvectors", eigenvectors);
        details[2] = new Detail("Inverse", FormatHelper.matrixToString(simple.invert().getMatrix()));
        details[3] = new Detail("Transpose", FormatHelper.matrixToString(simple.transpose().getMatrix()));
        details[4] = new Detail("Trace", simple.trace() + "");
        details[5] = new Detail("Determinant", simple.determinant() + "");
        details[6] = new Detail("Rank", MatrixFeatures.nullity(matrix) + "");
        details[7] = new Detail("Nullity", MatrixFeatures.rank(matrix) + "");
        details[8] = new Detail("Are rows linearly independent", FormatHelper.booleanToString(MatrixFeatures.isRowsLinearIndependent(matrix)));
        // details[9] = new Detail("Are rows and columns linearly independent", String.valueOf(MatrixFeatures.isFullRank(matrix)));
        details[9] = new Detail("balls? ", "yup");
        details[10] = new Detail("Identity", FormatHelper.booleanToString(MatrixFeatures.isIdentity(matrix, 1e-8)));
        details[11] = new Detail("Orthogonal", FormatHelper.booleanToString(MatrixFeatures.isOrthogonal(matrix, 1e-8)));
        details[12] = new Detail("Positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveDefinite(matrix)));
        details[13] = new Detail("Semi-positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveSemidefinite(matrix)));
        details[14] = new Detail("Skew symmetric", FormatHelper.booleanToString(MatrixFeatures.isSkewSymmetric(matrix, 1e-8)));
        details[15] = new Detail("Square", FormatHelper.booleanToString(MatrixFeatures.isSquare(matrix)));
        details[16] = new Detail("Symmetric", FormatHelper.booleanToString(MatrixFeatures.isSymmetric(matrix)));
        details[17] = new Detail("Upper triangular matrix", FormatHelper.booleanToString(MatrixFeatures.isUpperTriangle(matrix, 0, 1e-8)));

        return details;
    }

    private static Detail[] getDetailsVector(DenseMatrix64F matrix) {
        return new Detail[0];
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

}
