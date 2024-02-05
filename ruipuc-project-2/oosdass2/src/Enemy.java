import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.Random;

public class Enemy {
    private final Image ballImage = new Image("res/enemy.PNG");
    private double x;
    private final double y;
    private static final double STEP_SIZE = 1;
    private double directionX;
    //private double directionY;
    private int disappear = 0;

    /**
     * create the enemy class
     */
    public Enemy() {
        x = 100 + (Math.random() * (900 - 100));
        y = 100 + (Math.random() * (500 - 100));
        // Chose a random direction
        Random random = new Random();
        directionX = random.nextInt(2) == 0 ? -1 : 1;
    }

    /**
     * this function is used in level class
     * to check the distance between arrow and
     * enemy
     *
     */
    public int getDisappear() {
        return disappear;
    }

    /**
     * used when an arrow hit an enemy
     * after hit the enemy, the enemy
     * disappear
     */
    public void setDisappear(int disappear) {
        this.disappear = disappear;
    }

    /**
     * this function is used in level class
     * to check the distance between arrow and
     * enemy
     *
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * enemy moving and change direction when
     * enemy out of the range
     */
    public void update() {
        if (x <= 100 || x >= 900) {
            directionX *= -1;
        }

        x += directionX * STEP_SIZE;
        ballImage.draw(x, y);
    }
}

