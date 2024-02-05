public interface scoreable {
    int PREFECT_SCORE = 10;
    int GOOD_SCORE = 5;
    int BAD_SCORE = -1;
    int MISS_SCORE = -5;
    int PREFECT_BOUNDARY = 15;
    int GOOD_BOUNDARY = 50;
    int BAD_BOUNDARY = 100;
    int MISS_BOUNDARY = 200;
    double MID_END_DISTANCE = 82;

    /**
     * calculate the score
     * if the note is successfully pressed(distance <= 200)
     * then set the note to disappear
     * @param score
     * @return
     */
    public int calScore(int score);
}
