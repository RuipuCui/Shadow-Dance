import bagel.*;
import bagel.util.Point;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * Ruipu Cui
 */
public class ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final Font fontTitle = new Font("res/FSO8BITR.TTF",TITLE_SIZE);
    private final Font fontMessage = new Font("res/FSO8BITR.TTF",START_MESSAGE_SIZE);
    private static final Point TITLE = new Point(220, 250);
    private static final int LEVEL1_WIN_SCORE = 150;
    private static final int LEVEL2_WIN_SCORE = 400;
    private static final int LEVEL3_WIN_SCORE = 350;
    //the size of the message at the end of game
    private static final int END_MESSAGE_SIZE = 64;
    //the size of message at before start of the game
    private static final int START_MESSAGE_SIZE = 24;
    private static final int TITLE_SIZE = 64;
    private final int endMessageY = 300;
    private final int endMessageBotY = 500;
    private final int getEndMessageBotX = 175;
    private int pressed = 0;
    Level1 level1 = new Level1();
    Level2 level2 = new Level2();
    Level3 level3 = new Level3();
    private int currLevel;
    private int score;
    private int gameEnd = 0;

    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    // check whether the game ends
    // the game ends when all notes are disappeared
    private void gameEnd(int level){
        ArrayList<Note> NoteArray;
        if(level == 1) {
            NoteArray = level1.getNoteArray();
        }else if(level == 2){
            NoteArray = level2.getNoteArray();
        }else{
            NoteArray = level3.getNoteArray();
        }
        for(Note c : NoteArray){
            if(c.getDisappear() == 0){
                return;
            }
        }
        gameEnd = 1;
    }

    //check whether the player win or lose
    private int winCondition(int level){
        if(level == 1){
            if(level1.getScore() >= LEVEL1_WIN_SCORE){
                return 1;
            }
        }else if(level == 2){
            if(level2.getScore() >= LEVEL2_WIN_SCORE){
                return 1;
            }
        }else if(level == 3){
            if(level3.getScore() >= LEVEL3_WIN_SCORE){
                return 1;
            }
        }
        return 0;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        if(gameEnd == 1){
            Font endMessage = new Font("res/FSO8BITR.TTF",END_MESSAGE_SIZE);
            Font endMessageBot = new Font("res/FSO8BITR.TTF",START_MESSAGE_SIZE);
            if(winCondition(currLevel) == 1){
                endMessage.drawString("CLEAR", Window.getWidth()/2.0 - endMessage.getWidth("CLEAR")/ 2,
                        endMessageY);
                endMessageBot.drawString("PRESS SPACE TO RETURN TO LEVEL SELECTION", getEndMessageBotX, endMessageBotY);
            }else{
                endMessage.drawString("TRY AGAIN", Window.getWidth()/2.0 - endMessage.getWidth("TRY AGAIN")/ 2,
                        endMessageY);
                endMessageBot.drawString("PRESS SPACE TO RETURN TO LEVEL SELECTION", getEndMessageBotX, endMessageBotY);
            }
            if(input.wasPressed(Keys.SPACE)){
                pressed = 0;
                gameEnd = 0;
                level1 = new Level1();
                level2 = new Level2();
                level3 = new Level3();
            }
        }else if(input.wasPressed(Keys.NUM_1) || pressed == 1){
            level1.startRun(input);
            pressed = 1;
            currLevel = 1;
            gameEnd(1);
        }else if(input.wasPressed(Keys.NUM_2) || pressed == 2) {
            level2.startRun(input);
            pressed = 2;
            currLevel = 2;
            gameEnd(2);
        }else if(input.wasPressed(Keys.NUM_3) || pressed == 3) {
            level3.startRun(input);
            pressed = 3;
            currLevel = 3;
            gameEnd(3);
        }else if(pressed == 0) {
            fontTitle.drawString("SHADOW DANCE", TITLE.x, TITLE.y);
            fontMessage.drawString(" SELECT LEVELS WITH ", TITLE.x + 100, TITLE.y + 190);
            fontMessage.drawString(" NUMBER KEYS        ", TITLE.x + 100, TITLE.y + 214);
            fontMessage.drawString("      1       2       3     ", TITLE.x + 100, TITLE.y + 262);
        }

    }
}
