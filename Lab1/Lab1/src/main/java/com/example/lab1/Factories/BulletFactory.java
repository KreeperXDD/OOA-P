package com.example.lab1.Factories;

import com.example.lab1.Bullets.Bullet;
import com.example.lab1.Bullets.IBullet;
import com.example.lab1.SimplePool.SimplePool;

public class BulletFactory implements IFactory<IBullet>{

    private final SimplePool<IBullet> bulletPool = new SimplePool<>(Bullet::new, 0);

    @Override
    public IBullet Create() {
        var bullet = bulletPool.Get();
        bullet.getImageView().setVisible(true);
        return bullet;
    }

    public void ReturnBullet (IBullet bullet)
    {
        bullet.getImageView().setVisible(false);
        bulletPool.ReturnToPool(bullet);
    }
}
