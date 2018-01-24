package players;

import game.*;
import extra.Protocol;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, Board board) {
		super(name, color, board);
	}
	
	public ComputerPlayer(String name, Color color1, Color color2, int numberPlayers) {
		super(name, color1, color2, numberPlayers);
	}

	// gebruik determineMove alleen als hasMove true is!
	@Override
	public String determineMove() {
		Set<String> highest = new HashSet<>();
		String move;
		double value;
		double highestValue = -1;
		getBoard().calculateValue(this);
		// for each row
		for (int i = 0; i < 5; i++) {
			// for each column
			for (int k = 0; k < 5; k++) {
				// for each color this player has
				for (int j = 0; j < this.getColors().length; j++) {
					// for each ring on this field
					for (int w = 0; w < 5; w++) {
						value = getBoard().getField(i, k).getValue(Color.toEnum(j), Size.toEnum(w));
						if (value == highestValue) { 
							move = Protocol.makeMove(i, k, Color.toEnum(j).toChar(), w);
							highest.add(move);
						} else if (value > highestValue) {
							highestValue = value;
							highest.clear();
							move = Protocol.makeMove(i, k, Color.toEnum(j).toChar(), w);
							highest.add(move);
						}
					}
				}
			}
		}
		// take one of the highest value moves at random
		String output = null;
		int result = 0;
		int random = (int) (Math.random() * highest.size());
		Iterator<String> iterator = highest.iterator();
		while (iterator.hasNext()) {
			if (result == random) {
				output = iterator.next();
			}
			result++;
		}
		return output; // returns null als er geen zet is gevonden?
	}
}
