import bagel.Image;
import bagel.util.Point;

public class Guardian {
    private final double x = 800;
    private final double y = 600;
    private final Image guardianImage = new Image("res/guardian.PNG");

    /**
     * create the guardian class
     */
    public Guardian(){}

    /**
     * draw the image
     */
    public void draw(){
        guardianImage.draw(x, y);
    }

    /**
     * the arrow aims to the closest enemy
     * the x and y are the start point of arrow
     * used in level class to fina which enemy is the closest
     * @return
     */
    public Point getPosition() {
        return new Point(x, y);
    }
}
