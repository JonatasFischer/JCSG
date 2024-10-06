package com.keyboard.generator.keyboard;


import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.Sphere;
import eu.mihosoft.vvecmath.Transform;

public class CaseWalls {
    private DactylParameters params;
    private PlacementFunctions placementFunctions;

    public CaseWalls(DactylParameters params, PlacementFunctions placementFunctions) {
        this.params = params;
        this.placementFunctions = placementFunctions;
    }

    public CSG createFrontWall() {
        double step = 0.2;
        CSG wall = new Cube(0, 0, 0).toCSG(); // Start with an empty model

        for (double x = 0.7; x < params.COLUMNS.length - 0.5; x += step) {
            Transform t1 = placementFunctions.keyPlace((int) x, 4);
            Transform t2 = placementFunctions.keyPlace((int) (x + step), 4);

            // Create spheres at positions
            CSG sphere1 = new Sphere(1).toCSG().transformed(t1);
            CSG sphere2 = new Sphere(1).toCSG().transformed(t2);

            // Hull between spheres
            CSG hull = sphere1.hull(sphere2);

            wall = wall.union(hull);
        }

        return wall;
    }
}
