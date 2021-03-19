public class BlackjackGameSimulator {
    public static void main(String[] args) {
        // Card diamond = new Card();

        // diamond.printCardSymbol();

        int playerMoneyStart = (int) ( 1000 * Math.random() );

        System.out.println("Let's play some blackjack. You have $" + playerMoneyStart + " to bet.");

        Player you = new Player(playerMoneyStart);
    }
}