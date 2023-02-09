package com.sweeris.openkingscorner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import static java.lang.System.exit;

public class OpenKingsCorner extends com.badlogic.gdx.Game {

	SpriteBatch batch;
	BitmapFont font;
	Texture cardBack, logo, playButton, howToPlayButton, quitButton, backButton, match, one, player, round, three, two, winner, zero;
	Display display;
	Deck deck;
	Pile L, TL, T, TR, R, BR, B, BL;
	Player playerZero, playerOne, playerTwo, playerThree;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		display = Display.MAIN; // sets current display to Main Menu

		// Objects to play the game
		deck = new Deck();
		L = new Pile(false);
		TL = new Pile(true);
		T = new Pile(false);
		TR = new Pile(true);
		R = new Pile(false);
		BR = new Pile(true);
		B = new Pile(false);
		BL = new Pile(true);

		// loads players
		playerZero = new Player();
		playerOne = new Player();
		playerTwo = new Player();
		playerThree = new Player();

		// loads cards
		cardBack = new Texture("card_back.png");
		logo = new Texture("Logo.png");
		playButton = new Texture("PlayButton.png");
		howToPlayButton = new Texture("HowToPlayButton.png");
		quitButton = new Texture("QuitButton.png");
		backButton = new Texture("Back.png");

