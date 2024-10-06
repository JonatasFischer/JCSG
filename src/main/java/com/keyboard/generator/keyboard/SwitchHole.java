package com.keyboard.generator.keyboard;


import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.vvecmath.Transform;

public class SwitchHole {
    private DactylParameters params;

    public SwitchHole(DactylParameters params) {
        this.params = params;
    }

    public CSG create() {
        double switchSize = params.KEYSWITCH_WIDTH;
        double thickness = params.KEYSWITCH_THICKNESS + 10.0; // Profundidade extra para corte

        // Cria um cubo representando o buraco do switch
        CSG switchHole = new Cube(switchSize, switchSize, thickness)
                .toCSG()
                .transformed(Transform.unity().translateZ(-thickness / 2));

        return switchHole;
    }
}
