package com.keyboard.generator.keyboard;

public class DactylParameters {
    // Key switch dimensions
    public static final double KEYSWITCH_HEIGHT = 14.4;
    public static final double KEYSWITCH_WIDTH = 14.4;

    // SA keycap dimensions
    public static final double SA_PROFILE_KEY_HEIGHT = 12.7;
    public static final double SA_LENGTH = 18.25;
    public static final double SA_DOUBLE_LENGTH = 37.5;

    // Plate dimensions
    public static final double PLATE_THICKNESS = 4.0;
    public static final double MOUNT_WIDTH = KEYSWITCH_WIDTH + 3.0;
    public static final double MOUNT_HEIGHT = KEYSWITCH_HEIGHT + 3.0;

    // Angles
    public static final double ALPHA = Math.PI / 12; // Approximately 15 degrees
    public static final double BETA = Math.PI / 36;  // Approximately 5 degrees

    // Key positions
    public static final int[] COLUMNS = {0, 1, 2, 3, 4, 5};
    public static final int[] ROWS = {0, 1, 2, 3, 4};

    // Radii for positioning
    public static final double CAP_TOP_HEIGHT = PLATE_THICKNESS + SA_PROFILE_KEY_HEIGHT;
    public static final double ROW_RADIUS = ((MOUNT_HEIGHT / 2) / Math.sin(ALPHA / 2)) + CAP_TOP_HEIGHT;
    public static final double COLUMN_RADIUS = ((MOUNT_WIDTH / 2) / Math.sin(BETA / 2)) + CAP_TOP_HEIGHT;

    // Other parameters
    public static final double WEB_THICKNESS = 3.5;
    public static final int WALL_SPHERE_N = 20; // Sphere resolution
}
