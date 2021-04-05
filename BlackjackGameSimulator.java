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

        // initialize the amount of money a player will start out with
        int moneyStart = (int) ( 1000 * Math.random() );

        System.out.println("\nLet's play some blackjack. You have $" + moneyStart + " to bet.");

        // instantiate Player and Dealer objects
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

        // this loop will keep running until the player is out of money or the player does not want to play again
        while(!player.outOfMoney && playerPlayAgain) {
            System.out.print("Enter how much money as a whole number you would like to bet: ");

            moneyToBet = scanner.nextInt();

            // ensures that the player enters a number less than the money they started out with
            while (moneyToBet > moneyStart) {
                System.out.print("That's more than the money you have. Enter a lower number: ");
                moneyToBet = scanner.nextInt();
            }

            // get the string result of when the player draws cards
            playerGameResult = playerDrawCards(player, dealer, moneyStart, moneyToBet);

            // run this block if the player stays
            if (playerGameResult.equals("player stays")) {

                // get the string result of the dealer drawing cards
                dealerGameResult = dealerDrawCards(dealer);

                // run this block if the dealer wins. The player loses money and the program outputs the remaining money.
                if (dealerGameResult.equals("Dealer wins")) {
                    player.playerMoney -= moneyToBet;
                    System.out.println("You lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                
                // run this block if the dealer busts. The player wins money and the program outputs the updated money.
                } else if (dealerGameResult.equals("Dealer busts")) {
                    player.playerMoney += moneyToBet;
                    System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
                
                // run this block if the dealer's point value is between 17 & 21
                } else if (dealerGameResult.equals("Dealer has above 17 but less than 21")) {

                    // the player has more points than the dealer and wins. Add money to the player's money total
                    if (player.playerHandValue > dealer.dealerHandValue) {
                        player.playerMoney += moneyToBet;
                        System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");

                    // The player and dealer tied.
                    } else if (player.playerHandValue == dealer.dealerHandValue) {
                        System.out.println("This game ended in a tie. You neither gained nor lost any money.");

                    // the dealer has more points than the player and wins. Subtract money to the player's money total
                    } else {
                        player.playerMoney -= moneyToBet;
                        System.out.println("You lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                    }
                }
            
            // The player loses and also runs out of money. Re assigns the playerPlayAgain variable to exit the loop
            } else if (playerGameResult.equals("player loses and has run out of money")) {
                playerPlayAgain = false;
                break;
            
            // The player wins and adds money to their total
            } else if (playerGameResult.equals("player wins")) {
                player.playerMoney += moneyToBet;
                System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
            }

            // Asks if the user wants to play again
            System.out.print("Would you like to play again? Press Y for yes or N for no: ");
            playerPlayAgainString = scanner.next();
            playerPlayAgain = playerPlayAgainString.toUpperCase().equals("Y") ? true : false;

            // if the player wants to play again, reset the player's and dealer's point values to 0
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
        
        // player draws their first two cards
        player.drawCard(Participant.CARDS_TO_START, true);

        // checks to see if the player wins after drawing their first two cards
        boolean initialWin = player.checkIfInitWin(player.playerHandValue);
        if (initialWin) {
            initialWin = false;
            playerWins = true;
            playerGameResult = "player wins";
        } else {
            // the dealer draws their first two cards and asks if the player wants to hit or stay
            dealer.drawCard(Participant.CARDS_TO_START, true);
            System.out.print("It's your turn. Would you like to hit or stay? Press Y for hit or N for stay: ");

            playerHitOrStay = scanner.next();
            boolHitOrStay = playerHitOrStay.toUpperCase().equals("Y") ? true : false;
        }

        // run this while loop until the player does not want to play anymore and/or they didn't win after getting their first two cards
        while (boolHitOrStay && !initialWin) {

            // dealer shows their first two cards
            dealer.showHandStart(dealer.currentHand);

            // player draws one card
            player.drawCard(1, false);

            // variable for the player's point value
            int playerCurrHandValue = player.playerHandValue;

            // player busts
            if (playerCurrHandValue > Participant.MAX_POINTS) {

                // player loses money equal to the amount they bet
                player.playerMoney -= moneyToBet;

                // player runs out of money
                if (player.playerMoney == 0) {
                    player.outOfMoney = true;
                    System.out.println("Sorry, you have run out of money.");
                    playerGameResult = "player loses and has run out of money";
                // player loses money
                } else {
                    System.out.println("You busted and lost $" + moneyToBet + ". You now have $" + player.playerMoney + " left");
                    boolHitOrStay = false;
                    playerGameResult = "player loses";
                }

                // sets playerLosesOnOwnTurn variable to true
                playerLosesOnOwnTurn = true;
                break;

            // player wins and adds to their money total
            } else if (playerCurrHandValue == Participant.MAX_POINTS) {
                player.playerMoney += moneyToBet;
                System.out.println("You win and earned $" + moneyToBet + ". You now have $" + player.playerMoney + ".");
                boolHitOrStay = false;
                playerWins = true;
                playerGameResult = "player wins";
                break;

            // if the player neither wins nor busts, ask if the player wants to hit or stay
            } else {
                System.out.print("Would you like to hit or stay? Press Y for hit or N for stay: ");
                boolHitOrStay = scanner.next().toUpperCase().equals("Y") ? true : false;
            }
        }

        /* if the player wants to stay, did not yet lose before the dealer's turn and did not yet lose the round,
        return "player stays" to the playerGameResult */
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
     * @param dealer
     * @return String that will state the result of the method. This will in turn be used to determine the game's progression in the blackjackGame method.
     */
    public static String dealerDrawCards(Dealer dealer) {
        String result = "";
        System.out.println("dealer's turn");

        // show the dealer's hand
        dealer.showEntireHand(dealer.currentHand);

        // if the dealer has 21 points, the dealer wins
        if (dealer.dealerHandValue == Participant.MAX_POINTS) {
            result = "Dealer wins";
            System.out.println(result);
        
        // if the dealer has between 17 and 21 points, the dealer cannot draw cards
        } else if (dealer.dealerHandValue >= 17 && dealer.dealerHandValue <= Participant.MAX_POINTS) {
            result = "Dealer has above 17 but less than 21";
        
        // run a while loop for the dealer to draw cards until the dealer has at least 17 points or busts
        } else {
            while (dealer.dealerHandValue < 17) {
                dealer.drawCard(1, false);
                if (dealer.dealerHandValue > Participant.MAX_POINTS) {
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