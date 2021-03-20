public class Player implements Participant {

    private int playerMoneyStart;
    private int playerHandValue;
    private Card[] currentHand;

    public Player(int playerMoneyStart) {
        this.playerMoneyStart = playerMoneyStart;
    }

    public void drawCard(int cardsToDraw, boolean firstDraw) {
        if (firstDraw) {
            this.currentHand = new Card[cardsToDraw];
            // Card[] startingCards = {new Card(), new Card()};
    
            for (int i = 0; i < cardsToDraw; i++) {
                this.currentHand[i] = new Card();
            }
    
            System.out.print("\nYour hand: ");
            for (int i = 0; i < cardsToDraw; i++) {
                System.out.print(this.currentHand[i].getCardName() + " ");
                playerHandValue += this.currentHand[i].getCardValue();
            }
            System.out.println();
        }
        checkIfWinLose(this.playerHandValue);
    }

    public void getHandValue() {
        System.out.println(this.playerHandValue);
    }

    public void displayStartingMoney() {
        System.out.println(playerMoneyStart);
    }

    public void checkIfWinLose(int handValue) {
        if (handValue == 21) {
            System.out.println("You win!!!");
        } else if (handValue > 21) {
            System.out.println("Sorry, you lose.");
        }
    }
}
