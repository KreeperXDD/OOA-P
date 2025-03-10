package com.example.lab1.Towers;

import com.example.lab1.Factories.BulletFactory;
import com.example.lab1.Minions.IMinion;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
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

        this.range = 1000;
        this.bulletSpeed = 150;
        this.imageView = new ImageView(image);
        this.bulletFactory = new BulletFactory();
    }

    @Override
    public void Shoot(IMinion minion, Pane pane) {

        System.out.print("Я выстрелил");
        var bullet = bulletFactory.Create();
        ImageView bulletView = bullet.getImageView();

        pane.getChildren().add(bulletView);

        Bounds towerBounds = imageView.localToScene(imageView.getBoundsInLocal());
        Bounds playFieldBounds = pane.localToScene(pane.getBoundsInLocal());

        double towerX = towerBounds.getMinX() - playFieldBounds.getMinX() + towerBounds.getWidth() / 2;
        double towerY = towerBounds.getMinY() - playFieldBounds.getMinY() + towerBounds.getHeight() / 2;

        Bounds minionBounds = minion.getImageView().localToScene(minion.getImageView().getBoundsInLocal());
        double minionX = minionBounds.getMinX() - playFieldBounds.getMinX() + minionBounds.getWidth() / 2;
        double minionY = minionBounds.getMinY() - playFieldBounds.getMinY() + minionBounds.getHeight() / 2;

        bulletView.setLayoutX(towerX);
        bulletView.setLayoutY(towerY);

        double dx = towerX - minionX;
        double dy = towerY - minionY;
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
    public ImageView getImage() {
        return imageView;
    }

    @Override
    public double getRange() {
        return range;
    }
}
