package com.mobiledev.topimpamatricks.MatrixCalculation;

import com.mobiledev.topimpamatricks.Calculator.ComplexVector;
import com.mobiledev.topimpamatricks.R;

import org.ejml.alg.dense.misc.CTransposeAlgs;
import org.ejml.alg.dense.misc.RrefGaussJordanRowPivot;
import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.ComplexPolar64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CCommonOps;
import org.ejml.ops.CMatrixFeatures;
import org.ejml.ops.MatrixFeatures;
import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;


/**
 * Created by maiaphoebedylansamerjan on 4/29/16.
 */
public class DetailRecyclerViewHelper {
    /**
     * Classifies inputted complex matrix as complex/real, matrix/vector/number.
     * Then calls the appropriate getDetails method
     */
    public static Detail[] getDetails(CDenseMatrix64F matrix) {
        switch (MatrixHelper.classify(matrix)) {
            case "real number":
                return getRealNumberDetails(matrix.getReal(0, 0));
            case "complex number":
                return getComplexNumberDetails(new Complex64F(matrix.getReal(0, 0), matrix.getImaginary(0, 0)));
            case "real vector":
                return getRealVectorDetails(MatrixHelper.makeVector(MatrixHelper.makeReal(matrix)));
            case "complex vector":
                return getComplexVectorDetails(MatrixHelper.makeVector(matrix));
            case "real matrix" :
                return getRealMatrixDetails(MatrixHelper.makeReal(matrix));
        }
        return getComplexMatrixDetails(matrix);
    }

    public static Detail[] getComplexMatrixDetails(CDenseMatrix64F matrix) {
        Detail[] details = new Detail[11];

        // matrix details
        CDenseMatrix64F transposed = matrix.copy();
        CDenseMatrix64F conjugate = matrix.copy();
        if (matrix.numCols == matrix.numRows) { // if matrix is square
            CTransposeAlgs.square(transposed);
            CTransposeAlgs.squareConjugate(conjugate);
        } else {
            CTransposeAlgs.standard(matrix, transposed);
            CTransposeAlgs.standardConjugate(matrix, conjugate);
        }
        details[0] = new Detail("Transpose", FormatHelper.matrixToString(transposed), R.string.transpose_definition + "");
        details[1] = new Detail("Conjugate", FormatHelper.matrixToString(conjugate), "Conjugate: ");

        // numerical details
        details[2] = new Detail("Determinant", FormatHelper.complexToString(CCommonOps.det(matrix)) + "",
                "Determinant: The factor by which a transformation (specifically a square matrix) scales a the unit area.");

        CDenseMatrix64F inverse = matrix.copy();
        CCommonOps.invert(inverse);
        details[3] = new Detail("Inverse", FormatHelper.matrixToString(inverse), "Inverse: The matrix that undoes the transformation.");

        //details[] = new Detail("Real part", FormatHelper.matrixToString())

        details[4] = new Detail("Trace", FormatHelper.complexToString(MatrixHelper.trace(matrix)),
                "Trace: sum of the elements on the main diagonal.");

        // boolean details
        details[5] = new Detail("Self-adjoint (or Hermitian)", FormatHelper.booleanToString(CMatrixFeatures.isHermitian(matrix, 1e-8)),
                "Hermitian matrix: A square matrix equal to its own conjugate transpose.");
        details[6] = new Detail("Identity", FormatHelper.booleanToString(CMatrixFeatures.isIdentity(matrix, 1e-8)),
                "Identity Matrix: A square matrix with 1s along its main diagonal.");
        details[7] = new Detail("Positive definite", FormatHelper.booleanToString(CMatrixFeatures.isPositiveDefinite(matrix)),
                "In linear algebra, a symmetric n × n real matrix M is said to be positive definite if the scalar z^{\\mathrm{T}}Mz is positive " +
                        "for every non-zero column vector" + " z of n real numbers. Here z^{\\mathrm{T}} denotes the transpose of z.");
        /** SOURCE: https://en.wikipedia.org/wiki/Positive-definite_matrix */
        details[8] = new Detail("Unitary", FormatHelper.booleanToString(CMatrixFeatures.isUnitary(matrix, 1e-8)),
                "Unitary: A matrix equal to its conjugate transpose is equal to its inverse.");
        details[9] = new Detail("Square", FormatHelper.booleanToString(MatrixHelper.isSquare(matrix)),
                "Square: width equals height.");

        details[10] = new Detail("Totally positive", FormatHelper.booleanToString(MatrixHelper.isTotallyPositive(matrix)), "Totally positive matrix: A square matrix where the determinant of every square submatrix is positive");

        return details;
    }

