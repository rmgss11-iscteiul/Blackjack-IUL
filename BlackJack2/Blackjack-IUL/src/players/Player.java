package players;

import java.util.ArrayList;
import java.util.Random;
import cards.Card;
import cards.Card.Number;

public abstract class Player extends Person {
 
	private int money, splitPoints, bet, splitMoney, insurance;
	private Dealer dealer;
	private boolean playFinish, splitHandFinish, handSplited;
	private ArrayList<Card> splitHand;

	public enum loseDrawWin {
		WIN, DRAW, LOSE;
	}

	public loseDrawWin ldw;
	public loseDrawWin splitldw;

	public Player(Dealer dealer, String name) {
		super(name);
		dealer.registerPlayer(this);
		this.dealer = dealer;
		money = (int) initialMoney();
		bet = 0;
		splitMoney = 0;
		splitPoints = 0;
		insurance=0;
		playFinish = false;
		handSplited = false;
		splitHandFinish = false;
		splitHand = new ArrayList<Card>();
	}

	public int getInsurance() {
		return insurance;
	}

	public void setInsurance(int insurance) {
		this.insurance = insurance;
	}

	public double initialMoney() {
		int sigma = 200;
		int mu = 700;
		double p, p1, p2;
		Random r = new Random();
		do {
			p1 = (1 - r.nextDouble() * 2);
			p2 = (1 - r.nextDouble() * 2);
			p = p1 * p1 + p2 * p2;
		} while (p >= 1.0);
		return mu + sigma * p1 * Math.sqrt(-2 * Math.log(p) / p);
	}

	public void bet() {
	}

	public void hit() {
		if (handSplited && !splitHandFinish) { // && splitPoints<21
			// sleep x segundos
			splitHand.add(dealer.deal());
			countSplitPoints(splitHand);
			if (splitPoints > 21) {
				// splitHandFinish=true;
				splitLose();
			}
		} else {
			super.addCard(dealer.deal());
			if (super.getPoints() > 21)
				lose();
		}
	}

	public void stand() {
		if (handSplited && !splitHandFinish)
			splitHandFinish = true;
		else
			playFinish = true;
	}

	public void doubleBet() {
		if (handSplited && !splitHandFinish) {
			if (money - splitMoney >= 0 && splitHand.size() == 2) {
				splitMoney *= 2;
				hit();
				stand();
			}

		} else {

			if (money - bet >= 0 && hand.size() == 2) {
				bet *= 2;
				hit();
				stand();
			}
		}
	}

	public int getSplitMoney() {
		return splitMoney;
	}

	public void setSplitMoney(int splitMoney) {
		this.splitMoney = splitMoney;
	}

	public void split() {
		if (twoEqualCards(super.getHand()) != -1 && money - bet >= 0 && super.getHand().size() == 2 && !handSplited) {
			splitMoney = bet;
			money -= splitMoney;
			handSplited = true;
			if (twoEqualCards(super.getHand()) == 1) {
				splitHand.add(super.getHand().remove(0));
//				try {
//					TimeUnit.SECONDS.sleep(1);
					splitHand.add(dealer.deal());
//					TimeUnit.SECONDS.sleep(1);
					super.addCard(dealer.deal());
//				} catch (InterruptedException e) {
//				}
				if (splitHand.get(1).number == Number.TEN || splitHand.get(1).number == Number.JACK
						|| splitHand.get(1).number == Number.QUEEN || splitHand.get(1).number == Number.KING) {
					// money += splitMoney * 2;
					splitHandFinish = true;
					winSplit();
					// SINTO QUE FALTAM AQUI COISAS
				}
				if (super.getHand().get(1).number == Number.TEN || super.getHand().get(1).number == Number.JACK
						|| super.getHand().get(1).number == Number.QUEEN
						|| super.getHand().get(1).number == Number.KING) {
					playFinish = true;
					win();
				}
				// Acabar a rounda para a splitHand
				countSplitPoints(splitHand);
				splitHandFinish = true;

				// Acabar a rounda para o hand normal
				stand();
			} else {
				Card c = super.getHand().remove(0);
				addSplitedCard(c);
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//
//				}
				addSplitedCard(dealer.deal());
				countSplitPoints(splitHand);
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				super.addCard(dealer.deal());
			}
		}

	}

	public void addSplitedCard(Card card) {
		splitHand.add(card);
	}

	private void countSplitPoints(ArrayList<Card> list) {
		if (hand.size() != 0) {
			splitPoints = 0;
			int numberOfAces = 0;
			for (Card card : splitHand) {
				if (card.number == Card.Number.JACK || card.number == Card.Number.QUEEN
						|| card.number == Card.Number.KING) {
					splitPoints += 10;
				} else if (card.number != Card.Number.ACE) {
					splitPoints += card.number.id;
				} else {
					numberOfAces++;
					splitPoints += 11;
				}
				while (splitPoints > 21 && numberOfAces != 0) {
					splitPoints -= 10;
					numberOfAces--;
				}
			}
		}
		if (hand.size() == 2 && splitPoints == 21)
			blackjack = true;
	}

	/**
	 * 
	 * @param list
	 * @return 1:two aces; 0:two equal cards; -1:not equal
	 */
	public static int twoEqualCards(ArrayList<Card> list) {

		if (list.get(0).number.id == list.get(1).number.id) {
			if (list.get(0).number == Number.ACE && list.get(1).number == Number.ACE) {
				return 1;
			} else {
				return 0;
			}
		}
		return -1;
	}

	public void play() {
		if (blackjack == true) {
			money += Math.round(bet + (bet*1.5));
			bet = 0;
			playFinish = true;
		}
	}

	public void win() {
		this.ldw = loseDrawWin.WIN;
		money += bet * 2;
		bet = 0;
	}

	public void winSplit() {
		this.splitldw = loseDrawWin.WIN;
		money += splitMoney * 2;
		splitMoney = 0;
	}

	public void draw() {
		this.ldw = loseDrawWin.DRAW;
		money += bet;
		bet = 0;
	}

	public void splitDraw() {
		this.splitldw = loseDrawWin.DRAW;
		money += splitMoney;
		splitMoney = 0;
	}

	public void lose() {
		this.ldw = loseDrawWin.LOSE;
		// dealer.addLoser();
		bet = 0;
	}

	public void splitLose() {
		splitldw = loseDrawWin.LOSE;
		splitHandFinish = true;
		splitMoney = 0;
	}

	public void newRound() {

		splitHand.clear();
		splitHandFinish = false;
		handSplited = false;
		splitldw = null;
		blackjack=false;
		super.clearHand();
		playFinish = false;
		ldw = null;
		splitPoints=0;
	}

	// public void endSplit() {
	// }

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public boolean isplayFinish() {
		return playFinish;
	}

	public boolean isSplitHandFinish() {
		return splitHandFinish;
	}

	// public boolean isSplitHandCheck() {
	// return splitHandCheck;
	// }

	// public void setEndPlay(boolean endPlay) {
	// this.playFinish = endPlay;
	// }

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public ArrayList<Card> getSplitHand() {
		return splitHand;
	}

	public int getSplitPoints() {
		return splitPoints;
	}

	public boolean isHandSplited() {
		return handSplited;
	}
}
