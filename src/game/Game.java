package game;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import exceptions.AdjacentBaseException;
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
	private Color sharedColor;
	
	public Game(Player player1, Player player2) {
		board = new Board();
		players = new Player[] {player1, player2};
		turn = 0;
	}
	
	public Game(Player player1, Player player2, Player player3, Color sharedColor) {
		board = new Board();
		players = new Player[] {player1, player2, player3};
		this.sharedColor = sharedColor;
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

	/**
	 * The basic game loop. Regulates turns and ensures <code>Player</code>s can make
	 * moves. When the game is over displays the result.
	 */
	public void play() {
    	System.out.println(board.toString());
    	int error = 0;
    	boolean valid = false;
    	while (!gameOver()) {
    		if (players[turn].hasMove()) {
    			error = 0;
    			valid = false;
				while (error < 5 || valid == false) {
					try {
						players[turn].makeMove();
					} catch (InvalidMoveArgumentException e) {
						error++;
						System.out.println(e.getMessage());
					} catch (AdjacentBaseException e) {
						error++;
						System.out.println(e.getMessage());
					}
					valid = true;
				}
    		} 
    		update();
    		turn = (turn + 1) % players.length;
    	}
    	printResult();		
	}
	
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
	 * @return the winning <code>Color</code>. Returns null in case of a draw.  
	 */
	public Color determineWinner() {
		int[] colors = new int[]{0, 0, 0, 0};
		for (int i = 0; i < (Board.DIM * Board.DIM); i++) {
			switch (board.getField(i).owns()) {
				case RED: colors[0]++; break;
				case PURPLE: colors[1]++; break;
				case GREEN: colors[2]++; break;
				case YELLOW: colors[3]++; break;
				default: break;					
			}
		}
		//Check the value of the highest number of fields owned by a single color
		int highest = 0;
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] > highest) {
				highest = colors[i];
			}
		}
		//Check which color that highest number of fields belongs to
		Set<Color> winners = new HashSet<>(); 
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == highest) {
				winners.add(Color.toEnum(colors[i]));
			}
		}
		//Check if one of the winners is a shared color
		if (sharedColor != null) {
			if (winners.contains(sharedColor)) {
				winners.remove(sharedColor);
			}
		}
		//Check for simple winner or draw by number of fields
		if (winners.size() == 1) {
			return winners.iterator().next();
		} else {
			//Check the lowest number of rings remaining
			int lowest = 25;
			Color[] playercolors;
			Color next;
			while (winners.iterator().hasNext()) {
				next = winners.iterator().next();
				//Check the colors a player has
				for (int i = 0; i < players.length; i++) {
					playercolors = players[i].getColors().clone();
					for (int j = 0; j < playercolors.length; j++) {
						if (playercolors[j].equals(next)) {
							//If this player has the lowest number of rings left, 
							//set that number as lowest.
							if (players[i].remainingRings() > lowest) {
								lowest = players[i].remainingRings();
							}
						}
					}
				}
			}
			//Check which color has the lowest number of rings
			while (winners.iterator().hasNext()) {
				next = winners.iterator().next();
				for (int i = 0; i < players.length; i++) {
					playercolors = players[i].getColors().clone();
					for (int j = 0; j < playercolors.length; j++) {
						if (playercolors[j].equals(next)) {
							// If this player has more than the lowest number of rings,
							// set that number as lowest.
							if (players[i].remainingRings() > lowest) {
								winners.remove(next);
							}
						}
					}
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
	
	//Displays the outcome of a game. Not yet finished.
	public void printResult() {
		if (hasWinner()) {
            Color winner = determineWinner();
            Player playerWin = null;
            Color[] playercolors;
            for (int i = 0; i < players.length; i++) {
            	playercolors = players[i].getColors().clone();
            	for (int j = 0; j < playercolors.length; j++) {
					if (playercolors[j].equals(winner)) {
						playerWin = players[i];
					}
            	}
            }
            System.out.println("Player " + playerWin.getName() + " ("
                    + winner.toString() + ") has won!");
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
