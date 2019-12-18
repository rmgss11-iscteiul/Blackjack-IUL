 package cards;

import java.util.ArrayList;
import java.util.Random;

import cards.Card.Number;

public class Deck {
	public ArrayList<Card> cards;

	public Deck(int decks) {
		this.cards = new ArrayList<Card>();
		for (int i = 0; i < decks; i++) {
			for (Card.Suit suit : Card.Suit.values()) {
				for (Card.Number number : Card.Number.values()) {
//					if(i%2==0)
//						cards.add(new Card( Number.ACE,suit));
//					else
						cards.add(new Card(number, suit));					
//				}
//					cards.add(new Card(number,suit));
//					cards.add(new Card( Number.FIVE,suit));
//					cards.add(new Card( Number.TWO, suit));
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
