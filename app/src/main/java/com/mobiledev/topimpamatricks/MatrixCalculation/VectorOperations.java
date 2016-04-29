package com.mobiledev.topimpamatricks.MatrixCalculation;

/**
 * Created by maiaphoebedylansamerjan on 4/29/16.
 */
public class VectorOperations {
    /** Is orthogonal? */
    public static boolean isOrthogonal(Vector vectorA, Vector vectorB) {
        return (0 == dot(vectorA, vectorB));
    }

    /** Returns projection of Vector onto this. */
    public static Vector projection(Vector vectorA, Vector vectorB) {
        double scalar = dot(vectorA, vectorB)/Math.pow(vectorA.getMagnitude(), 2);
        return new Vector(vectorA.scale(scalar).getComponents(), "projection");
    }

    /** Adds two vectors. */
    public static Vector add(Vector vectorA, Vector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension()) throw new IllegalArgumentException("undefined") ;
        double[] components = new double[(int) vectorA.getDimension()];
        for (double component : components) { component = 0; }
        for (int i = 0; i < vectorA.getDimension(); i++) {
            components[i] += vectorA.getComponents()[i] + vectorB.getComponents()[i];
        }
        return new Vector(components, "Sum");
    }

    /** Adds n vectors. */
    public static Vector add(Vector[] vectors) {
        int dimension = (int) vectors[0].getDimension();
        double[] components = new double[dimension];
        for (Vector vector : vectors) {
            if (dimension != vector.getDimension()) throw new IllegalArgumentException("undefined") ;
        }
        for (Vector vector : vectors) {
            for (int i = 0; i < vector.getDimension(); i++) {
                components[i] += vector.getComponents()[i];
            }
        }
        return new Vector(components, "Sum");
    }

    /** Returns difference of vectorA - vectorB. */
    public static Vector subtract(Vector vectorA, Vector vectorB) {
        if (vectorA.getDimension() != vectorB.getDimension()) throw new IllegalArgumentException("undefined") ;
        double[] components = new double[(int) vectorA.getDimension()];
        for (double component : components) { component = 0; }
        for (int i = 0; i < vectorA.getDimension(); i++) {
            components[i] = vectorA.getComponents()[i] - vectorB.getComponents()[i];
        }
        return new Vector(components, "Difference");
    }

    /** Naive subtraction. */
    public static Vector subtract(Vector[] vectors) {
        for (Vector vector : vectors) {
            vector.scale(-1);
        }
        return add(vectors);
    }

    /** Returns dot product of A and B. */
    public static double dot(Vector vectorA, Vector vectorB) {
        double dotProduct = 0;
        if (vectorA.getDimension() != vectorB.getDimension()) throw new IllegalArgumentException("Dot proectdut undefined");
        for (int i = 0; i < vectorA.getDimension(); i++) {
            dotProduct += vectorA.getComponents()[i] * vectorB.getComponents()[i];
        }
        return dotProduct;
    }

    /** Runs Gram Schmidt process on 2 vectors. */
    public static Vector[] gramSchmidt(Vector vectorA, Vector vectorB) {
        return new Vector[] {vectorA.normalize(), add(vectorB, projection(vectorA, (vectorB).scale(-1)).normalize())};
    }

    /** Runs Gram Schmidt process on 3 vectors. */
    public static Vector[] gramSchmidt(Vector vectorA, Vector vectorB, Vector vectorC){
        Vector[] dopeBasis = new Vector[3];
        dopeBasis[0] = vectorA.normalize();
        dopeBasis[1] = add(vectorB, projection(vectorA, vectorB.scale(-1)).normalize());
        dopeBasis[2] = add(add(vectorC, projection(vectorA, vectorC).scale(-1)), projection(vectorB, vectorC.scale(-1)).normalize());
        return dopeBasis;
    }
}
