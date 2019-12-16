 package players;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import cards.Card;

public class Bot extends Player {
	public Random r;
	
	public Bot(Dealer dealer, String name) {
		super(dealer,name);
		r= new Random();
	}
	
	public void refreshBotGui(){
		getDealer().getGui().refreshBot(this);
	}
	
	@Override
	public void play() {
		super.play();
		this.setBet(10);////////////////////////VAREAVEL ALEATORIA
		while(!isplayFinish() && ldw==null) {//player.ldw!=loseDrawWin.LOSE
			try {
				TimeUnit.SECONDS.sleep(2);//VAREAVEL ALEATORIA
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if( super.getPoints()>14 && super.getPoints()<17 && r.nextBoolean() && this.hand.size()==2) {//podia ser geometrica
				doubleBet();
			}
			else if(getPoints()>=17) {
				stand();
			}
			else { // points < 17
				hit();
			}
			refreshBotGui();
		}
	}
	
	@Override
	public void win() {
		super.win();
		super.ldw=loseDrawWin.WIN;
		refreshBotGui();
	}
	@Override
	public void draw() {
		super.draw();
		super.ldw=loseDrawWin.DRAW;
		refreshBotGui();
	}
	@Override
	public void lose() {
		super.lose();
		super.ldw=loseDrawWin.LOSE;
		refreshBotGui();
	}
	
	
	@Override
	public void bet() {
		super.bet();
		if(getBet()==0) {
			this.setBet(10);//r.nextInt(getMoney())); // VAREAVEL ALEATORIA
			this.setMoney(this.getMoney()-getBet());
		}
		refreshBotGui();
	}
	
	@Override 
	public void addCard(Card card) {
		super.addCard(card);
		refreshBotGui();
	}

	@Override
	public void newRound() {
		super.newRound();
		refreshBotGui();
	}

}
