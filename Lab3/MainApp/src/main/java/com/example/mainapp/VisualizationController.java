package com.example.mainapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizationController {

    @FXML
    private Pane rootPane;

    private List<Node> traversalNodes;
    private final Map<Node, Label> nodeLabels = new HashMap<>();
    private final Map<Node, Integer> nodeLevels = new HashMap<>();

    private static final double X_SPACING = 120;    // Расстояние между узлами по горизонтали
    private static final double Y_SPACING = 100;    // Расстояние по вертикали между уровнями
    private static final double START_CENTER_X = 1000; // Центр экрана
    private static final double START_Y = 50;       // Начальная высота

    private double scaleValue = 1.0; // Масштабирование

    @FXML
    public void initialize() {
        rootPane.setOnScroll(event -> {
            if (event.isControlDown()) {  // ⌃Ctrl + колесо для масштабирования
                if (event.getDeltaY() > 0) {
                    scaleValue *= 1.1;
                } else {
                    scaleValue /= 1.1;
                }
                rootPane.setScaleX(scaleValue);
                rootPane.setScaleY(scaleValue);
                event.consume();
            }
        });
    }

    public void SetTraversalOrder(List<Node> nodes) {
        this.traversalNodes = nodes;
        calculateLevels();
        visualize();
    }

    private void calculateLevels() {
        for (Node node : traversalNodes) {
            nodeLevels.put(node, getDepth(node));
        }
    }

    private int getDepth(Node node) {
        int depth = 0;
        Node current = node;
        while (findParents(current).size() > 0) {
            depth++;
            current = findParents(current).get(0); // Берем первого родителя
        }
        return depth;
    }

    private void visualize() {
        Timeline timeline = new Timeline();
        int step = 0;
        Map<Integer, Integer> levelNodeCount = new HashMap<>();

        for (Node node : traversalNodes) {
            int level = nodeLevels.getOrDefault(node, 0);
            int countInLevel = levelNodeCount.getOrDefault(level, 0);

            double offset = (countInLevel - 0.5 * getNodesAtLevel(level)) * X_SPACING;
            double x = START_CENTER_X + offset;
            double y = START_Y + level * Y_SPACING;

            KeyFrame keyFrame = new KeyFrame(Duration.millis(300 * step), e -> {
                Label label = createNodeLabel(node, x, y);
                rootPane.getChildren().add(label);
                nodeLabels.put(node, label);

                for (Node parent : findParents(node)) {
                    if (nodeLabels.containsKey(parent)) {
                        drawLineBetween(parent, node);
                    }
                }
            });

            levelNodeCount.put(level, countInLevel + 1);
            timeline.getKeyFrames().add(keyFrame);
            step++;
        }

        timeline.play();
    }

    private Label createNodeLabel(Node node, double x, double y) {
        Label label = new Label(node.GetName());
        label.setPrefWidth(80);
        label.setPrefHeight(30);

        label.setStyle("-fx-border-color: black; " +
                "-fx-background-color: #d7ffd9; " +
                "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-padding: 5;");

        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    private void drawLineBetween(Node parent, Node child) {
        Label parentLabel = nodeLabels.get(parent);
        Label childLabel = nodeLabels.get(child);

        double startX = parentLabel.getLayoutX() + parentLabel.getPrefWidth() / 2;
        double startY = parentLabel.getLayoutY() + parentLabel.getPrefHeight();

        double endX = childLabel.getLayoutX() + childLabel.getPrefWidth() / 2;
        double endY = childLabel.getLayoutY();

        Line line = new Line(startX, startY, endX, endY);
        line.setStyle("-fx-stroke: gray; -fx-stroke-width: 1.5;");

        rootPane.getChildren().add(0, line); // Добавляем линии ПОД узлы
    }

    private List<Node> findParents(Node child) {
        return traversalNodes.stream()
                .filter(parent -> parent.GetChildren().contains(child))
                .toList();
    }

    private int getNodesAtLevel(int level) {
        return (int) traversalNodes.stream()
                .filter(node -> nodeLevels.getOrDefault(node, 0) == level)
                .count();
    }

}

