package com.keyboard.generator.keyboard;


import eu.mihosoft.vvecmath.Transform;

public class PlacementFunctions {
    private DactylParameters params;

    public PlacementFunctions(DactylParameters params) {
        this.params = params;
    }

    public Transform keyPlace(int column, int row) {
        double rowAngle = params.ALPHA * (row - 2);
        double colAngle = params.BETA * (column - 2);

        double rowRadius = params.ROW_RADIUS;
        double colRadius = params.COLUMN_RADIUS;

        // Start with the key at the origin
        Transform transform = Transform.unity();

        // Apply row rotation and translation
        transform = transform.translateZ(-rowRadius)
                .rotX(Math.toDegrees(rowAngle))
                .translateZ(rowRadius);

        // Apply column rotation and translation
        transform = transform.translateZ(-colRadius)
                .rotY(Math.toDegrees(colAngle))
                .translateZ(colRadius);

        // Adjust for any offsets if necessary
        transform = transform.rotY(Math.toDegrees(params.ALPHA / 12))
                .translateZ(13); // Final adjustment as per original code

        return transform;
    }
}
