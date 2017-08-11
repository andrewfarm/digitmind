package com.andrewofarm.digitmind.network;

/**
 * Created by Andrew on 8/10/17.
 */

public class Network {

    private int numLayers;
    private float[][][] weights;
    private float[][][] biases;

    public Network(float[][][] weights, float[][][] biases) {
        if (weights.length != biases.length) {
            throw new IllegalArgumentException("lengths of weight and bias arrays do not match");
        }
        numLayers = weights.length;
        this.weights = weights;
        this.biases = biases;
    }

    private static float sigmoid(float z) {
        return (float) (1.0 / (1.0 + Math.exp(-z)));
    }

    private static void sigmoid(float[][] v) {
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                v[i][j] = sigmoid(v[i][j]);
            }
        }
    }

    public float[][] feedforward(float[][] activations) {
        for (int layer = 0; layer < numLayers; layer++) {
            activations = Matrix.add(Matrix.mul(weights[layer], activations), biases[layer]);
            sigmoid(activations);
        }
        return activations;
    }

    public int recognize(float[][] digitPixels) {
        float[][] outputActivations = feedforward(digitPixels);
        float maxActivation = outputActivations[0][0];
        int maxIndex = 0;
        for (int i = 0; i < outputActivations.length; i++) {
            if (outputActivations[i][0] > maxActivation) {
                maxActivation = outputActivations[i][0];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
