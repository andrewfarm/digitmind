package com.andrewofarm.digitmind.network;

/**
 * Created by Andrew on 8/10/17.
 */

public abstract class Matrix {

    public static float[][] transpose(float[][] m) {
        float[][] dest = new float[m[0].length][m.length];
        for (int i = 0; i < dest.length; i++) {
            for (int j = 0; j < dest[0].length; i++) {
                dest[i][j] = m[j][i];
            }
        }
        return dest;
    }

    public static float[][] mul(float[][] lhm, float[][] rhm) {
        if (lhm[0].length != rhm.length) {
            throw new IllegalArgumentException(
                    "attempt to multiply " + lhm.length + "x" + lhm[0].length + "matrix by " +
                            rhm.length + "x" + rhm[0].length + " matrix");
        }
        float[][] dest = new float[lhm.length][rhm[0].length];
        float sum;
        for (int i = 0; i < lhm.length; i++) {
            for (int j = 0; j < rhm[0].length; j++) {
                sum = 0;
                for (int n = 0; n < rhm.length; n++) {
                    sum += lhm[i][n] * rhm[n][j];
                }
                dest[i][j] = sum;
            }
        }
        return dest;
    }

    public static float[][] dot(float[][] lhv, float[][] rhv) {
        return inner(lhv, rhv);
    }

    public static float[][] inner(float[][] lhv, float[][] rhv) {
        return mul(transpose(lhv), rhv);
    }

    public static float[][] outer(float[][] lhv, float[][] rhv) {
        return mul(lhv, transpose(rhv));
    }

    public static float[][] hadamard(float[][] lhv, float[][] rhv) {
        if ((lhv.length != rhv.length) || (lhv[0].length != rhv.length)) {
            throw new IllegalArgumentException(
                    "attempt to component-wise multiply " + lhv.length + "x" + lhv[0].length +
                            "matrix by " + rhv.length + "x" + rhv[0].length + " matrix");
        }
        float[][] dest = new float[lhv.length][lhv[0].length];
        for (int i = 0; i < dest.length; i++) {
            for (int j = 0; j < dest[0].length; j++) {
                dest[i][j] = lhv[i][j] * rhv[i][j];
            }
        }
        return dest;
    }

    public static float[][] add(float[][] lhv, float[][] rhv) {
        if ((lhv.length != rhv.length) || (lhv[0].length != rhv[0].length)) {
            throw new IllegalArgumentException(
                    "attempt to component-wise add " + lhv.length + "x" + lhv[0].length +
                            " matrix by " + rhv.length + "x" + rhv[0].length + " matrix");
        }
        float[][] dest = new float[lhv.length][lhv[0].length];
        for (int i = 0; i < dest.length; i++) {
            for (int j = 0; j < dest[0].length; j++) {
                dest[i][j] = lhv[i][j] + rhv[i][j];
            }
        }
        return dest;
    }
}
