
package unoonline1;

/**
 *
 * @author sugam
 */

/**
 * Represents a computer player in the Uno game.
 */
public class ComputerPlayer extends Player {
    /**
     * Creates a new computer player with the given name.
     *
     * @param name The name of the computer player.
     */
    public ComputerPlayer(String name) {
        super(name);
    }

    /**
     * Chooses a card to play from the computer player's hand based on the top card on the discard pile.
     * The computer player tries to play the card with the most common color or value first.
     * If no valid card is found, it draws a card until a valid card is obtained.
     *
     * @param topCard The top card on the discard pile.
     * @return The index of the chosen card in the hand, or -1 if no valid card found (and needs to draw).
     */
    public int chooseCardToPlay(Card topCard) {
        int[] colorCount = new int[UnoConstants.COLORS.length];
        int[] valueCount = new int[15]; // 0 to 9, Draw Two, Skip, Reverse, Wild, Wild Draw Four

        for (Card card : getHand()) {
            colorCount[getCardColorIndex(card)]++;
            valueCount[getCardValueIndex(card)]++;
        }

        // Find the most common color and value in the hand
        int mostCommonColorIndex = findIndexOfMaxValue(colorCount);
        int mostCommonValueIndex = findIndexOfMaxValue(valueCount);

        for (int i = 0; i < getHandSize(); i++) {
            Card card = getHand().get(i);
            if (card.canBePlayedOn(topCard)) {
                if (getCardColorIndex(card) == mostCommonColorIndex || getCardValueIndex(card) == mostCommonValueIndex) {
                    return i; // Found a valid card with the most common color or value
                }
            }
        }

        return -1; // No valid card found to play (and needs to draw)
    }

    /**
     * Finds the index of the maximum value in an integer array.
     *
     * @param array The input integer array.
     * @return The index of the maximum value in the array.
     */
    private int findIndexOfMaxValue(int[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Gets the color index of a card in UnoConstants.COLORS array.
     * For Wild cards, returns the index of the WILD_COLOR.
     *
     * @param card The card to get the color index for.
     * @return The color index of the card.
     */
    private int getCardColorIndex(Card card) {
        if (card.isWild()) {
            return UnoConstants.COLORS.length; // Index of WILD_COLOR
        } else {
            for (int i = 0; i < UnoConstants.COLORS.length; i++) {
                if (card.getColor().equals(UnoConstants.COLORS[i])) {
                    return i;
                }
            }
        }
        return -1; // Invalid color (shouldn't happen)
    }

    /**
     * Gets the value index of a card in UnoConstants.VALUES array.
     *
     * @param card The card to get the value index for.
     * @return The value index of the card.
     */
    private int getCardValueIndex(Card card) {
        for (int i = 0; i < UnoConstants.VALUES.length; i++) {
            if (card.getValue().equals(UnoConstants.VALUES[i])) {
                return i;
            }
        }
        return -1; // Invalid value (shouldn't happen)
    }
}
