import bagel.Image;
import bagel.Window;

public class SpecNote extends Note{
    private final static int EXECUTE_RANGE = 50;
    public SpecNote(String noteDirection, String noteType, double frameNum){
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
            //y += speed;
            super.setY(y+speed);
        }

        //miss when the note fall off the window
        if(y >= Window.getHeight()){
            super.setDisappear(1);
            super.setPressed(1);
        }

        return score_;
    }

}




















