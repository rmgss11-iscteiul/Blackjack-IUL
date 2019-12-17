package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cards.Card;
import players.Bot;
import players.Dealer;
import players.Human;
import players.Player;

public class BlackjackGUI {
	private JFrame frame;
	private Dealer dealer;
	private JPanel info;
	private JPanel buttons;
	private JPanel dealerPanel;
	private JPanel humanCardsPanel;
	static final File imageFolder = new File("CardImages");
	private Map<Player, JPanel> botPanels = new HashMap<Player, JPanel>();

	public BlackjackGUI(Dealer dealer) {
		this.dealer = dealer;
		dealer.setGui(this);
		frame = new JFrame("Blackjack");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		addFrameContent();
		frame.pack();
		frame.setSize(802, 451);
		frame.setMinimumSize(new Dimension(802, 451));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		refreshButtons(dealer.getHuman());
	}

	private void addFrameContent() {
		JPanel table = new JPanel(new GridLayout(1, 3));
		JPanel leftTable = new JPanel(new GridLayout(2, 1));
		JPanel centerTable = new JPanel(new GridLayout(2, 1));
		JPanel rightTable = new JPanel(new GridLayout(2, 1));
		centerTable.add(dealerPanel());
		centerTable.add(humanCardsPanel());
		int numberOfPlayers = dealer.getPlayers().size();
		if (numberOfPlayers < 5) {
			JPanel temp = new JPanel();
			temp.setBackground(new Color(18, 163, 25));
			rightTable.add(temp);
		}
		if (numberOfPlayers < 4) {
			JPanel temp = new JPanel();
			temp.setBackground(new Color(18, 163, 25));
			leftTable.add(temp);
		}
		if (numberOfPlayers < 3) {
			JPanel temp = new JPanel();
			temp.setBackground(new Color(18, 163, 25));
			rightTable.add(temp);
		}
		if (numberOfPlayers < 2) {
			JPanel temp = new JPanel();
			temp.setBackground(new Color(18, 163, 25));
			leftTable.add(temp);
		}
		for (int i = 0; i < dealer.getPlayers().size(); i++) {
			Player player = dealer.getPlayers().get(i);
			if (player instanceof Bot) {
				JPanel botPanel = botPanel((Bot) player);
				botPanels.put(player, botPanel);
				if (i % 2 == 0)
					leftTable.add(botPanel);
				else
					rightTable.add(botPanel);
			}
		}
		table.add(leftTable);
		table.add(centerTable);
		table.add(rightTable);
		frame.add(table);
		frame.add(bottomPanel(), BorderLayout.SOUTH);
	}

	private JPanel humanCardsPanel() {
		humanCardsPanel = new JPanel();
		humanCardsPanel.setLayout(new BoxLayout(humanCardsPanel, BoxLayout.Y_AXIS));
		JLabel splitldw = new JLabel("", SwingConstants.CENTER);
		JPanel split = new JPanel();
		JLabel ldw = new JLabel("", SwingConstants.CENTER);
		JPanel cards = new JPanel();
		cards.setMinimumSize(new Dimension(cards.getMinimumSize().width, 15));
		split.setMinimumSize(new Dimension(cards.getMinimumSize().width, 15));
		cards.setBackground(new Color(18, 163, 25));
		split.setBackground(new Color(18, 163, 25));
		humanCardsPanel.setBackground(new Color(18, 163, 25));
		humanCardsPanel.add(splitldw);
		humanCardsPanel.add(split);// splitCards
		humanCardsPanel.add(ldw); // ldw
		humanCardsPanel.add(cards); // cartas
		return humanCardsPanel;
	}

	private JPanel dealerPanel() {
		dealerPanel = new JPanel();
		dealerPanel.setBackground(new Color(18, 163, 25));
		dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
		JLabel name = new JLabel("Name: " + dealer.toString());
		JLabel points = new JLabel("Points: " + dealer.getPoints());
		JPanel cards = new JPanel();
		cards.setMinimumSize(new Dimension(cards.getMinimumSize().width, 15)); // ficar a info do dealer
		cards.setBackground(new Color(18, 163, 25));
		dealerPanel.add(name);
		dealerPanel.add(points);
		dealerPanel.add(cards);
		return dealerPanel;
	}

