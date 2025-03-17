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


//    private void StartCheckTowerRange() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> CheckTowersRange()));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//    }

//    private void CheckTowersRange() {
//        for (ITower tower : towers) {
//            for (IMinion minion : minions) {
//                if (IsMinionInRange(tower,minion))
//                {
//                    tower.Shoot(minion,playField);
//                }
//            }
//        }
//    }

//    private boolean IsMinionInRange(ITower tower, IMinion minion) {
//
//        Point2D towerCenter = NodeUtils.GetCenter(playField, tower.getImageView());
//        Point2D minionCenter = NodeUtils.GetCenter(playField, minion.getImageView());
//
//        double dx = minionCenter.getX() - towerCenter.getX();
//        double dy = minionCenter.getY() - towerCenter.getY();
//        double distance = Math.sqrt(dx*dx + dy*dy);
//
//        return distance <= tower.getRange();
//    }

//    private void StartMinionSpawn()
//    {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e->SpawnMinion()));
//        timeline.setDelay(Duration.seconds(1));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
//    }
//
//    private void SpawnMinion()
//    {
//        var minion = minionFactory.Create();
//        ImageView minionView = minion.getImageView();
//
//        playField.getChildren().add(minionView);
//
//        minions.add(minion);
//
//        Bounds bounds = grid.getBoundsInParent();
//        double leftX = bounds.getMinX() - 32;
//        double rightX = bounds.getMaxX() + 32;
//        double topY = bounds.getMinY() - 32;
//        double bottomY = bounds.getMaxY();
//
//        Path path = new Path();
//        path.getElements().add(new MoveTo(leftX,bottomY));
//        path.getElements().add(new LineTo(leftX,topY));
//        path.getElements().add(new LineTo(rightX,topY));
//        path.getElements().add(new LineTo(rightX,bottomY));
//
//        PathTransition pathTransition = new PathTransition(Duration.seconds(10),path ,minionView );
//
//        pathTransition.setOnFinished(e -> {
//            playField.getChildren().remove(minionView);
//
//            minionFactory.ReturnMinion(minion);
//
//            minions.remove(minion);
//        });
//
//        pathTransition.play();
//    }

//    private void AddTower(int row, int col)
//    {
//        while (!freePane[row][col])
//        {
//            row = (int)(Math.random()*grid.getRowCount());
//            col = (int)(Math.random()*grid.getColumnCount());
//        }
//
//        ITower tower = towerFactory.Create();
//
//        ImageView towerView = tower.getImageView();
//
//        grid.add(towerView, col, row);
//        freePane[row][col] = false;
//
//        towers.add(tower);
//    }
}