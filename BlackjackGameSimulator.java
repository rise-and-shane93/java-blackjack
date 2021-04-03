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
        String playerHitOrStay;
        boolean boolHitOrStay = false;

        dealer.drawCard(Participant.CARDS_TO_START, true);

        player.drawCard(Participant.CARDS_TO_START, true);
        boolean initialWin = player.checkIfWinLose(player.playerHandValue);

        if (initialWin) {
            System.out.println("You win!");
        } else {
            System.out.print("It's your turn. Would you like to hit or stay? Press Y for hit or N for stay: ");

            playerHitOrStay = scanner.nextLine();
            boolHitOrStay = playerHitOrStay.toUpperCase().equals("Y") ? true : false;
        }
        
        while (boolHitOrStay && !initialWin) {
            player.drawCard(1, false);
            int playerCurrHandValue = player.playerHandValue;
            if (playerCurrHandValue > 21) {
                System.out.println("you lose");
                boolHitOrStay = false;
                break;
            } else if (playerCurrHandValue == 21) {
                System.out.println("you win");
                boolHitOrStay = false;
                break;
            } else {
                System.out.print("Would you like to hit or stay? Press Y for hit or N for stay: ");
                boolHitOrStay = scanner.nextLine().toUpperCase().equals("Y") ? true : false;
            }
        }
        System.out.println(player.playerHandValue);
    }
}