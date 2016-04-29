package com.mobiledev.topimpamatricks.Calculator;

import com.mobiledev.topimpamatricks.MatrixCalculation.Detail;
import com.mobiledev.topimpamatricks.MatrixCalculation.FormatHelper;
import com.mobiledev.topimpamatricks.MatrixCalculation.MatrixHelper;
import com.mobiledev.topimpamatricks.MatrixCalculation.VectorOperations;
import com.mobiledev.topimpamatricks.MatrixCalculation.Vector;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.CCommonOps;


/**
 * Created by maiaphoebedylansamerjan on 4/17/16.
 */
public class CalculatorRecyclerViewHelper {

    public static Detail[] getCalculations(CDenseMatrix64F matrixA, CDenseMatrix64F matrixB) {

        // checks if matrices are actual real or complex vectors and redirects accordingly
        if (MatrixHelper.classify(matrixA).equals("real vector") && MatrixHelper.classify(matrixB).equals("real vector")) {
            return getRealVectorCalculations(MatrixHelper.makeVector(MatrixHelper.makeReal(matrixA)), MatrixHelper.makeVector(MatrixHelper.makeReal(matrixB)));
        } else if (MatrixHelper.classify(matrixA).equals("complex vector") && MatrixHelper.classify(matrixB).equals("complex vector")) {
            return getComplexVectorCalculations(MatrixHelper.makeVector(matrixA), MatrixHelper.makeVector(matrixB));
        }

        Detail[] details = new Detail[4];

        CDenseMatrix64F sum = matrixA.copy();
        CCommonOps.add(matrixA, matrixB, sum);
        details[0] = new Detail("Sum", FormatHelper.matrixToString(sum));

        CDenseMatrix64F difference = matrixA.copy();
        CCommonOps.subtract(matrixA, matrixB, difference);
        details[1] = new Detail("Difference", FormatHelper.matrixToString(difference));

        CDenseMatrix64F product = matrixA.copy();
        CCommonOps.mult(matrixA, matrixB, product);
        details[2] = new Detail("Product", FormatHelper.matrixToString(product));

        CDenseMatrix64F solveProduct = matrixA.copy();
        CCommonOps.solve(matrixA, matrixB, solveProduct);
        details[3] = new Detail("Solution to Ax = B", FormatHelper.matrixToString(solveProduct));

        return details;
    }

    public static Detail[] getRealVectorCalculations(Vector vectorA, Vector vectorB) {
        // gram schmidt formatting
        Vector[] orthonormalized = VectorOperations.gramSchmidt(vectorA, vectorB);
        String gramSchmidt = "v_1 = " + FormatHelper.vectorToString(orthonormalized[0]) + ", v_2 = " + FormatHelper.vectorToString(orthonormalized[1]);

        Detail[] details = new Detail[5];
        details[0] = new Detail("Sum", FormatHelper.vectorToString(VectorOperations.add(vectorA, vectorB)));
        details[1] = new Detail("Difference", FormatHelper.vectorToString(VectorOperations.subtract(vectorA, vectorB)));
        details[3] = new Detail("Dot product", FormatHelper.doubleToString(VectorOperations.dot(vectorA, vectorB), 2));
        details[2] = new Detail("Projection of A onto B", FormatHelper.vectorToString(VectorOperations.projection(vectorA, vectorB)));
        details[5] = new Detail("Gram Schmidt Process", gramSchmidt);
        details[4] = new Detail("Are vectors orthogonal?", FormatHelper.booleanToString(VectorOperations.isOrthogonal(vectorA, vectorB)));
        return details;
    }

    public static Detail[] getComplexVectorCalculations(ComplexVector vectorA, ComplexVector vectorB) {
        Detail[] details = new Detail[3];
        details[0] = new Detail("Sum", FormatHelper.vectorToString((ComplexVectorOperations.add(vectorA, vectorB)).getComponents()));
        details[1] = new Detail("Difference", FormatHelper.vectorToString(ComplexVectorOperations.subtract(vectorA, vectorB).getComponents()));
        details[2] = new Detail("Product", FormatHelper.vectorToString(ComplexVectorOperations.multiply(vectorA, vectorB).getComponents()));
        return details;
    }

}
