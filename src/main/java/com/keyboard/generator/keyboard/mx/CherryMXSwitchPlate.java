package com.keyboard.generator.keyboard.mx;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.Cylinder;
import eu.mihosoft.vvecmath.Transform;
import eu.mihosoft.vvecmath.Vector3d;

public class CherryMXSwitchPlate {
    private CSG plate;

    public CherryMXSwitchPlate() {
        buildPlate();
    }

    public CSG getPlate() {
        return plate;
    }

    private void buildPlate() {
        // // https://cdn.sparkfun.com/datasheets/Components/Switches/MX%20Series.pdf
        // cherry_plate_dimensions = [24, 24, 1.5];
        Vector3d cherry_plate_dimensions = Vector3d.xyz(24, 24, 1.5);

        // cherry_plate_switch_cutout = [14, 14, cherry_plate_dimensions.z];
        Vector3d cherry_plate_switch_cutout = Vector3d.xyz(14, 14, cherry_plate_dimensions.z());

        // cherry_plate_switch_cutout_position = [
        //     (cherry_plate_dimensions.x - cherry_plate_switch_cutout.x) / 2,
        //     (cherry_plate_dimensions.y - cherry_plate_switch_cutout.y) / 2,
        //     0
        // ];
        Vector3d cherry_plate_switch_cutout_position = Vector3d.xyz(
                (cherry_plate_dimensions.x() - cherry_plate_switch_cutout.x()) / 2,
                (cherry_plate_dimensions.y() - cherry_plate_switch_cutout.y()) / 2,
                0
        );

        // // Screw holes for #2-56 screws
        // cherry_plate_screw_hole_diameter = 2.5;
        double cherry_plate_screw_hole_diameter = 2.5;

        // _cpshetc = (cherry_plate_screw_hole_diameter / 2) + 1;
        double _cpshetc = (cherry_plate_screw_hole_diameter / 2) + 1;

        // cherry_plate_screw_locations = [
        //     [_cpshetc, _cpshetc, 0], // Bottom left
        //     [_cpshetc, cherry_plate_dimensions.y - _cpshetc, 0], // Top left
        //     [cherry_plate_dimensions.x - _cpshetc, cherry_plate_dimensions.y - _cpshetc, 0], // Top right
        //     [cherry_plate_dimensions.x - _cpshetc, _cpshetc, 0] // Bottom right
        // ];
        Vector3d[] cherry_plate_screw_locations = new Vector3d[] {
                Vector3d.xyz(_cpshetc, _cpshetc, 0), // Bottom left
                Vector3d.xyz(_cpshetc, cherry_plate_dimensions.y() - _cpshetc, 0), // Top left
                Vector3d.xyz(cherry_plate_dimensions.x() - _cpshetc, cherry_plate_dimensions.y() - _cpshetc, 0), // Top right
                Vector3d.xyz(cherry_plate_dimensions.x() - _cpshetc, _cpshetc, 0) // Bottom right
        };

        // module CherryMXSwitchPlate() {
        //     difference() {
        //         cube(cherry_plate_dimensions);

        //         translate(cherry_plate_switch_cutout_position) {
        //             cube(cherry_plate_switch_cutout);
        //         }

        //         for(cpsh = cherry_plate_screw_locations) {
        //             translate(cpsh) {
        //                 cylinder(d = cherry_plate_screw_hole_diameter, h = cherry_plate_dimensions.z, $fn = 60);
        //             }
        //         }
        //     }
        // }

        // **Create the base plate cube**
        // Note: In OpenSCAD, cube() creates a cube from (0,0,0) to (x,y,z) unless center=true.
        // In JCSG, Cube(x, y, z) creates a cube centered at the origin.
        // So we need to adjust for this difference by translating the cube.

        // Create base plate cube (from (0,0,0) to (24,24,1.5))
        CSG basePlate = new Cube(cherry_plate_dimensions.x(), cherry_plate_dimensions.y(), cherry_plate_dimensions.z())
                .toCSG()
                .transformed(Transform.unity().translate(
                        cherry_plate_dimensions.x() / 2,
                        cherry_plate_dimensions.y() / 2,
                        cherry_plate_dimensions.z() / 2
                ));

        // **Create the switch cutout cube**
        CSG switchCutout = new Cube(
                cherry_plate_switch_cutout.x(),
                cherry_plate_switch_cutout.y(),
                cherry_plate_switch_cutout.z()
        ).toCSG()
                .transformed(Transform.unity().translate(
                        cherry_plate_switch_cutout_position.x() + cherry_plate_switch_cutout.x() / 2,
                        cherry_plate_switch_cutout_position.y() + cherry_plate_switch_cutout.y() / 2,
                        cherry_plate_switch_cutout.z() / 2
                ));

        // **Create the screw holes**
        CSG screwHoles = null;
        for (Vector3d cpsh : cherry_plate_screw_locations) {
            CSG hole = new Cylinder(
                    cherry_plate_screw_hole_diameter / 2,
                    cherry_plate_dimensions.z()+1,
                    60
            ).toCSG()
                    .transformed(Transform.unity().translate(
                            cpsh.x(),
                            cpsh.y(),
                            cherry_plate_dimensions.z() / 2
                    ));
            if (screwHoles == null) {
                screwHoles = hole;
            } else {
                screwHoles = screwHoles.union(hole);
            }
        }

        // **Combine the cutouts**
        CSG cutouts = switchCutout.union(screwHoles);

        // **Subtract the cutouts from the base plate**
        plate = basePlate.difference(cutouts);
    }
}
