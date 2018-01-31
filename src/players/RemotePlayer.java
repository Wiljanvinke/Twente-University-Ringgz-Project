package players;

import game.Board;
import game.Color;
import servernew.*;

public class RemotePlayer extends Player {

	private ClientHandler clientHandler;
		

	public RemotePlayer(String name, Color color, Board board, ClientHandler clientHandler) {
		super(name, color, board);
		this.clientHandler = clientHandler;
	}

	public RemotePlayer(String name, Color color1, Color color2, Board board, int numberPlayers, ClientHandler clientHandler) {
		super(name, color1, color2, board, numberPlayers);
		this.clientHandler = clientHandler;	
	}

	
	@Override
	public String determineMove() {
		String command = clientHandler.getMove();
		return command;
	}

	@Override
	public String firstMove() {
		return determineMove();
	}

}
