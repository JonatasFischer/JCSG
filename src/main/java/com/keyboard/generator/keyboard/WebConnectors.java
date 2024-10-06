package com.keyboard.generator.keyboard;


import eu.mihosoft.jcsg.CSG;

public class WebConnectors {
    private DactylParameters params;

    public WebConnectors(DactylParameters params) {
        this.params = params;
    }

    public CSG createWebConnector(CSG key1, CSG key2) {
        // Use hull to create a connector between two keys
        return key1.hull(key2);
    }
}