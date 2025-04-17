package com.example.lab1;

import com.example.lab1.Factories.IFactory;
import com.example.lab1.Factories.MinionFactory;
import com.example.lab1.Factories.TowerFactory;
import com.example.lab1.Minions.MinionSpawner;
import com.example.lab1.Towers.ITower;
import com.example.lab1.Towers.RangeChecker;
import com.example.lab1.Towers.TowerManager;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TowerDefence
{
    @FXML
    private Pane playField;
    @FXML
    private GridPane grid;

    private GameEngine gameEngine;

    @FXML
    public void initialize()
    {
        IFactory<ITower> towerFactory = new TowerFactory();
        MinionFactory minionFactory = new MinionFactory();
        boolean[][] freePane =
                {
                        {true, true, true, true, true},
                        {true, true, true, true, true},
                        {true, true, true, true, true},
                };

        MinionSpawner minionSpawner = new MinionSpawner(playField, grid, minionFactory);
        TowerManager towerManager = new TowerManager(grid,towerFactory,freePane);
        RangeChecker rangeChecker = new RangeChecker(playField);

        gameEngine = new GameEngine(minionSpawner, towerManager, rangeChecker);
        gameEngine.StartGame();
    }

    @FXML
    protected void onStartButtonClick()
    {
        int row = (int)(Math.random()*grid.getRowCount());
        int col = (int)(Math.random()*grid.getColumnCount());
        gameEngine.AddTower(row, col);
    }
}