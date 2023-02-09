package com.sweeris.openkingscorner;

/**
 * Used to represent the different Suits a card can have
 */
public enum Suit {
    CLUB,
    DIAMOND,
    HEART,
    SPADE;

    /**
     * Returns whether a given Suit is red
     * @param e
     * @return
     */
    public static boolean isRed(Suit e) {
        return (e==DIAMOND||e==HEART);
    }

    /**
     * Returns whether a given Card is red
     * @param c
     * @return
     */
    public static boolean isRed(Card c) {
        return isRed(c.getSuit());
    }
}
