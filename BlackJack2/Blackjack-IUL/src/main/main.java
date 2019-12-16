 package main;

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

	public static void main(String[] args) {
		Dealer dealer = new Dealer("Tozé");
		Bot b1 = new Bot(dealer, "Bot1");
		// Bot b2 = new Bot(dealer, "Bot2");
		// Bot b3 = new Bot(dealer, "Bot3");
		// Bot b4 = new Bot(dealer, "Bot4");
		Human p1 = new Human(dealer, name());
		BlackjackGUI gui = new BlackjackGUI(dealer);
		dealer.start();
	}
}
