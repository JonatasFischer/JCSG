package com.keyboard.generator.keyboard;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.vvecmath.Transform;

public class ThumbCluster {
    private DactylParameters params;
    private PlacementFunctions placementFunctions;

    public ThumbCluster(DactylParameters params, PlacementFunctions placementFunctions) {
        this.params = params;
        this.placementFunctions = placementFunctions;
    }

    public CSG createThumbCluster(CSG keySwitch, CSG keyCap) {
        CSG cluster = new Cube(0, 0, 0).toCSG(); // Start with an empty model

        // Define thumb positions
        int[][] positions = {
                {0, -1}, {1, -1}, {1, 1}, {2, -1}, {2, 0}, {2, 1}
        };

        for (int[] pos : positions) {
            int col = pos[0];
            int row = pos[1];

            Transform transform = thumbPlace(col, row);

            // Place keyswitch
            CSG placedSwitch = keySwitch.transformed(transform);
            cluster = cluster.union(placedSwitch);

            // Place keycap
            CSG placedCap = keyCap.transformed(transform);
            cluster = cluster.union(placedCap);
        }

        return cluster;
    }

    private Transform thumbPlace(int column, int row) {
        double alpha = params.ALPHA;
        double beta = params.BETA;

        double rowRadius = params.ROW_RADIUS;
        double colRadius = params.COLUMN_RADIUS;

        double rowAngle = alpha * row;
        double colAngle = beta * column;

        Transform transform = Transform.unity();

        transform = transform.translateZ(-rowRadius)
                .rotX(Math.toDegrees(rowAngle))
                .translateZ(rowRadius);

        transform = transform.translateZ(-colRadius)
                .rotY(Math.toDegrees(colAngle))
                .translateZ(colRadius);

        // Additional adjustments specific to thumb cluster
        transform = transform.translateX(params.MOUNT_WIDTH)
                .rotZ(Math.toDegrees(-Math.PI / 4 - Math.PI / 16))
                .rotY(Math.toDegrees(params.ALPHA / 12))
                .translate(-52, -45, 40);

        return transform;
    }
}
