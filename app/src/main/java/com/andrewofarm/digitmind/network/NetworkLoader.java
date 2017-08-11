package com.andrewofarm.digitmind.network;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Andrew on 8/10/17.
 */

public abstract class NetworkLoader {
    public static Network loadNetwork(Context context, int resId) {
        try {
            InputStream in = context.getResources().openRawResource(resId);
            byte[] fileData = new byte[in.available()];
            //noinspection ResultOfMethodCallIgnored
            in.read(fileData);
            in.close();

            ByteBuffer buf = ByteBuffer.wrap(fileData).order(ByteOrder.LITTLE_ENDIAN);

            int numLayers = buf.getInt();
            int[] sizes = new int[numLayers + 1];
            for (int layer = 0; layer < numLayers + 1; layer++) {
                sizes[layer] = buf.getInt();
            }
            float[][][] weights = new float[numLayers][][];
            float[][][] biases = new float[numLayers][][];
            for (int layer = 0; layer < numLayers; layer++) {
                weights[layer] = new float[sizes[layer + 1]][sizes[layer]];
                for (int i = 0; i < weights[layer].length; i++) {
                    for (int j = 0; j < weights[layer][0].length; j++) {
                        weights[layer][i][j] = (float) buf.getDouble();
                    }
                }

                biases[layer] = new float[sizes[layer + 1]][1];
                for (int i = 0; i < biases[layer].length; i++) {
                    biases[layer][i][0] = (float) buf.getDouble();
                }
            }

            return new Network(weights, biases);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
