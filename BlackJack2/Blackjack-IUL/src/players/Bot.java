 package players;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import cards.Card;

public class Bot extends Player {
	public Random r;

	public Bot(Dealer dealer, String name) {
		super(dealer, name);
		r = new Random();
	}

	public void refreshBotGui() {
		getDealer().getGui().refreshBot(this);
	}

	public static double sleepTime() {
		double sigma = 0.6;
		double um = 2.5;
		double p, p1, p2;
		Random r = new Random();
		do {
			p1 = (1 - r.nextDouble() * 2);
			p2 = (1 - r.nextDouble() * 2);
			p = p1 * p1 + p2 * p2;
		} while (p >= 1.0);
		return um + sigma * p1 * Math.sqrt(-2 * Math.log(p) / p);
	}

	public int betMoney() {
		Random r = new Random();
		double temp = r.nextDouble();
		if (temp < 0.25) {
			return (int) (getMoney() * 0.1);
		}
		if (temp > 0.95) {
			return (getMoney());
		} else {
			return (int) ((temp / 2) * getMoney());
		}
	}

	public boolean botFazerDouble() {
		Random r = new Random();
		double temp = r.nextDouble();
		if (temp < 0.2)
			return true;
		return false;
	}

	@Override
	public void play() {
		super.play();
		this.setBet(betMoney());
		while (!isplayFinish() && ldw == null) {
			try {
				TimeUnit.MILLISECONDS.sleep((int) (sleepTime() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (super.getPoints() > 14 && super.getPoints() < 17 && botFazerDouble() && this.hand.size() == 2) {
				doubleBet();
			} else if (getPoints() >= 17) {
				stand();
			} else { // points < 17
				hit();
			}
			refreshBotGui();
		}
	}

	@Override
	public void win() {
		super.win();
		super.ldw = loseDrawWin.WIN;
		refreshBotGui();
	}

	@Override
	public void draw() {
		super.draw();
		super.ldw = loseDrawWin.DRAW;
		refreshBotGui();
	}

	@Override
	public void lose() {
		super.lose();
		super.ldw = loseDrawWin.LOSE;
		refreshBotGui();
	}

	@Override
	public void bet() {
		super.bet();
		if (getBet() == 0) {
			this.setBet(10);// r.nextInt(getMoney())); // VAREAVEL ALEATORIA
			this.setMoney(this.getMoney() - getBet());
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
