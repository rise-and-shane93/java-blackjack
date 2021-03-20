public interface Participant {
    int MAX_POINTS = 21;
    int CARDS_TO_START = 2;

    void drawCard(int cardsToDraw, boolean firstDraw);
    void getHandValue();
}