		// loads text
		match = new Texture("Match.png");
		round = new Texture("Round.png");
		zero = new Texture("Zero.png");
		one = new Texture("One.png");
		two = new Texture("Two.png");
		three = new Texture("Three.png");
		player = new Texture("Player.png");
		winner = new Texture("Winner.png");
	}

	private Texture getTexture(Card c) {
		return getTexture(c.getSuit(),c.getType());
	}
	private Texture getTexture(Suit suit, int type) {
		String textureName = "";
		if (suit==Suit.CLUB) {
			textureName += "card_clubs_";
		} else if (suit==Suit.DIAMOND) {
			textureName += "card_diamonds_";
		} else if (suit==Suit.HEART) {
			textureName += "card_hearts_";
		} else if (suit==Suit.SPADE){
			textureName += "card_spades_";
		}
		int temp = type + 1;
		if (temp ==1) {
			textureName+="A";
		} else if (temp==11) {
			textureName+="J";
		} else if (temp==12) {
			textureName+="Q";
		} else if (temp==13) {
			textureName+="K";
		} else if (temp==10) {
			textureName+="" + temp;
		} else {
			textureName+="0"+temp;
		}
		textureName += ".png";
		return new Texture(textureName);
	}

	/**
	 * Sets up new match
	 */
	private void newMatch() {
		// resets player points
		playerZero = new Player();
		playerOne = new Player();
		playerTwo = new Player();
		playerThree = new Player();

		newRound();
	}

	/**
	 * Sets up new round
	 */
	private void newRound() {
		gameOver = false;

		playerZero.startTurn();
		// creates new Deck and Piles
		deck = new Deck();
		L = new Pile(false);
		TL = new Pile(true);
		T = new Pile(false);
		TR = new Pile(true);
		R = new Pile(false);
		BR = new Pile(true);
		B = new Pile(false);
		BL = new Pile(true);

		// empties the players hands
		playerZero.emptyHand();
		playerOne.emptyHand();
		playerTwo.emptyHand();
		playerThree.emptyHand();

		// draws the players initial seven cards
		for (int i = 0; i < 7; i++) {
			playerZero.getCard(deck.drawCard());
			playerOne.getCard(deck.drawCard());
			playerTwo.getCard(deck.drawCard());
			playerThree.getCard(deck.drawCard());
		}

		// places first four cards, or as many cards until all sides of cross filled
		while (B.isEmpty()) {
			Card c = deck.drawCard();
			if (c.getType()==12) { // if a king is drawn, it is placed in a corner
				if (TL.isEmpty()) {
					TL.playCard(c);
				} else if (TR.isEmpty()) {
					TR.playCard(c);
				} else if (BR.isEmpty()) {
					BR.playCard(c);
				} else {
					BL.playCard(c);
				}
			}
			else { // if a king isn't drawn, the card is placed normally
				if (L.isEmpty()) {
					L.takeStartingCard(c);
				} else if (T.isEmpty()) {
					T.takeStartingCard(c);
				} else if (R.isEmpty()) {
					R.takeStartingCard(c);
				} else {
					B.takeStartingCard(c);
				}
			}
		}
		playerZero.getCard(deck.drawCard());
	}

	/**
	 * Method cleans up render() and renders all piles (plus center card if deck isn't empty)
	 */
	private void pileRender() {
		// This will render all of the cards currently on the table for every pile
		ArrayList<Card> tempList;
		Sprite tempCard;
		if (!L.isEmpty()) { // renders left pile
			tempList = L.getList();
			for (int i = 0; i < tempList.size(); i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(216 - 10 * i, 268);
				tempCard.setRotation(90);
				tempCard.draw(batch);
			}
		}
		if (!TL.isEmpty()) { // renders top left pile
			tempList = TL.getList();
			for (int i = 0; i < tempList.size(); i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(206 - 10 * i, 332 + 10 * i);
				tempCard.setRotation(45);
				tempCard.draw(batch);
			}
		}
		if (!T.isEmpty()) { // renders top pile
			tempList = T.getList();
			for (int i = 0; i < tempList.size();i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(268, 332 + 10 * i);
				tempCard.draw(batch);
			}
		}
		if (!TR.isEmpty()) { // renders top right pile
			tempList = TR.getList();
			for (int i = 0; i < tempList.size(); i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(330 + 10 * i, 332 + 10 * i);
				tempCard.setRotation(135);
				tempCard.draw(batch);
			}
		}
		if (!R.isEmpty()) { // renders right pile
			tempList = R.getList();
			for (int i = 0; i < tempList.size(); i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(320 + 10 * i, 268);
				tempCard.setRotation(270);
				tempCard.draw(batch);
			}
		}
		if (!BR.isEmpty()) { // renders bottom right pile
			tempList = BR.getList();
			for (int i = 0; i < tempList.size(); i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(330 + 10 * i, 206 - 10 * i);
				tempCard.setRotation(225);
				tempCard.draw(batch);
			}
		}
		if (!B.isEmpty()) { // renders bottom pile
			tempList = B.getList();
			for (int i = 0; i < tempList.size();i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(268, 204 - 10 * i);
				tempCard.draw(batch);
			}
		}
		if (!BL.isEmpty()) { // renders bottom left pile
			tempList = BL.getList();
			for (int i = 0; i < tempList.size(); i++) {
				tempCard = new Sprite(getTexture(tempList.get(i).getSuit(), tempList.get(i).getType()));
				tempCard.setPosition(206 - 10 * i, 206 - 10 * i);
				tempCard.setRotation(315);
				tempCard.draw(batch);
			}
		}

		// renders deck in the center of the cards if the deck isn't empty
		if (deck.getDeckSize()>0) {
			Sprite deck = new Sprite(cardBack);
			deck.setPosition(268,268);
			deck.draw(batch);
		}
	}

	/**
	 * Method cleans up render() and renders all Player hands
	 */
	private void handRender() {
		Sprite tempCard;

		// PlayerZero render
		for (int i = 0; i < playerZero.getHandSize(); i++) {
			tempCard = new Sprite(getTexture(playerZero.getHand().get(i)));
			if (playerZero.getSelectedCardIndex()==i) {
				tempCard.setPosition((268 - (20 * playerZero.getHandSize() / 2)) + 20 * i, 60);
			}
			else {
				tempCard.setPosition((268 - (20 * playerZero.getHandSize() / 2)) + 20 * i, 40);
			}
			tempCard.draw(batch);
			tempCard = null;
		}

		// PlayerOne render
		for (int i = 0; i < playerOne.getHandSize(); i++) {
			tempCard = new Sprite(cardBack);
			tempCard.setRotation(270);
			tempCard.setPosition(8, 268 - (20 * playerOne.getHandSize() / 2) + 20 * i);
			tempCard.draw(batch);
			tempCard = null;
		}
		// PlayerTwo render
		for (int i = 0; i < playerTwo.getHandSize(); i++) {
			tempCard = new Sprite(cardBack);
			tempCard.setPosition(268 - (20 * playerOne.getHandSize() / 2) + 20 * i, 500);
			tempCard.draw(batch);
			tempCard = null;
		}
		// PlayerThree render
		for (int i = 0; i < playerThree.getHandSize(); i++) {
			tempCard = new Sprite(cardBack);
			tempCard.setRotation(90);
			tempCard.setPosition(528, 268 - (20 * playerOne.getHandSize() / 2) + 20 * i);
			tempCard.draw(batch);
			tempCard = null;
		}
	}

	/**
	 * Renders a display of the points of each player
	 */
	private void pointsRender() {
		font.draw(batch, "Your Points - " + playerZero.getPoints() + "\nPlayer One Points - " + playerOne.getPoints() +
				"\nPlayer Two Points - " + playerTwo.getPoints() + "\nPlayer Three Points - " + playerThree.getPoints(), 10, 580);
	}

	/**
	 * Renders a display of the players, and who's turn it is
	 */
	private void playerRender() {
		// for Player
		font.draw(batch, "Your Hand", 255, 40);
		if (playerZero.isTurn()) {
			font.draw(batch, "YOUR TURN", 245, 20);
		}

		// for Player One
		font.draw(batch, "P\nL\nA\nY\nE\nR\n\nO\nN\nE", 80, 370);
		if (playerOne.isTurn()) {
			font.draw(batch, "T\nH\nE\nI\nR\n\nT\nU\nR\nN", 120, 370);
		}

		// for Player Two
		font.draw(batch, "PLAYER TWO", 255, 580);
		if (playerTwo.isTurn()) {
			font.draw(batch, "THEIR TURN", 255, 500);
		}

		// for Player Three
		font.draw(batch, "P\nL\nA\nY\nE\nR\n\nT\nH\nR\nE\nE", 510, 370);
		if (playerThree.isTurn()) {
			font.draw(batch, "T\nH\nE\nI\nR\n\nT\nU\nR\nN", 470, 370);
		}
	}

	/**
	 * Returns if the point limit has been exceeded and a winner can be determined
	 * @return
	 */
	private boolean noTieGameOver() {
		int[] ary = {playerZero.getPoints(), playerOne.getPoints(), playerTwo.getPoints(), playerThree.getPoints()};
		int winner = 0;
		boolean isTie = false;
		for (int i = 1; i < 4; i++) {
			if (ary[i]<ary[winner]) {
				isTie = false;
				winner=i;
			}
			if (ary[i]==ary[winner]) isTie=true;
		}
		return isTie;
	}

	boolean gameOver = false;
	long then;
	long now;
	private boolean roundOver() {
		if (!gameOver&&(playerZero.getHandSize()==0||playerOne.getHandSize()==0||playerTwo.getHandSize()==0||playerThree.getHandSize()==0)) {
			then = TimeUtils.millis();
			gameOver = true;

			// placing point addition here prevents games from going past limit
			playerZero.addPoints(getPoints(playerZero.getHand()));
			playerOne.addPoints(getPoints(playerOne.getHand()));
			playerTwo.addPoints(getPoints(playerTwo.getHand()));
			playerThree.addPoints(getPoints(playerThree.getHand()));
		}
		return playerZero.getHandSize()==0||playerOne.getHandSize()==0||playerTwo.getHandSize()==0||playerThree.getHandSize()==0;
	}

	private int getPoints(ArrayList<Card> ary) {
		int x = 0;
		for (Card c : ary) {
			if (c.getType()==12)x+=10;
			else
				x++;
		}
		return x;
	}

	private Pile getPile(int i) {
		if (i==0) return L;
		if (i==1) return TL;
		if (i==2) return T;
		if (i==3) return TR;
		if (i==4) return R;
		if (i==5) return BR;
		if (i==6) return B;
		else
			return BL;
	}

	/**
	 * checks if piles can merge onto each other
	 * @param bottom
	 * @param top
	 * @return
	 */
	private boolean canMerge(int bottom, int top) {
		if (!getPile(bottom).isEmpty()&&!getPile(top).isEmpty()) {
			return getPile(bottom).isValid(getPile(top));
		}
		else
			return false;
	}

	/**
	 * merges bottom and top pile if possible
	 * @param bottom
	 * @param top
	 */
	private void merge(int bottom, int top) {
		if (canMerge(bottom,top)) {
			getPile(bottom).mergePile(getPile(top));
			getPile(top).emptyPile();
		}
	}

	// timer so player can see enemy actions
	long aiTimerStart, aiTimerEnd;

	// used to enable multiple key presses
	boolean isPlacing=false, isMerging=false, actionTaken=false;

	// used to hold bottom pile
	int bottomMerge=-1;
	@Override
	public void render () {
		ScreenUtils.clear(0, .4f, 0.2f, 1);
		batch.begin();

		// displays the main menu
		if (display==Display.MAIN) {
			Sprite temp;

			// displays the logo of the game
			Sprite Logo = new Sprite(logo);
			Logo.setScale(4f);
			Logo.setPosition(Gdx.graphics.getWidth()*.2f, Gdx.graphics.getHeight()*.8f);
			Logo.draw(batch);

			// display game buttons
			Sprite playButton = new Sprite(this.playButton);
			Sprite howToPlayButton = new Sprite(this.howToPlayButton);
			Sprite quitButton = new Sprite(this.quitButton);

			playButton.setScale(2f);
			howToPlayButton.setScale(2f);
			quitButton.setScale(2f);

			playButton.setPosition(Gdx.graphics.getWidth()*.1f, Gdx.graphics.getHeight()*.6f);
			howToPlayButton.setPosition(Gdx.graphics.getWidth()*.17f, Gdx.graphics.getHeight()*.5f);
			quitButton.setPosition(Gdx.graphics.getWidth()*.1f, Gdx.graphics.getHeight()*.4f);

			playButton.draw(batch);
			howToPlayButton.draw(batch);
			quitButton.draw(batch);

			// accounts for button presses
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
				display = Display.GAME;
				newMatch();
			}if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
				display = Display.HOWTOPLAY;
			}if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
				exit(0);
			}

			// displays cards as filler texture
			for (int i = 0; i < 13; i++) {
				temp = new Sprite(getTexture(Suit.CLUB, i));
				temp.setPosition(400, 450 - 30 * i);
				temp.draw(batch);
				temp = new Sprite(getTexture(Suit.DIAMOND, i));
				temp.setPosition(400+40, 450 - 30 * i);
				temp.draw(batch);
				temp = new Sprite(getTexture(Suit.HEART, i));
				temp.setPosition(400+80, 450 - 30 * i);
				temp.draw(batch);
				temp = new Sprite(getTexture(Suit.SPADE, i));
				temp.setPosition(400+120, 450 - 30 * i);
				temp.draw(batch);
			}

			// writes game credits
			font.draw(batch, "Game programmed by Andrew Sweeris. \nPlaying card graphics created by Kenney (www.kenney.nl)", 20, 60);
		}
		else if (display==Display.HOWTOPLAY) {
			// display game buttons
			Sprite back = new Sprite(backButton);
			back.setScale(2f);
			back.setPosition(Gdx.graphics.getWidth()*.1f, Gdx.graphics.getHeight()*.9f);
			back.draw(batch);

			// accounts for button presses
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
				display = Display.MAIN;
			}

			// writes game instructions and how to play.
			font.draw(batch, "Open Kings Corner is a game played completely with the keyboard. When it is \n" +
								 "your turn, you will select your card by using the arrow keys LEFT and RIGHT,\n" +
								 "and then choosing your action. You may do the following:\n\n" +
								 " - Play a card onto a pile by pressing P, then selecting the pile by pressing\n" +
								 "   0-7 (with 0-7 representing the game piles)\n\n" +
								 " - Merge two piles by pressing M, then selecting the two piles (BOTTOM, TOP) by\n" +
								 "   pressing 0-7 twice.\n\n" +
								 " - Shuffle the deck by pressing S\n\n" +
								 " - End your turn by pressing E\n\n" +
								 "If an invalid move is chosen the game will not play it. A valid move is when the\n" +
								 "player plays a card onto another card which is of a type one higher (Queen on\n" +
								 "top of King) and an opposite color. This applies with merges as the topCard of\n" +
								 "the bottom deck and the bottomCard of the topDeck should be able to be played\n" +
								 "upon each other. A valid move is also when any card is played on an empty pile\n" +
					 			 "that isn't a corner, or a king is played on an empty corner.\n\n" +
								 "A round ends when one player runs out of cards, and the other players tally their\n" +
								 "points (king is worth 10 points, other cards are worth 1 point), and when a player\n" +
								 "goes over 25 points the game ends, and the player with the least points wins. If\n" +
								 "there is a tie, another round is played so that a winner can be determined.", 20, 500);
		}
		else {
			aiTimerEnd =System.currentTimeMillis();

			// renders piles
			pileRender();

			// renders hands
			handRender();

			// points render
			pointsRender();

			// renders names and who's current
			playerRender();

			// checks if there isn't a tie and someone's gone over the point limit, then displays the match winner
			if ((playerZero.getPoints()>=25||playerOne.getPoints()>=25||playerTwo.getPoints()>=25||playerThree.getPoints()>=25)&&noTieGameOver()) {
				int[] ary = {playerZero.getPoints(), playerOne.getPoints(), playerTwo.getPoints(), playerThree.getPoints()};
				int winner = 0;
				for (int i = 1; i < 4; i++) {
					if (ary[i]<ary[winner]) {
						winner=i;
					}
				}
				// displays match winner
				Sprite MA = new Sprite(match);
				Sprite win = new Sprite(this.winner);
				Sprite play = new Sprite(player);
				Sprite winn;
				if (winner==0) {
					winn = new Sprite(this.zero);
				}
				else if (winner == 1) {
					winn = new Sprite(this.one);
				}
				else if (winner == 2) {
					winn = new Sprite(this.two);
				} else {
					winn = new Sprite(this.three);
				}

				// sets positions
				MA.setPosition(300, 500);
				win.setPosition(300, 400);
				play.setPosition(300, 300);
				winn.setPosition(300, 200);

				// sets scales
				MA.setScale(4f);
				win.setScale(4f);
				play.setScale(4f);
				winn.setScale(4f);

				// draws images
				MA.draw(batch);
				win.draw(batch);
				play.draw(batch);
				winn.draw(batch);
			}
			// checks if someone's out of cards, and then displays round winner
			else if (roundOver()) {
				now = TimeUtils.millis();
				if (now-then>=5000) { // starts a new round
					if (!((playerZero.getPoints()>=25||playerOne.getPoints()>=25||playerTwo.getPoints()>=25||playerThree.getPoints()>=25)&&noTieGameOver())) {
						newRound();
						gameOver = false;
					}
				}
				else {
					// displays round winner
					Sprite rou = new Sprite(round);
					Sprite win = new Sprite(this.winner);
					Sprite play = new Sprite(player);
					Sprite winn;
					if (playerZero.getHandSize()==0) {
						winn = new Sprite(this.zero);
					}
					else if (playerOne.getHandSize()==0) {
						winn = new Sprite(this.one);
					}
					else if (playerTwo.getHandSize()==0) {
						winn = new Sprite(this.two);
					} else {
						winn = new Sprite(this.three);
					}

					// sets position
					rou.setPosition(300, 500);
					win.setPosition(300, 400);
					play.setPosition(300, 300);
					winn.setPosition(300, 200);

					// sets to better scale
					rou.setScale(4f);
					win.setScale(4f);
					play.setScale(4f);
					winn.setScale(4f);

					// draws images
					rou.draw(batch);
					win.draw(batch);
					play.draw(batch);
					winn.draw(batch);

				}
			}
			else {
				// Here the enemy AI and the player act

				actionTaken = true; // used for AI end turn
				if (playerZero.isTurn()) {

					if (isPlacing) { // handles player placing cards
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
							if (L.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								L.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
							if (TL.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								TL.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
							if (T.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								T.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
							if (TR.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								TR.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
							if (R.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								R.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
							if (BR.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								BR.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
							if (B.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								B.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
							if (BL.isValid(playerZero.peekCard(playerZero.getSelectedCardIndex()))) {
								BL.playCard(playerZero.playCard(playerZero.getSelectedCardIndex()));
							}
						}
					}
					if (isMerging) { // handles player merging piles
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
							if (bottomMerge==-1) {
								bottomMerge=0;
							} else {
								merge(bottomMerge, 0);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
							if (bottomMerge==-1) {
								bottomMerge=1;
							} else {
								merge(bottomMerge, 1);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
							if (bottomMerge==-1) {
								bottomMerge=2;
							} else {
								merge(bottomMerge, 2);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
							if (bottomMerge==-1) {
								bottomMerge=3;
							} else {
								merge(bottomMerge, 3);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
							if (bottomMerge==-1) {
								bottomMerge=4;
							} else {
								merge(bottomMerge, 4);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
							if (bottomMerge==-1) {
								bottomMerge=5;
							} else {
								merge(bottomMerge, 5);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
							if (bottomMerge==-1) {
								bottomMerge=6;
							} else {
								merge(bottomMerge, 6);
							}
						}
						if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
							if (bottomMerge==-1) {
								bottomMerge=7;
							} else {
								merge(bottomMerge, 7);
							}
						}
					}

					if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { // selects card to left
						playerZero.incrementSelectedCardIndex(false);
					}
					if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { // selects card to right
						playerZero.incrementSelectedCardIndex(true);
					}
					if (Gdx.input.isKeyJustPressed(Input.Keys.S)) { // allows player to shuffle cards at will
						deck.shuffle();
					}
					if (Gdx.input.isKeyJustPressed(Input.Keys.P)) { // enables place mode
						isPlacing=true;
						isMerging=false;
						bottomMerge = -1;
					}
					if (Gdx.input.isKeyJustPressed(Input.Keys.M)) { // enables merge mode
						isPlacing=false;
						isMerging=true;
						bottomMerge = -1;
					}
					if (Gdx.input.isKeyJustPressed(Input.Keys.E)) { // ends turn
						playerZero.endTurn();
						playerOne.startTurn();
						if (deck.getDeckSize()>0)
							playerOne.getCard(deck.drawCard());
						isMerging=false;
						isPlacing=false;
						bottomMerge = -1;
						aiTimerStart=System.currentTimeMillis();
					}


					// For testing purposes

					/*
					if (aiTimerEnd-aiTimerStart>50) {
						aiTimerStart=TimeUtils.millis();
						actionTaken=false;
						play(playerZero);
						if (!actionTaken) {
							playerZero.endTurn();
							playerOne.startTurn();
							if (deck.getDeckSize()>0)
								playerOne.getCard(deck.drawCard());
							aiTimerStart=TimeUtils.millis();
						}
					}
					 */


				}
				else if (playerOne.isTurn()) { // AI-1 Turn
					if (aiTimerEnd-aiTimerStart>700) {
						aiTimerStart=TimeUtils.millis();
						actionTaken=false;
						play(playerOne);
						if (!actionTaken) {
							playerOne.endTurn();
							playerTwo.startTurn();
							if (deck.getDeckSize()>0)
								playerTwo.getCard(deck.drawCard());
							aiTimerStart=TimeUtils.millis();
						}
					}
				}
				else if (playerTwo.isTurn()) { // AI-2 Turn
					if (aiTimerEnd-aiTimerStart>700) {
						aiTimerStart=TimeUtils.millis();
						actionTaken=false;
						play(playerTwo);
						if (!actionTaken) {
							playerTwo.endTurn();
							playerThree.startTurn();
							if (deck.getDeckSize() > 0)
								playerThree.getCard(deck.drawCard());
							aiTimerStart = TimeUtils.millis();
						}
					}
				}
				else if (playerThree.isTurn()) { // AI-3 Turn
					if (aiTimerEnd-aiTimerStart>700) {
						aiTimerStart=TimeUtils.millis();
						actionTaken=false;
						play(playerThree);
						if (!actionTaken) {
							playerThree.endTurn();
							playerZero.startTurn();
							if (deck.getDeckSize() > 0)
								playerZero.getCard(deck.drawCard());
							aiTimerStart = TimeUtils.millis();
						}
					}
				}
			}
		}
		batch.end();
	}

	/**
	 * Helper function that runs the enemy AI. Prioritizes first merging, then playing cards from top to bottom.
	 * The return will let the Ai timer work
	 * @param p
	 */
	private boolean play(Player p) {
		for (int i = 0; i < 8; i++) { // attempts all possible merges first
			for (int j = 0; j < 8; j++) {
				if (i!=j) {
					if (canMerge(i,j)) {
						actionTaken=true;
						merge(i, j);
						return true;
					}
				}
			}
		}
		for (int i = 0; i < 8; i++) { // plays all possible cards
			for (int j = 0; j < p.getHandSize(); j++) {
				if (getPile(i).isValid(p.peekCard(j))) {
					actionTaken=true;
					getPile(i).playCard(p.playCard(j));
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public void dispose () {
		batch.dispose();
		cardBack.dispose(); logo.dispose();
		playButton.dispose();
		howToPlayButton.dispose();
		quitButton.dispose();
		backButton.dispose();
		match.dispose();
		one.dispose();
		player.dispose();
		round.dispose();
		three.dispose();
		two.dispose();
		winner.dispose();
		zero.dispose();
	}
}
