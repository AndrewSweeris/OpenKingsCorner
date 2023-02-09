package com.sweeris.openkingscorner;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class used to represent individual cards
 */
public class Card implements Comparable<Card> {
    private final Suit suit;
    private final int type;

    /**
     * Constructor method that creates the card
     * @param suit
     * @param type
     */
    public Card(Suit suit, int type) {
        this.suit=suit;
        this.type=type;
    }

    /**
     * Returns a Card's Suit
     * @return
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns a Card's type
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * Returns if this Card is red
     * @return
     */
    public boolean isRed() {
        return Suit.isRed(suit);
    }

    /**
     * Returns if the given Card can be placed on this Card. Dependent on if the two cards have opposite colors and if
     * the given Card c's type is one less than the type of the current Card.
     * @return
     */
    public boolean isValid(Card c) {
        return (c.isRed()!=isRed())&&(c.getType()+1==getType());
    }

    /**
     * Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     * @param c
     * @return
     */
    @Override
    public int compareTo(Card c) {
        if (getType()!=c.getType()) {
            return getType()-c.getType();
        }
        if (isRed()!=c.isRed()){
            if (isRed()) return 1;
            return -1;
        }
        return 0;
    }

    /**
     * Returns a string equivalent of the card
     * @return
     */
    @Override
    public String toString() {
        String suit;
        if (this.suit==Suit.CLUB) {
            suit = "Club-";
        }
        else if (this.suit==Suit.DIAMOND) {
            suit = "Diamond-";
        }
        else if (this.suit==Suit.HEART) {
            suit = "Heart-";
        } else {
            suit = "Spade-";
        }
        suit += getType();
        return suit;
    }
}
