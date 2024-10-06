package com.keyboard.generator.keyboard.keys;


import com.keyboard.generator.keyboard.DactylParameters;
import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.Sphere;
import eu.mihosoft.vvecmath.Transform;


public class KeyCap {
    private DactylParameters params;

    public KeyCap(DactylParameters params) {
        this.params = params;
    }

    public CSG createKeyCap(double size) {
        double length = params.SA_LENGTH * size;
        double width = params.SA_LENGTH;
        double height = params.SA_PROFILE_KEY_HEIGHT;

        // Create the base of the keycap
        CSG base = new Cube(width, length, height / 2)
                .toCSG()
                .transformed(Transform.unity().translateZ(height / 4));

        // Create the top part with a curved surface
        CSG top = new Sphere(width / 2)
                .toCSG()
                .transformed(Transform.unity()
                        .scaleX(1)
                        .scaleY(length / width)
                        .scaleZ(0.5)
                        .translate(0, 0, height * 0.75));

        // Combine base and top
        CSG keycap = base.difference(top);

        // Adjust the position relative to the plate
        keycap = keycap.transformed(Transform.unity().translateZ(params.PLATE_THICKNESS));

        return keycap;
    }
}

