package com.keyboard.generator.keyboard.mx;

import eu.mihosoft.vvecmath.Vector3d;

public class CherryMXPlateParameters {
    public static final double PLATE_WIDTH = 24.0;
    public static final double PLATE_HEIGHT = 24.0;
    public static final double PLATE_THICKNESS = 1.5;

    public static final double SWITCH_CUTOUT_SIZE = 14.0;

    // Screw holes for #2-56 screws
    public static final double SCREW_HOLE_DIAMETER = 2.5;
    public static final double SCREW_HOLE_RADIUS = SCREW_HOLE_DIAMETER / 2;

    // Calculate screw hole offset
    public static final double SCREW_HOLE_OFFSET = SCREW_HOLE_RADIUS + 1.0;

    // Screw hole positions (Bottom left, Top left, Top right, Bottom right)
    public static final Vector3d[] SCREW_HOLE_POSITIONS = {
            Vector3d.xyz(SCREW_HOLE_OFFSET, SCREW_HOLE_OFFSET, 0), // Bottom left
            Vector3d.xyz(SCREW_HOLE_OFFSET, PLATE_HEIGHT - SCREW_HOLE_OFFSET, 0), // Top left
            Vector3d.xyz(PLATE_WIDTH - SCREW_HOLE_OFFSET, PLATE_HEIGHT - SCREW_HOLE_OFFSET, 0), // Top right
            Vector3d.xyz(PLATE_WIDTH - SCREW_HOLE_OFFSET, SCREW_HOLE_OFFSET, 0) // Bottom right
    };
}
