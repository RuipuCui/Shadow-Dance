import bagel.Image;
import bagel.Window;

public class HoldNote extends Note implements scoreable{

    /**
     * create the holdnote with these 3 attrubites
     * @param noteDirection
     * @param noteType
     * @param frameNum
     */
    public HoldNote(String noteDirection, String noteType, double frameNum){
        super(noteDirection, noteType, frameNum);
    }

    public int update(int score){
        int score_ = score;
        double y = super.getY();
        double x = super.getX();
        int speed = super.getSpeed();
        int disappear = super.getDisappear();
        int isPressed = super.getIsPressed();
        Image noteImage = super.getNoteImage();
        if (y < Window.getHeight() && disappear == 0) {
            noteImage.draw(x, y);
            super.setStarRun();
            super.setY(y+speed);
        }

        //miss when the note fall off the window
        if(y >= Window.getHeight()){
            if(isPressed == 0 && disappear == 0){
                score_ += MISS_SCORE * super.getScoreFactor();
            }

            super.setDisappear(1);
            super.setPressed(1);
        }

        return score_;
    }

    public int calScore(int score){
        int score_ = score;
        double y = super.getY();
        int isPressed = super.getIsPressed();
        double distance;
        if(isPressed == 0){
            distance = Math.abs(y + MID_END_DISTANCE - STATIONARY_NOTE_HEIGHT);
        } else {
            distance = Math.abs(y - MID_END_DISTANCE - STATIONARY_NOTE_HEIGHT);
        }

        if(distance <= PREFECT_BOUNDARY){
            score_ = score_ + PREFECT_SCORE * super.getScoreFactor();
        }else if(distance > PREFECT_BOUNDARY && distance <= GOOD_BOUNDARY){
            score_ = score_ + GOOD_SCORE * super.getScoreFactor();
        }else if(distance > GOOD_BOUNDARY && distance <= BAD_BOUNDARY){
            score_ = score_ + BAD_SCORE * super.getScoreFactor();
        }else if(distance > BAD_BOUNDARY && distance <= MISS_BOUNDARY){
            score_ = score_ + MISS_SCORE * super.getScoreFactor();
        }

        //if the distance is bigger than 200, nothing will happen
        if(distance <= MISS_BOUNDARY){

            if(isPressed == 1){
                super.setDisappear(1);
            }
            super.setPressed((isPressed+1));

        }

        return score_;
    }

}
