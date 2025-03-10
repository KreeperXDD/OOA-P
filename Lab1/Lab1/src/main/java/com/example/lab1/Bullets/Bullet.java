package com.example.lab1.Bullets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class Bullet implements IBullet{

    @Override
    public ImageView getImageView() {
        URL resourceUrl = getClass().getResource("/png/bullet.png");
        Image image = new Image(resourceUrl.toExternalForm());
        return new ImageView(image);
    }
}
