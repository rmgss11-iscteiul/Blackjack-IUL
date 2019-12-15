package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import cards.Card;
import cards.Deck;
import players.Bot;
import players.Dealer;
import players.Player;

public class TesteGUI {
	JFrame frame;
	static final File imageFolder = new File("CardImages");

	public TesteGUI() {
		frame = new JFrame("Blackjack");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		//		frame.getContentPane().setBackground( Color.red );
		addFrameContent();
		frame.pack();
		frame.setSize(800, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}




	private void addFrameContent() {
		Deck d= new Deck(1);
		JPanel p= new JPanel();
		for (Card card:d.cards){
			ImageIcon cardImage = new ImageIcon(imageFolder.getName()+"/"+card.toString()+".png");			
			JLabel cardLabel= new JLabel();
			cardLabel.setIcon(cardImage);
			p.add(cardLabel);
		}
		frame.add(p);
//		for (Card card: d.cards){
//			ImageIcon cardImage = new ImageIcon(imageFolder.getName()+"/"+card.toString()+".png");			
//			JLabel cardLabel= new JLabel();
//			cardLabel.setIcon(cardImage);
//			frame.add(cardLabel);
//		}
	}
	public static void main(String[] args) {
		TesteGUI g= new TesteGUI();
	}
}