package unoonline1;


/**
 *
 * @author sugam
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the Uno deck, which contains a list of Uno cards.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Creates a new Uno deck with 108 cards and shuffles them.
     */
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    /**
     * Initializes the Uno deck with 108 cards, following Uno rules.
     */
    private void initializeDeck() {
        for (String color : UnoConstants.COLORS) {
            // Add one zero card for each color
            cards.add(new Card(color, UnoConstants.ZERO_VALUE));

            // Add two cards for each number card (1 to 9) for each color
            for (int i = 1; i <= 9; i++) {
                cards.add(new Card(color, String.valueOf(i)));
                cards.add(new Card(color, String.valueOf(i)));
            }

            // Add special action cards (Draw Two, Skip, Reverse) for each color
            for (int i = 0; i < 2; i++) {
                cards.add(new Card(color, Card.DRAW_TWO));
                cards.add(new Card(color, Card.SKIP));
                cards.add(new Card(color, Card.REVERSE));
            }
        }

        // Add four Wild cards and four Wild Draw Four cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(UnoConstants.WILD_COLOR, Card.WILD));
            cards.add(new Card(UnoConstants.WILD_COLOR, Card.WILD_DRAW_FOUR));
        }
    }

    /**
     * Shuffles the Uno deck using the Collections.shuffle() method.
     */
    public void shuffle() {
        int n = cards.size();
        for (int i = n - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }    
    /**
     * Draws a card from the Uno deck.
     *
     * @return The top card from the deck, or null if the deck is empty.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    /**
     * Returns the number of cards remaining in the Uno deck.
     *
     * @return The number of cards in the deck.
     */
    public int getRemainingCardsCount() {
        return cards.size();
    }

    /**
     * Returns a string representation of the Uno deck.
     *
     * @return The string representation of the deck.
     */
    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
