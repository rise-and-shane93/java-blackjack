public class Card {

    private String cardName;
    private int cardValue;
    private String cardSymbol;
    private String alphaNumCardValue;
    private int cardRandomNum;

    private static String[] cardArr = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static String[] symbols = {"♥", "◆", "♠", "♣"};
    
    public Card() {
        // this.cardName = setCardName();
        this.cardRandomNum = (int) ( cardArr.length * Math.random() );
        this.cardValue = setCardValue();
        this.cardSymbol = setCardSymbol();
        this.cardName = setCardName();
    }

    public int setCardValue() {
        // int randomNumberCard = (int) ( cardArr.length * Math.random() );

        String stringArrElement = cardArr[cardRandomNum];
        int result = 0;
        

        switch (stringArrElement) {
            case "2", "3", "4", "5", "6", "7", "8", "9", "10":
                result = Integer.parseInt(stringArrElement);
                break;
            case "J", "Q", "K":
                result = 10;
                break;
            case "A":
                result = 11;
                System.out.print("You drew an ace. Press 1 if you want it's value to be 1 or 11 if you want it's value to be 11: ");
                break;
        }
        return result;
    }

    public String setCardName() {
        return cardArr[cardRandomNum] + " " + this.cardSymbol;
    }

    public String setCardSymbol() {
        int randomNumberSymbol = (int) ( symbols.length * Math.random() );

        return symbols[randomNumberSymbol];
    }

    public void printCardSymbol() {
        System.out.println(this.cardName);
    }

    public String getCardName() {
        return this.cardName;
    }

    public int getCardValue() {
        return this.cardValue;
    }
}