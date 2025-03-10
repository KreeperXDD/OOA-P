package com.example.lab1.Factories;

import com.example.lab1.Towers.GrayTower;
import com.example.lab1.Towers.GreenTower;
import com.example.lab1.Towers.ITower;
import com.example.lab1.Towers.RedTower;
import java.util.Random;

public class TowerFactory implements IFactory<ITower>
{
    private final Random rand = new Random();

    @Override
    public ITower Create()
    {
        int x = rand.nextInt(3);
        return switch (x)
        {
            case 0 -> new GreenTower();
            case 1 -> new RedTower();
            default -> new GrayTower();
        };
    }
}
