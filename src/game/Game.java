package game;

import java.util.Scanner;

import players.*;
import ss.week4.tictactoe.Player;

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
	
	public void start() {
        boolean next = true;
        while (next) {
            reset();
            play();
            next = readBoolean("\n> Play another time? (y/n)?", "y", "n");
        }
	}
	
	public void play() {
    	System.out.println(board.toString());
    	while (!board.gameOver()) {
    		players[current].makeMove(board);
    		update();
    		current = (current + 1) % 2;
    	}
    	printResult();		
	}
	
	public void update() {
        System.out.println("\nCurrent game situation: \n\n" + board.toString()
                + "\n");
	}

	public void reset() {
		turn = 0;
		board.reset();
	}
	
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
