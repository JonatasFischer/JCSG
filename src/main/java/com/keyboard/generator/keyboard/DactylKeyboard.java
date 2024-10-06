package com.keyboard.generator.keyboard;

import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.jcsg.FileUtil;
import eu.mihosoft.vvecmath.Transform;

public class DactylKeyboard {
    private DactylParameters params;
    private KeySwitch keySwitch;
    private KeyCap keyCap;
    private PlacementFunctions placementFunctions;
    private WebConnectors webConnectors;
    private ThumbCluster thumbCluster;
    private CaseWalls caseWalls;
    private BottomPlate bottomPlate;

    public DactylKeyboard() {
        this.params = new DactylParameters();
        this.keySwitch = new KeySwitch(params);
        this.keyCap = new KeyCap(params);
        this.placementFunctions = new PlacementFunctions(params);
        this.webConnectors = new WebConnectors(params);
        this.thumbCluster = new ThumbCluster(params, placementFunctions);
        this.caseWalls = new CaseWalls(params, placementFunctions);
        this.bottomPlate = new BottomPlate(params, placementFunctions);
    }

    public CSG buildKeyboard() {
        CSG keyboard = new Cube(0, 0, 0).toCSG();

        // Build key switches and caps
        for (int column : params.COLUMNS) {
            for (int row : params.ROWS) {
                if (!(column == 0 && row == 4)) {
                    CSG switchHole = keySwitch.createSwitchHole();
                    CSG cap = keyCap.createKeyCap(1.0);
                    Transform transform = placementFunctions.keyPlace(column, row);

                    CSG placedSwitch = switchHole.transformed(transform);
                    CSG placedCap = cap.transformed(transform);

                    keyboard = keyboard.union(placedSwitch).union(placedCap);
                }
            }
        }

        // Add thumb cluster
        CSG thumb = thumbCluster.createThumbCluster(keySwitch.createSwitchHole(), keyCap.createKeyCap(1.0));
        keyboard = keyboard.union(thumb);

        // Add web connectors (simplified)
        // You can expand this to match the original code's complexity

        // Add case walls
        CSG frontWall = caseWalls.createFrontWall();
        keyboard = keyboard.union(frontWall);

        // Add bottom plate
        CSG bottom = bottomPlate.createBottomPlate();
        keyboard = keyboard.union(bottom);

        return keyboard;
    }

    public void saveModel(String filename) {
        CSG model = buildKeyboard();
        try {
            FileUtil.write(new java.io.File(filename).toPath(), model.toStlString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
