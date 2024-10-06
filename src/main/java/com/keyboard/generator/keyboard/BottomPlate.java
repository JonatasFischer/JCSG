package com.keyboard.generator.keyboard;


import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.vvecmath.Transform;

public class BottomPlate {
    private DactylParameters params;
    private PlacementFunctions placementFunctions;

    public BottomPlate(DactylParameters params, PlacementFunctions placementFunctions) {
        this.params = params;
        this.placementFunctions = placementFunctions;
    }

    public CSG createBottomPlate() {
        CSG plate = new Cube(0, 0, 0).toCSG(); // Start with an empty model

        // Add key guards
        for (int column : params.COLUMNS) {
            for (int row : params.ROWS) {
                // Exclude specific key positions as in original code
                if (!(column == 0 && row == 4)) {
                    CSG guard = createKeyGuard();
                    Transform transform = placementFunctions.keyPlace(column, row);
                    CSG placedGuard = guard.transformed(transform);
                    plate = plate.union(placedGuard);
                }
            }
        }

        return plate;
    }

    private CSG createKeyGuard() {
        double width = params.MOUNT_WIDTH;
        double height = params.MOUNT_HEIGHT;
        double thickness = params.WEB_THICKNESS;

        return new Cube(width, height, thickness)
                .toCSG()
                .transformed(Transform.unity().translateZ(-thickness / 2 - 4.5));
    }
}
