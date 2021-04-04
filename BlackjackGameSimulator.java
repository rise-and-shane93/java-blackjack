/**
 * This is a blackjack clone coded in the Java programming language. The user is the player
 * and is trying to earn money by either winning blackjack with their initial hand, end up 
 * with more points than the dealer, or have the dealer busting (getting more than 21 points).
 * At the same time, the user is trying to not bust or lose to the dealer as the user would
 * lose money. At the end of each game, regardless of outcome, the user has the option to play
 * again unless the user has no more money.
 * 
 * @author Shane Harper
 * EN.605.201.84.SP21
 * Assignment 6 - Employee Records
 */

import java.util.Scanner;

public class BlackjackGameSimulator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int moneyStart = (int) ( 1000 * Math.random() );

        System.out.println("\nLet's play some blackjack. You have $" + moneyStart + " to bet.");

        Player you = new Player(moneyStart);

        Dealer dealer = new Dealer();

        blackjackGame(you, dealer, moneyStart);
    }

    /**
     * This static method runs the entire blackjack game. It takes in both the player and dealer objects as well as the
     * amount of money that the player starts with. This method runs the player and dealer segments of blackjack with 
     * conditional statements to check to see if it's necessary to run various segments of the game. For example, as
     * you will see later, the String returned from the playerDrawCards method will determine if the game will continue
     * to the player having to take hits, if the player busts, or continuing to the dealer segment.
     * 
     * @author Shane Harper
     * @version 1.0
     * @param player The player object
     * @param dealer The dealer object
     * @param moneyStart The amount of money that a player starts with (assigned to the player upon instantiating the player object).
     * @return void
     */

    public static void blackjackGame(Player player, Dealer dealer, int moneyStart) {
        Scanner scanner = new Scanner(System.in);
        String playerPlayAgainString = "";
        boolean playerPlayAgain = true;
        String playerGameResult = "";
        String dealerGameResult = "";
        int moneyToBet;

        while(!player.outOfMoney && playerPlayAgain) {
            System.out.print("Enter how much money as a whole number you would like to bet: ");

            moneyToBet = scanner.nextInt();

            while (moneyToBet > moneyStart) {
                System.out.print("That's more than the money you have. Enter a lower number: ");
                moneyToBet = scanner.nextInt();
            }

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
            } else if (playerGameResult.equals("player wins")) {
                player.playerMoney += moneyToBet;
                System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
            }
            System.out.print("Would you like to play again? Press Y for yes or N for no: ");
            playerPlayAgainString = scanner.next();
            playerPlayAgain = playerPlayAgainString.toUpperCase().equals("Y") ? true : false;

            if (playerPlayAgain) {
                player.playerHandValue = 0;
                dealer.dealerHandValue = 0;
            }
        }

        System.out.println("Thank you for playing.");
        
    }

    /**
     * This method is responsible for result of the player drawing their first two cards at the beginning of the game and
     * taking hits (drawing one card). The return type is a string because there are multiple outcomes: the player wins upon
     * the first draw, the player wins by taking hits, the player busts, and the player runs out of money. Therefore, the
     * string return type reflects the outcome and is used to determine which code to execute in the blackjackGame method.
     * 
     * @author Shane Harper
     * @version 1.0
     * @param player The player object
     * @param dealer The dealer object
     * @param moneyToStart The amount of money that the player starts with. This changes based if the player wins or loses money
     * @param moneyToBet The amount of money that the player bets.
     * @return String that will state the result of the method. This will in turn be used to determine the game's progression in the blackjackGame method.
     */
    public static String playerDrawCards(Player player, Dealer dealer, int moneyToStart, int moneyToBet) {
        Scanner scanner = new Scanner(System.in);
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

        return playerGameResult;
    }

    /**
     * Just like the playerDrawCards method, this one will process the result of the dealer drawing cards. The dealer has
     * a different set of rules in the sense that the dealer is dealt one card face up and another one face down. When the
     * player ends their turn, the dealer flips their face down card face up and if their hand is not at least 17 points, 
     * continue to take hits until it is at least 17. If the dealer busts in the process, then the player wins. Therefore,
     * the result variable is used to reflect the outcome.
     * 
     * @author Shane Harper
     * @version 1.0
     * @param dealer
     * @return String that will state the result of the method. This will in turn be used to determine the game's progression in the blackjackGame method.
     */
    public static String dealerDrawCards(Dealer dealer) {
        String result = "";
        System.out.println("dealer's turn");

        dealer.showEntireHand(dealer.currentHand);
        if (dealer.dealerHandValue == 21) {
            result = "Dealer wins";
            System.out.println(result);
        } else if (dealer.dealerHandValue >= 17 && dealer.dealerHandValue <= 21) {
            result = "Dealer has above 17 but less than 21";
        } else {
            while (dealer.dealerHandValue < 17) {
                dealer.drawCard(1, false);
                if (dealer.dealerHandValue > 21) {
                    result = "Dealer busts";
                    System.out.println(result);
                    break;
                } else {
                    result = "Dealer has above 17 but less than 21";
                }
            }
        }
        return result;
    }
}