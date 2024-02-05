import bagel.*;
import bagel.util.Point;

public class Lane {
    private String laneType;
    private double x;
    private Image laneImage;

    /**
     * create the lane class and choose different
     * image with differeny type
     * @param laneType
     * @param x
     */
    public Lane(String laneType, double x){
        this.laneType = laneType;
        this.x = x;
        if(laneType.equals("Left")){
            laneImage = new Image("res/laneLeft.png");
        }else if(laneType.equals("Right")){
            laneImage = new Image("res/laneRight.png");
        }else if(laneType.equals("Up")){
            laneImage = new Image("res/laneUp.png");
        }else if(laneType.equals("Down")){
            laneImage = new Image("res/laneDown.png");
        }else if(laneType.equals("Special")){
            laneImage = new Image("res/laneSpecial.PNG");
        }
    }

    /**
     * used to draw the image in level class
     * @return
     */
    public Image getLaneImage(){
        return laneImage;
    }

    /**
     * used to draw the image in level class
     * the x is the position
     * @return
     */
    public double getX(){
        return x;
    }

}
