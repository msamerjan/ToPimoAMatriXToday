package com.mobiledev.topimpamatricks.Calculator;

/**
 * Created by larspmayrand on 4/24/16.
 */

import org.ejml.data.Complex64F;

/** Model of a Complex Vector */
public class ComplexVector {

    /** Name of vector. */
    private String name;

    /** Components. */
    private Complex64F[] components;

    /** Dimension. */
    private double dimension;

    /** Theduh. */
    private double theta;

    /** Constructs vector from components and name. */
    public ComplexVector(Complex64F[] components, String name) {
        this.name = name;
        this.dimension = components.length;
        this.components = components;
    }

    /** Constructs a vector from components. */
    public ComplexVector(Complex64F[] components) {
        this.dimension = components.length;
        this.components = components;
    }

    /** Prints a vector. */
    public void print() {
        System.out.print(name + " = <");
        for (int i = 0; i < dimension; i++) {
            if (i == dimension - 1) {
                System.out.print(components[i] + ">");
            } else {
                System.out.print(components[i] + ", ");
            }
        }
        System.out.println();
    }

    /** Scale vector. */
    public ComplexVector scale(double scalar) {
        ComplexVector scaled = new ComplexVector(components);
        for (int i = 0; i < dimension; i++) {
            scaled.components[i].setReal(scaled.components[i].real * scalar);
            scaled.components[i].setImaginary(scaled.components[i].imaginary * scalar);
        }
        return scaled;
    }

    public boolean equals(ComplexVector vector) {
        return components.equals(vector.getComponents());
    }

    public String getName() {
        return name;
    }

    public double getDimension() {
        return dimension;
    }

    public double getTheta() {
        return theta;
    }

    public Complex64F[] getComponents() {
        return components;
    }

}

