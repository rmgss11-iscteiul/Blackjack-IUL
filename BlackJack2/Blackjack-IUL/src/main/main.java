package main;
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
		Human p1= new Human(dealer,"Ricado");
		BlackjackGUI gui=new BlackjackGUI(dealer);
		dealer.start();
	}
}
