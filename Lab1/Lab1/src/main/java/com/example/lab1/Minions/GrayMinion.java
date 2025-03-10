package com.example.lab1.Minions;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

public class GrayMinion  implements IMinion
{
    @Override
    public ImageView getImageView()
    {
        URL resourceUrl = getClass().getResource("/png/minion1.png");
        Image image = new Image(resourceUrl.toExternalForm());
        return new ImageView(image);
    }
}
