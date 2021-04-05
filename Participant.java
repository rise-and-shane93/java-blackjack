/**
 * The Participant interface is used to create a template of which the Dealer and Player class can implement.
 * It has two variables which are used in the BlackjackGameSimulator file and two methods which are defined in
 * the implementing classes.
 * 
 * @author Shane Harper
 * @version 1.0
 */

public interface Participant {
    int MAX_POINTS = 21;
    int CARDS_TO_START = 2;

    void drawCard(int cardsToDraw, boolean firstDraw);
    void getHandValue();
}
