package com.example.lab1.Minions;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

public class RedGreenMinion implements IMinion{
    @Override
    public ImageView getImageView()
    {
        URL resourceUrl = getClass().getResource("/png/minion3.png");
        Image image = new Image(resourceUrl.toExternalForm());
        return new ImageView(image);
    }
}