	private JPanel botPanel(Bot bot) { // so para bots
		JPanel botPanel = new JPanel();
		botPanel.setBackground(new Color(18, 163, 25));
		botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.Y_AXIS));
		JLabel name = new JLabel("Name: " + bot.toString());
		JLabel money = new JLabel("Money: " + bot.getMoney());
		JLabel bet = new JLabel("Bet: " + bot.getBet());
		// JLabel insurance = new JLabel("Insurance: "); // TODO boolean insurance ou
		// int
		JLabel points = new JLabel("Points: " + bot.getPoints());
		JPanel cards = new JPanel();
		JLabel ldw = new JLabel();
		cards.setMinimumSize(new Dimension(cards.getMinimumSize().width, 15)); // fixar a info do bot
		name.setHorizontalAlignment(SwingConstants.LEFT);
		money.setHorizontalAlignment(SwingConstants.LEFT);
		// insurance.setHorizontalAlignment(SwingConstants.LEFT);
		points.setHorizontalAlignment(SwingConstants.LEFT);
		cards.setBackground(new Color(18, 163, 25));
		botPanel.add(name);
		botPanel.add(money);
		botPanel.add(bet);
		// botPanel.add(insurance);
		botPanel.add(points);
		botPanel.add(ldw);
		botPanel.add(cards);
		return botPanel;
	}

	public void refreshBot(Bot player) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel panel = botPanels.get(player);
				((JLabel) panel.getComponent(1)).setText("Money: " + player.getMoney());// dinheiro
				((JLabel) panel.getComponent(2)).setText("Bet: " + player.getBet());// aposta
				// ((JLabel) panel.getComponent(3)).setText("Insurance: "); // TODO boolean
				// insurance ou int);//seguro
				((JLabel) panel.getComponent(3)).setText("Points: " + player.getPoints());
				JPanel cardsPanel = (JPanel) panel.getComponent(5);
				if (player.ldw != null)
					((JLabel) panel.getComponent(4)).setText(player.ldw.toString());
				else
					((JLabel) panel.getComponent(4)).setText("");

				cardsPanel.removeAll();
				for (Card card : player.getHand()) {
					ImageIcon cardImage = new ImageIcon(imageFolder.getName() + "/" + card.toString() + ".png");
					JLabel cardLabel = new JLabel();
					cardLabel.setIcon(cardImage);
					cardsPanel.add(cardLabel);
				}
				cardsPanel.revalidate();
				cardsPanel.repaint();
			}
		});
	}

	public void refreshHuman() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Human human = dealer.getHuman();
				refreshInfo(human);
				refreshButtons(human);
				refreshHumanCards(human);
			}
		});
	}

	private void refreshButtons(Human human) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				
				
				
				if(!human.isPlaying()) {
					buttons.getComponent(0).setEnabled(false); // stand
					buttons.getComponent(1).setEnabled(false); // hit
					buttons.getComponent(2).setEnabled(false); // doubleBet
					buttons.getComponent(3).setEnabled(false); // split
					buttons.getComponent(4).setEnabled(false); // insurance
				}else {
					buttons.getComponent(0).setEnabled(true); // stand
					buttons.getComponent(1).setEnabled(true); // hit
					if (human.getHand().size() == 2)
					buttons.getComponent(2).setEnabled(true); // doubleBet
					else
						buttons.getComponent(2).setEnabled(false); // doubleBet
					if (human.getHand().size() == 2 && !human.isplayFinish() && !human.isHandSplited()
							&& Player.twoEqualCards(human.getHand()) != -1)
					buttons.getComponent(3).setEnabled(true); // split
					else
					buttons.getComponent(3).setEnabled(false); // split
					if(dealer.getHand().get(0).number.equals(Card.Number.ACE)&&human.getInsurance()==0 &&human.getHand().size() == 2 && !human.isHandSplited())
					buttons.getComponent(4).setEnabled(true); // insurance
					else
					buttons.getComponent(4).setEnabled(false); // insurance
					
				}
				if (dealer.isRoundFinish() || human.getBet() == 0) {
					buttons.getComponent(5).setEnabled(true); // bet TextField
					buttons.getComponent(6).setEnabled(true); // bet button
				} else {
					buttons.getComponent(5).setEnabled(false); // bet TextField
					buttons.getComponent(6).setEnabled(false); // bet button
				}
