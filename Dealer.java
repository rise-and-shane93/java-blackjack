public class Dealer implements Participant {

    private int dealerHandValue;
    private Card[] currentHand;
    
    public void drawCard(int cardsToDraw, boolean firstDraw) {
        if (firstDraw) {
            this.currentHand = new Card[cardsToDraw];
            // Card[] startingCards = {new Card(), new Card()};
    
            for (int i = 0; i < cardsToDraw; i++) {
                this.currentHand[i] = new Card();
            }
    
            System.out.print("\nDealer's hand: ");
            for (int i = 0; i < cardsToDraw; i++) {
                if (i == 0) {
                    System.out.print("XX ");
                } else {
                    System.out.print(this.currentHand[i].getCardName() + " ");
                }
            }
            System.out.println();
        }
    }

    public void getHandValue() {

    }
}