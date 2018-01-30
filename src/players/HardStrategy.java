package players;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import extra.Protocol;
import game.*;

public class HardStrategy implements Strategy {
	
	public String determineMove(Player player, Board board) {
		Set<String> highest = new HashSet<>();
		String move;
		double value;
		double highestValue = -1;
		player.getBoard().calculateValue(player);
		// for each row
		for (int i = 0; i < 5; i++) {
			// for each column
			for (int k = 0; k < 5; k++) {
				// for each color this player has
				for (int j = 0; j < player.getColors().length; j++) {
					// for each ring on this field
					for (int w = 0; w < 5; w++) {
						if (board.getField(i, k).isLegal(
								player.getColors()[j], Size.toEnum(w), player)) {
							value = player.getBoard().getField(i, k).
								getValue(player.getColors()[j], Size.toEnum(w));
							if (value == highestValue) { 
								move = Protocol.makeMove(i, k, player.getColors()[j].toChar(), w);
								highest.add(move);
							} else if (value > highestValue) {
								highestValue = value;
								highest.clear();
								move = Protocol.makeMove(i, k, player.getColors()[j].toChar(), w);
								highest.add(move);
							}
						}
						
					}
				}
			}
		}
		// take one of the highest value moves at random
		String output = null;
		boolean finished = false;
		int result = 0;
		int random = (int) (Math.random() * highest.size());
		Iterator<String> iterator = highest.iterator();
		while (!finished && iterator.hasNext()) {
			if (result == random) {
				output = iterator.next();
				finished = true;
			} else {
				result++;
				iterator.next();
			}
		}
		System.out.println(output + "\nValue: " + highestValue);
		return output;
	}
}
