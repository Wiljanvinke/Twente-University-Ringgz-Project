package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import exceptions.*;
import players.*;

/**
 * Class for maintaining the game logic.
 * Makes sure the game progresses correctly.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 1.0
 */
public class Game extends Thread {

	private Board board;
	private Player[] players;
	private int turn;
	private Color sharedColor = null;
	
	/**
	 * Constructs a new <code>Game</code> with two <code>Player</code>s.
	 * @param player1 the first <code>Player</code>.
	 * @param player2 the second <code>Player</code>.
	 * @param board the <code>Board</code> this <code>Game</code> will be played on
	 */
	public Game(Player player1, Player player2, Board board) {
		this.board = board;
		players = new Player[] {player1, player2};
		turn = 0;
	}
	
	/**
	 * Constructs a new <code>Game</code> with three <code>Player</code>s.
	 * @param player1 the first <code>Player</code>.
	 * @param player2 the second <code>Player</code>.
	 * @param player3 the third <code>Player</code>.
	 * @param board the <code>Board</code> this <code>Game</code> will be played on
	 * @param sharedColor the second <code>Color</code> shared by all three <code>Player</code>s.
	 */
	public Game(Player player1, Player player2, Player player3, Board board, Color sharedColor) {
		this.board = board;
		players = new Player[] {player1, player2, player3};
		this.sharedColor = sharedColor;
		turn = 0;
	}
	
	/**
	 * Constructs a new <code>Game</code> with four <code>Player</code>s.
	 * @param player1 the first <code>Player</code>.
	 * @param player2 the second <code>Player</code>.
	 * @param player3 the third <code>Player</code>.
	 * @param player4 the fourth <code>Player</code>.
	 * @param board the <code>Board</code> this <code>Game</code> will be played on
	 */
	public Game(Player player1, Player player2, Player player3, Player player4, Board board) {
		this.board = board;
		players = new Player[] {player1, player2, player3, player4};
		turn = 0;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void nextTurn() {
		turn = (turn + 1) % players.length;
	}
	
	/**
	 * Starts a new <code>Game</code>.
	 * Also resets a <code>Board</code>.
	 * Continues until user determines not to replay anymore.
	 */
	public void run() {
        boolean replay = true;
        while (replay) {
            reset();
            firstMove();          
            play();
            replay = readBoolean("\n> Play another time? (y/n)?", "y", "n");
        }
	}
	
	/**
	 * A mini game loop specifically designed for the first move.
	 */
	public void firstMove() {
		System.out.println(board.toString());
		boolean valid = false;
		while (!valid) {
			players[turn].firstMove();
			valid = true;
		}
		update();
		turn = (turn + 1) % players.length;
	}
	
	/**
	 * The basic game loop. Regulates turns and ensures <code>Player</code>s can make
	 * moves. When the game is over displays the result.
	 */
	public void play() {
    	int error = 0;
    	boolean valid = false;
    	while (!gameOver()) {
    		if (players[turn].hasMove()) {
    			error = 0;
    			valid = false;
				while (!valid && error < 5) {
					try {
						players[turn].makeMove();
						valid = true;
					} catch (InvalidMoveArgumentException e) {
						error++;
						System.out.println(e.getMessage());
					}
				}
    		} 
    		update();
    		turn = (turn + 1) % players.length;
    	}
    	printResult();		
	}
	
	/**
	 * Checks if a <code>Game</code> is done by checking if there are any moves
	 * left to be made.
	 * @return true if there are no moves left. Returns false if there are moves left. 
	 */
	public boolean gameOver() {
		for (int i = 0; i < players.length; i++) {
			if (players[i].hasMove()) {
				return false;
			}
		}
		return true;
	}
		
	/**
	 * Displays the current version of the <code>Board</code>.
	 */
	public void update() {
        System.out.println("\nCurrent game situation: \n\n" + board.toString()
                + "\n");
	}

	/**
	 * Clears the <code>Board</code> and resets the turn token.
	 */
	public void reset() {
		turn = 0;
		board.reset();
	}
	
	
	/**
	 * Calculates a winner. Also ensures a shared <code>Color</code> cannot win in a 
	 * three <code>Player</code> match.
	 * @return the winning <code>Player</code>. Returns null in case of a draw.  
	 */
	public Player determineWinner() {
		//Checks which colors won how many fields
		Map<Color, Integer> colorscores = new HashMap<>();
		for (int i = 0; i < Color.values().length; i++) {
			colorscores.put(Color.toEnum(i), 0);
		}
		Color temp = null;
		for (int i = 0; i < (Board.DIM * Board.DIM); i++) {
			temp = board.getField(i).owns();
			if (temp != null && !temp.equals(sharedColor)) {
				colorscores.put(temp, colorscores.get(temp) + 1);
			}
		}
		//Makes a map containing the scores of the players
		Map<Player, Integer> playerscores = new HashMap<>();
		for (int i = 0; i < players.length; i++) {
			playerscores.put(players[i], 0);
		}
		Color[] playercolors;
		//Adds the colors belonging to a certain player as scores
		for (int i = 0; i < players.length; i++) {
			playercolors = players[i].getColors();
			for (int j = 0; j < playercolors.length; j++) {
				if (colorscores.containsKey(playercolors[j])) {
					playerscores.put(players[i], 
							colorscores.get(playercolors[j]) + playerscores.get(players[i]));
				}
			}
		}
		//Check the value of the highest number of fields owned by a single color
		int highest = 0;
		Set<Player> winners = new HashSet<>();	
		for (int i = 0; i < players.length; i++) {
			if (playerscores.get(players[i]) > highest) {
				winners.clear();
				winners.add(players[i]);
				highest = playerscores.get(players[i]);
			} else if (playerscores.get(players[i]) == highest) {
				winners.add(players[i]);
			}
		}
		//Check for simple winner or draw by number of fields
		if (winners.size() == 1) {
			return winners.iterator().next();
		} else {
			//Check the lowest number of rings remaining
			int lowest = 25;
			Player next;
			while (winners.iterator().hasNext()) {
				next = winners.iterator().next();
				if (next.remainingRings() == lowest) {
					lowest = next.remainingRings();
				}				
			}
			//Check which player has the lowest number of rings
			while (winners.iterator().hasNext()) {
				next = winners.iterator().next();
				if (next.remainingRings() > lowest) {
					winners.remove(next);
				}
			}
			//Check if there is a single winner or draw
			if (winners.size() == 1) {
				return winners.iterator().next();
			} else {
				return null;
			}
		} 
	}	
	
	/**
	 * Checks if the game has a winner.
	 * @return true if the game has a winner. Returns false in case of a draw.
	 */
	public boolean hasWinner() {
		if (determineWinner() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Displays the outcome of a game. Prints the winning <code>Player</code>.
	 */
	public void printResult() {
		if (hasWinner()) {
            Player winner = determineWinner();
            System.out.println("Player " + winner.getName() + " has won!");
        } else {
            System.out.println("Draw. There is no winner!");
        }
	}
	
    /**
     * Asks a yes or no question and records the answer.
     * @param prompt The question being asked
     * @param yes The expected positive answer
     * @param no The expected negative answer
     * @return True if the user input equals the yes parameter
     */
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
