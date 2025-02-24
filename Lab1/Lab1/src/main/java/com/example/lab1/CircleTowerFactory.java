package com.example.lab1;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


public class CircleTowerFactory implements IFactory<Circle>
{
    @Override
    public Circle create()
    {
        return new Circle(20, Color.BLUE);
    }
}
