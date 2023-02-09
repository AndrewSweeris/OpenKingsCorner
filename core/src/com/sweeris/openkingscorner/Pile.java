package com.sweeris.openkingscorner;

import java.util.ArrayList;

/**
 * Class used to represent the piles that cards are placed on
 */
public class Pile {
    private ArrayList<Card> pile;
    private boolean isCorner;

    /**
     * Creates an empty pile that accepts a King suited card only
     */
    public Pile(boolean isCorner) {
        pile = new ArrayList<>();
        this.isCorner=isCorner;
    }

    /**
     * Creates a pile that takes the given card as the initial, bottom card
     * @param c
     */
    public Pile(Card c, boolean isCorner) {
        pile = new ArrayList<>();
        pile.add(c);
        this.isCorner=isCorner;
    }

    /**
     * Creates a pile from a pile
     * @param p
     */
    public Pile(Pile p) {
        pile = p.getList();
    }

    /**
     * Returns an ArrayList<Card> of the pile of Cards
     * @return
     */
    public ArrayList<Card> getList() {
        return pile;
    }

    /**
     * Returns the top card of the deck, or throws an error if the deck is empty
     * @return
     */
    public Card getTopCard() {
        if (pile.size()>0) {
            return pile.get(pile.size()-1);
        } else {
            throw new Error("Attempted to access TopCard of empty deck");
        }
    }

    /**
     * Returns the bottom card of the deck, or throws an error if the deck is empty
     * @return
     */
    public Card getBottomCard() {
        if (pile.size()>0) {
            return pile.get(0);
        } else {
            throw new Error("Attempted to access BottomCard of empty deck");
        }
    }

    /**
     * Returns if the Pile is empty
     * @return
     */
    public boolean isEmpty() {
        return pile.size()==0;
    }

    /**
     * Returns if the given card can be played on the Pile, and if the Pile is open
     * @param c
     * @return
     */
    public boolean isValid(Card c) {
        if (isEmpty()) {
            return (c.getType()==12&&isCorner)||(c.getType()!=12&&!isCorner);
        }
        else {
            return getTopCard().isValid(c);
        }
    }

    /**
     * Returns if a given Pile can be merged onto the current Pile dependent on whether the current Pile isn't empty and
     * if the given Pile's bottom card can be played onto the current Pile's top card
     * @param p
     * @return
     */
    public boolean isValid(Pile p) {
        return !isEmpty()&& getTopCard().isValid(p.getBottomCard());
    }

    /**
     * Plays the given Card on the Pile, or returns an error if the play is invalid
     * @param c
     */
    public void playCard(Card c) {
        if (!isValid(c)) {
            if (isEmpty()) {
                throw new Error("Attempted an illegal move - non-King Card " + c.toString() + " on an empty pile");
            }
            else {
                throw new Error("Attempted an illegal move - Card " + c.toString() + " on Card " + getTopCard().toString());
            }
        }
        pile.add(c);
    }

    /**
     * Used to force a pile to take a starting Card, and will throw an error if a king is placed out of a corner
     * @param c
     */
    public void takeStartingCard(Card c) {
        if (isValid(c)&&c.getType()==12) {
            throw new Error("Attempted an illegal move - King Card " + c.toString() + " on an empty pile that isn't in a corner");
        }
        pile.add(c);
    }

    /**
     * Merges the given Pile onto the current Pile, or returns an error if the merge is invalid
     * @param p
     */
    public void mergePile(Pile p) {
        if (!isValid(p)) {
            if (isEmpty()) {
                throw new Error("Attempted an illegal move - merge onto an empty pile");
            }
            else {
                throw new Error("Attempted an illegal move - merge pile with top Card " + getTopCard() + " onto pile" +
                        "with bottom Card " + getBottomCard());
            }
        }
        ArrayList<Card> temp = p.getList();
        for (int i = 0; i < temp.size(); i++) {
            playCard(temp.get(i));
        }
    }

    /**
     * Empties piles after a merge
     */
    public void emptyPile() {
        pile = new ArrayList<>();
    }
}
