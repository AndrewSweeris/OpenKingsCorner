package com.sweeris.openkingscorner;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used to represent an individual player in the game
 */
public class Player {
    private boolean isTurn;
    private ArrayList<Card> hand;
    private int points;
    private int selectedCardIndex;

    /**
     * Constructor method that creates an empty player
     */
    public Player() {
        isTurn = false;
        hand = new ArrayList<>();
        points = 0;
        selectedCardIndex = 0;
    }

    /**
     * Constructor method that creates a player with the given hand
     *
     * @param hand
     */
    public Player(ArrayList<Card> hand) {
        isTurn = false;
        this.hand = hand;
        points = 0;
        selectedCardIndex = 0;
    }

    /**
     * Returns if Player's turn is going
     *
     * @return
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     * Starts player turn
     */
    public void startTurn() {
        isTurn=true;
    }

    /**
     * Ends player turn
     */
    public void endTurn() {
        isTurn=false;
    }

    /**
     * Returns a Player's hand
     *
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Returns the size of a Player's hand
     *
     * @return
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * Returns the Player's points
     *
     * @return
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds the given points to the Player
     *
     * @param var
     */
    public void addPoints(int var) {
        points += var;
    }

    /**
     * returns index of selected card
     * @return
     */
    public int getSelectedCardIndex() {return selectedCardIndex;}

    /**
     * increments selected card by one (true = right, false = left)
     * @param b
     */
    public void incrementSelectedCardIndex(Boolean b) {
        if (b) {
            selectedCardIndex = (selectedCardIndex + 1) % hand.size();
        } else {
            selectedCardIndex--;
            if (selectedCardIndex<0) selectedCardIndex=hand.size()-1;
        }
    }

    /**
     * Sorts the Player's hand based on the Card's type and Suit - higher types are right bound and red is greater than
     * black. Use of Comparable allows Collections.sort() to be used
     */
    public void sortHand() {
        Collections.sort(hand);
    }

    /**
     * Empties hand for new game
     */
    public void emptyHand() {
        selectedCardIndex=0;
        hand = new ArrayList<>();
    }

    /**
     * Adds the Card c to the player's hand in sorted order
     *
     * @param c
     */
    public void getCard(Card c) {
        hand.add(c);
        sortHand();
    }

    /**
     * peeks at given card
     * @param index
     * @return
     */
    public Card peekCard(int index) {
        return hand.get(index);
    }

    /**
     * returns the card at index i and deletes from hand
     *
     * @param index
     */
    public Card playCard(int index) {
        incrementSelectedCardIndex(false);
        return hand.remove(index);
    }
}