//				
				
				
				
				
				
				
				
				
				
				
				
				
			}
		});

	}

	private void refreshHumanCards(Human human) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel cardsHuman = (JPanel) humanCardsPanel.getComponent(3);// cartas
				cardsHuman.removeAll();
				JPanel splitHuman = (JPanel) humanCardsPanel.getComponent(1);// spliHand
				splitHuman.removeAll();
				for (Card card : human.getSplitHand()) {
					ImageIcon cardImage = new ImageIcon(imageFolder.getName() + "/" + card.toString() + ".png");
					JLabel cardLabel = new JLabel();
					cardLabel.setIcon(cardImage);
					splitHuman.add(cardLabel);
					// System.out.println(human.getSplitHand() + "Human split");
				}
				for (Card card : human.getHand()) {
					ImageIcon cardImage = new ImageIcon(imageFolder.getName() + "/" + card.toString() + ".png");
					JLabel cardLabel = new JLabel();
					cardLabel.setIcon(cardImage);
					cardsHuman.add(cardLabel);
					// System.out.println(human.getHand() + " Human hand");
				}
				cardsHuman.revalidate();
				cardsHuman.repaint();
				splitHuman.revalidate();
				splitHuman.repaint();
			}
		});
	}

	private void refreshInfo(Human human) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				((JLabel) info.getComponent(1)).setText("Money: " + human.getMoney());// money
				((JLabel) info.getComponent(2)).setText("Insurance: ");// TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				info.getComponent(2).setVisible(false); // insurance label
				if (human.getInsurance() != 0)
					info.getComponent(2).setVisible(true); // insurance label
				((JLabel) info.getComponent(3)).setText("Points: " + human.getPoints());// points
				((JLabel) info.getComponent(4)).setText("Bet: " + human.getBet());// points
				((JLabel) info.getComponent(5)).setText("Split Points: " + human.getSplitPoints());
				if (human.splitldw == null) {
					((JLabel) humanCardsPanel.getComponent(0)).setText("");
				} else {
					((JLabel) humanCardsPanel.getComponent(0)).setText(human.splitldw.toString());
				}
				if (human.ldw == null) {
					((JLabel) humanCardsPanel.getComponent(2)).setText("");
				} else {
					((JLabel) humanCardsPanel.getComponent(2)).setText(human.ldw.toString());
				}
			}
		});
	}

	public void refreshDealer() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				((JLabel) dealerPanel.getComponent(1)).setText("Points: " + dealer.getPoints());
				JPanel cardsPanel = (JPanel) dealerPanel.getComponent(2);
				cardsPanel.removeAll();
				for (Card card : dealer.getHand()) {
					ImageIcon cardImage = new ImageIcon(imageFolder.getName() + "/" + card.toString() + ".png");
					JLabel cardLabel = new JLabel();
					cardLabel.setIcon(cardImage);
					cardsPanel.add(cardLabel);

				}
				if (dealer.getHiddenCard() != null) {
					ImageIcon cardImage = new ImageIcon(imageFolder.getName() + "/CardBack.png");
					JLabel cardLabel = new JLabel();
					cardLabel.setIcon(cardImage);
					cardsPanel.add(cardLabel);
				}
				cardsPanel.revalidate();
				cardsPanel.repaint();
			}
		});
	}

	private JPanel bottomPanel() {
		Player human = dealer.getHuman();
		JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		info = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		buttons.setBackground(new Color(18, 163, 25));
		info.setBackground(new Color(18, 163, 25));
		JButton standButton = new JButton("Stand");
		JButton hitButton = new JButton("Hit");
		JButton doubleBetButton = new JButton("Double");
		JButton splitButton = new JButton("Split");
		JButton insuranceButton = new JButton("Insurance");
		JTextField betNumber = new JTextField();
		betNumber.setPreferredSize(new Dimension(50, 25));
		JButton setButton = new JButton("Bet");
		buttons.add(standButton);
		buttons.add(hitButton);
		buttons.add(doubleBetButton);
		buttons.add(splitButton);
		buttons.add(insuranceButton);
		buttons.add(betNumber);
		buttons.add(setButton);
		JLabel nameLabel = new JLabel("Name: " + human.toString());
		JLabel moneyLabel = new JLabel("Money: " + human.getMoney());
		JLabel insuranceLabel = new JLabel("Insurance: ");///////// TODO
		insuranceLabel.setVisible(false);
		JLabel pointsLabel = new JLabel("Points: " + human.getPoints());
		JLabel bet = new JLabel("Bet: " + human.getBet());
		JLabel splitPointsLabel = new JLabel("Split Points: " + human.getSplitPoints());
		info.add(nameLabel);
		info.add(moneyLabel);
		info.add(insuranceLabel);
		info.add(pointsLabel);
		info.add(bet);
		info.add(splitPointsLabel);
		bottomPanel.add(buttons);
		bottomPanel.add(info);
		return bottomPanel;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public JPanel getInfo() {
		return info;
	}

	public void setInfo(JPanel info) {
		this.info = info;
	}

	public JPanel getButtons() {
		return buttons;
	}

	public void setButtons(JPanel buttons) {
		this.buttons = buttons;
	}

	public JPanel getDealerPanel() {
		return dealerPanel;
	}

	public void setDealerPanel(JPanel dealerPanel) {
		this.dealerPanel = dealerPanel;
	}

	public JPanel getHumanCadsPanel() {
		return humanCardsPanel;
	}

	public void setHumanCadsPanel(JPanel humanCadsPanel) {
		this.humanCardsPanel = humanCadsPanel;
	}

	public Map<Player, JPanel> getBotPanels() {
		return botPanels;
	}

	public void setBotPanels(Map<Player, JPanel> botPanels) {
		this.botPanels = botPanels;
	}

}
