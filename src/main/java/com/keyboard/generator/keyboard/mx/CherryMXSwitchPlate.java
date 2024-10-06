package com.keyboard.generator.keyboard.mx;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.Cylinder;
import eu.mihosoft.vvecmath.Transform;
import eu.mihosoft.vvecmath.Vector3d;

import java.util.Optional;

import static com.keyboard.generator.keyboard.mx.CherryMXPlateParameters.*;

public class CherryMXSwitchPlate {
    private CSG plate;

    public CherryMXSwitchPlate() {
        buildPlate();
    }

    public CSG getPlate() {
        return plate;
    }

    private void buildPlate() {
        Vector3d cherry_plate_dimensions = Vector3d.xyz(PLATE_WIDTH, PLATE_HEIGHT, PLATE_THICKNESS);

        CSG basePlate = getBasePlate(cherry_plate_dimensions);
        CSG switchCutOut = getSwitchCutout(cherry_plate_dimensions);
        CSG screwHoles = getScrewHoles(cherry_plate_dimensions);
        plate = basePlate.difference(switchCutOut).difference(screwHoles);
    }

    private static CSG getBasePlate(Vector3d cherry_plate_dimensions) {
        return new Cube(cherry_plate_dimensions.x(), cherry_plate_dimensions.y(), cherry_plate_dimensions.z())
                .toCSG()
                .transformed(Transform.unity().translate(
                        cherry_plate_dimensions.x() / 2,
                        cherry_plate_dimensions.y() / 2,
                        cherry_plate_dimensions.z() / 2
                ));
    }

    private static CSG getScrewHoles(Vector3d cherry_plate_dimensions) {
        // **Create the screw holes**
        CSG screwHoles = null;
        for (Vector3d positionStart : SCREW_HOLE_POSITIONS) {
            var positionEnd= Vector3d.xyz(positionStart.x(),positionStart.y(),cherry_plate_dimensions.z());
            CSG hole = new Cylinder(positionStart, positionEnd, SCREW_HOLE_RADIUS,NUM_SLICES).toCSG();
           screwHoles = Optional.ofNullable(screwHoles).map(e -> e.union(hole)).orElse(hole);
        }
        return screwHoles;
    }

    private static CSG getSwitchCutout(Vector3d cherry_plate_dimensions) {
        // **Create the switch cutout cube**
        Vector3d cherry_plate_switch_cutout = Vector3d.xyz(14, 14, cherry_plate_dimensions.z());
        Vector3d cherry_plate_switch_cutout_position = Vector3d.xyz(
                (cherry_plate_dimensions.x() - cherry_plate_switch_cutout.x()) / 2,
                (cherry_plate_dimensions.y() - cherry_plate_switch_cutout.y()) / 2,
                0
        );
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
        return switchCutout;
    }
}
