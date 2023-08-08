package unoonline1;


/**
 *
 * @author sugam
 */
public class UnoConstants {
    // Colors
    public static final String RED = "Red";
    public static final String GREEN = "Green";
    public static final String BLUE = "Blue";
    public static final String YELLOW = "Yellow";
    public static final String WILD_COLOR = "Wild";

    // Special values
    public static final String ZERO_VALUE = "0";
    public static final String DRAW_TWO = "Draw Two";
    public static final String SKIP = "Skip";
    public static final String REVERSE = "Reverse";
    public static final String WILD = "Wild";
    public static final String WILD_DRAW_FOUR = "Wild Draw Four";

    // Array of all possible card values including zero, 1 to 9, and special cards
    public static final String[] VALUES = {ZERO_VALUE, "1", "2", "3", "4", "5", "6", "7", "8", "9",
            DRAW_TWO, SKIP, REVERSE, WILD, WILD_DRAW_FOUR};

    // Colors array for iterating through colors
    public static final String[] COLORS = {RED, GREEN, BLUE, YELLOW};
}
