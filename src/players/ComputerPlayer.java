package players;

import game.*;
import extra.Protocol;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, Board board) {
		super(name, color, board);
	}
	
	public ComputerPlayer(String name, Color color1, Color color2, int numberPlayers) {
		super(name, color1, color2, numberPlayers);
	}

	@Override
	public String determineMove() {
		int value;
		int highestValue = -1;
		int bestIndex;
		String bestColor;
		int bestSize;
		getBoard().calculateValue(this);
		// for each field
		for (int i = 0; i < 25; i++) {
			// for each color this player has
			for (int j = 0; j < this.getColors().length; j++) {
				// for each ring on this field
				for (int w = 0; w < 5; w++) {
					value = getBoard().getField(i).getValue(Color.toEnum(j), Size.toEnum(w));
					if (value > highestValue) {
						highestValue = value;
						bestIndex = i;
						bestColor = Color.toEnum(j).toChar();
						bestSize = w;
					}
				}
			}
		}
		
		//Protocol.makeMove(boardRow, boardColumn, ringColor, ringSize);
		// hier zet je de zetten om in String volgens het protocol.
		
		return "";
	}

}