    public static Detail[] getRealMatrixDetails(DenseMatrix64F matrix) {
        SimpleMatrix simple = SimpleMatrix.wrap(matrix);

        // eigenstuff
        SimpleEVD eigenstuff = simple.eig();
        int numEigenvalues = eigenstuff.getNumberOfEigenvalues();
        String eigenvalues = "";
        String eigenvectors = "";
        for (int i = 0; i < numEigenvalues; i++) {
            if (i < numEigenvalues - 1) {
                eigenvalues += "λ_" +  i + " = " + FormatHelper.complexToString(eigenstuff.getEigenvalue(i)) + ", ";
                eigenvectors += "v_" + i + " = " + FormatHelper.matrixToString(eigenstuff.getEigenVector(i)) + ", ";
            } else {
                eigenvalues += "λ_" +  i + " = " + FormatHelper.complexToString(eigenstuff.getEigenvalue(i));
                eigenvectors += "v_" + i + " = " + FormatHelper.matrixToString(eigenstuff.getEigenVector(i));
            }
        }

        // Row reduction
        RrefGaussJordanRowPivot rowReducer = new RrefGaussJordanRowPivot();
        DenseMatrix64F rref = matrix.copy();
        rowReducer.reduce(rref, matrix.numCols);

        Detail[] details = new Detail[19];

        // matrix details
        details[0] = new Detail("Row reduced echlon form", FormatHelper.matrixToString(rref),
                "The matrix resulting from Gaussian elimination.");


        details[1] = new Detail("Eigenvalues", eigenvalues,
                "In linear algebra, an eigenvector or characteristic vector of a linear transformation is a non-zero vector " +
                        "that does not change its direction when that linear transformation is applied to it. In other words," +
                        " if v is a vector that is not the zero vector, then it is an eigenvector of a linear transformation" +
                        " T if T(v) is a scalar multiple of v. This condition can be written as the equation\n" +
                        "\n" +
                        "T(\\mathbf{v}) = \\lambda \\mathbf{v},\n" +
                        "where λ is a scalar known as the eigenvalue or characteristic value associated with the eigenvector v.");
        /** SOURCE: https://en.wikipedia.org/wiki/Eigenvalues_and_eigenvectors */

        details[2] = new Detail("Eigenvectors", eigenvectors,
                "In linear algebra, an eigenvector or characteristic vector of a linear transformation is a non-zero vector that does" +
                        " not change its direction when that linear transformation is applied to it. In other words, if v is a vector that " +
                        "is not the zero vector, then it is an eigenvector of a linear transformation T if T(v) is a scalar multiple of v. " +
                        "This condition can be written as the equation\n" +
                        "\n" +
                        "T(\\mathbf{v}) = \\lambda \\mathbf{v},\n" +
                        "where λ is a scalar known as the eigenvalue or characteristic value associated with the eigenvector v.");
        /** SOURCE: https://en.wikipedia.org/wiki/Eigenvalues_and_eigenvectors */

        details[3] = new Detail("Inverse", FormatHelper.matrixToString(simple.invert().getMatrix()),
                "Inverse: The matrix that undoes the transformation.");

        details[4] = new Detail("Transpose", FormatHelper.matrixToString(simple.transpose().getMatrix()),
                "Transpose: The resulting matrix when reflect elements over the main diagonal.");

        // numerical details
        details[5] = new Detail("Determinant", FormatHelper.round(simple.determinant(), 3) + "",
                "Determinant: The factor by which a transformation (specifically a square matrix) scales a the unit area.");
        details[6] = new Detail("Trace", FormatHelper.round(simple.trace(), 3) + "",
                "Trace: sum of the elements on the main diagonal.");
        details[7] = new Detail("Rank", MatrixFeatures.nullity(matrix) + "",
                "Rank: Dimension of the space spanned by the column vectors.");
        details[8] = new Detail("Nullity", MatrixFeatures.rank(matrix) + "", "Nullity: Dimension of the kernel.");

        // ADD DETAIL FOR KERNEL !!!

        // boolean details
        details[9] = new Detail("Totally positive", FormatHelper.booleanToString(MatrixHelper.isTotallyPositive(matrix)), "Totally positive matrix: A square matrix where the determinant of every square submatrix is positive");
        details[10] = new Detail("Are rows linearly independent", FormatHelper.booleanToString(MatrixFeatures.isRowsLinearIndependent(matrix)), "Are rows linearly independent, what else do you want me to say man.");
        details[11] = new Detail("Orthogonal", FormatHelper.booleanToString(MatrixFeatures.isOrthogonal(matrix, 1e-8)), "Orthogonal: A square matrix whoose columns and rows are orthogonal unit vectors.");
        details[12] = new Detail("Positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveDefinite(matrix)), "Positive definite: a symmetric n × n real matrix M where the scalar z^{\\mathrm{T}}Mz is positive for every non-zero column vector z of n real numbers.");
        details[13] = new Detail("Semi-positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveSemidefinite(matrix)),
                "In linear algebra, a symmetric n × n real matrix M is said to be positive definite if the scalar z^{\\mathrm{T}}Mz is positive for every non-zero column vector z of n real numbers. Here z^{\\mathrm{T}} denotes the transpose of z.[1]\n" +
                        "\n" +
                        "More generally, an n × n Hermitian matrix M is said to be positive definite if the scalar z^{*}Mz is real and positive for all non-zero column vectors z of n complex numbers. Here z^{*} denotes the conjugate transpose of z.\n" +
                        "\n" +
                        "The negative definite, positive semi-definite, and negative semi-definite matrices are defined in the same way, except that the expression z^{\\mathrm{T}}Mz or z^{*}Mz is required to be always negative, non-negative");
        /** SOURCE: https://en.wikipedia.org/wiki/Positive-definite_matrix .*/
        details[14] = new Detail("Identity", FormatHelper.booleanToString(MatrixFeatures.isIdentity(matrix, 1e-8)),
                "Identity Matrix: A square matrix with 1s along its main diagonal.");
        details[15] = new Detail("Square", FormatHelper.booleanToString(MatrixFeatures.isSquare(matrix)), "Square: width equals height.");
        details[16] = new Detail("Symmetric", FormatHelper.booleanToString(MatrixFeatures.isSymmetric(matrix)), "Symmetric: A matrix equal to its transpose.");
        details[17] = new Detail("Upper triangular matrix", FormatHelper.booleanToString(MatrixFeatures.isUpperTriangle(matrix, 0, 1e-8)), "Upper triangular: all entries above the main diagonal are zero.");
        // lower triangular matrix?
        details[18] = new Detail("Skew symmetric", FormatHelper.booleanToString(MatrixFeatures.isSkewSymmetric(matrix, 1e-8)), "Skew symmetric (anti-symmetric): A square matrix that equals its negative transpose");

        // details[9] = new Detail("Are rows and columns linearly independent", String.valueOf(MatrixFeatures.isFullRank(matrix)))

        return details;
    }

    public static Detail[] getComplexVectorDetails(ComplexVector vector) {
        return new Detail[0];
    }

    public static Detail[] getRealVectorDetails(Vector vector) {
        Detail[] details = new Detail[7];
        details[0] = new Detail("Magnitude", "|v| = " + FormatHelper.round(vector.getMagnitude(), 2), "Magnitude: Length of the vector.");
        details[1] = new Detail("Angle", "θ = " + FormatHelper.round(vector.getTheta(), 2), "Angle: Angle from positive horizontal axis to the vector.");
        details[2] = new Detail("Dimension", vector.getDimension() + "", "Dimension: Number of dimensions the vector lives in.");
        details[3] = new Detail("Normalized", FormatHelper.vectorToString(vector.normalize()), "Normalized: The vector which still points the same direction, but has a length of one.");
        details[4] = new Detail("Polar form", FormatHelper.polarVectorToString(vector), "Polar form: where r = distance and θ = angle");
        details[5] = new Detail("Slope in xy-plane", FormatHelper.doubleToString(vector.getComponents()[1] / vector.getComponents()[0], 2), "Slope: of the damn vector duh.");
        details[6] = new Detail("Unit", FormatHelper.booleanToString(vector.getMagnitude() == 1), "Unit: A vector of length one.");
        return  details;
    }

    public static Detail[] getComplexNumberDetails(Complex64F number) {
        ComplexPolar64F polarForm = new ComplexPolar64F(number);
        Detail[] details = new Detail[2];
        details[0] = new Detail("Modulus", "|z| = " + FormatHelper.round(polarForm.getR(), 2), "Modulus: Distance from the origin to the number in the complex plane.");
        details[1] = new Detail("Argument", "θ = " + FormatHelper.round(polarForm.getTheta(), 2), "Argument: Angle from the positive real axis to the number.");
        details[2] = new Detail("Conjugate", FormatHelper.complexToString(new Complex64F(number.real, -number.imaginary)), "Conjugate: Result when reflecting a number over the real axis.");
        // include visuals!!!
        return details;
    }

    public static Detail[] getRealNumberDetails(double number) {
        Detail[] details = new Detail[4];
        details[0] = new Detail("Prime", FormatHelper.booleanToString(MatrixHelper.isPrime(number)), "Prime: Only divisible by itself and 1.");
        details[1] = new Detail("Twin prime", FormatHelper.booleanToString(MatrixHelper.isTwin(number)), "Twin Primes: Two primes which differ by 2.");
        details[2] = new Detail("Even", FormatHelper.booleanToString(number % 2 == 0), "Even: Number divisible by 2.");
        details[3] = new Detail("Odd", FormatHelper.booleanToString(number % 2 != 0), "Odd: Number not divisible by 2.");
        return details;
    }
}
