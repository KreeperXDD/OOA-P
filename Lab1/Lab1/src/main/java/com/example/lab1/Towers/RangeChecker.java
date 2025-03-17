package com.example.lab1.Towers;

import com.example.lab1.Minions.IMinion;
import com.example.lab1.NodeUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

public class RangeChecker {
    private final Pane playField;

    public RangeChecker(Pane playField) {
        this.playField = playField;
    }

    public void StartChecking(List<ITower> towers, List<IMinion> minions) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> CheckRange(towers, minions)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void CheckRange(List<ITower> towers, List<IMinion> minions) {
        for (ITower tower : towers) {
            for (IMinion minion : minions) {
                if (IsMinionInRange(tower, minion))
                {
                    tower.Shoot(minion, playField);
                }
            }
        }
    }

    private boolean IsMinionInRange(ITower tower, IMinion minion) {
        Point2D towerCenter = NodeUtils.GetCenter(playField, tower.getImageView());
        Point2D minionCenter = NodeUtils.GetCenter(playField, minion.getImageView());

        double dx = minionCenter.getX() - towerCenter.getX();
        double dy = minionCenter.getY() - towerCenter.getY();
        double distance = Math.sqrt(dx*dx + dy*dy);

        return distance <= tower.getRange();
    }
}
