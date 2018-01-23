package players;

import game.*;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color color, Board board) {
		super(name, color, board);
	}
	
	public HumanPlayer(String name, Color color1, Color color2, int numberPlayers) {
		super(name, color1, color2, numberPlayers);
	}
	
	@Override
	public int[] determineMove(Board board) {
		return null;
		
	}

}
