public class Dealer implements Participant {

    public int dealerHandValue;
    public Card[] currentHand;
    private boolean hasAce;
    private boolean hasAceHighValue;
    
    public void drawCard(int cardsToDraw, boolean firstDraw) {
        if (firstDraw) {
            this.currentHand = new Card[cardsToDraw];
            // Card[] startingCards = {new Card(), new Card()};
    
            for (int i = 0; i < cardsToDraw; i++) {
                this.currentHand[i] = new Card();

                if (i == 0 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    this.currentHand[i].cardValue = 11;
                    this.hasAce = true;
                    this.hasAceHighValue = true;
                    this.dealerHandValue += this.currentHand[i].cardValue;
                } else if (i == 1 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    if (this.dealerHandValue + 11 > 21) {
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
        } else {
            Card cardToDraw = new Card();
            int numCardsInCurrentHand = currentHand.length;
            Card[] updatedHand = new Card[numCardsInCurrentHand + 1];

            for (int i = 0; i < updatedHand.length; i++) {
                if (i < numCardsInCurrentHand) {
                    updatedHand[i] = currentHand[i];
                } else {

                    if (cardToDraw.getCardAlphaNumValue() == "A") {
                        // System.out.println("you drew an ace!");
                        this.hasAce = true;
                        if (this.dealerHandValue + 11 > 21) {
                            // System.out.println("ace low value");
                            cardToDraw.cardValue = 1;
                        } else {
                            // System.out.println("ace high value");
                            cardToDraw.cardValue = 11;
                            this.hasAceHighValue = true;
                        }
                    }
                    updatedHand[i] = cardToDraw;
                }
            }

            this.currentHand = updatedHand;

            System.out.print("\nDealer's hand: ");
            this.dealerHandValue = 0;
            for (int j = 0; j < updatedHand.length; j++) {
                System.out.print(updatedHand[j].getCardName() + " ");
                this.dealerHandValue += updatedHand[j].getCardValue();
            }
            System.out.println();

            // convert ace from 11 to 1 if player would otherwise bust
            if (this.dealerHandValue > 21 && this.hasAceHighValue) {
                // System.out.println("has ace so take off 10 points");
                this.dealerHandValue -= 10;
                this.hasAce = false;
            } /*else {
                This was for checking that the player did not draw an ace
                System.out.println("no ace");
            }*/
            
        }
        getHandValue();
    }

    public void showHandStart(Card[] hand) {
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

    public void getHandValue() {
        System.out.println(this.dealerHandValue);
    }

    public void showEntireHand(Card[] hand) {
        System.out.print("\nDealer's hand: ");
        for (int i = 0; i < hand.length; i++) {
            System.out.print(this.currentHand[i].getCardName() + " ");
        }
        System.out.println();
    }
}