package players;

import game.*;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, Board board) {
		super(name, color, board);
	}
	
	public ComputerPlayer(String name, Color color1, Color color2, int numberPlayers) {
		super(name, color1, color2, numberPlayers);
	}

	@Override
	public int[] determineMove(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}