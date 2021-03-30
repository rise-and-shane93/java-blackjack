import java.util.Scanner;

public class BlackjackGameSimulator {

    // public static boolean keepPlaying;
    // public int playerMoneyStart;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        // Card diamond = new Card();

        // diamond.printCardSymbol();

        int moneyStart = (int) ( 1000 * Math.random() );

        System.out.println("\nLet's play some blackjack. You have $" + moneyStart + " to bet.");

        Player you = new Player(moneyStart);

        Dealer dealer = new Dealer();

        blackjackGame(you, dealer, moneyStart, input);
    }

    public static void blackjackGame(Player player, Dealer dealer, int moneyStart, Scanner scanner) {
        int playerMoneyStart = moneyStart;
        boolean hitOrStay = false;
        boolean winOrLose = true;
        // String 

        dealer.drawCard(Participant.CARDS_TO_START, true);

        player.drawCard(Participant.CARDS_TO_START, true);

        System.out.print("It's your turn. Would you like to hit or stay? Press Y for hit or N for stay: ");

        hitOrStay = scanner.nextLine() == "Y" ? true : false;
        // if (inp)
        while (hitOrStay || winOrLose) {
            player.drawCard(1, false);
            winOrLose = player.checkIfWinLose(player.playerHandValue);
            System.out.print("It's your turn. Would you like to hit or stay? Press Y for hit or N for stay: ");
            hitOrStay = scanner.nextLine() == "Y" ? true : false;
        }

        System.out.println("out of while loop");
        // player.drawCard(1, false);
        // player.getHandValue();
    }
}