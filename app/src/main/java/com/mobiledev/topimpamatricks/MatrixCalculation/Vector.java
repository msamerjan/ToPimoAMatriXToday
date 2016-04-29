package com.mobiledev.topimpamatricks.MatrixCalculation;

/**
 * Created by maiaphoebedylansamerjan on 4/29/16.
 */
public class Vector {
    /**
     * Name of vector.
     */
    private String name;

    /**
     * Components.
     */
    private double[] components;

    /**
     * Dimension.
     */
    private double dimension;

    /**
     * Magnitude.
     */
    private double magnitude;

    /**
     * Theduh.
     */
    private double theta;

    /**
     * Constructs vector from components and name.
     */
    public Vector(double[] components, String name) {
        this.name = name;
        this.dimension = components.length;
        this.components = components;
        this.magnitude = magnitude();
        this.theta = angle();
    }

    /**
     * Constructs a vector from components.
     */
    public Vector(double[] components) {
        this.dimension = components.length;
        this.components = components;
        this.magnitude = magnitude();
        this.theta = angle();
    }

    /**
     * Construct from polar form.
     */
    public Vector(double magnitude, double θ, String name) {
        this.name = name;
        this.dimension = 2;
        this.magnitude = magnitude;
        this.theta = θ;
        this.components = new double[]{magnitude * Math.cos(θ), magnitude * Math.sin(θ)};
    }

    /**
     * Magnitude.
     */
    public double magnitude() {
        double magnitude = 0;
        for (double component : components) {
            magnitude += component * component;
        }
        return (Math.sqrt(magnitude));
    }

    /**
     * Prints a vector.
     */
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

    /**
     * Normalizes vector.
     */
    public Vector normalize() {
        Vector normalized = new Vector(components, "normalized");
        double magnitude = normalized.magnitude();
        for (int i = 0; i < normalized.dimension; i++) {
            normalized.components[i] /= magnitude;
        }
        return normalized;
    }

    /**
     * Angle in Polur chords.
     */
    public double angle() {
        if (components[0] == 0) return Math.PI / 2;
        else if (components[1] == 0) return 0;
        return Math.atan(components[0] / components[1]);
    }

    /**
     * Scale vector.
     */
    public Vector scale(double scalar) {
        Vector scaled = new Vector(components, "scaled");
        for (int i = 0; i < scaled.dimension; i++) {
            scaled.components[i] *= scalar;
        }
        return scaled;
    }

    public Vector polarForm() {
        return new Vector(magnitude, theta, "Polar");
    }

    public double slope() {
        if (components[1] == 0) return 0;
        return components[0] / components[1];
    }

    public boolean equals(Vector vector) {
        if (vector.dimension != dimension) return false;
        for (int i = 0; i < dimension; i++) {
            if (components[i] != vector.getComponents()[i]) return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public double getDimension() {
        return dimension;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getTheta() {
        return theta;
    }

    public double[] getComponents() {
        return components;
    }
}
