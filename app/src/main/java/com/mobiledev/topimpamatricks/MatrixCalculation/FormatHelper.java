package com.mobiledev.topimpamatricks.MatrixCalculation;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by maiaphoebedylansamerjan on 4/14/16.
 */
public class FormatHelper {
    /**
     * For HTML string size, not LaTeX size (unfortunately).
     */
    public static String makeLatexString(int size, String string) {
        return "<html><head>"
                + "<link rel='stylesheet' href='file:///android_asset/jqmath-0.4.3.css'>"
                + "<script src='file:///android_asset/jquery-1.4.3.min.js'></script>"
                + "<script src='file:///android_asset/jqmath-etc-0.4.3.min.js'></script>"
                + "</head><font size = " + size + "><body>"
                + "$$" + string + "$$</body></font></html>";
    }

    public static String makeLatexString(String string) { // defaults to size = ???
        return "<html><head>"
                + "<link rel='stylesheet' href='file:///android_asset/jqmath-0.4.3.css'>"
                + "<script src='file:///android_asset/jquery-1.4.3.min.js'></script>"
                + "<script src='file:///android_asset/jqmath-etc-0.4.3.min.js'></script>"
                + "</head></font><body>"
                + "$$" + string + "$$</body></html>";
    }

    public static String matricesToLatex(CDenseMatrix64F matrixA, CDenseMatrix64F matrixB) {
        return makeLatexString(6, matrixToString(matrixA) + matrixToString(matrixB));
    }

    public static String matrixToLatex(CDenseMatrix64F matrix) {
        return makeLatexString(6, matrixToString(matrix));
    }

    public static String matrixToLatex(DenseMatrix64F matrix) {
        return makeLatexString(6, matrixToString(matrix));
    }

    public static String matrixToString(CDenseMatrix64F matrix) {
        String string = "(\\table ";
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                string += complexToString(new Complex64F(matrix.getReal(r, c), matrix.getImaginary(r, c)));
                if (c < matrix.numCols - 1) {
                    string += ", ";
                }
            }
            if (r < matrix.numRows - 1) {
                string += "; ";
            }
        }
        return string + ")";
    }

    public static String matrixToString(DenseMatrix64F matrix) { // also truncate entries
        String string = "(\\table ";
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.get(r, c) == (int) matrix.get(r, c)) {
                    string += (int) matrix.get(r, c);
                } else {
                    string += round(matrix.get(r, c), 2);
                }
                if (c < matrix.numCols - 1) {
                    string += ", ";
                }
            }
            if (r < matrix.numRows - 1) {
                string += "; ";
            }
        }
        return string + ")";
    }

    public static String vectorToString(Complex64F[] vector) {
        String string = "(";
        for (int i = 0; i < vector.length; i++) {
            if (i < vector.length - 1) {
                string += complexToString(vector[i]) + ")";
            } else {
                string += complexToString(vector[i]) + ", ";
            }
        }
        return string;
    }

    public static String vectorToString(double[] vector) {
        String string = "(";
        for (int i = 0; i < vector.length; i++) {
            if (i < vector.length - 1) {
                string += round(vector[i], 2) + ")";
            } else {
                string += round(vector[i], 2) + ", ";
            }
        }
        return string;
    }

    public static String complexToString(Complex64F complex) { // cuz ejml's SUCKS
        if (complex.real == 0) {
            int imaginaryPart = (int) Math.abs(complex.imaginary);
            switch (imaginaryPart) {
                case 0:
                    return round(complex.real, 2) + "";
                case 1:
                    return complex.imaginary > 0 ? " + i" : " - i";
                default:
                    if ((int) complex.imaginary == complex.imaginary) {
                        return complex.imaginary > 0 ? " + " + (int) complex.imaginary + "i" : " - " + (int) complex.imaginary + "i";
                    }
                    return complex.imaginary > 0 ? round(complex.imaginary, 2) + "i" : round(complex.imaginary, 2) + "i";
            }
        }
        String realPart = ((int) complex.real == complex.real) ? (int) complex.real + "" : round(complex.real, 2) + "";
        if (complex.isReal()) {
            return realPart;
        }
        if (complex.imaginary == 0) {
            return round(complex.real, 2) + "";
        }
        if (Math.abs(complex.imaginary) == 1) {
            return complex.imaginary > 0 ? realPart + " + i" : realPart + " - i";
        }
        String imaginaryPart = ((int) complex.imaginary == complex.imaginary) ? (int) Math.abs(complex.imaginary) + "" : round(Math.abs(complex.imaginary), 2) + "";
        return complex.imaginary > 0 ? realPart + " + " + imaginaryPart + "i" : realPart + " - " + imaginaryPart + "i";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Complex64F roundComplex(Complex64F complex, int places) {
        return new Complex64F(round(complex.real, places), round(complex.imaginary, places));
    }

    public static String booleanToString(boolean b) {
        return b ? "yes" : "no";
    }
}
