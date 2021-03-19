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
                result = 1;
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
}