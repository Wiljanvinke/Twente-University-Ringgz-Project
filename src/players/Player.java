package players;

import game.*;

import java.util.Scanner;

import exceptions.*;

/**
 * A class to keep players in the game.
 * There are different players: human and computer.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.3
 */
public abstract class Player {
	private String name;
	private Color[] colors;
	private int[] rings1;
	private int[] rings2;
	private Board board;

	/**
	 * Constructs a <code>Player</code> for a four-player game.
	 * The second color is not used and set to the same color.
	 * @param color The color the <code>Player</code> gets
	 */
	public Player(String name, Color color, Board board) {
		this.name = name;
		this.colors = new Color[] {color};
		this.board = board;
		rings1 = new int[] {3, 3, 3, 3, 3};
		rings2 = new int[] {0, 0, 0, 0, 0};
	}
	
	/**
	 * Constructs a new <code>Player</code>. 
	 * If less than 4 <code>Player</code>s have been specified, an extra set
	 * of <code>Ring</code>s will be given with a different <code>Color</code>. 
	 * The size of the extra set depends on the number of <code>Player</code>s.
	 * @param color1 The <code>Color</code> of the first set of <code>Ring</code>s
	 * @param color2 The <code>Color</code> of the second set of <code>Ring</code>s
	 * @param numberPlayers: The number of total <code>Player</code>s who will be playing the game
	 */
	public Player(String name, Color color1, Color color2, int numberPlayers) {
		this.name = name;
		this.colors = new Color[] {color1, color2};
		rings1 = new int[] {3, 3, 3, 3, 3};
		switch (numberPlayers) {
			case 2: rings2 = new int[] {3, 3, 3, 3, 3}; break;
			case 3: rings2 = new int[] {1, 1, 1, 1, 1}; break;
			case 4: rings2 = new int[] {0, 0, 0, 0, 0}; break; // zet in verslag waarom niet verwijst naar andere constructor
		}
	}
	
	/**
	 * Gives you an array of <code>Color</code>s this <code>Player</code> has.
	 * @return an array of length 1 or 2 containing the color of the <code>Player</code>
	 */
	public Color[] getColors() {
		return colors;
	}
	
	public Board getBoard() {
		return board;
	}

	/**
	 * Gives you the name of the <code>Player</code>.
	 * @return the <code>Player</code>s name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns an array of length 5 that contains how many <code>Ring</code>s 
	 * this <code>Player</code> has of the given <code>Color</code>.
	 * @param color The <code>Color</code> of the <code>Ring</code>s
	 * @return array of 5 integers, first value represents the number of tiny <code>Ring</code>s. 
	 * 		Returns null if this <code>Player</code> does not play the given <code>Color</code>
	 */
	public int[] getRings(Color color) {
		if (color == colors[0]) {
			return rings1;
		} else if (color == colors[1]) {
			return rings2;
		}
		return null;
	}
	
	/**
	 * Tells you how many <code>Ring</code>s and bases the <code>Player</code> has.
	 * @return the number of remaining <code>Ring</code>s and bases
	 */
	public int remainingRings() {
		int result = 0;
		for (int i = 0; i < 5; i++) {
			result += rings1[i] + rings2[i];
		}
		return result;
	}
	
	/**
	 * Checks if a this <code>Player</code> has a <code>Ring</code> 
	 * with a specific <code>Color</code> and <code>Size</code>.
	 * @param color The <code>Color</code> of the <code>Ring</code> to check
	 * @param size The <code>Size</code> of the <code>Ring</code> to check
	 * @return true if the <code>Player</code> has this Ring else false
	 */
	public boolean hasRing(Color color, Size size) {
		if ((color == colors[0] && rings1[size.toInt()] > 1) || 
				(color == colors[1] && rings2[size.toInt()] > 1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a this <code>Player</code> has a specific <code>Ring</code> <code>Size</code>.
	 * @param ring The <code>Ring</code> to check
	 * @return true if the <code>Player</code> has this Ring else false
	 */
	public boolean hasSize(Size size) {
		if (rings1[size.toInt()] > 1 || rings1[size.toInt()] > 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if this <code>Player</code> has any legal moves on this <code>Board</code>.
	 * @return True if a legal move is found
	 */
	public boolean hasMove() {
		if (remainingRings() > 0) {
			// for each field of the board
			for (int i = 0; i < 25; i++) {
				// for each ring on this field
				for (int j = 0; j < 5; j++) {
					// for each color this player has
					for (int w = 0; w < colors.length; w++) {
						if (board.getField(i).playable(colors[w]) &&
								board.getField(i).isEmptySlot(Size.toEnum(j)) && 
								hasRing(colors[w], Size.toEnum(j))) {
							// deze checked niet voor naast elkaar liggende bases!!!!
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes a <code>Ring</code> from the <code>Player</code>.
	 * @param ring The <code>Ring</code> the method needs to remove
	 */
	public void removeRing(Color color, Size size) {
		if (hasRing(color, size)) {
			getRings(color)[size.toInt()]--;
		}
	}
	
	
   /**
    * Actually make the move after it has been determined by a <code>HumanPlayer</code> 
    * or <code>ComputerPlayer</code>. 
    * @param boardRow the row of the field the <code>Ring</code> needs to be placed on
    * @param boardColumn the column of the field the <code>Ring</code> needs to be placed on
    * @param ringColor the <code>Color</code> of the <code>Ring</code> that needs to be placed
    * @param ringSize the <code>Size</code> of the <code>Ring</code> that needs to be placed
    */
    public void makeMove() throws InvalidMoveArgumentException {
    	String move = determineMove();
		Scanner in = new Scanner("move");
		int boardRow = 0;
		int boardColumn = 0;
		Color ringColor = null;
		Size ringSize = null;
		if (in.hasNextInt()) {
			boardRow = Integer.parseInt(in.next());			
		} else {
	        in.close();
			throw new InvalidMoveArgumentException();
		}
		if (in.hasNextInt()) {
			boardColumn = Integer.parseInt(in.next());
		} else {
	        in.close();
			throw new InvalidMoveArgumentException();
		}
		if (in.hasNext("[RGYP]")) {
			ringColor = Color.toEnum(in.next());
		} else {
	        in.close();
			throw new InvalidMoveArgumentException();
		}
		if (in.hasNextInt()) {
			ringSize = Size.toEnum(Integer.parseInt(in.next()));
		}
        board.getField(boardRow, boardColumn).placeRing(ringColor, ringSize, this);
        in.close();
    }
	
    
    //oud
	public abstract String determineMove();
}
