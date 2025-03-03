package com.example.lab1;

import com.example.lab1.Factories.TowerFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class HelloController
{
    @FXML
    public GridPane grid;
    private final TowerFactory towerFactory = new TowerFactory();
    private final boolean[][] freePane =
            {
                    {true, true, true, true, true},
                    {true, true, true, true, true},
                    {true, true, true, true, true},
            };

    @FXML
    protected void onHelloButtonClick()
    {
        int row = (int)(Math.random()*grid.getRowCount());
        int col = (int)(Math.random()*grid.getColumnCount());
        addTower(row, col);
    }

    private void addTower(int row, int col)
    {
        while (!freePane[row][col])
        {
            row = (int)(Math.random()*grid.getRowCount());
            col = (int)(Math.random()*grid.getColumnCount());
        }
        grid.add(towerFactory.create().getImage(), col, row);
        freePane[row][col] = false;
    }
}