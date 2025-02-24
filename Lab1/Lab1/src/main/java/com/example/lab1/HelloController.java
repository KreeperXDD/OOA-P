package com.example.lab1;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;


public class HelloController {
    @FXML
    public GridPane grid;
    private final boolean[][] occupied =
            {
                    {true, true, true, true, true},
                    {true, true, true, true, true},
                    {true, true, true, true, true},
            };
    private final CircleTowerFactory towerFactory = new CircleTowerFactory();

    @FXML
    protected void onHelloButtonClick()
    {
        int row = (int)(Math.random()*grid.getRowCount());
        int col = (int)(Math.random()*grid.getColumnCount());
        addTower(row, col);
    }

    private void addTower(int row, int col)
    {
        while (occupied[row][col]) {
            grid.add(towerFactory.create(), col, row);
            occupied[row][col] = false;
        }
    }
}