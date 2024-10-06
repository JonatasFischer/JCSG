package com.keyboard.generator.keyboard;

import eu.mihosoft.jcsg.Bounds;
import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Cube;
import eu.mihosoft.vvecmath.Transform;


public class Case {
    private DactylParameters params;

    public Case(DactylParameters params) {
        this.params = params;
    }

    public CSG create(CSG keyPlate) {
        // Cria uma casca ao redor da placa de teclas
        CSG caseShell = keyPlate.clone();

        // Realiza uma operação de shell (expandir a superfície)
        // Nota: O JCSG não possui uma função direta para shell, precisamos criar manualmente

        // Aqui, vamos criar uma caixa maior e subtrair a keyPlate para formar o case
        double padding = 5.0;
        Bounds bounds = keyPlate.getBounds();
        double width = bounds.getMax().x() - bounds.getMin().x() + 2 * padding;
        double depth = bounds.getMax().y() - bounds.getMin().y() + 2 * padding;
        double height = bounds.getMax().z() - bounds.getMin().z() + padding;

        CSG outerBox = new Cube(width, depth, height)
                .toCSG()
                .transformed(Transform.unity()
                        .translateX(bounds.getCenter().x())
                        .translateY(bounds.getCenter().y())
                        .translateZ(bounds.getCenter().z() + padding / 2));

        CSG keyboardCase = outerBox.difference(keyPlate);

        return keyboardCase;
    }
}
