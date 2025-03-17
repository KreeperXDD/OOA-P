package com.example.lab1.Towers;

import com.example.lab1.Minions.IMinion;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;

public class GrayTower implements ITower
{
    private double range;
    private double attackSpeed;

    @Override
    public void Shoot(IMinion minion, Pane pane) {

    }

    @Override
    public ImageView getImageView()
    {
        URL resourceUrl = getClass().getResource("/png/tower3.png");
        Image image = new Image(resourceUrl.toExternalForm());
        return new ImageView(image);
    }

    @Override
    public double getRange() {
        return 0;
    }
}
