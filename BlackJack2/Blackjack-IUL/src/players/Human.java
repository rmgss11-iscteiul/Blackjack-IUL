package players;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cards.Card;

public class Human extends Player { 

	ActionListener buttonsListener=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String source = ((JButton) e.getSource()).getText();
			switch(source) {
			case("Hit"):
				hit();
			break;
			case("Double"):
				doubleBet();
			break;
			case("Stand"):
				stand();
			break;
			case("Split"):
				split();
			break;
			case("Insurance"):
//				insurance();
				System.out.println("Botão ainda não foi implementado");
			break;
			}

			refreshHumanGui();
		}
	};

	public Human(Dealer dealer, String name) {
		super(dealer, name);
	}
	public void refreshHumanGui(){
		getDealer().getGui().refreshHuman();
	}

	@Override
	public void	play() {
		super.play();
		JButton stand = (JButton) getDealer().getGui().getButtons().getComponent(0);
		JButton hit = (JButton) getDealer().getGui().getButtons().getComponent(1);
		JButton doubleBet = (JButton) getDealer().getGui().getButtons().getComponent(2);
		JButton split = (JButton) getDealer().getGui().getButtons().getComponent(3);
		JButton insurance = (JButton) getDealer().getGui().getButtons().getComponent(4);
		stand.addActionListener(buttonsListener);
		hit.addActionListener(buttonsListener);
		doubleBet.addActionListener(buttonsListener);
		split.addActionListener(buttonsListener);
		insurance.addActionListener(buttonsListener);
		while((!isplayFinish() && ldw==null)) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		stand.removeActionListener(buttonsListener);
		hit.removeActionListener(buttonsListener);
		doubleBet.removeActionListener(buttonsListener);
		split.removeActionListener(buttonsListener);
		insurance.removeActionListener(buttonsListener);
	}


	@Override
	public void win() {
		super.win();
		refreshHumanGui();
	}
	@Override
	public void draw() {
		super.draw();
		refreshHumanGui();
	}
	@Override
	public void lose() {
		super.lose();
		refreshHumanGui();
	}

	@Override
	public void bet() {
		super.bet();
		JButton betButton = (JButton) getDealer().getGui().getButtons().getComponent(6);
		betButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String betString= ((JTextField) getDealer().getGui().getButtons().getComponent(5)).getText();
				try{
					int betNumber= Integer.parseInt(betString);
					if(getMoney()-betNumber>0) {
						setBet(betNumber);
						setMoney(getMoney()-getBet());
					}else {
						JOptionPane.showMessageDialog(null, "Não pode apostar mais que o dinhiro que tem");
					}
				}
				catch(NumberFormatException exception){
					JOptionPane.showMessageDialog(null, "Introduza Numeros Inteiros");
				}
			}
		});
		while(getBet()==0) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		betButton.removeActionListener(buttonsListener);
		refreshHumanGui();
	}

	@Override 
	public void addCard(Card card) {
		super.addCard(card);
		refreshHumanGui();
	}
	
	@Override 
	public void addSplitedCard(Card card) {
		super.addSplitedCard(card);
		refreshHumanGui();
	}
	@Override
	public void newRound() {
		super.newRound();
		refreshHumanGui();
	}

}

