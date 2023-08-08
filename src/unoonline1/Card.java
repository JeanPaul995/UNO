package unoonline1;

import java.util.InputMismatchException;
import java.util.Scanner;



/**
 *
 * @author sugam
 */
public class Card {
    // Constants for special cards
    public static final String DRAW_TWO = "Draw Two";
    public static final String SKIP = "Skip";
    public static final String REVERSE = "Reverse";
    public static final String WILD = "Wild";
    public static final String WILD_DRAW_FOUR = "Wild Draw Four";

    private String color;
    private final String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public boolean isWild() {
        return value.equals(WILD) || value.equals(WILD_DRAW_FOUR);
    }

    public boolean isActionCard() {
        return value.equals(DRAW_TWO) || value.equals(SKIP) || value.equals(REVERSE) || isWild();
    }

    public boolean isSameColor(Card otherCard) {
        return this.color.equals(otherCard.getColor());
    }

    public boolean isSameValue(Card otherCard) {
        return this.value.equals(otherCard.getValue());
    }

    public boolean canBePlayedOn(Card otherCard) {
        if (isWild() || otherCard.isWild()) {
            return true;
        }
        return isSameColor(otherCard) || isSameValue(otherCard);
    }

    @Override
    public String toString() {
        return color + " " + value;
    }
        public void setColor(String color) {
        if (isWild()) {
            this.color = color;
        }
    }
}
