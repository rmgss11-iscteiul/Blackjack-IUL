 package cards;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	public ArrayList<Card> cards;

	public Deck(int decks) {
		this.cards = new ArrayList<Card>();
		for (int i = 0; i < decks; i++) {
			for (Card.Suit suit : Card.Suit.values()) {
				for (Card.Number number : Card.Number.values()) {
						cards.add(new Card(number, suit));		
				}
			}
		}
	}
	

	public Card removeCard() {
		Random r = new Random();
		int c = r.nextInt(cards.size());
		return cards.remove(c);

	}
}
