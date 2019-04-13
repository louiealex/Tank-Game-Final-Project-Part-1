package TankGame;

import TankGame.GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TankGame extends JPanel {


    public static final int WORLD_WIDTH = 2240;
    public static final int WORLD_HEIGHT = 2240;

    private static final int SCREEN_WIDTH = 1280;//1440
    private static final int SCREEN_HEIGHT = 704;//900

    //private static final int SCREEN_WIDTH = 800;
    //private static final int SCREEN_HEIGHT = 600;

    private Tank t1;
    private Tank t2;

    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;

    private ArrayList<GameObjects> ActiveGameObjects;


    public static void main(String[] args) {
        //Thread x;
        TankGame GameWorld = new TankGame();
        GameWorld.init();

        try {
            while (GameWorld.t1.getLivesRemaining() > 0 && GameWorld.t2.getLivesRemaining() > 0) {

                int i = 0;
                // removes bullets if collided with object or edge.
                while (i < GameWorld.ActiveGameObjects.size()) {
                    GameWorld.ActiveGameObjects.get(i).update();
                    if (!GameWorld.ActiveGameObjects.get(i).isActive()) {
                        GameWorld.ActiveGameObjects.remove(i);
                    } else {
                        i++;
                    }
                }


                for (i = 0; i < GameWorld.ActiveGameObjects.size(); i++) {
                    for (int j = i; j < GameWorld.ActiveGameObjects.size(); j++) {
                        GameObjects obj1 = GameWorld.ActiveGameObjects.get(i);
                        GameObjects obj2 = GameWorld.ActiveGameObjects.get(j);

                        Collision collision = new Collision(obj1, obj2);
                        collision.checkForCollision(); // Also looks ahead if its a Tank and  Wall or  Wall and Tank
                    }
                }

                //System.out.println("Tank1: " + GameWorld.t1.toString());
                //System.out.println("Tank2: " + GameWorld.t2.toString());

                GameWorld.repaint();

                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

        long temp = System.currentTimeMillis();
        while (System.currentTimeMillis() - temp < 10000) {
            GameWorld.repaint();
        }
        //Code to exit game window and stop execution
        Window win = SwingUtilities.getWindowAncestor(GameWorld);
        win.setVisible(false);
        win.dispose();

    }

    public void addBullet(Bullet bullet) {
        ActiveGameObjects.add(bullet);
    }


    private void init() {


        this.jf = new JFrame("Tank Game");
        this.world = new BufferedImage(TankGame.WORLD_WIDTH, TankGame.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        ActiveGameObjects = new ArrayList<>();
        initMap();

        t1 = new Tank(WORLD_WIDTH * 3 / 32, WORLD_HEIGHT * 3 / 32, 90, this);
        t2 = new Tank(WORLD_WIDTH * 29 / 32, WORLD_HEIGHT * 29 / 32, 270, this);
        TankPlayerControls tankPlayerControls1 = new TankPlayerControls(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankPlayerControls tankPlayerControls2 = new TankPlayerControls(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);

        this.ActiveGameObjects.add(t1);
        this.ActiveGameObjects.add(t2);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tankPlayerControls1);
        this.jf.addKeyListener(tankPlayerControls2);

        this.jf.setSize(TankGame.SCREEN_WIDTH, TankGame.SCREEN_HEIGHT + 32);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


    }

    private void initMap() {

        //background walls
        for (int i = 0; i < WORLD_WIDTH; i = i + 320) {
            for (int j = 0; j < WORLD_HEIGHT; j = j + 240) {
                BackgroundTile b = new BackgroundTile(i, j);
                ActiveGameObjects.add(b);
            }
        }


        // Adding Health PowerUps to the map
        for (int i = WORLD_WIDTH * 2 / 5; i < WORLD_WIDTH * 3 / 5 + 10; i = i + WORLD_WIDTH / 5) {
            for (int j = WORLD_HEIGHT * 2 / 5; j < WORLD_HEIGHT * 3 / 5 + 10; j = j + WORLD_HEIGHT / 5) {
                HealthPowerUp healthPowerUp = new HealthPowerUp(i, j);
                ActiveGameObjects.add(healthPowerUp);
            }
        }

        // Adding Shield PowerUps to the map
        for (int i = WORLD_WIDTH / 5; i < WORLD_WIDTH * 4 / 5 + 10; i = i + WORLD_WIDTH * 3 / 5) {
            for (int j = WORLD_HEIGHT / 5; j < WORLD_HEIGHT * 4 / 5 + 10; j = j + WORLD_HEIGHT * 3 / 5) {
                ShieldPowerUp shieldPowerUp = new ShieldPowerUp(i, j);
                ActiveGameObjects.add(shieldPowerUp);
            }
        }


        for (int i = 0; i < WORLD_WIDTH; i = i + 32) {
            for (int j = 0; j < WORLD_HEIGHT; j = j + 32) {
                //border walls
                if (i == 0 || (WORLD_WIDTH - i) <= 32 || j == 0 || (WORLD_HEIGHT - j) <= 32) {
                    Wall w = new Wall(i, j, 9);
                    ActiveGameObjects.add(w);

                    // middle of the map walls
                } else if (i < (WORLD_WIDTH / 2 + 17) && i > (WORLD_WIDTH / 2 - 17)) {
                    Wall w = new Wall(i, j, 3);
                    ActiveGameObjects.add(w);
                } else if (j < (WORLD_WIDTH / 2 + 17) && j > (WORLD_WIDTH / 2 - 17)) {
                    Wall w = new Wall(i, j, 3);
                    ActiveGameObjects.add(w);
                } else if (i < (WORLD_WIDTH / 4 + 17) && i > (WORLD_WIDTH / 4 - 17) && j % 7 == 0) {
                    Wall w = new Wall(i, j, 3);
                    ActiveGameObjects.add(w);
                } else if (i < (WORLD_WIDTH * 3 / 4 + 17) && i > (WORLD_WIDTH * 3 / 4 - 17) && j % 7 == 0) {
                    Wall w = new Wall(i, j, 3);
                    ActiveGameObjects.add(w);
                } else if (i < (WORLD_WIDTH * 3 / 8 + 16) && i > (WORLD_WIDTH * 3 / 8 - 17) && j % 3 == 0) {
                    Wall w = new Wall(i, j, 8);
                    ActiveGameObjects.add(w);
                } else if (i < (WORLD_WIDTH * 5 / 8 + 17) && i > (WORLD_WIDTH * 5 / 8 - 16) && j % 3 == 0) {
                    Wall w = new Wall(i, j, 8);
                    ActiveGameObjects.add(w);
                }
            }
        }


    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.buffer = this.world.createGraphics();
        super.paintComponent(g2);


        if (this.t1.getLivesRemaining() > 0 && this.t2.getLivesRemaining() > 0) {
            // drawing each active object
            for (int i = 0; i < this.ActiveGameObjects.size(); i++) {
                this.ActiveGameObjects.get(i).drawImage(this.buffer);
            }


            // calculating mini map display.
            int sub1x = t1.getX() - SCREEN_WIDTH * 7 / 40;
            if ((t1.getX() - SCREEN_WIDTH * 7 / 40) < 0) {
                sub1x = 0;
            } else if ((t1.getX() + SCREEN_WIDTH * 7 / 40) > (WORLD_WIDTH)) {
                sub1x = WORLD_WIDTH - SCREEN_WIDTH * 7 / 20;
            }

            int sub1y = t1.getY() - SCREEN_HEIGHT / 2;
            if ((t1.getY() - SCREEN_HEIGHT / 2) < 0) {
                sub1y = 0;
            } else if ((t1.getY() + SCREEN_HEIGHT / 2) > (WORLD_HEIGHT)) {
                sub1y = WORLD_HEIGHT - SCREEN_HEIGHT;
            }


            int sub2x = t2.getX() - SCREEN_WIDTH * 7 / 40;
            if ((t2.getX() - SCREEN_WIDTH * 7 / 40) < 0) {
                sub2x = 0;
            } else if ((t2.getX() + SCREEN_WIDTH * 7 / 40) > (WORLD_WIDTH)) {
                sub2x = WORLD_WIDTH - SCREEN_WIDTH * 7 / 20;
            }

            int sub2y = t2.getY() - SCREEN_HEIGHT / 2;
            if ((t2.getY() - SCREEN_HEIGHT / 2) < 0) {
                sub2y = 0;
            } else if ((t2.getY() + SCREEN_HEIGHT / 2) > (WORLD_HEIGHT)) {
                sub2y = WORLD_HEIGHT - SCREEN_HEIGHT;
            }


            // displaying sub images Left(1), Right(2) and mini map.
            BufferedImage sub1 = this.world.getSubimage(sub1x, sub1y, SCREEN_WIDTH * 7 / 20, SCREEN_HEIGHT);
            BufferedImage sub2 = this.world.getSubimage(sub2x, sub2y, SCREEN_WIDTH * 7 / 20, SCREEN_HEIGHT);
            BufferedImage miniMap = this.world.getSubimage(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            g2.drawImage(sub1, 0, 0, null);
            g2.drawImage(sub2, SCREEN_WIDTH * 13 / 20, 0, null);
            g2.drawImage(miniMap.getScaledInstance(SCREEN_WIDTH * 3 / 10, SCREEN_WIDTH * 3 / 10, BufferedImage.TYPE_INT_RGB), SCREEN_WIDTH * 7 / 20, 0, null);


            // displays health and lives for Tank1
            g2.setColor(Color.BLACK);
            g2.drawString("Tank 1 Lives Remaining: " + this.t1.getLivesRemaining(), SCREEN_WIDTH * 29 / 80, SCREEN_HEIGHT * 26 / 40);
            g2.drawString("Tank 1 Health: " + this.t1.getHealth(), SCREEN_WIDTH * 29 / 80, SCREEN_HEIGHT * 28 / 40);
            g2.drawRect(SCREEN_WIDTH * 29 / 80 - 1, SCREEN_HEIGHT * 29 / 40 - 1, 102, 12);
            g2.setColor(Color.GREEN);
            for (int i = 0; i < this.t1.getHealth() && i < 100; i++) {
                g2.drawRect(SCREEN_WIDTH * 29 / 80 + i, SCREEN_HEIGHT * 29 / 40, 1, 10);
            }


            // displays health and lives for Tank2
            g2.setColor(Color.BLACK);
            g2.drawString("Tank 2 Lives Remaining: " + this.t2.getLivesRemaining(), SCREEN_WIDTH * 29 / 80, SCREEN_HEIGHT * 33 / 40);
            g2.drawString("Tank 2 Health: " + this.t2.getHealth(), SCREEN_WIDTH * 29 / 80, SCREEN_HEIGHT * 35 / 40);
            g2.drawRect(SCREEN_WIDTH * 29 / 80 - 1, SCREEN_HEIGHT * 36 / 40 - 1, 102, 12);
            g2.setColor(Color.GREEN);
            for (int i = 0; i < this.t2.getHealth() && i < 100; i++) {
                g2.drawRect(SCREEN_WIDTH * 29 / 80 + i, SCREEN_HEIGHT * 36 / 40, 1, 10);
            }

        } else {
            if (this.t1.getLivesRemaining() > 0) {
                g2.drawString("Tank 1 Wins", SCREEN_WIDTH * 9 / 20, SCREEN_HEIGHT * 18 / 40);
            } else {
                g2.drawString("Tank 2 Wins", SCREEN_WIDTH * 9 / 20, SCREEN_HEIGHT * 18 / 40);
            }

            g2.drawString("This window will close after about 10 seconds.", SCREEN_WIDTH * 8 / 20, SCREEN_HEIGHT * 25 / 40);
        }

    }


}

