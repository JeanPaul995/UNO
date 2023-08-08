
package unoonline1;

/**
 *
 * @author sugam
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Uno game.
 */
public class Player {
    private final String name;
    private final List<Card> hand;
    private boolean declaredUno; 

    /**
     * Creates a new player with the given name and an empty hand.
     *
     * @param name The name of the player.
     */
    
     Player(String name) {
            this.name = name;
            hand = new ArrayList<>();
            declaredUno = false; // Initialize the "Uno" declaration state to false
        }


    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Draws a card and adds it to the player's hand.
     *
     * @param card The card to be drawn and added to the hand.
     */
    public void drawCard(Card card) {
        hand.add(card);
    }

    /**
     * Plays a card from the player's hand and removes it from the hand.
     *
     * @param cardIndex The index of the card to be played in the hand.
     * @return The card that was played.
     * @throws IllegalArgumentException if the cardIndex is out of bounds.
     */
    public Card playCard(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            throw new IllegalArgumentException("Invalid card index.");
        }
        return hand.remove(cardIndex);
    }

    /**
     * Checks if the player has only one card left (Uno!).
     *
     * @return True if the player has one card left, otherwise False.
     */
    public boolean hasUno() {
        return hand.size() == 1;
    }

    /**
     * Checks if the player has won the game (no cards left).
     *
     * @return True if the player has no cards left, otherwise False.
     */
    public boolean hasWon() {
        return hand.isEmpty();
    }

    /**
     * Returns the player's hand as a list of cards.
     *
     * @return The player's hand.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Returns the number of cards in the player's hand.
     *
     * @return The number of cards in the player's hand.
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * Returns a string representation of the player.
     *
     * @return The name of the player.
     */
    @Override
    public String toString() {
        return name;
    }
    
    public boolean hasDeclaredUno() {
        return declaredUno;
    }
    
    public void setDeclaredUno(boolean declaredUno) {
        this.declaredUno = declaredUno;
        }
}
