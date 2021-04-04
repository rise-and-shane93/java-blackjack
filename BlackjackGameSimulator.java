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
        // input.close();
        blackjackGame(you, dealer, moneyStart, moneyToBet);
    }

    public static void blackjackGame(Player player, Dealer dealer, int moneyStart, int moneyToBet) {
        Scanner scanner = new Scanner(System.in);
        String playerPlayAgainString = "";
        boolean playerPlayAgain = true;
        String playerGameResult = "";
        String dealerGameResult = "";
        
        while(!player.outOfMoney && playerPlayAgain) {
            playerGameResult = playerDrawCards(player, dealer, moneyStart, moneyToBet);
            if (playerGameResult.equals("player stays")) {
                dealerGameResult = dealerDrawCards(dealer);
                if (dealerGameResult.equals("Dealer wins")) {
                    player.playerMoney -= moneyToBet;
                    System.out.println("You lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                } else if (dealerGameResult.equals("Dealer busts")) {
                    player.playerMoney += moneyToBet;
                    System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
                } else if (dealerGameResult.equals("Dealer has above 17 but less than 21")) {
                    if (player.playerHandValue > dealer.dealerHandValue) {
                        player.playerMoney += moneyToBet;
                        System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
                    } else if (player.playerHandValue == dealer.dealerHandValue) {
                        System.out.println("This game ended in a tie. You neither gained nor lost any money.");
                    } else {
                        player.playerMoney -= moneyToBet;
                        System.out.println("You lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                    }
                }
            } else if (playerGameResult.equals("player loses and has run out of money")) {
                playerPlayAgain = false;
                break;
            }
            System.out.print("Would you like to play again? Press Y for yes or N for no: ");
            playerPlayAgainString = scanner.next();
            playerPlayAgain = playerPlayAgainString.toUpperCase().equals("Y") ? true : false;

            if (playerPlayAgain) {
                player.playerHandValue = 0;
                dealer.dealerHandValue = 0;
                // player.currentHand
            }
        }

        System.out.println("Thank you for playing.");
        
    }

    public static String playerDrawCards(Player player, Dealer dealer, int moneyToStart, int moneyToBet) {
        Scanner scanner = new Scanner(System.in);
        // int playerMoneyStart = moneyStart;
        String playerGameResult = "";
        String playerHitOrStay = "";
        boolean boolHitOrStay = false;
        boolean playerLosesOnOwnTurn = false;
        boolean playerPlayAgain = false;
        boolean playerWins = false;
        
        // while (!player.outOfMoney && )
        player.drawCard(Participant.CARDS_TO_START, true);

        boolean initialWin = player.checkIfWinLose(player.playerHandValue);
        if (initialWin) {
            System.out.println("You win!");
            initialWin = false;
            playerWins = true;
            playerGameResult = "player wins";
        } else {
            dealer.drawCard(Participant.CARDS_TO_START, true);
            System.out.print("It's your turn. Would you like to hit or stay? Press Y for hit or N for stay: ");

            playerHitOrStay = scanner.next();
            boolHitOrStay = playerHitOrStay.toUpperCase().equals("Y") ? true : false;
        }

        while (boolHitOrStay && !initialWin) {
            dealer.showHandStart(dealer.currentHand);
            player.drawCard(1, false);
            int playerCurrHandValue = player.playerHandValue;
            if (playerCurrHandValue > 21) {
                player.playerMoney -= moneyToBet;
                if (player.playerMoney == 0) {
                    player.outOfMoney = true;
                    System.out.println("Sorry, you have run out of money.");
                    playerGameResult = "player loses and has run out of money";
                } else {
                    System.out.println("You busted and lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                    boolHitOrStay = false;
                    playerGameResult = "player loses";
                }
                playerLosesOnOwnTurn = true;
                break;
            } else if (playerCurrHandValue == 21) {
                player.playerMoney += moneyToBet;
                System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
                boolHitOrStay = false;
                playerWins = true;
                playerGameResult = "player wins";
                break;
            } else {
                System.out.print("Would you like to hit or stay? Press Y for hit or N for stay: ");
                boolHitOrStay = scanner.next().toUpperCase().equals("Y") ? true : false;
            }
        }

        if (!boolHitOrStay && !playerLosesOnOwnTurn && !playerWins) {
            playerGameResult = "player stays";
        }
        // System.out.println(player.playerHandValue);

        // playerGameResult = "";
        // playerHitOrStay = "";
        // boolHitOrStay = false;
        // playerLosesOnOwnTurn = false;
        // playerPlayAgain = false;
        // playerWins = false;

        return playerGameResult;
    }

    public static String dealerDrawCards(Dealer dealer) {
        String result = "";
        System.out.println("dealer's turn");

        dealer.showEntireHand(dealer.currentHand);
        System.out.println(dealer.dealerHandValue);
        if (dealer.dealerHandValue == 21) {
            result = "Dealer wins";
            System.out.println(result);
        } else if (dealer.dealerHandValue >= 17 && dealer.dealerHandValue <= 21) {
            // System.out.println(dealer.dealerHandValue);
            result = "Dealer has above 17 but less than 21";
        } else {
            while (dealer.dealerHandValue <= 17) {
                dealer.drawCard(1, false);
                if (dealer.dealerHandValue > 21) {
                    result = "Dealer busts";
                    System.out.println(result);
                    break;
                } else {
                    result = "Dealer has above 17 but less than 21";
                }
            }
            // System.out.println(dealer.dealerHandValue);
        }
        return result;
    }
}