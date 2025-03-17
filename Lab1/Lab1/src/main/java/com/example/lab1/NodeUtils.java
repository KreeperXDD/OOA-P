package com.example.lab1;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public final class NodeUtils {

    private NodeUtils() {}

    public static Point2D GetCenter(Parent parent  , Node node)
    {
        Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());

        return parent.sceneToLocal(
                nodeBounds.getMinX() + nodeBounds.getWidth() / 2,
                nodeBounds.getMinY() + nodeBounds.getHeight()/2);
    }
}
