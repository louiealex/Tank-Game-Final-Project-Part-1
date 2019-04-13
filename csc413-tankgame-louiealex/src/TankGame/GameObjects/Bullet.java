package TankGame.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet extends GameObjects {

    private final int R = 9;
    private BufferedImage explosionImg1, explosionImg2;
    private Tank shotBy;
    private int explosionTime;
    private boolean collided;


    Bullet(int x, int y, int angle, Tank tank) {
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
        this.active = true;
        this.explosionTime = 0;
        this.shotBy = tank;
        this.collided = false;

        try {
            this.img = ImageIO.read(getClass().getResource("/Resources/Rocket.gif"));
            this.explosionImg2 = ImageIO.read(getClass().getResource("/Resources/Explosion_large.gif"));
            this.explosionImg1 = ImageIO.read(getClass().getResource("/Resources/Explosion_small.gif"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        r = new Rectangle(x, y, img.getWidth(), img.getHeight());
        //System.out.println("bullet has been created");


    }

/*    private void checkBorder() {
        if (x < getWidth()) {
            x = 28;
            active = false;
        }
        if (x >= TankGame.WORLD_WIDTH - 75) {
            x = TankGame.WORLD_WIDTH - 75;
            active = false;
        }
        if (y < 40) {
            y = 40;
            active = false;
        }
        if (y >= TankGame.WORLD_HEIGHT - 60) {
            y = TankGame.WORLD_HEIGHT - 60;
            active = false;
        }

    }*/


    @Override
    public void update() {
        x += vx;
        y += vy;
        //checkBorder();
        r.setLocation(x, y);

        if (explosionTime >= 4) {
            active = false;
        }

        if (collided) {
            explosionTime++;
        }
    }

    @Override
    public void collision(Class c) {

        if (!(c.equals(HealthPowerUp.class) || c.equals(ShieldPowerUp.class))) {
            this.collided = true;
            this.vx = 0;
            this.vy = 0;

            if (c.equals(Tank.class)) {
                this.img = explosionImg2; // large
            } else {
                this.img = explosionImg1; // small
            }
        }
    }

    public boolean hasCollided() {
        return collided;
    }

    public Tank getShotBy() {
        return shotBy;
    }


}
