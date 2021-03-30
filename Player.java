public class Player implements Participant {

    private int playerMoneyStart;
    public int playerHandValue;
    private Card[] currentHand;
    private boolean playerWinOrLose;
    private boolean hasAce;

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
                    playerHandValue += 11;
                    hasAce = true;
                } else if (i == 1 && this.currentHand[i].getCardAlphaNumValue() == "A") {
                    playerHandValue += 1;
                    hasAce = true;
                } else {
                    playerHandValue += this.currentHand[i].getCardValue();
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

                    if (cardToDraw.getCardAlphaNumValue() == "A" && (playerHandValue + 11 > 21)) {
                        hasAce = true;
                        cardToDraw.cardValue = 1;
                    }

                    updatedHand[i] = cardToDraw;
                }
            }

            this.currentHand = updatedHand;

            System.out.print("\nYour hand: ");
            this.playerHandValue = 0;
            for (int j = 0; j < updatedHand.length; j++) {
                System.out.print(updatedHand[j].getCardName() + " ");
                playerHandValue += updatedHand[j].getCardValue();
            }
            System.out.println();
        }
        // checkIfWinLose(this.playerHandValue);
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
            playerWinOrLose = true;
        } else if (handValue > 21) {
            System.out.println("Sorry, you lose.");
            playerWinOrLose = false;
        }
        return playerWinOrLose;
    }
}