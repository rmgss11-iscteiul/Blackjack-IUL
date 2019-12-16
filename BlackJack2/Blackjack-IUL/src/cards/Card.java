package cards;

public class Card {
	public Suit suit;
	public Number number;

	public enum Suit {
		SPADES, HEARTS, CLUBS, DIAMONDS;
	}

	public enum Number {
		ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(
				12), KING(13);
		public int id;

		Number(int id) {
			this.id = id;
		}
	}

	public Card(Number number, Suit suit) {
		this.number = number;
		this.suit = suit;
	}

	@Override
	public String toString() {
		return this.number + " OF " + this.suit;
	}

}
