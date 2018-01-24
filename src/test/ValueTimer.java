package test;

import game.*;
import players.*;

public class ValueTimer {
	public static void main(String[] args) {
		
		Board myBoard = new Board();
		Player myPlayer = new HumanPlayer("Name", Color.RED, myBoard);
		
		long start = System.nanoTime();
		myBoard.calculateValue(myPlayer);
		long eind = System.nanoTime();
		long duration = eind - start;
		System.out.println(duration / 1000 + " microseconds (10^-6 s).");
	}
}
