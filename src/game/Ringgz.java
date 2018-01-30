package game;

import players.*;

/**
 * Executable class for the game Ringgz.
 * Programming project 2017-2018.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Ringgz {
	public static void main(String[] args) {
		Board myBoard = new Board();
		HumanPlayer player1 = new HumanPlayer("Wouter", Color.RED, Color.PURPLE, myBoard, 2);
		//HumanPlayer player2 = new HumanPlayer("Wiljan", Color.YELLOW, Color.GREEN, myBoard, 2);
		//ComputerPlayer player1 = new ComputerPlayer("ComputerWouter", Color.RED, Color.PURPLE, myBoard, 2);
		ComputerPlayer player2 = new ComputerPlayer("ComputerWiljan", Color.YELLOW, Color.GREEN, myBoard, 2);
		
		Game myGame = new Game(player1, player2, myBoard);
		myGame.start();
	}
}