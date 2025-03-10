package com.example.lab1.Towers;

import com.example.lab1.Minions.IMinion;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public interface ITower
{
   void Shoot(IMinion minion, Pane pane);
   ImageView getImage();
   double getRange();
}
