import bagel.*;

public class AccuracyMessage {
    private static final double x = Window.getWidth()/2.0;
    private static final double y = Window.getHeight()/2.0;
    private static final int messageSize = 40;
    //the maximum number of frame a score accuracy exits
    private static final int maxDuration = 30;
    private String accuracy;
    private final int scoreChange;
    private final Font message = new Font("res/FSO8BITR.TTF", messageSize);

    private static final int PREFECT_SCORE = 10;
    private static final int GOOD_SCORE = 5;
    private static final int BAD_SCORE = -1;
    private static final int MISS_SCORE = -5;
    private final static int SPECSCORE = 15;
    //the current number of frame rendered
    private int duration = 0;

    /**
     * create class when note other than
     * special note is pressed
     * @param scoreChange
     */
    public AccuracyMessage(int scoreChange){
        this.scoreChange = scoreChange;
        if (scoreChange == PREFECT_SCORE) {
            accuracy = "PERFECT";
        } else if (scoreChange == GOOD_SCORE) {
            accuracy = "GOOD";
        } else if (scoreChange == BAD_SCORE) {
            accuracy = "BAD";
        } else if (scoreChange == MISS_SCORE) {
            accuracy = "MISS";
        } else if (scoreChange == SPECSCORE) {
            //accuracy = ""
        }

    }

    /**
     * this constructor is used when the message is about
     * special note
     * @param type
     */
    public AccuracyMessage(String type){
        scoreChange = SPECSCORE;
        if(type.equals("Bomb")){
            accuracy = "Lane Clear";
        }else if(type.equals("SpeedUp")){
            accuracy = "Speed Up";
        }else if(type.equals("SlowDown")){
            accuracy = "Slow Down";
        }else if(type.equals("DoubleScore")){
            accuracy = "Double Score";
        }
    }

    /**
     * draw the message in the middle of the screen
     */
    public void drawMessage(){
        if(scoreChange != 0) {
            if (duration <= maxDuration) {
                message.drawString(accuracy, x - message.getWidth(accuracy)/ 2, y);
            }
            duration++;
        }

    }

}
