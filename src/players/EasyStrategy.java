package players;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import extra.Protocol;
import game.*;

public class EasyStrategy implements Strategy {
	
	public String determineMove(Player player, Board board) {
		Set<String> moves = new HashSet<>();
		String move;
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
							move = Protocol.makeMove(i, k, player.getColors()[j].toChar(), w);
							moves.add(move);
						}
					}
				}
			}
		}
		
		String output = null;
		boolean finished = false;
		int result = 0;
		int random = (int) (Math.random() * moves.size());
		Iterator<String> iterator = moves.iterator();
		while (!finished && iterator.hasNext()) {
			if (result == random) {
				output = iterator.next();
				finished = true;
			} else {
				result++;
				iterator.next();
			}
		}
		System.out.println("Using random move");
		return output;
	}
}
