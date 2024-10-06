package com.keyboard.generator.keyboard.mx;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.Cylinder;
import eu.mihosoft.vvecmath.Vector3d;

import java.util.List;
import java.util.Optional;

import static com.keyboard.generator.keyboard.mx.CherryMXPlateParameters.*;

/**
 * // https://cdn.sparkfun.com/datasheets/Components/Switches/MX%20Series.pdf
 * cherry_plate_dimensions = [24, 24, 1.5];
 *
 * cherry_plate_switch_cutout = [14, 14, cherry_plate_dimensions.z];
 *
 * cherry_plate_switch_cutout_position = [
 *     (cherry_plate_dimensions.x - cherry_plate_switch_cutout.x) / 2,
 *     (cherry_plate_dimensions.y - cherry_plate_switch_cutout.y) / 2,
 *     0
 * ];
 *
 * // Screw holes for #2-56 screws
 * cherry_plate_screw_hole_diameter = 2.5;
 *
 * _cpshetc = (cherry_plate_screw_hole_diameter / 2) + 1;
 * cherry_plate_screw_locations = [
 *     [_cpshetc, _cpshetc, 0], // Bottom left
 *     [_cpshetc, cherry_plate_dimensions.y - _cpshetc, 0], // Top left
 *     [cherry_plate_dimensions.x - _cpshetc, cherry_plate_dimensions.y - _cpshetc, 0], // Top right
 *     [cherry_plate_dimensions.x - _cpshetc, _cpshetc, 0] // Bottom right
 * ];
 *
 *
 *
 * module CherryMXSwitchPlate() {
 *     difference() {
 *         cube(cherry_plate_dimensions);
 *
 *         translate(cherry_plate_switch_cutout_position) {
 *             cube(cherry_plate_switch_cutout);
 *         }
 *
 *         for(cpsh = cherry_plate_screw_locations) {
 *             translate(cpsh) {
 *                 cylinder(d = cherry_plate_screw_hole_diameter, h = cherry_plate_dimensions.z, $fn = 60);
 *             }
 *         }
 *     }
 * }
 */

public class CherryMXSwitchPlate {
    private CSG plate = buildPlate();

    public CSG getPlate() {
        return plate;
    }

    private static  CSG buildPlate() {
        Vector3d cherry_plate_dimensions = Vector3d.xyz(PLATE_WIDTH, PLATE_HEIGHT, PLATE_THICKNESS);
        Vector3d center = Vector3d.xyz(PLATE_WIDTH / 2, PLATE_HEIGHT / 2, PLATE_THICKNESS / 2);

        return getBasePlate(center, cherry_plate_dimensions)
                .difference(getSwitchCutout(center, cherry_plate_dimensions))
                .difference(getScrewHoles(cherry_plate_dimensions));
    }

    private static CSG getBasePlate(Vector3d center, Vector3d dimensions) {
        return new Cube(center, dimensions).toCSG();
    }

    private static List<CSG> getScrewHoles(Vector3d dimensions) {
        // **Create the screw holes**
        CSG screwHoles = null;
        for (Vector3d positionStart : SCREW_HOLE_POSITIONS) {
            var positionEnd = Vector3d.xyz(positionStart.x(),positionStart.y(),dimensions.z());
            CSG hole = new Cylinder(positionStart, positionEnd, SCREW_HOLE_RADIUS,NUM_SLICES).toCSG();
           screwHoles = Optional.ofNullable(screwHoles).map(e -> e.union(hole)).orElse(hole);
        }
        return List.of(screwHoles);
    }

    private static  CSG getSwitchCutout(Vector3d center, Vector3d dimensions) {
        Vector3d cut = Vector3d.xyz(SWITCH_CUTOUT_SIZE, SWITCH_CUTOUT_SIZE, dimensions.z());
        return  new Cube(center,cut).toCSG();
    }
}
