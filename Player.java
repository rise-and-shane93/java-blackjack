/**
 * The Player class implements the Participant interface but adds methods unique to the class (ex: checking if the player won
 * at the beginning of the game with only two cards). The class uses the constructor to only assign the amount of money a player
 * has to start with. Finally, the class uses logic to assign the correct value of an ace as per blackjack rules.
 * 
 * @author Shane Harper
 * @version 1.0
 */

public class Player implements Participant {

    public int playerMoney;
    public int playerHandValue;
    private Card[] currentHand;
    private boolean playerWinOrLose;
    private boolean hasAce;
    private boolean hasAceHighValue;
    public boolean outOfMoney;

    public Player(int playerMoneyStart) {
        this.playerMoney = playerMoneyStart;
    }

    /**
     * This method handles the actual drawing and displaying of cards as well as adding each card's value to the
     * player's points. The method's behavior is determined of whether the player draws the first two cards or
     * drawing one card after the start of the round. In addition, the method has conditional statements to handle
     * the value of an ace if the player drew that card.
     * 
     * @param cardsToDraw
     * @param firstDraw
     * @return void
     */
    public void drawCard(int cardsToDraw, boolean firstDraw) {

        // if the player is drawing their first two cards
        if (firstDraw) {

            // initialize the Card arraw and add two cards to it
            this.currentHand = new Card[cardsToDraw];
    
            for (int i = 0; i < cardsToDraw; i++) {
                this.currentHand[i] = new Card();
            }
    
            // Display the player's hand and add each card's value to the playerHandValue variable
            System.out.print("\nYour hand: ");
            for (int i = 0; i < cardsToDraw; i++) {
                System.out.print(this.currentHand[i].getCardName() + " ");

                /* the rules around aces in Blackjack. This code block is 
                responsible for assigning point values to the card object that is an ace. */
                if (i == 0 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    this.currentHand[i].cardValue = 11;
                    this.hasAce = true;
                    this.hasAceHighValue = true;
                    this.playerHandValue += this.currentHand[i].cardValue;
                } else if (i == 1 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    if (this.playerHandValue + 11 > Participant.MAX_POINTS) {
                        this.currentHand[i].cardValue = 1;
                    } else {
                        this.currentHand[i].cardValue = 11;
                        this.hasAceHighValue = true;
                    }
                    this.hasAce = true;
                    this.playerHandValue += this.currentHand[i].cardValue;
                } else {
                    this.playerHandValue += this.currentHand[i].getCardValue();
                }
            }
            System.out.println();
        
        // the player is drawing one card
        } else {

            // create a temporary array that is the length of the current card array plus one
            Card cardToDraw = new Card();
            int numCardsInCurrentHand = currentHand.length;
            Card[] updatedHand = new Card[numCardsInCurrentHand + cardsToDraw];

            // Add the new card to the last index and skip all of the other ones.
            for (int i = 0; i < updatedHand.length; i++) {
                if (i < numCardsInCurrentHand) {
                    updatedHand[i] = currentHand[i];
                } else {

                    /* the rules around aces in Blackjack. This code block is 
                    responsible for assigning point values to the card object that is an ace. */
                    if (cardToDraw.getCardAlphaNumValue() == "A") {
                        this.hasAce = true;
                        if (this.playerHandValue + 11 > Participant.MAX_POINTS) {
                            cardToDraw.cardValue = 1;
                        } else {
                            cardToDraw.cardValue = 11;
                            this.hasAceHighValue = true;
                        }
                    }
                    updatedHand[i] = cardToDraw;
                }
            }

            // replace the currentHand array with the updatedHand array
            this.currentHand = updatedHand;

            // display the updated hand, reset the point value and add each card's value
            System.out.print("\nYour hand: ");
            this.playerHandValue = 0;
            for (int j = 0; j < updatedHand.length; j++) {
                System.out.print(updatedHand[j].getCardName() + " ");
                this.playerHandValue += updatedHand[j].getCardValue();
            }
            System.out.println();

            // convert ace from 11 to 1 if player would otherwise bust
            if (this.playerHandValue > Participant.MAX_POINTS && this.hasAceHighValue) {
                this.playerHandValue -= 10;
                this.hasAce = false;
            } /*else {
                This was for checking that the player did not draw an ace
                System.out.println("no ace");
            }*/
            
        }
    }

    /**
     * This method was used throughout the program's development to print the player's point value. Inherited from 
     * the Participant interface.
     * 
     * @return void
     */
    public void getHandValue() {
        System.out.println(this.playerHandValue);
    }

    /**
     * This method is used to see if the player wins on the initial hand and returns a boolean to be used
     * before the hit/stay while loop in BlackjackGameSimulator.java
     * 
     * @param handValue
     * @return boolean if the player wins or loses on the initial hand
     */
    public boolean checkIfInitWin(int handValue) {
        if (handValue == Participant.MAX_POINTS) {
            this.playerWinOrLose = true;
        } else if (handValue > Participant.MAX_POINTS) {
            this.playerWinOrLose = false;
        } else {
            this.playerWinOrLose = false;
        }
        return this.playerWinOrLose;
    }
}