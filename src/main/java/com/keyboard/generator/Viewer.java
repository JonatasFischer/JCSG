package com.keyboard.generator;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

// Import the STL importer
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;

import java.io.File;

public class Viewer extends Application {
    private static final String STL_FILE = "dactyl_keyboard.stl";

    @Override
    public void start(Stage primaryStage) {
        // Load the STL file
        MeshView meshView = loadStlModel(STL_FILE);

        if (meshView == null) {
            System.err.println("Failed to load the STL file.");
            System.exit(1);
        }

        // Create a Group and add the MeshView
        Group root = new Group(meshView);

        // Apply material to the mesh
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);

        meshView.setMaterial(material);
        meshView.getTransforms().addAll(
                new Rotate(-90, Rotate.X_AXIS)
        );

        // Set up the scene
        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.color(0.1, 0.1, 0.1));

        // Add a camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-500);
        scene.setCamera(camera);

        // Add mouse control (rotation and zoom)
        addMouseControl(root, scene, primaryStage, camera);

        // Set up the stage
        primaryStage.setTitle("Dactyl Keyboard Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MeshView loadStlModel(String filename) {
        try {
            StlMeshImporter importer = new StlMeshImporter();
            importer.read(new File(filename));
            TriangleMesh mesh = importer.getImport();
            importer.close();

            MeshView meshView = new MeshView(mesh);
            return meshView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addMouseControl(Group group, Scene scene, Stage stage, Camera camera) {
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        group.getTransforms().addAll(rotateX, rotateY);

        scene.setOnMousePressed(event -> {
            scene.setUserData(new double[]{event.getSceneX(), event.getSceneY(), rotateX.getAngle(), rotateY.getAngle()});
        });

        scene.setOnMouseDragged(event -> {
            double[] userData = (double[]) scene.getUserData();
            double deltaX = event.getSceneX() - userData[0];
            double deltaY = event.getSceneY() - userData[1];
            rotateX.setAngle(userData[2] - deltaY * 0.2);
            rotateY.setAngle(userData[3] + deltaX * 0.2);
        });

        scene.setOnScroll(event -> {
            double delta = event.getDeltaY();
            camera.setTranslateZ(camera.getTranslateZ() + delta);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
