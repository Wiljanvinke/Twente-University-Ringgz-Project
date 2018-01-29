package players;

import game.*;
import extra.Protocol;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ComputerPlayer extends Player {

	private double[] weights = new double[] {1, 1, 1};
	
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
		boolean finished = false;
		int result = 0;
		int random = (int) (Math.random() * highest.size());
		Iterator<String> iterator = highest.iterator();
		while (!finished && iterator.hasNext()) {
			if (result == random) {
				output = iterator.next();
				finished = true;
			}
			result++;
			iterator.next();
		}
		System.out.println(output);
		return output;
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
