public class Player implements Participant {

    private int playerMoneyStart;
    public int playerHandValue;
    private Card[] currentHand;
    private boolean playerWinOrLose;
    private boolean hasAce;
    private boolean hasAceHighValue;

    public Player(int playerMoneyStart) {
        this.playerMoneyStart = playerMoneyStart;
    }

    public void drawCard(int cardsToDraw, boolean firstDraw) {
        if (firstDraw) {
            this.currentHand = new Card[cardsToDraw];
    
            for (int i = 0; i < cardsToDraw; i++) {
                this.currentHand[i] = new Card();
                // if (this.currentHand[i].getCard())
                // this.currentHand[i].getCardAlphaNumValue();
            }
    
            System.out.print("\nYour hand: ");
            for (int i = 0; i < cardsToDraw; i++) {
                System.out.print(this.currentHand[i].getCardName() + " ");

                if (i == 0 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    this.currentHand[i].cardValue = 11;
                    this.hasAce = true;
                    this.hasAceHighValue = true;
                    this.playerHandValue += this.currentHand[i].cardValue;
                } else if (i == 1 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    if (this.playerHandValue + 11 > 21) {
                        this.currentHand[i].cardValue = 1;
//                        this.playerHandValue += 1;
                    } else {
                        this.currentHand[i].cardValue = 11;
//                        this.playerHandValue += 11;
                        this.hasAceHighValue = true;
                    }
                    this.hasAce = true;
                    this.playerHandValue += this.currentHand[i].cardValue;
                } else {
                    this.playerHandValue += this.currentHand[i].getCardValue();
                }
            }
            System.out.println();
        } else {
            Card cardToDraw = new Card();
            int numCardsInCurrentHand = currentHand.length;
            Card[] updatedHand = new Card[numCardsInCurrentHand + 1];

            for (int i = 0; i < updatedHand.length; i++) {
                if (i < numCardsInCurrentHand) {
                    updatedHand[i] = currentHand[i];
                } else {

                    if (cardToDraw.getCardAlphaNumValue() == "A") {
                        System.out.println("you drew an ace!");
                        this.hasAce = true;
                        if (this.playerHandValue + 11 > 21) {
                            System.out.println("ace low value");
                            cardToDraw.cardValue = 1;
                        } else {
                            System.out.println("ace high value");
                            cardToDraw.cardValue = 11;
                            this.hasAceHighValue = true;
                        }
                    }
                    updatedHand[i] = cardToDraw;
                }
            }

            this.currentHand = updatedHand;

            System.out.print("\nYour hand: ");
            this.playerHandValue = 0;
            for (int j = 0; j < updatedHand.length; j++) {
                System.out.print(updatedHand[j].getCardName() + " ");
                this.playerHandValue += updatedHand[j].getCardValue();
            }

            // convert ace from 11 to 1 if player would otherwise bust
            if (this.playerHandValue > 21 && this.hasAceHighValue) {
                System.out.println("has ace so take off 10 points");
                this.playerHandValue -= 10;
                this.hasAce = false;
            } else {
                System.out.println("no ace");
            }
            
        }
        // checkIfWinLose(this.playerHandValue);
        System.out.println("\n" + this.playerHandValue);
    }

    public void getHandValue() {
        System.out.println(this.playerHandValue);
    }

    public void displayStartingMoney() {
        System.out.println(playerMoneyStart);
    }

    public boolean checkIfWinLose(int handValue) {
        if (handValue == 21) {
            System.out.println("You win!!!");
            this.playerWinOrLose = true;
        } else if (handValue > 21) {
            System.out.println("Sorry, you lose.");
            this.playerWinOrLose = false;
        }
        return this.playerWinOrLose;
    }
}