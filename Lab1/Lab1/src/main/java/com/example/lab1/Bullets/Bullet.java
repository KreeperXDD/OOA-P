package com.example.lab1.Bullets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class Bullet implements IBullet{

    private final ImageView imageView;

    public Bullet(){
        URL resourceUrl = getClass().getResource("/png/bullet.png");
        Image image = new Image(resourceUrl.toExternalForm());
        this.imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }
}
