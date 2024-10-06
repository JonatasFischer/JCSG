package com.keyboard.generator.keyboard;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.vvecmath.Transform;

public class ThumbCluster {
    private DactylParameters params;

    public ThumbCluster(DactylParameters params) {
        this.params = params;
    }

    public CSG[] create(CSG switchHole) {
        CSG[] thumbKeys = new CSG[3];
        double[] angles = {-30.0, 0.0, 30.0}; // Ângulos para as teclas do polegar

        for (int i = 0; i < angles.length; i++) {
            Transform rotation = Transform.unity().rotZ(angles[i]);
            Transform translation = Transform.unity().translate(50.0, -70.0, -20.0);

            CSG key = switchHole.transformed(rotation);
            key = key.transformed(translation);

            thumbKeys[i] = key;
        }

        return thumbKeys;
    }
}
