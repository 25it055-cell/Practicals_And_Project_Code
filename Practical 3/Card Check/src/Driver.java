public class Driver {
    public static void main(String[] args) {

        Card[] cardPool = {
                new Card("Ace", "Spades"),
                new Card("Jack", "Diamonds"),
                new Card("Queen", "Hearts"),
                new Card("Ace", "Spades"), // Duplicate card
                new Card("Ten", "Clubs")
        };

        Card[] myDeck = new Card[cardPool.length];
        int cardCount = 0;

        for (Card nextCard : cardPool) {
            boolean isDuplicate = false;

            for (int i = 0; i < cardCount; i++) {
                if (myDeck[i].equals(nextCard)) {
                    System.out.println("Duplicate found: " + nextCard);
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                myDeck[cardCount] = nextCard;
                cardCount++;
            }
        }
    }
}
