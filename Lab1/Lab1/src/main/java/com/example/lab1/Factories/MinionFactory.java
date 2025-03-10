package com.example.lab1.Factories;

import com.example.lab1.Minions.GrayMinion;
import com.example.lab1.Minions.IMinion;
import com.example.lab1.Minions.OrangeMinion;
import com.example.lab1.Minions.RedGreenMinion;
import com.example.lab1.SimplePool.SimplePool;

import java.util.Random;

public class MinionFactory implements IFactory<IMinion> {

    private Random rand = new Random();
    private final SimplePool<IMinion> minionPool = new SimplePool<>(this::RandomMinion, 0);

    @Override
    public IMinion Create() {
        var minion =  minionPool.Get();
        minion.getImageView().setVisible(true);
        return  minion;
    }

    public void ReturnMinion(IMinion minion) {
        minion.getImageView().setVisible(false);
//        minion.getImageView().setLayoutX(0);
//        minion.getImageView().setLayoutY(0);
        minionPool.ReturnToPool(minion);
    }

    private IMinion RandomMinion() {
        int x = rand.nextInt(3);
        switch (x) {
            case 0:
                var minion1 = new GrayMinion();
                minion1.getImageView().setVisible(false);
                return minion1;
            case 1:
                var minion2 =  new OrangeMinion();
                minion2.getImageView().setVisible(false);
                return minion2;
            default:
                var minion3 = new RedGreenMinion();
                minion3.getImageView().setVisible(false);
                return minion3;
        }
    }
}
