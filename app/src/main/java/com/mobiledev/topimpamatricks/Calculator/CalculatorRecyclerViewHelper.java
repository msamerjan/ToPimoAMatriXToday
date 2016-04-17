package com.mobiledev.topimpamatricks.Calculator;

import com.mobiledev.topimpamatricks.MatrixCalculation.Detail;
import com.mobiledev.topimpamatricks.MatrixCalculation.FormatHelper;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.CCommonOps;

/**
 * Created by maiaphoebedylansamerjan on 4/17/16.
 */
public class CalculatorRecyclerViewHelper {
    /**
     * Classifies inputted complex matrix as complex/real, matrix/vector/number.
     * Then calls the appropriate getDetails method
     */
    public static Detail[] getCalculations(CDenseMatrix64F matrixA, CDenseMatrix64F matrixB) {
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

}
