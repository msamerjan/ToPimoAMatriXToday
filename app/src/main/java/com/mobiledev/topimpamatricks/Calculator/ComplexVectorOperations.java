package com.mobiledev.topimpamatricks.Calculator;

import org.ejml.data.Complex64F;

/**
 * Created by maiaphoebedylansamerjan on 4/29/16.
 */
public class ComplexVectorOperations {
    public static ComplexVector add(ComplexVector vectorA, ComplexVector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension())
            throw new IllegalArgumentException("undefined");
        Complex64F[] products = new Complex64F[2]; // vectors only have 1 component here :(
        for (int i = 0; i < vectorA.getDimension(); i++) {
            products[i] = vectorA.getComponents()[i].plus(vectorB.getComponents()[i]);
        }
        return new ComplexVector(products);
    }

    public static ComplexVector subtract(ComplexVector vectorA, ComplexVector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension())
            throw new IllegalArgumentException("undefined");
        Complex64F[] products = new Complex64F[2];
        for (int i = 0; i < vectorA.getDimension(); i++) {
            products[i] = vectorA.getComponents()[i].minus(vectorB.getComponents()[i]);
        }
        return new ComplexVector(products);
    }

    public static ComplexVector multiply(ComplexVector vectorA, ComplexVector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension())
            throw new IllegalArgumentException("undefined");
        Complex64F[] products = new Complex64F[2];
        for (int i = 0; i < vectorA.getDimension(); i++) {
            products[i] = vectorA.getComponents()[i].times(vectorB.getComponents()[i]);
        }
        return new ComplexVector(products);
    }
}
