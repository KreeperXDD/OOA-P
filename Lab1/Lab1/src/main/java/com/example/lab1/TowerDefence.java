package com.example.lab1;

import com.example.lab1.Factories.IFactory;
import com.example.lab1.Factories.MinionFactory;
import com.example.lab1.Factories.TowerFactory;
import com.example.lab1.Minions.GrayMinion;
import com.example.lab1.Minions.IMinion;
import com.example.lab1.Minions.OrangeMinion;
import com.example.lab1.Minions.RedGreenMinion;
import com.example.lab1.SimplePool.SimplePool;
import com.example.lab1.Towers.GrayTower;
import com.example.lab1.Towers.GreenTower;
import com.example.lab1.Towers.ITower;
import com.example.lab1.Towers.RedTower;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class TowerDefence
{
    @FXML
    private Pane playField;
    @FXML
    private GridPane grid;

//    private final IFactory<ITower> towerFactory = new TowerFactory();
//    private final MinionFactory minionFactory = new MinionFactory();

    private final List<ITower> towers = new ArrayList<>();
    private final List<IMinion> minions = new ArrayList<>();
    private final SimplePool<IMinion> minionPool = new SimplePool<>(this::RandomMinion, 0);

    private final Random rand = new Random();

    private IMinion RandomMinion() {
        int x  = rand.nextInt(3);
        return switch (x) {
            case 0 -> new GrayMinion();
            case 1 -> new OrangeMinion();
            default -> new RedGreenMinion();
        };
    }

    private final boolean[][] freePane =
            {
                    {true, true, true, true, true},
                    {true, true, true, true, true},
                    {true, true, true, true, true},
            };

    @FXML
    protected void onStartButtonClick()
    {
        int row = (int)(Math.random()*grid.getRowCount());
        int col = (int)(Math.random()*grid.getColumnCount());
        AddTower(row, col);
    }

    @FXML
    public void initialize()
    {
        StartMinionSpawn();
        StartCheckTowerRange();
    }

    private void StartCheckTowerRange() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> CheckTowersRange()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void CheckTowersRange() {
        for (ITower tower : towers) {
            for (IMinion minion : minions) {
                if (IsMinionInRange(tower,minion))
                {
                    System.out.println("типо стреляем");
                    tower.Shoot(minion,playField);
                }
            }
        }
    }

    private boolean IsMinionInRange(ITower tower, IMinion minion) {
        Bounds towerBounds = tower.getImage().localToScene(tower.getImage().getBoundsInLocal());
        Bounds fieldBounds = playField.localToScene(playField.getBoundsInLocal());

        double towerX = towerBounds.getMinX() - fieldBounds.getMinX() + towerBounds.getWidth()/2;
        double towerY = towerBounds.getMinY() - fieldBounds.getMinY() + towerBounds.getHeight()/2;

        Bounds minionBounds = minion.getImageView().localToScene(minion.getImageView().getBoundsInLocal());
        double minionX = minionBounds.getMinX() - fieldBounds.getMinX() + minionBounds.getWidth()/2;
        double minionY = minionBounds.getMinY() - fieldBounds.getMinY() + minionBounds.getHeight()/2;

        double dx = towerX - minionX;
        double dy = towerY - minionY;
        double distance = Math.sqrt(dx*dx + dy*dy);

        return distance <= tower.getRange();
    }

    private void StartMinionSpawn()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e->SpawnMinion()));
        timeline.setDelay(Duration.seconds(1));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void SpawnMinion()
    {
//        var minion = minionFactory.Create();
        var minion = minionPool.Get();
        ImageView minionView = minion.getImageView();

        playField.getChildren().add(minionView);

        minions.add(minion);

        Bounds bounds = grid.getBoundsInParent();
        double leftX = bounds.getMinX() - 32;
        double rightX = bounds.getMaxX() + 32;
        double topY = bounds.getMinY() - 32;
        double bottomY = bounds.getMaxY();

        Path path = new Path();
        path.getElements().add(new MoveTo(leftX,bottomY));
        path.getElements().add(new LineTo(leftX,topY));
        path.getElements().add(new LineTo(rightX,topY));
        path.getElements().add(new LineTo(rightX,bottomY));

        PathTransition pathTransition = new PathTransition(Duration.seconds(10),path ,minionView );

        pathTransition.setOnFinished(e -> {
            playField.getChildren().remove(minionView);

//            minionFactory.ReturnMinion(minion);

            minion.getImageView().setVisible(false);
            minionPool.ReturnToPool(minion);

            minions.remove(minion);
        });

        pathTransition.play();
    }

    private void AddTower(int row, int col)
    {
        while (!freePane[row][col])
        {
            row = (int)(Math.random()*grid.getRowCount());
            col = (int)(Math.random()*grid.getColumnCount());
        }

//        ITower tower = towerFactory.Create();

        Supplier<ITower> randomTower = () -> {
            int x = rand.nextInt(3);
            return switch (x) {
                case 0 -> new GreenTower();
                case 1 -> new RedTower();
                default -> new GrayTower();
            };
        };

        ITower tower = randomTower.get();
        ImageView towerView = tower.getImage();

        grid.add(towerView, col, row);
        freePane[row][col] = false;

        towers.add(tower);
    }
}