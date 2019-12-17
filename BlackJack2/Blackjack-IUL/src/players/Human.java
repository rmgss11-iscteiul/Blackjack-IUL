package players;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cards.Card;

public class Human extends Player {

	private boolean isPlaying;

	ActionListener buttonsListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String source = ((JButton) e.getSource()).getText();
			switch (source) {
			case ("Hit"):
				hit();
				break;
			case ("Double"):
				doubleBet();
				break;
			case ("Stand"):
				stand();
				break;
			case ("Split"):
				split();
				break;
			case ("Insurance"):
				// insurance();
				System.out.println("Botão ainda não foi implementado");
				break;
			}

			refreshHumanGui();
		}
	};

	public Human(Dealer dealer, String name) {
		super(dealer, name);
	}

	public void refreshHumanGui() {
		getDealer().getGui().refreshHuman();
	}

	@Override
	public void play() {
		super.play();
		isPlaying = true;
		refreshHumanGui();
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
		while ((!isplayFinish() && ldw == null)) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		isPlaying = false;
		stand.removeActionListener(buttonsListener);
		hit.removeActionListener(buttonsListener);
		doubleBet.removeActionListener(buttonsListener);
		split.removeActionListener(buttonsListener);
		insurance.removeActionListener(buttonsListener);
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
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

<<<<<<< HEAD
//	@Override
//<<<<<<< HEAD
//	public void bet() {
//		super.bet();
//		JButton betButton = (JButton) getDealer().getGui().getButtons().getComponent(6);
//		betButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String betString= ((JTextField) getDealer().getGui().getButtons().getComponent(5)).getText();
//				try{
//					int betNumber= Integer.parseInt(betString);
//					if(getMoney()-betNumber>=0) {
//						setBet(betNumber);
//						setMoney(getMoney()-getBet());
//					}else {
//						JOptionPane.showMessageDialog(null, "Não pode apostar mais que o dinhiro que tem");
//=======
	public void bet() {
		if (getMoney() == 0) {
			String[] buttons = { "Quit", "Reset Money" };
			int returnValue = JOptionPane.showOptionDialog(null, "Play Again?", "Game Over", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
			if (returnValue == 0) {// quit
				getDealer().getGui().getFrame()
						.dispatchEvent(new WindowEvent(getDealer().getGui().getFrame(), WindowEvent.WINDOW_CLOSING));
			} else if (returnValue == 1) {// reset Money
=======
	@Override
	public void bet()  {
		if(getMoney()==0) {
			String[] buttons = { "Quit", "Reset Money"};    
			int returnValue = JOptionPane.showOptionDialog(null, "Play Again?","Game Over",
					JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
			if(returnValue== 0 || returnValue == JOptionPane.CLOSED_OPTION) {//quit
				getDealer().getGui().getFrame().dispatchEvent(new WindowEvent(getDealer().getGui().getFrame(), WindowEvent.WINDOW_CLOSING));
			}else if(returnValue== 1) {//reset Money
>>>>>>> branch 'master' of https://github.com/rmgss11-iscteiul/Blackjack-IUL.git
				setMoney((int) initialMoney());
				refreshHumanGui();
				bet();
			}
<<<<<<< HEAD
		} else {
			JButton betButton = (JButton) getDealer().getGui().getButtons().getComponent(6);
			getDealer().getGui().getFrame().getRootPane().setDefaultButton(betButton);
			super.bet();
			ActionListener betListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String betString = ((JTextField) getDealer().getGui().getButtons().getComponent(5)).getText();
=======
		}else{
				super.bet();
				JButton betButton = (JButton) getDealer().getGui().getButtons().getComponent(6);
				getDealer().getGui().getFrame().getRootPane().setDefaultButton(betButton);
				ActionListener betListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String betString= ((JTextField) getDealer().getGui().getButtons().getComponent(5)).getText();
						try{
							int betNumber= Integer.parseInt(betString);
							if(betNumber>0) {
								if(getMoney()-betNumber>=0) {
									setBet(betNumber);
									setMoney(getMoney()-getBet());
								}else {
									JOptionPane.showMessageDialog(null, "Não pode apostar mais que o dinheiro que tem");
								}
							}else
								JOptionPane.showMessageDialog(null, "Introduza Números Positivos");
						}
						catch(NumberFormatException exception){
							JOptionPane.showMessageDialog(null, "Introduza Numeros Inteiros");
						}
					}
				};
				betButton.addActionListener(betListener);
				while(getBet()==0) {
>>>>>>> branch 'master' of https://github.com/rmgss11-iscteiul/Blackjack-IUL.git
					try {
<<<<<<< HEAD
						int betNumber = Integer.parseInt(betString);
						if (betNumber > 0) {
							if (getMoney() - betNumber >= 0) {
								setBet(betNumber);
								setMoney(getMoney() - getBet());
							} else {
								JOptionPane.showMessageDialog(null, "Não pode apostar mais que o dinheiro que tem");
							}
						} else
							JOptionPane.showMessageDialog(null, "Introduza Números Positivos");
					} catch (NumberFormatException exception) {
						JOptionPane.showMessageDialog(null, "Introduza Numeros Inteiros");
=======
						wait();
					} catch (Exception e) {
>>>>>>> branch 'master' of https://github.com/rmgss11-iscteiul/Blackjack-IUL.git
					}
				}
			};
			betButton.addActionListener(betListener);
			while (getBet() == 0) {
				try {
					wait();
				} catch (Exception e) {

				}
			}
			betButton.removeActionListener(betListener);
			refreshHumanGui();
		}
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
