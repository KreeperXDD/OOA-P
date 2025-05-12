package com.example.lab1.Towers;

import com.example.lab1.Factories.IFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TowerManager {
    private final GridPane grid;
    private final IFactory<ITower> towerFactory;
    private final boolean[][] freePane;
    private final List<ITower> towers;

    public TowerManager(GridPane grid, IFactory<ITower> towerFactory, boolean[][] freePane) {
        this.grid = grid;
        this.towerFactory = towerFactory;
        this.freePane = freePane;
        this.towers = new ArrayList<>();
    }

    public void AddTower(int row, int col) {
        while (!freePane[row][col]) {
            row = (int) (Math.random() * grid.getRowCount());
            col = (int) (Math.random() * grid.getColumnCount());
        }
        ITower tower = towerFactory.Create();
        ImageView towerView = tower.getImageView();
        grid.add(towerView, col, row);;
        freePane[row][col] = false;
        towers.add(tower);
    }

    public List<ITower> GetTowers() {
        return towers;
    }
}
