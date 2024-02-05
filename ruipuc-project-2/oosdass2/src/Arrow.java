import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

public class Arrow {
    private final Image ballImage = new Image("res/arrow.PNG");
    private final static double START_X = 800;
    private final static double START_Y = 600;
    private double x = START_X;
    private double y = START_Y;
    private static final double STEP_SIZE = 6;
    private double directionX;
    private double directionY;
    private final DrawOptions option = new DrawOptions();
    private int disappear = 0;

    /**
     * in level class, an arrow will be removed
     * when the arrow is disappear
     *
     */
    public int getDisappear() {
        return disappear;
    }

    /**
     * used when an arrow hit an enemy
     * after hit the enemy, the arrow
     * disappear
     */
    public void setDisappear(int disappear) {
        this.disappear = disappear;
    }

    /**
     * Dest the position of the closet enemy
     * Dest is used in setting direction and
     * the rotation angle
     * @param Dest
     */
    public Arrow(Point Dest){
        //Point Dest = new Point(enemyX, enemyY);
        setDirectionTo(Dest);
        double radians = Math.atan2(Dest.y - y, Dest.x - x);
        option.setRotation(radians);
    }

    private void setDirectionTo(Point Dest){
        double Len = new Point(x, y).distanceTo(Dest);
        directionX = (Dest.x - x)/Len;
        directionY = (Dest.y - y)/Len;
    }

    /**
     * the arrow moves with setted direction
     */
    public void update(){
        ballImage.draw(x, y, option);
        x += STEP_SIZE * directionX;
        y += STEP_SIZE * directionY;
    }

    /**
     * this function is used in level class
     * to check the distance between arrow and
     * enemy
     *
     */
    public Point getPosition(){
        return new Point(x, y);
    }

}






















