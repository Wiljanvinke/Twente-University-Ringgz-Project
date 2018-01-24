package game;

import java.util.Scanner;

import exceptions.InvalidMoveArgumentException;
import players.*;

/**
 * Class for maintaining the game logic.
 * Makes sure the game progresses correctly.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Game {

	private Board board;
	private Player[] players;
	private int turn;
	
	public Game(Player player1, Player player2) {
		board = new Board();
		players = new Player[] {player1, player2};
		turn = 0;
	}
	
	public Game(Player player1, Player player2, Player player3) {
		board = new Board();
		players = new Player[] {player1, player2, player3};
		turn = 0;
	}
	
	public Game(Player player1, Player player2, Player player3, Player player4) {
		board = new Board();
		players = new Player[] {player1, player2, player3, player4};
		turn = 0;
	}
	
	
	/**
	 * Starts a new <code>Game</code>.
	 * Also resets a <code>Board</code>.
	 * Continues until user determines not to replay anymore.
	 */
	public void start() {
        boolean replay = true;
        while (replay) {
            reset();
            play();
            replay = readBoolean("\n> Play another time? (y/n)?", "y", "n");
        }
	}
	
	
	//Not done yet, waiting for "game over" and "winner" methods.
	public void play() {
    	System.out.println(board.toString());
    	while (!gameOver()) {
    		if (players[turn].hasMove()) {
    			
    			while (error < 5)
        		try {
					players[turn].makeMove();
				} catch (InvalidMoveArgumentException e) {
					
				}
        		update();
        		turn = (turn + 1) % players.length;
    		}
    	}
    	printResult();		
	}
	
	public boolean gameOver() {
		for(int i = 0; i < players.length; i++) {
			if (players[i].hasMove()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Displays the current version of the board.
	 */
	public void update() {
        System.out.println("\nCurrent game situation: \n\n" + board.toString()
                + "\n");
	}

	/**
	 * Clears the board and resets the turn token.
	 */
	public void reset() {
		turn = 0;
		board.reset();
	}
	
	//Displays the outcome of a game. Not yet finished.
	public void printResult() {
		if (board.hasWinner()) {
            Player winner = board.isWinner(players[0].getMark()) ? players[0]
                    : players[1];
            System.out.println("Speler " + winner.getName() + " ("
                    + winner.getMark().toString() + ") has won!");
        } else {
            System.out.println("Draw. There is no winner!");
        }
	}
	
    //Might be temporary until the main input is done.
	private boolean readBoolean(String prompt, String yes, String no) {
        String answer;
        do {
            System.out.print(prompt);
            try (Scanner in = new Scanner(System.in)) {
                answer = in.hasNextLine() ? in.nextLine() : null;
            }
        } while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
        return answer.equals(yes);
    }
	
}
