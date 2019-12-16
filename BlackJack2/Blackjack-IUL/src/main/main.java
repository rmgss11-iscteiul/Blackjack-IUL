package main;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import GUI.BlackjackGUI;
import players.Bot;
import players.Dealer;
import players.Human;

public class main {
	//	private static int inteiro=1;

	public static void main(String[] args) {
		Dealer dealer= new Dealer("Tozé");
		//		Bot b1 = new Bot(dealer, "Bot1");
		//		Bot b2 = new Bot(dealer, "Bot2");
		//		Bot b3 = new Bot(dealer, "Bot3");
		//		Bot b4 = new Bot(dealer, "Bot4");
		JTextField name = new JTextField();
		JOptionPane.showMessageDialog(null, name,"Name: ", JOptionPane.DEFAULT_OPTION);
		String str= "Anonymous";
		if(!name.getText().equals(""))
			str = name.getText();
		Human p1= new Human(dealer,str);
		BlackjackGUI gui=new BlackjackGUI(dealer);
		dealer.start();
	}
}
