package players;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import GUI.BlackjackGUI;
import cards.Card;
import cards.Deck;

public class Dealer extends Person{ 
	private Deck deck;
	private ArrayList <Player> players;
//	private int playersinGame;
	private BlackjackGUI gui;
	private Human human;
	private Card hiddenCard;
	
	public Dealer(String name) {
		super(name);
		this.deck=new Deck(5); //////////////////////////////possivel variavel
		players= new ArrayList<Player>();
//		playersinGame=0;
		human=null;
		hiddenCard = null;
	}

	public void registerPlayer(Player player) {
		players.add(player);
//		playersinGame++;
	}
//	public void addLoser() {
//		playersinGame--;
//	}

	public void start() {
		while(true) { //por condicao de gameOver
			collectBets();
			startRound(); //player nao ter dinheiro ou carregar em acabar jogo
			round();
			endRound();			
		}
	}

	public void collectBets() {
		for(Player player: players) {
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
		this.newRound();
		for(Player player: players)
			player.newRound();
		deck.removeCard();//queimar cartas
		deck.removeCard();
		for(int i=0;i<2;i++) {
			for(Player player: players) { 
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.addCard(deck.removeCard());
			}try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i==0)
			this.addCard(deck.removeCard());
			if(i==1) {
				this.hiddenCard = deck.removeCard();
				refreshDealer();
			}
		}
	}

	public void round() {
		for(Player player: players) 
			player.play();

	}

	@Override
	public void addCard(Card card) {
		super.addCard(card);
		refreshDealer();
	}
	
	public void endRound() {
			this.addCard(hiddenCard);
			hiddenCard = null;
			while(this.getPoints()<17) {
				addCard(deck.removeCard());
			}
			for(Player player:players) {
				if(player.ldw==null) {
					if((super.getPoints() < player.getSplitPoints() || super.getPoints()>21) && player.isSplitHandFinish()) {
						player.winSplit();//basta um destes
//						player.endSplit();
					}else if(super.getPoints()==player.getSplitPoints() && player.isSplitHandFinish()) {
						player.splitDraw();
//						player.endSplit();					
					}
					else if(super.getPoints()> player.getSplitPoints() && player.isSplitHandFinish()) {
						player.splitLose();
//						player.endSplit();						
					}			
					if((super.getPoints()<player.getPoints() || super.getPoints()>21) && player.isplayFinish()) {
						player.win();						
					}
					else if(super.getPoints()==player.getPoints() && player.isplayFinish())
						player.draw();
					else if(super.getPoints()> player.getPoints() && player.isplayFinish())
						player.lose();
				}
			}
	}

	public Player getHuman() {
		if(human == null)
			for(Player temp: players)
				if(temp instanceof Human)
					human=(Human) temp;
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

//	public int getPlayersinGame() {
//		return playersinGame;
//	}
//
//	public void setPlayersinGame(int playersinGame) {
//		this.playersinGame = playersinGame;
//	}

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
}




