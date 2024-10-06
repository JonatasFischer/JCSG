package com.keyboard.generator.keyboard;

public class DactylParameters {
    // Dimensões dos switches
    public static final double KEYSWITCH_WIDTH = 14.0;
    public static final double KEYSWITCH_HEIGHT = 14.0;
    public static final double KEYSWITCH_THICKNESS = 4.0; // Espessura da placa de montagem

    // Parâmetros geométricos
    public static final double ALPHA = Math.PI / 12; // Ângulo entre as fileiras
    public static final double BETA = Math.PI / 36;  // Ângulo entre as colunas

    // Lista de colunas e linhas
    public static final int[] COLUMNS = {0, 1, 2, 3, 4, 5};
    public static final int[] ROWS = {0, 1, 2, 3, 4};

    // Raio para o posicionamento das teclas
    public static final double ROW_RADIUS = 100.0;
    public static final double COLUMN_RADIUS = 100.0;

    // Altura da placa
    public static final double PLATE_HEIGHT = 0.0;
}
