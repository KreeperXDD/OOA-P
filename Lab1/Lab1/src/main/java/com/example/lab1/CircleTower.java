package com.example.lab1;

public class CircleTower implements ITower
{
    public double AttackRange;
    public double AttackSpeed;

    @Override
    public void Shoot()
    {
        System.out.print("I shot");
    }
}
