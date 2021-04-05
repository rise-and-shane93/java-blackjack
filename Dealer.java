/**
 * The Dealer class implements the Participant interface but adds methods unique to the class (ex: only showing one card).
 * It does not have a constructor method as the constructor method in the Player is used to initialize how much money
 * the player has. Finally, the class uses logic to assign the correct value of an ace as per blackjack rules.
 * 
 * @author Shane Harper
 * @version 1.0
 */

public class Dealer implements Participant {

    public int dealerHandValue;
    public Card[] currentHand;
    private boolean hasAce;
    private boolean hasAceHighValue;
    
    /**
     * This method handles the actual drawing and displaying of cards as well as adding each card's value to the
     * dealer's points. The method's behavior is determined of whether the dealer draws the first two cards or
     * drawing one card after the start of the round. In addition, the method has conditional statements to handle
     * the value of an ace if the dealer drew that card.
     * 
     * @param cardsToDraw
     * @param firstDraw
     * @return void
     */
    public void drawCard(int cardsToDraw, boolean firstDraw) {

        // if the dealer is drawing their first two cards
        if (firstDraw) {

            // initialize the Card arraw and add two cards to it
            this.currentHand = new Card[cardsToDraw];
    
            // assign random cards to the currentHand array and add the point values to the dealerHandValue variable
            for (int i = 0; i < cardsToDraw; i++) {
                this.currentHand[i] = new Card();

                /* the rules around aces in Blackjack. This code block is 
                responsible for assigning point values to the card object that is an ace. */
                if (i == 0 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    this.currentHand[i].cardValue = 11;
                    this.hasAce = true;
                    this.hasAceHighValue = true;
                    this.dealerHandValue += this.currentHand[i].cardValue;
                } else if (i == 1 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    if (this.dealerHandValue + 11 > Participant.MAX_POINTS) {
                        this.currentHand[i].cardValue = 1;
                    } else {
                        this.currentHand[i].cardValue = 11;
                        this.hasAceHighValue = true;
                    }
                    this.hasAce = true;
                    this.dealerHandValue += this.currentHand[i].cardValue;
                } else {
                    this.dealerHandValue += this.currentHand[i].getCardValue();
                }
            }

            showHandStart(this.currentHand);

        // the dealer is drawing one card
        } else {

            Card cardToDraw = new Card();
            int numCardsInCurrentHand = currentHand.length;

            // create a temporary array that is the length of the current card array plus one
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
                        if (this.dealerHandValue + 11 > Participant.MAX_POINTS) {
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

            // Displays the dealer's updated hand
            System.out.print("\nDealer's hand: ");
            this.dealerHandValue = 0;
            for (int j = 0; j < updatedHand.length; j++) {
                System.out.print(updatedHand[j].getCardName() + " ");
                this.dealerHandValue += updatedHand[j].getCardValue();
            }
            System.out.println();

            // convert ace from 11 to 1 if player would otherwise bust
            if (this.dealerHandValue > Participant.MAX_POINTS && this.hasAceHighValue) {
                this.dealerHandValue -= 10;
                this.hasAce = false;
            } /*else {
                This was for checking that the player did not draw an ace
                System.out.println("no ace");
            }*/
            
        }
    }

    /**
     * This method is responsible for showing the dealer's hand at the start of the game and before it's the 
     * dealer's turn. Since the dealer is dealt one card face down, there's a conditional statement in the for
     * loop to not display the first card's value.
     * 
     * @param hand The dealer's current hand of cards
     * @return void
     */
    public void showHandStart(Card[] hand) {

        // display only one card to the player
        System.out.print("\nDealer's hand: ");
        for (int i = 0; i < hand.length; i++) {
            if (i == 0) {
                System.out.print("XX ");
            } else {
                System.out.print(this.currentHand[i].getCardName() + " ");
            }
        }
        System.out.println();
    }

    /**
     * This method was used throughout the program's development to print the dealer's point value. Inherited from the
     * Participant interface.
     * 
     * @return void
     */
    public void getHandValue() {
        System.out.println(this.dealerHandValue);
    }

    /**
     * This method is invoked when it's the dealer's turn. As opposed to the showHandStart method, this
     * method will show the dealer's entire hand of cards.
     * 
     * @param hand The dealer's current hand of cards
     * @return void
     */
    public void showEntireHand(Card[] hand) {

        // display both of the dealer's cards to the player once the player stays
        System.out.print("\nDealer's hand: ");
        for (int i = 0; i < hand.length; i++) {
            System.out.print(this.currentHand[i].getCardName() + " ");
        }
        System.out.println();
    }
}