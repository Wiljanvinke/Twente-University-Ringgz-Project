package players;

import game.*;

public interface Strategy {
	public String determineMove(Player player, Board board);
}
