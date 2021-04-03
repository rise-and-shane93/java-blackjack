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

        System.out.print("Enter how much money as a whole number you would like to bet: ");

        int moneyToBet = input.nextInt();

        while (moneyToBet > moneyStart) {
            System.out.print("That's more than the money you have. Enter a lower number: ");
            moneyToBet = input.nextInt();
        }

        blackjackGame(you, dealer, moneyStart, input, moneyToBet);
    }

    public static void blackjackGame(Player player, Dealer dealer, int moneyStart, Scanner scanner, int moneyToBet) {
        // int playerMoneyStart = moneyStart;
        String playerHitOrStay;
        boolean boolHitOrStay = false;
        // boolean outOfMoney = false;

        player.drawCard(Participant.CARDS_TO_START, true);
        
        boolean initialWin = player.checkIfWinLose(player.playerHandValue);
        if (initialWin) {
            System.out.println("You win!");
        } else {
            dealer.drawCard(Participant.CARDS_TO_START, true);
            System.out.print("It's your turn. Would you like to hit or stay? Press Y for hit or N for stay: ");

            playerHitOrStay = scanner.nextLine();
            boolHitOrStay = playerHitOrStay.toUpperCase().equals("Y") ? true : false;
        }
        
        while (boolHitOrStay && !initialWin) {
            player.drawCard(1, false);
            int playerCurrHandValue = player.playerHandValue;
            if (playerCurrHandValue > 21) {
                player.playerMoney -= moneyToBet;
                if (player.playerMoney == 0) {
                    player.outOfMoney = true;
                    System.out.println("Sorry, you have run out of money.");
                    break;
                } else {
                    System.out.println("You busted and lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                    boolHitOrStay = false;
                    break;
                }
            } else if (playerCurrHandValue == 21) {
                player.playerMoney += moneyToBet;
                System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
                boolHitOrStay = false;
                break;
            } else {
                System.out.print("Would you like to hit or stay? Press Y for hit or N for stay: ");
                boolHitOrStay = scanner.nextLine().toUpperCase().equals("Y") ? true : false;
            }
        }
        // System.out.println(player.playerHandValue);
    }

    public static String playerDrawCards(Player player, int moneyToStart, int moneyToBet, Scanner scanner) {

    }

    public static void dealerDrawCards(Dealer dealer) {

    }
}