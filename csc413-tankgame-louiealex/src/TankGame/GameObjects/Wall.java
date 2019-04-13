package TankGame.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Wall extends GameObjects {
    private int strength;
    private BufferedImage stillWall;
    public Wall(int x, int y, int strength) {
        this.x = x;
        this.y = y;
        if (strength >= 4 && strength < 9) {

            Random rand = new Random();
            this.vx = rand.nextInt(3) - 1;
            this.vy = rand.nextInt(3) - 1;

            //this.vx = -1;
            //this.vy = -1;
        } else {
            this.vx = 0;
            this.vy = 0;
        }
        this.angle = 0;
        this.strength = strength;
        try {
            this.img = ImageIO.read(getClass().getResource("/Resources/Wall2.gif"));
            this.stillWall = ImageIO.read(getClass().getResource("/Resources/Wall1.gif"));
            if (strength <= 3 || strength >= 9) {
                this.img = this.stillWall;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.active = true;

        r = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }


    @Override
    public void update() {
        if (strength <= 3) {
            this.vx = 0;
            this.vy = 0;
            this.img = stillWall;
        }

        this.x += this.vx;
        this.y += this.vy;
        r.setLocation(x, y);
    }

    @Override
    public void collision(Class c) {

        if (this.strength < 9) {
            if (c.equals(Bullet.class)) {
                this.strength -= 1;
                if (strength <= 0) {
                    this.active = false;
                }
            }

            if (c.equals(Wall.class) || c.equals(Tank.class)) {
                this.vx = -this.vx;
                this.vy = -this.vy;
                this.x += this.vx;
                this.y += this.vy;
            }
        }

    }


}
