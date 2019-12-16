package players;

import java.util.ArrayList;

import cards.Card;

public abstract class Person {
	ArrayList<Card> hand;
	private int points;
	private String name;
	boolean blackjack;

	Person(String name) {
		this.name = name;
		hand = new ArrayList<Card>();
	}

	public void addCard(Card card) {
		hand.add(card);
		countPoints();
	}

	public void clearHand() {
		hand = new ArrayList<Card>();
		points = 0;
	}

	private void countPoints() {
		points = 0;
		if (hand.size() != 0) {
			int numberOfAces = 0;
			for (Card card : hand) {
				if (card.number == Card.Number.JACK || card.number == Card.Number.QUEEN
						|| card.number == Card.Number.KING) {
					points += 10;
				} else if (card.number != Card.Number.ACE) {
					points += card.number.id;
				} else {
					numberOfAces++;
					points += 11;
				}
				while (points > 21 && numberOfAces != 0) {
					points -= 10;
					numberOfAces--;
				}
			}
		}
		if (hand.size() == 2 && points == 21)
			blackjack = true;
	}

	@Override
	public String toString() {
		return name;
	}

//	public String deck2String() {
//		String temp = "";
//		for (Card c : this.getHand()) {
//			temp += c.toString();
//			temp += "\n";
//		}
//		return temp.substring(0, temp.length() - 2);
//	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBlackJack() {
		return blackjack;
	}

	public void setBlackjack(boolean blackjack) {
		this.blackjack = blackjack;
	}
}
