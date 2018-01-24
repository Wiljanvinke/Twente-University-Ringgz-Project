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
	public String determineMove() {
		int highestValue;
		int bestIndex;
		int bestColor;
		int bestSize;
		getBoard().calculateValue(this);
		// for each field
		for (int i = 0; i < 25; i++) {
			// for each color this player has
			for (int j = 0; j < this.getColors().length; j++) {
				// for each ring on this field
				for (int w = 0; w < 5; w++) {
					getBoard().getField(i).getValue(Color.toEnum(j), Size.toEnum(w));
				}
			}
			
			
		}
		
		
		// hier zet je de zetten om in String volgens het protocol.
		
		return "";
	}

}
