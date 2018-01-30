package players;

import game.*;
import extra.Protocol;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ComputerPlayer extends Player {

	private double[] weights = new double[] {1, 1, 2.5};
	Strategy strategy = new HardStrategy();
	
	public ComputerPlayer(String name, Color color, Board board) {
		super(name, color, board);
	}
	
	public ComputerPlayer(String name, Color color1, Color color2, Board board, int numberPlayers) {
		super(name, color1, color2, board, numberPlayers);
	}

	public double[] getWeights() {
		return weights;
	}
	
	public void setWeights(double w1, double w2, double w3) {
		weights[0] = w1;
		weights[1] = w2;
		weights[2] = w3;
	}
	
	@Override
	public String determineMove() {
		// if move is not legal, return easy.determineMove(this, getBoard()) ?
		return strategy.determineMove(this, getBoard());
	}
	
	/**
	 * Determines where to place the starting base.
	 * In this version the base is always placed in the middle.
	 */
	public String firstMove() {
		board.getField(2, 2).placeStart();
		return Protocol.makeMove(2, 2, Color.START.toChar(), Size.BASE.toInt());
	}
}
