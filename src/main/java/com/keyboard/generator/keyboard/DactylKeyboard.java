package com.keyboard.generator.keyboard;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.FileUtil;
import eu.mihosoft.vvecmath.Transform;

public class DactylKeyboard {
    private DactylParameters params;
    private SwitchHole switchHole;
    private KeyPlacement keyPlacement;
    private ThumbCluster thumbCluster;
    private Case keyboardCase;

    public DactylKeyboard() {
        this.params = new DactylParameters();
        this.switchHole = new SwitchHole(params);
        this.keyPlacement = new KeyPlacement(params);
        this.thumbCluster = new ThumbCluster(params);
        this.keyboardCase = new Case(params);
    }

    public CSG build() {
        CSG switchHoleModel = switchHole.create();
        CSG keyPlate = new Cube(0, 0, 0).toCSG(); // Inicia com um modelo vazio

        // Adiciona os buracos dos switches
        for (int column : params.COLUMNS) {
            for (int row : params.ROWS) {
                if (!(column == 0 && row == 4)) {
                    CSG key = keyPlacement.placeKey(column, row, switchHoleModel);
                    keyPlate = keyPlate.union(key);
                }
            }
        }

        // Adiciona o cluster do polegar
        CSG[] thumbKeys = thumbCluster.create(switchHoleModel);
        for (CSG key : thumbKeys) {
            keyPlate = keyPlate.union(key);
        }

        // Cria a placa base
        double plateThickness = params.KEYSWITCH_THICKNESS;
        double plateWidth = 200.0;
        double plateDepth = 200.0;

        CSG plate = new Cube(plateWidth, plateDepth, plateThickness)
                .toCSG()
                .transformed(Transform.unity()
                        .translate(-plateWidth / 2, -plateDepth / 2, plateThickness / 2 + params.PLATE_HEIGHT));

        // Subtrai os buracos dos switches da placa
        plate = plate.difference(keyPlate);

        // Cria a carcaça
        CSG keyboardCaseModel = keyboardCase.create(plate);

        return keyboardCaseModel;
    }

    public void saveModel(String filename) {
        CSG model = build();
        try {
            FileUtil.write(new java.io.File(filename).toPath(), model.toStlString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

