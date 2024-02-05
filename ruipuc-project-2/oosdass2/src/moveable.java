public interface moveable {
    double STATIONARY_NOTE_HEIGHT = 657;

    /**
     * run the note and check whether it out of the screen
     * with no pressed. if so, the note considers a miss
     * @param score
     * @return
     */
    public int update(int score);
}
