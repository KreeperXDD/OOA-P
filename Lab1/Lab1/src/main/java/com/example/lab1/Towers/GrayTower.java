package com.example.lab1.Towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

public class GrayTower implements ITower
{
    @Override
    public void Shoot()
    {

    }

    @Override
    public ImageView getImage()
    {
        URL resourceUrl = getClass().getResource("/png/tower3.png");
        Image image = new Image(resourceUrl.toExternalForm());
        return new ImageView(image);
    }
}
