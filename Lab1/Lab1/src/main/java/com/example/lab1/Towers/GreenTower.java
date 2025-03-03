package com.example.lab1.Towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

public class GreenTower implements ITower
{
    @Override
    public void Shoot()
    {
        System.out.print("I shot");
    }

    @Override
    public ImageView getImage() {
        URL resourceUrl = getClass().getResource("/png/tower1.png");
        Image image = new Image(resourceUrl.toExternalForm());
        return new ImageView(image);
    }
}
