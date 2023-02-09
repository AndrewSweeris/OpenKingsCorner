package com.sweeris.openkingscorner;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * Class used to represent the deck of cards that is drawn from
 */
public class Deck {
    private ArrayList<Card> deck;

    /**
     * Constructor method that creates the Deck and then shuffles it
     */
    public Deck() {
        deck = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            deck.add(new Card(Suit.CLUB, i));
            deck.add(new Card(Suit.DIAMOND,i));
            deck.add(new Card(Suit.HEART, i));
            deck.add(new Card(Suit.SPADE,i));
        }
        shuffle();
    }

    /**
     * Function that randomly shuffles every card in the deck
     */
    public void shuffle() {
        ArrayList<Card> temp = new ArrayList<>();
        while (!deck.isEmpty()) {
            int i = (int)(Math.random()*deck.size());
            Card c = deck.get(i);
            deck.remove(i);
            temp.add(c);
        }
        deck = temp;
    }

    /**
     * Returns the size of the deck
     * @return
     */
    public int getDeckSize() {
        return deck.size();
    }

    /**
     * Returns the top Card of the deck and then removes it from the deck.
     * @return
     */
    public Card drawCard() {
        Card c = deck.get(deck.size()-1);
        deck.remove(deck.size()-1);
        return c;
    }
}
