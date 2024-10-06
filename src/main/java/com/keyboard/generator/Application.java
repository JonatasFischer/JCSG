package com.keyboard.generator;

import com.keyboard.generator.keyboard.DactylKeyboard;

public class Application {
    public static void main(String[] args) {
        DactylKeyboard keyboard = new DactylKeyboard();
        keyboard.saveModel("dactyl_keyboard.stl");
        System.out.println("Modelo salvo como 'dactyl_keyboard.stl'");

        // Launch the viewer
        Viewer.main(args);
    }
}
