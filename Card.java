/**
 * The Card class is responsible for generating a random number (from 2-10) card of face card (J, Q, K & A).
 * In addition, it assigns a random symbol for aesthetics. This class does not assign a value to the ace card
 * because of blackjack rules around aces.
 * 
 * @author Shane Harper
 * @version 1.0
 */

public class Card {

    private String cardName;
    public int cardValue;
    private String cardSymbol;
    private String alphaNumCardValue;
    private int cardRandomNum;

    private static String[] cardArr = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static String[] symbols = {"♥", "◆", "♠", "♣"};
    
    public Card() {
        this.cardRandomNum = (int) ( cardArr.length * Math.random() );
        this.cardValue = setCardValue();
        this.cardSymbol = setCardSymbol();
        this.cardName = setCardName();
    }

    /**
     * This method will set the value of the card that either the player or dealer drew. It uses the object's
     * random number to select a random element from the cardArr array. All cards except the ace get a value
     * as the ace has special rules.
     * 
     * @return int that represents the number value of the card
     */
    public int setCardValue() {

        // get the random character in the cardArr array
        String stringArrElement = cardArr[cardRandomNum];
        int result = 0;

        switch (stringArrElement) {

            // parse the character as a number if it's a number card (2-10)
            case "2", "3", "4", "5", "6", "7", "8", "9", "10":
                result = Integer.parseInt(stringArrElement);
                break;
            
            // assign the integer 10 if it's a face card
            case "J", "Q", "K":
                result = 10;
                break;
            
            // do nothing. The drawCard method in the Dealer and Player classes will assign the value
            case "A":                
                break;
        }
        return result;
    }

    /**
     * This method creates the card name displayed to the user. It combines the card's number value and
     * the symbol, both of which are randomly selected. For example: "5 ♣"
     * 
     * @return String that is the combination of the number value and the card's symbol
     */
    public String setCardName() {
        return cardArr[cardRandomNum] + " " + this.cardSymbol;
    }

    /**
     * This method randomly selects the card symbol and is assigned to the card object's cardSymbol variable.
     * 
     * @return String that randomly selects one of the four card symbols
     */
    public String setCardSymbol() {
        int randomNumberSymbol = (int) ( symbols.length * Math.random() );

        return symbols[randomNumberSymbol];
    }

    /**
     * This method returns the card name.
     * 
     * @return String that is the card value and the symbol
     */
    public String getCardName() {
        return this.cardName;
    }

    /**
     * This method returns a random number/face/ace card from the card array.
     * 
     * @return String that represents the number or letter of the card (2-10, a face card or an ace)
     */
    public String getCardAlphaNumValue() {
        return cardArr[cardRandomNum];
    }

    /**
     * This method returns the card symbol.
     * 
     * @return String that represents the card symbol
     */
    public String getCardSymbol() {
        return this.cardSymbol;
    }

    /**
     * This method returns the card's numeric value
     * 
     * @return int that represents the card's value
     */
    public int getCardValue() {
        return this.cardValue;
    }
}