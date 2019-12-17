 package main;

import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import GUI.BlackjackGUI;
import players.Bot;
import players.Dealer;
import players.Human;

public class main {

	private static String name() {
		JTextField text = new JTextField();
		JOptionPane.showMessageDialog(null, text, "Name: ", JOptionPane.DEFAULT_OPTION);
		String name = "Anonymous";
		if (!text.getText().equals(""))
			name = text.getText();
		return name;
	}

	public static int numberOfBots() {
		Random random = new Random();
		double r = random.nextDouble();
		if (r < 0.2) {
			return 0;
		}
		if (r > 0.2 && r < 0.9) {
			return 2;
		} else {
			return 4;
		}
	}

	public static void main(String[] args) {
//		int numberOfBots = numberOfBots();
		Dealer dealer = new Dealer("Tozé");
//		if (numberOfBots > 0) {Bot b1 = new Bot(dealer, "Bot1");}
//		if (numberOfBots > 1) {Bot b2 = new Bot(dealer, "Bot2");}
//		if (numberOfBots > 2) {Bot b3 = new Bot(dealer, "Bot3");}
//		if (numberOfBots > 3) {Bot b4 = new Bot(dealer, "Bot4");}
		Human p1 = new Human(dealer, name());
		BlackjackGUI gui = new BlackjackGUI(dealer);
		dealer.start();
	}
}
