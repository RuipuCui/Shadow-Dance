import bagel.*;
import bagel.util.Point;
import java.lang.Math;

public class Note implements moveable{
    private static final double NORMAL_NOTE_START_HEIGHT = 100;
    private static final double HOLE_NOTE_START_HEIGHT = 24;
    private final String noteDirection;
    private final String noteType;
    private final double frameNum;
    private Image noteImage;
    private int scoreFactor = 1;
    public int getScoreFactor(){
        return scoreFactor;
    }
    public void setScoreFactor(int factor){
        scoreFactor = factor;
    }

    /**
     * used in level class when speedup or slowdown
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private double y;
    private double x;
    private int speed = 2;
    private int disappear = 0;
    private int starRun = 0;
    private int islowest = 0;
    private double distance;

    //this variable is to record whether a note being pressed
    private int isPressed = 0;

    /**
     * create the note and setted up the
     * image due to its type and direction
     * @param noteDirection
     * @param noteType
     * @param frameNum
     */
    public Note(String noteDirection, String noteType, double frameNum){
        this.noteDirection = noteDirection;
        this.noteType= noteType;
        this.frameNum = frameNum;

        if(noteDirection.equals("Left") && noteType.equals("Normal")){
            noteImage = new Image("res/noteLeft.png");
        }else if(noteDirection.equals("Right") && noteType.equals("Normal")){
            noteImage = new Image("res/noteRight.png");
        }else if(noteDirection.equals("Up") && noteType.equals("Normal")){
            noteImage = new Image("res/noteUp.png");
        }else if(noteDirection.equals("Down") && noteType.equals("Normal")){
            noteImage = new Image("res/noteDown.png");
        }else if(noteDirection.equals("Left") && noteType.equals("Hold")){
            noteImage = new Image("res/holdNoteLeft.png");
        }else if(noteDirection.equals("Right") && noteType.equals("Hold")){
            noteImage = new Image("res/holdNoteRight.png");
        }else if(noteDirection.equals("Up") && noteType.equals("Hold")){
            noteImage = new Image("res/holdNoteUp.png");
        }else if(noteDirection.equals("Down") && noteType.equals("Hold")){
            noteImage = new Image("res/holdNoteDown.png");
        }else if(noteType.equals("SpeedUp")){
            noteImage = new Image("res/noteSpeedUp.png");
        }else if(noteType.equals("SlowDown")){
            noteImage = new Image("res/noteSlowDown.PNG");
        }else if(noteType.equals("Bomb")){
            noteImage = new Image("res/noteBomb.PNG");
        }else if(noteType.equals("DoubleScore")){
            noteImage = new Image("res/note2x.PNG");
        }
    }

    /**
     * return the direction of the note
     * @return noteDirection
     */
    public String getNoteDirection(){
        return noteDirection;
    }

    /**
     * framenum of the note is the start frame
     * of a note
     * @return frameNum
     */
    public double getFrameNum(){
        return frameNum;
    }

    protected Image getNoteImage(){
        return noteImage;
    }

    /**
     * get the y position
     * @return y
     */
    public double getY(){
        return y;
    }

    /**
     * get the x position
     * @return x
     */
    public double getX(){
        return x;
    }

    /**
     * get whether the note is disappeared or note
     * @return disappear
     */
    public int getDisappear(){
        return disappear;
    }

    /**
     * get the type of the note
     * @return noteTyoe
     */
    public String getNoteType(){
        return noteType;
    }

    /**
     * used in calculating the score of normal note
     * as the normal note can score twice
     * @return isPressed
     */
    public int getIsPressed(){
        return isPressed;
    }

    /**
     * get the speed
     * @return speed
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * set the start y position
     * @param x
     */
    public void setcoordinate(double x){
        this.x = x;
        if(noteType.equals("Normal") || noteDirection.equals("Special")){
            y = NORMAL_NOTE_START_HEIGHT;
        }

        if(noteType.equals("Hold")){
            y = HOLE_NOTE_START_HEIGHT;
        }

    }

    /**
     * get the position of the note
     * used to check whether the note is stoolen by tge enemy
     * @return Point
     */
    public Point getPosition(){
        return new Point(x, y);
    }

    protected void setPressed(int n){
        isPressed = n;
    }

    /**
     * set the note to disappear
     * @param n
     */
    public void setDisappear(int n){
        disappear = n;
    }

    protected void setY(double y){
        this.y = y;
    }

    public int update(int score){
        return score;
    }

    public int calScore(int score){
        return score;
    }

    protected void setStarRun() {
        this.starRun = 1;
    }

    /**
     * starRun is used when a bomb note is pressed
     * all note that in the same lane and start to run
     * are disappear
     * @return
     */
    public int getStarRun() {
        return starRun;
    }
}












