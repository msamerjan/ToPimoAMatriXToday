package com.mobiledev.topimpamatricks;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by maiaphoebedylansamerjan on 4/14/16.
 */
public class FormatHelper {
    /** For HTML string size, not LaTeX size (unfortunately). */
    public static String makeLatexString(int size, String string) {
        return  "<html><head>"
                + "<link rel='stylesheet' href='file:///android_asset/jqmath-0.4.3.css'>"
                + "<script src='file:///android_asset/jquery-1.4.3.min.js'></script>"
                + "<script src='file:///android_asset/jqmath-etc-0.4.3.min.js'></script>"
                + "</head><font size = " + size + "><body>"
                + "$$" + string + "$$</body></font></html>";
    }

    public static String makeLatexString(String string) { // defaults to size = ???
        return  "<html><head>"
                + "<link rel='stylesheet' href='file:///android_asset/jqmath-0.4.3.css'>"
                + "<script src='file:///android_asset/jquery-1.4.3.min.js'></script>"
                + "<script src='file:///android_asset/jqmath-etc-0.4.3.min.js'></script>"
                + "</head></font><body>"
                + "$$" + string + "$$</body></html>";
    }

    public static String matrixToString(CDenseMatrix64F matrix) {
        String string = "(\\table ";
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.getImaginary(r, c) == 0) {
                    if (matrix.getReal(r, c) == (int) matrix.getReal(r, c)) {
                        string += (int) matrix.getReal(r, c);
                    } else {
                        string += FormatHelper.round(matrix.getReal(r, c), 4);
                    }
                } else {
                    string += FormatHelper.round(matrix.getReal(r, c), 4) + " " + FormatHelper.round(matrix.getImaginary(r, c), 4) + "i";
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

    public static String matrixToLatex(CDenseMatrix64F matrix) {
        String string = "(\\table ";
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
//                if (matrix.getImaginary(r, c) == 0) {
//                    if (matrix.getReal(r, c) == (int) matrix.getReal(r, c)) {
//                        string += (int) matrix.getReal(r, c);
//                    } else {
//                        string += FormatHelper.round(matrix.getReal(r, c), 4);
//                    }
//                } else {
//                    string += FormatHelper.round(matrix.getReal(r, c), 4) + " " + FormatHelper.round(matrix.getImaginary(r, c), 4) + "i";
//                }
                string += FormatHelper.complexToString(new Complex64F(matrix.getReal(r, c), matrix.getImaginary(r, c)));
                if (c < matrix.numCols - 1) {
                    string += ", ";
                }
            }
            if (r < matrix.numRows - 1) {
                string += "; ";
            }
        }
        return makeLatexString(6, string + ")");
    }

    public static String matrixToString(DenseMatrix64F matrix) { // also truncate entries
        String string = "(\\table ";
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.get(r, c) == (int) matrix.get(r, c)) {
                    string += (int) matrix.get(r, c);
                } else {
                    string += FormatHelper.round(matrix.get(r, c), 4);
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

    public static String matrixToLatex(DenseMatrix64F matrix) {
        String string = "(\\table ";
        for (int r = 0; r < matrix.numRows; r++) {
            for (int c = 0; c < matrix.numCols; c++) {
                if (matrix.get(r, c) == (int) matrix.get(r, c)) {
                    string += (int) matrix.get(r, c);
                } else {
                    string += FormatHelper.round(matrix.get(r, c), 4);
                }
                if (c < matrix.numCols - 1) {
                    string += ", ";
                }
            }
            if (r < matrix.numRows - 1) {
                string += "; ";
            }
        }
        return makeLatexString(6, string + ")");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String complexToString(Complex64F complex) { // cuz ejml's SUCKS
        String realPart = ((int) complex.real == complex.real) ? (int) complex.real + "" : complex.real + "";
        if (complex.isReal()) {
            return realPart;
        } else {
            if (Math.abs(complex.imaginary) == 1) {
                return complex.imaginary > 0 ? realPart + " + i" : realPart + " - i";
            }
            String imaginaryPart = ((int) complex.imaginary == complex.imaginary) ? (int) Math.abs(complex.imaginary) + "" : Math.abs(complex.imaginary) + "";
            return complex.imaginary > 0 ? realPart + " + i" + imaginaryPart : realPart + " - i" + imaginaryPart;
        }
    }

    public static String booleanToString(boolean b) {
        return b ? "yes" : "no";
    }

}
