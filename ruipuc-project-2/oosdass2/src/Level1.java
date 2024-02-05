import bagel.Font;
import bagel.*;
import bagel.util.Point;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Level1 implements runable{
    private final Font scoreMessage = new Font("res/FSO8BITR.TTF", SCORE_MESSAGE_SIZE);
    private final static double STATIONARY_NOTE_HEIGHT = 657;
    private static final int LANE_Y = 384;
    private static final int SCORE_MESSAGE_SIZE = 30;
    //count the number of lane
    private int i = 0;
    //count the number of note
    private int j = 0;
    private int numFrame = 0;

    public Font getScoreMessage() {
        return scoreMessage;
    }

    public void setCurrAccuracy(int currAccuracy) {
        this.currAccuracy = currAccuracy;
    }

    public int getCurrAccuracy() {
        return currAccuracy;
    }

    public AccuracyMessage getAccMessage(){
        return accMessage;
    }
    public void setAccMessage() {
        accMessage = new AccuracyMessage(currAccuracy);
    }
    public void setSpecAccMessage(String type){
        accMessage = new AccuracyMessage(type);
    }

    private double leftLaneX;
    private double upLaneX;
    private double downLaneX;
    private double rightLaneX;
    private double spcLaneX;
    private int score = 0;
    private int prevScore = 0;
    private int currAccuracy = 0;
    private final ArrayList<Lane> laneArray = new ArrayList<Lane>();
    private final ArrayList<Note> noteArray = new ArrayList<Note>();
    private AccuracyMessage accMessage = new AccuracyMessage(currAccuracy);
    private int speed = 2;

    /**
     * create the level class
     * read file and create lane arraylist and
     * note arraylist
     */
    public Level1(){
        readCSV();
        noteCoordinateSet();
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    protected void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/test1.csv"))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] element = text.split(",");
                String name = element[0];
                String type = element[1];
                double data = Double.parseDouble(element[2]);
                elementInsert(name, type, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //insert note and lane into array
    protected void elementInsert(String name, String type, double data){
        if(name.equals("Lane")){
            Lane lane = new Lane(type, data);
            laneArray.add(lane);
            switch (type) {
                case "Left":
                    leftLaneX = data;
                    break;
                case "Down":
                    downLaneX = data;
                    break;
                case "Up":
                    upLaneX = data;
                    break;
                case "Right":
                    rightLaneX = data;
                    break;
            }
            i++;
        }else{
            Note note;
            if(type.equals("Normal")) {
                note = new NormalNote(name, type, data);
                noteArray.add(note);
            }
            else if(type.equals("Hold")){
                note = new HoldNote(name, type, data);
                noteArray.add(note);
            }
            j++;
        }
    }

    protected void noteCoordinateSet(){
        for(Note c : noteArray){
            switch (c.getNoteDirection()) {
                case "Left":
                    c.setcoordinate(leftLaneX);
                    break;
                case "Right":
                    c.setcoordinate(rightLaneX);
                    break;
                case "Up":
                    c.setcoordinate(upLaneX);
                    break;
                case "Down":
                    c.setcoordinate(downLaneX);
                    break;
            }
        }
    }

    protected int getLowestNote(String type){
        int n = 0;
        double distance = STATIONARY_NOTE_HEIGHT;
        int lowest = 0;
        for(Note c : noteArray){
            if(c.getNoteDirection().equals(type) && c.getDisappear() == 0){
                if(Math.abs(c.getY() - STATIONARY_NOTE_HEIGHT) < distance){
                    lowest = n;
                    distance = Math.abs(c.getY() - STATIONARY_NOTE_HEIGHT);
                }
                //return n;
            }
            n++;
        }
        return lowest;
        //return n-1;
    }

    public void startRun(Input input){
        //draw score message
        prevScore = score;
        String strScore = Integer.toString(score);
        scoreMessage.drawString("SCORE "+strScore, 35, 35);

        //draw lane
        for(Lane c : laneArray){
            c.getLaneImage().draw(c.getX(), LANE_Y);
        }

        //draw note
        for(Note c : noteArray){
            if(c.getFrameNum() <= numFrame){
                score = c.update(score);
            }
        }

        //find the lowest note each lane
        Note lowestLeftKey = noteArray.get(getLowestNote("Left"));
        Note lowestRightKey = noteArray.get(getLowestNote("Right"));
        Note lowestUpKey = noteArray.get(getLowestNote("Up"));
        Note lowestDownKey = noteArray.get(getLowestNote("Down"));

        //after a key is pressed and the lowest note is normal
        if(input.wasPressed(Keys.LEFT) && lowestLeftKey.getIsPressed() == 0){
            score = lowestLeftKey.calScore(score);
        }else if(input.wasPressed(Keys.RIGHT) && lowestRightKey.getIsPressed() == 0){
            score = lowestRightKey.calScore(score);
        }else if(input.wasPressed(Keys.UP) && lowestUpKey.getIsPressed() == 0){
            score = lowestUpKey.calScore(score);
        }else if(input.wasPressed(Keys.DOWN) && lowestDownKey.getIsPressed() == 0){
            score = lowestDownKey.calScore(score);
        }

        //after a key is pressed and the lowest note is hold
        if(input.wasReleased(Keys.LEFT) && lowestLeftKey.getNoteType().equals("Hold") && lowestLeftKey.getIsPressed() == 1){
            score = lowestLeftKey.calScore(score);
        }else if(input.wasReleased(Keys.RIGHT) && lowestRightKey.getNoteType().equals("Hold") && lowestRightKey.getIsPressed() == 1){
            score = lowestRightKey.calScore(score);
        }else if(input.wasReleased(Keys.UP) && lowestUpKey.getNoteType().equals("Hold") && lowestUpKey.getIsPressed() == 1){
            score = lowestUpKey.calScore(score);
        }else if(input.wasReleased(Keys.DOWN) && lowestDownKey.getNoteType().equals("Hold") && lowestDownKey.getIsPressed() == 1){
            score = lowestDownKey.calScore(score);
        }

        //draw the score accuracy message
        if(score != prevScore){
            currAccuracy = score - prevScore;
            accMessage = new AccuracyMessage(currAccuracy);
        }

        accMessage.drawMessage();
        numFrame++;

    }

    /**
     * used in its child class
     * In ShadowDance class, used to check whether the game end
     * @return noteArray
     */
    public ArrayList<Note> getNoteArray(){
        return noteArray;
    }

    protected ArrayList<Lane> getLaneArray() { return laneArray; }

    /**
     * get the socre
     * in shadowdance class, the score is used to check the win condtion
     * @return score
     */
    public int getScore(){
        return score;
    }
    protected double getLaneY(){
        return LANE_Y;
    }

    protected int getNumFrame(){
        return numFrame;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    protected void setPrevScore(int prevScore) {
        this.prevScore = prevScore;
    }

    protected int getPrevScore() {
        return prevScore;
    }

    protected void setNumFrame(){
        numFrame += 1;
    }

    protected void setLeftLaneX(double leftLaneX) {
        this.leftLaneX = leftLaneX;
    }

    protected void setDownLaneX(double downLaneX) {
        this.downLaneX = downLaneX;
    }

    protected void setRightLaneX(double rightLaneX) {
        this.rightLaneX = rightLaneX;
    }

    protected void setSpcLaneX(double spcLaneX) {
        this.spcLaneX = spcLaneX;
    }

    protected double getLeftLaneX() {
        return leftLaneX;
    }

    protected double getDownLaneX() {
        return downLaneX;
    }

    protected double getRightLaneX() {
        return rightLaneX;
    }

    protected double getSpcLaneX() {
        return spcLaneX;
    }

}
