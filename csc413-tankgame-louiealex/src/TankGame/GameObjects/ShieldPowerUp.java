package TankGame.GameObjects;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ShieldPowerUp extends GameObjects {

    public ShieldPowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
        this.active = true;
        try {
            this.img = ImageIO.read(getClass().getResource("/Resources/Shield1.gif"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        r = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }


    @Override
    public void update() {

    }

    @Override
    public void collision(Class c) {
        if (c.equals(Tank.class)) {
            this.active = false;
        }
    }
}
