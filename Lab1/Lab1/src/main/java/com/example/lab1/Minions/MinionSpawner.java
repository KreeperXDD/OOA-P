package com.example.lab1.Minions;

import com.example.lab1.Factories.MinionFactory;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MinionSpawner {
    private final Pane playField;
    private final GridPane grid;
    private final MinionFactory minionFactory;
    private final List<IMinion> minions;

    public MinionSpawner(Pane playField, GridPane grid, MinionFactory minionFactory) {
        this.playField = playField;
        this.grid = grid;
        this.minionFactory = minionFactory;
        this.minions = new ArrayList<>();
    }

    public void StartSpawning()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> SpawnMinion()));
        timeline.setDelay(Duration.seconds(1));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void SpawnMinion()
    {
        var minion = minionFactory.Create();
        ImageView minionView = minion.getImageView();
        playField.getChildren().add(minionView);
        minions.add(minion);

        Bounds bounds = grid.getBoundsInParent();
        double leftX = bounds.getMinX() - 32;
        double rightX = bounds.getMaxX() + 32;
        double topY = bounds.getMinY() - 32;
        double bottomY = bounds.getMaxY();

        Path path = new Path();
        path.getElements().add(new MoveTo(leftX,bottomY));
        path.getElements().add(new LineTo(leftX,topY));
        path.getElements().add(new LineTo(rightX,topY));
        path.getElements().add(new LineTo(rightX,bottomY));

        PathTransition pathTransition = new PathTransition(Duration.seconds(10),path ,minionView );

        pathTransition.setOnFinished(e -> {
            playField.getChildren().remove(minionView);

            minionFactory.ReturnMinion(minion);

            minions.remove(minion);
        });

        pathTransition.play();
    }

    public List<IMinion> GetMinions()
    {
        return minions;
    }
}
