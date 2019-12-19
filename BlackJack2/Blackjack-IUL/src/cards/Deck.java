 package cards;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	public ArrayList<Card> cards;
	private int decks;

	public Deck(int decks) {
		this.decks = decks;
		this.cards = new ArrayList<Card>();
		for (int i = 0; i < this.decks; i++) {
			for (Card.Suit suit : Card.Suit.values()) {
				for (Card.Number number : Card.Number.values()) {
						cards.add(new Card(number, suit));		
				}
			}
		}
	}
	

	public Card removeCard() {
		if(cards.size()==0) {
			Deck deckTemp = new Deck(decks);
			this.cards= deckTemp.cards;
		}
		System.out.println(cards.size());
		Random r = new Random();
		int c = r.nextInt(cards.size());
		return cards.remove(c);

	}
}
