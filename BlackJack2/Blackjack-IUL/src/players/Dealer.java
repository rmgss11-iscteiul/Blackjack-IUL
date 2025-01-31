package players;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import GUI.BlackjackGUI;
import cards.Card;
import cards.Deck;

public class Dealer extends Person {
	private Deck deck;
	private ArrayList<Player> players;
	private ArrayList<Player> losers;
	 private int playersinGame;
	private BlackjackGUI gui;
	private Human human;
	private Card hiddenCard;
	private boolean roundFinish;

	public Dealer(String name) {
		super(name);
		this.deck = new Deck(numberOfDecks());
		players = new ArrayList<Player>();
		losers = new ArrayList<Player>();
		 playersinGame=0;
		human = null;
		hiddenCard = null;
	}

	public int numberOfDecks() {
		Random random = new Random();
		double r = random.nextDouble();
		if (r < 0.1) {
			return 1;
		}
		if (r > 0.1 && r < 0.3) {
			return 2;
		}
		if (r > 0.3 && r < 0.7) {
			return 4;
		}
		if (r > 0.7 && r < 0.9) {
			return 6;
		} else {
			return 8;
		}
	}

	public void registerPlayer(Player player) {
		players.add(player);
		 playersinGame++;
	}
	 public void addLoser() {
	 playersinGame--;
	 }

	public void start() {
		while (true) { // por condicao de gameOver
			collectBets();
			startRound(); // player nao ter dinheiro ou carregar em acabar jogo
			round();
			endRound();
		}
	}

	public void collectBets() {
		for (Player player : players) {
			player.bet();
		}
	}

	public void refreshDealer() {
		this.gui.refreshDealer();
	}

	private void newRound() {
		clearHand();
		refreshDealer();
	}

	public void startRound() {
		roundFinish = false;
		this.newRound();
		for (Player player : players)
			player.newRound();
		deck.removeCard();// queimar cartas
		deck.removeCard();
		for (int i = 0; i < 2; i++) {
			for (Player player : players) {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.addCard(deck.removeCard());
			}
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (i == 0)
				this.addCard(deck.removeCard());
			if (i == 1) {
				this.hiddenCard = deck.removeCard();
				refreshDealer();
			}
		}
	}

	public boolean isRoundFinish() {
		return roundFinish;
	}

	public void setRoundFinish(boolean roundFinish) {
		this.roundFinish = roundFinish;
	}

	public void round() {
		for (Player player : players)
			player.play();

	}

	@Override
	public void addCard(Card card) {
		super.addCard(card);
		refreshDealer();
	}

	public void endRound() {
		removeLosers();
		// ainad estiver algum que nao tenha perdido
		boolean anyPlayerInGame = false;
		for (Player p : players) {
			if (p.ldw == null || (p.splitldw == null && p.isHandSplited())) {
				anyPlayerInGame = true;
				break;
			}
		}
		
		if (anyPlayerInGame) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.addCard(hiddenCard);
			hiddenCard = null;
			while (this.getPoints() < 17) { // so precisa de tirar cartas se ainda houver jogadores em jogo
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addCard(deck.removeCard());
			}
			for (Player player : players) {
				if (player.ldw == null || player.isHandSplited() || player.getInsurance() != 0) {

					// Testa BlackJacks
					if (super.blackjack == true || player.blackjack == true) {
						if (super.blackjack == true && player.blackjack == true) {
							player.draw();
							player.acquireInsurance();
						} else if (super.blackjack == true && player.blackjack == false) {
							player.lose();
							player.acquireInsurance();
						} else if (super.blackjack == false && player.blackjack == true)
							player.win();

					}
					if ((super.blackjack == true || player.splitBlackJack == true) && player.isHandSplited()) {
						if (super.blackjack == true && player.splitBlackJack == true) {
							player.splitDraw();
							player.acquireInsurance();
						} else if (super.blackjack == true && player.splitBlackJack == false) {
							player.splitLose();
							player.acquireInsurance();
						} else if (super.blackjack == false && player.splitBlackJack == true)
							player.winSplit();

					} else {

						// Testa SplitPoints
						if (player.getSplitPoints() < 22 && player.splitBlackJack == false && player.isHandSplited()) {
							if ((super.getPoints() < player.getSplitPoints() || super.getPoints() > 21)
									&& player.isSplitHandFinish()) {
								player.winSplit();// basta um destes
								// player.endSplit();
							} else if (super.getPoints() == player.getSplitPoints() && player.isSplitHandFinish()) {
								player.splitDraw();
								// player.endSplit();
							} else if (super.getPoints() > player.getSplitPoints() && player.isSplitHandFinish()) {
								player.splitLose();
								// player.endSplit();
							}
						}

						// Testa Points
						if (player.blackjack == false) {
							if ((super.getPoints() < player.getPoints() || super.getPoints() > 21)
									&& player.isplayFinish())
								player.win();
							else if (super.getPoints() == player.getPoints() && player.isplayFinish())
								player.draw();
							else if (super.getPoints() > player.getPoints() && player.isplayFinish())
								player.lose();

						}
						if (player.getPoints() > 21)
							player.lose();
						if (player.getSplitPoints() > 21)
							player.splitLose();
					}
				}
			}
		}
		super.blackjack = false;
		hiddenCard = null;
		roundFinish = true;
		human.refreshHumanGui();
	}

	public void removeLosers() {
		players.removeAll(losers);
	}

	public Human getHuman() {
		if (human == null)
			for (Player temp : players)
				if (temp instanceof Human)
					human = (Human) temp;
		return human;
	}

	public Card deal() {
		return deck.removeCard();
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	 public int getPlayersinGame() {
	 return playersinGame;
	 }
	
	 public void setPlayersinGame(int playersinGame) {
	 this.playersinGame = playersinGame;
	 }

	public BlackjackGUI getGui() {
		return gui;
	}

	public void setGui(BlackjackGUI blackjackGUI) {
		this.gui = blackjackGUI;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public Card getHiddenCard() {
		return hiddenCard;
	}

	public void setHiddenCard(Card hiddenCard) {
		this.hiddenCard = hiddenCard;
	}

	public ArrayList<Player> getLosers() {
		return losers;
	}

	public void setLosers(ArrayList<Player> losers) {
		this.losers = losers;
	}
}
