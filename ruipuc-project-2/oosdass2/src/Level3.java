import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Level3 extends Level1{
    private final static double STATIONARY_NOTE_HEIGHT = 657;
    private int maxChangeFrame;
    private final static int DURATION = 480;
    private final static int SPECSCORE = 15;
    private String spcType = "Default";
    Guardian guardian = new Guardian();
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private ArrayList<Arrow> arrowList = new ArrayList<Arrow>();
    private static final int SHOOT_RANGE = 62;
    private static final int STEAL_RANGE = 104;

    /**
     * create the level3 class
     */
    public Level3(){
        super();
    }

    @Override
    protected void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/test3.csv"))) {
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

    @Override
    protected void elementInsert(String name, String type, double data){
        if(name.equals("Lane")){
            Lane lane = new Lane(type, data);
            super.getLaneArray().add(lane);
            switch (type) {
                case "Left":
                    super.setLeftLaneX(data);
                    break;
                case "Special":
                    super.setSpcLaneX(data);
                    break;
                case "Right":
                    super.setRightLaneX(data);
                    break;
            }
        }else{
            Note note;
            if(type.equals("Normal")) {
                note = new NormalNote(name, type, data);
                super.getNoteArray().add(note);
            }
            else if(type.equals("Hold")){
                note = new HoldNote(name, type, data);
                super.getNoteArray().add(note);;
            }
            else if(name.equals("Special")){
                note = new SpecNote(name, type, data);
                super.getNoteArray().add(note);
            }
            else if(type.equals("Bomb")){
                note = new SpecNote(name, type, data);
                super.getNoteArray().add(note);
            }

        }
    }

    protected void noteCoordinateSet(){
        for(Note c : super.getNoteArray()){
            switch (c.getNoteDirection()) {
                case "Left":
                    c.setcoordinate(super.getLeftLaneX());
                    break;
                case "Right":
                    c.setcoordinate(super.getRightLaneX());
                    break;
                case "Special":
                    c.setcoordinate(super.getSpcLaneX());
                    break;
            }
        }
    }

    private int getClosestEnemy(){
        //double Len = new Point(x, y).distanceTo(Dest);
        int i = 0;
        int closest = 0;
        double distance = enemyList.get(0).getPosition().distanceTo(guardian.getPosition());
        for(Enemy e : enemyList){
            if(e.getPosition().distanceTo(guardian.getPosition()) < distance){
                distance = e.getPosition().distanceTo(guardian.getPosition());
                closest = i;
            }
            i++;
        }
        return closest;
    }

    public void startRun(Input input){

        guardian.draw();

        //draw score message
        super.setPrevScore(super.getScore());
        String strScore = Integer.toString(super.getScore());
        super.getScoreMessage().drawString("SCORE "+strScore, 35, 35);

        //draw lane
        for(Lane c : super.getLaneArray()){
            c.getLaneImage().draw(c.getX(), super.getLaneY());
        }

        //draw note
        for(Note c : super.getNoteArray()){
            if(c.getFrameNum() <= super.getNumFrame()){
                //score = c.update(score);
                super.setScore(c.update(super.getScore()));
            }
        }

        //draw enemy
        if(super.getNumFrame() % 600 == 0 && super.getNumFrame() != 0){
            Enemy enemy = new Enemy();
            enemyList.add(enemy);
        }
        for(Enemy e : enemyList){
            e.update();
        }

        //draw arrow
        if(input.wasPressed(Keys.LEFT_SHIFT)){
            Arrow arrow;
            if(!enemyList.isEmpty()){
                arrow = new Arrow(enemyList.get(getClosestEnemy()).getPosition());
                arrowList.add(arrow);
            }
        }
        for(Arrow a : arrowList){
            a.update();
        }

        //shoot enemy
        for(Enemy e : enemyList){
            for(Arrow a : arrowList){
                double distance = e.getPosition().distanceTo(a.getPosition());
                if(distance <= SHOOT_RANGE){
                    e.setDisappear(1);
                    a.setDisappear(1);
                }
            }
        }
        enemyList.removeIf(e -> e.getDisappear() == 1);
        arrowList.removeIf(a -> a.getDisappear() == 1);

        //steal note
        for(Enemy e : enemyList){
            for(Note n : getNoteArray()){
                if(n.getNoteType().equals("Normal") && n.getStarRun() == 1){
                    double distance = e.getPosition().distanceTo(n.getPosition());
                    if(distance <= STEAL_RANGE){
                        n.setDisappear(1);
                    }
                }
            }
        }

        //find the lowest note each lane
        Note lowestLeftKey = super.getNoteArray().get(getLowestNote("Left"));
        Note lowestRightKey = super.getNoteArray().get(getLowestNote("Right"));
        Note lowestSpecKey = super.getNoteArray().get(getLowestNote("Special"));

        //after a key is pressed and the lowest note is normal
        if(input.wasPressed(Keys.LEFT) && lowestLeftKey.getIsPressed() == 0){
            if(lowestLeftKey.getNoteType().equals("Bomb") && Math.abs(lowestLeftKey.getY() - STATIONARY_NOTE_HEIGHT) <= 50){
                bomb(lowestLeftKey.getNoteDirection());
                spcType = "Bomb";
                super.setSpecAccMessage(spcType);
            }
            else {
                super.setScore(lowestLeftKey.calScore(super.getScore()));
            }

        }else if(input.wasPressed(Keys.RIGHT) && lowestRightKey.getIsPressed() == 0){
            if(lowestRightKey.getNoteType().equals("Bomb") && Math.abs(lowestRightKey.getY() - STATIONARY_NOTE_HEIGHT) <= 50){
                bomb(lowestRightKey.getNoteDirection());
                spcType = "Bomb";
                super.setSpecAccMessage(spcType);
            }
            else {
                super.setScore(lowestRightKey.calScore(super.getScore()));
            }

        }else if(input.wasPressed(Keys.SPACE) && lowestSpecKey.getIsPressed() == 0){
            //super.setScore(lowestLeftKey.calScore(super.getScore()));
            if(Math.abs(lowestSpecKey.getY() - STATIONARY_NOTE_HEIGHT) <= 50) {
                if (lowestSpecKey.getNoteType().equals("Bomb")) {
                    bomb(lowestSpecKey.getNoteDirection());
                    spcType = "Bomb";
                }else if(lowestSpecKey.getNoteType().equals("SpeedUp")){
                    for(Note c : super.getNoteArray()){
                        c.setSpeed(c.getSpeed() + 1);
                    }
                    spcType = "SpeedUp";
                    super.setScore(super.getScore() + SPECSCORE * lowestSpecKey.getScoreFactor());
                }else if(lowestSpecKey.getNoteType().equals("SlowDown")){
                    for(Note c : super.getNoteArray()){
                        c.setSpeed(c.getSpeed() - 1);
                    }
                    spcType = "SlowDown";
                    super.setScore(super.getScore() + SPECSCORE * lowestSpecKey.getScoreFactor());
                }else if(lowestSpecKey.getNoteType().equals("DoubleScore")){
                    for(Note c : super.getNoteArray()){
                        c.setScoreFactor(c.getScoreFactor()*2);
                    }
                    spcType = "DoubleScore";
                    super.setScore(super.getScore() + SPECSCORE * lowestSpecKey.getScoreFactor());
                    maxChangeFrame = super.getNumFrame() + DURATION;
                }
                super.setSpecAccMessage(spcType);
                lowestSpecKey.setDisappear(1);
            }
        }

        if(input.wasReleased(Keys.LEFT) && lowestLeftKey.getNoteType().equals("Hold") && lowestLeftKey.getIsPressed() == 1){
            super.setScore(lowestLeftKey.calScore(super.getScore()));
        }else if(input.wasReleased(Keys.RIGHT) && lowestRightKey.getNoteType().equals("Hold") && lowestRightKey.getIsPressed() == 1){
            super.setScore(lowestRightKey.calScore(super.getScore()));
        }

        //draw the score accuracy message
        if(super.getScore() != super.getPrevScore() && spcType.equals("Default")){
            //currAccuracy = score - prevScore;
            super.setCurrAccuracy((super.getScore() - super.getPrevScore()) / lowestLeftKey.getScoreFactor());
            super.setAccMessage();
        }
        super.getAccMessage().drawMessage();

        if(super.getNumFrame() == maxChangeFrame){
            for(Note c : super.getNoteArray()){
                c.setScoreFactor(1);
            }
        }

        spcType = "Default";
        super.setNumFrame();

    }

    private void bomb(String Direction){
        for(Note c : getNoteArray()){
            if(c.getNoteDirection().equals(Direction) && c.getStarRun() == 1){
                c.setDisappear(1);
            }
        }
    }
}
