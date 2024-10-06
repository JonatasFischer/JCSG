package com.keyboard.generator.keyboard;


import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.vvecmath.Transform;

public class KeySwitch {
    private DactylParameters params;

    public KeySwitch(DactylParameters params) {
        this.params = params;
    }

    public CSG createSwitchHole() {
        double width = params.KEYSWITCH_WIDTH + 0.5; // Adjusted for clearance
        double height = params.KEYSWITCH_HEIGHT + 0.5;

        CSG hole = new Cube(width, height, params.PLATE_THICKNESS)
                .toCSG()
                .transformed(Transform.unity().translateZ(params.PLATE_THICKNESS / 2));

        return hole;
    }
}
