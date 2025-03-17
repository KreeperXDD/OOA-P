package com.example.lab1.Towers;

import com.example.lab1.Factories.BulletFactory;
import com.example.lab1.Minions.IMinion;
import com.example.lab1.NodeUtils;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;

public class RedTower implements ITower
{
    private final double range;
    private final double bulletSpeed;
    private final ImageView imageView;
    private final BulletFactory bulletFactory;

    public RedTower()
    {
        URL resourceUrl = getClass().getResource("/png/tower2.png");
        Image image = new Image(resourceUrl.toExternalForm());

        this.range = 600;
        this.bulletSpeed = 150;
        this.imageView = new ImageView(image);
        this.bulletFactory = new BulletFactory();
    }

    @Override
    public void Shoot(IMinion minion, Pane pane) {

        var bullet = bulletFactory.Create();
        ImageView bulletView = bullet.getImageView();
        pane.getChildren().add(bulletView);

        Point2D towerCenter = NodeUtils.GetCenter(pane, imageView);
        Point2D minionCenter = NodeUtils.GetCenter(pane, minion.getImageView());

        bulletView.setLayoutX(towerCenter.getX());
        bulletView.setLayoutY(towerCenter.getY());

        double dx = minionCenter.getX() - towerCenter.getX();
        double dy = minionCenter.getY() - towerCenter.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double time = distance /bulletSpeed;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(time), bulletView);
        transition.setFromX(0);
        transition.setFromY(0);
        transition.setToX(dx);
        transition.setToY(dy);

        transition.setOnFinished(event -> {
            pane.getChildren().remove(bulletView);
            bulletFactory.ReturnBullet(bullet);
            pane.getChildren().remove(minion.getImageView());
        });

        transition.play();
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public double getRange() {
        return range;
    }
}
