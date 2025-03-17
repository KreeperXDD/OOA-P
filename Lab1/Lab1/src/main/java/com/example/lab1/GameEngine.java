package com.example.lab1;

import com.example.lab1.Minions.MinionSpawner;
import com.example.lab1.Towers.RangeChecker;
import com.example.lab1.Towers.TowerManager;

public class GameEngine {
    private final MinionSpawner minionSpawner;
    private final TowerManager towerManager;
    private final RangeChecker rangeCheck;

    public GameEngine(MinionSpawner minionSpawner, TowerManager towerManager, RangeChecker rangeCheck) {
        this.minionSpawner = minionSpawner;
        this.towerManager = towerManager;
        this.rangeCheck = rangeCheck;
    }

    public void StartGame() {
        minionSpawner.StartSpawning();
        rangeCheck.StartChecking(towerManager.GetTowers(), minionSpawner.GetMinions());
    }

    public void AddTower(int row, int col) {
        towerManager.AddTower(row, col);
    }
}
