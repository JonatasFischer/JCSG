package com.keyboard.generator.keyboard;


import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.vvecmath.Transform;

public class KeyPlacement {
    private DactylParameters params;

    public KeyPlacement(DactylParameters params) {
        this.params = params;
    }

    public CSG placeKey(int column, int row, CSG switchHole) {
        double rowAngle = params.ALPHA * (row - 2);
        double colAngle = params.BETA * (column - 2.5);

        // Rotaciona em torno do eixo X (fileira)
        Transform rowRotation = Transform.unity().rotX(Math.toDegrees(rowAngle));

        // Rotaciona em torno do eixo Y (coluna)
        Transform colRotation = Transform.unity().rotY(Math.toDegrees(colAngle));

        // Aplica as rotações
        CSG key = switchHole.transformed(rowRotation);
        key = key.transformed(colRotation);

        // Translada para a altura da placa
        key = key.transformed(Transform.unity().translateZ(params.PLATE_HEIGHT));

        return key;
    }
}
