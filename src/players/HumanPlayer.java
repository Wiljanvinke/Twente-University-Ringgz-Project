package players;

import java.util.Scanner;

import exceptions.*;
import game.*;

public class HumanPlayer extends Player {
	
	private String color;
	private int size;
	private int row;
	private int column;
	
	/**
	 * Constructs a new <code>HumanPlayer</code> for a four <code>Player</code> match.
	 * @param name the name the <code>Player</code> should have
	 * @param color the <code>Color</code> the <code>Player</code> will have
	 * @param board the <code>Board</code> the <code>Player</code> plays on
	 */
	public HumanPlayer(String name, Color color, Board board) {
		super(name, color, board);
	}

	/**
	 * Constructs a new <code>HumanPlayer</code>. 
	 * If less than 4 <code>Player</code>s have been specified, an extra set
	 * of <code>Ring</code>s will be given with a different <code>Color</code>. 
	 * The size of the extra set depends on the number of <code>Player</code>s.
	 * @param color1 The <code>Color</code> of the first set of <code>Ring</code>s
	 * @param color2 The <code>Color</code> of the second set of <code>Ring</code>s
	 * @param board The <code>Board</code> the <code>Player</code> will play on
	 * @param numberPlayers: The number of total <code>Player</code>s who will be playing the game
	 */
	public HumanPlayer(String name, Color color1, Color color2, Board board, int numberPlayers) {
		super(name, color1, color2, board, numberPlayers);
	}
	
	/**
	 * Asks the user for input of a <code>Ring</code> and a <code>Field</code>. 
	 * Also checks if that <code>Ring</code> can be placed in that <code>Field</code>.
	 * @return the String containing the move arguments
	 */
	@Override
	public String determineMove() {
		String prompt = "You can not make this move.";
		String ring = determineRing();
		String field = determineField();
		boolean valid = false;
		try { 
			valid = board.getField(row, column).isLegal(Color.toEnum(color), 
					Size.toEnum(size), this);
		} catch (AdjacentBaseException e) {
			System.out.println(e.getMessage());
		}
		while (!valid) {
			System.out.println(prompt);
			ring = determineRing();
			field = determineField();
			try {
				valid = board.getField(row, column).isLegal(Color.toEnum(color), 
						Size.toEnum(size), this);
			} catch (AdjacentBaseException e) {
				System.out.println(e.getMessage());
			}
		}
        return field + " " + ring;
	}

	/**
	 * Asks the user for input of a <code>Ring</code>. 
	 * Also checks if the <code>Player</code> has that <code>Ring</code>.
	 * @return the String containing the move arguments for the <code>Ring</code>
	 */
	public String determineRing() {
        String prompt = "> " + getName() + "what is your choice of color (RGYP)?";
        color = readChar(prompt);
        prompt = "> " + getName() + "what is your choice of size (01234)?";
        size = readInt(prompt);
        boolean valid = hasRing(Color.toEnum(color), Size.toEnum(size));
        while (!valid) {
            System.out.println("ERROR: You do not have that ring.");
            prompt = "> " + getName() + "what is your choice of color (RGYP)?";
            color = readChar(prompt);
            prompt = "> " + getName() + "what is your choice of size (01234)?";
            size = readInt(prompt);           
            valid = hasRing(Color.toEnum(color), Size.toEnum(size));
        }
        String ring = color + " " + size;
        return ring;
	}
	
	/**
	 * Asks the user for input of a <code>Field</code>. 
	 * Also checks if that <code>Field</code> exists.
	 * @return the String containing the move arguments for the <code>Field</code>
	 */
	private String determineField() {
        String prompt = "> " + getName() + "what row do you want to place it in?";
        row = readInt(prompt);
        prompt = "> " + getName() + "what column do you want to place it in?";
        column = readInt(prompt);
        boolean valid = board.isField(row, column);
        while (!valid) {
            System.out.println("ERROR: That field does not exist.");
            prompt = "> " + getName() + "what row do you want to place it in?";
            row = readInt(prompt);
            prompt = "> " + getName() + "what column do you want to place it in?";
            column = readInt(prompt);  
            valid = board.isField(row, column);
        }
        String field = row + " " + column;
        return field;
	}
	
    /**
     * Reads an integer from the standard input.
     * @param prompt the message that is printed describing the input asked
     * @return the integer that is input in the standard input
     */
    private int readInt(String prompt) {
        int value = 0;
        boolean intRead = false;
        Scanner line = new Scanner(System.in);
        do {
            System.out.print(prompt);
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNextInt()) {
                    intRead = true;
                    value = scannerLine.nextInt();
                }
            }
        } while (!intRead);
        line.close();
        return value;
    }
    
    /**
     * Reads a character from the standard input.
     * @param prompt the message that is printed describing the input asked
     * @return the character indicating a color that is input in the standard input
     */
    private String readChar(String prompt) {
        String s = "";
        boolean charRead = false;
        Scanner line = new Scanner(System.in);
        do {
            System.out.print(prompt);
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNext("[RGYP]")) {
                    charRead = true;
                    s = scannerLine.next();
                }
            }
        } while (!charRead);
        line.close();
        return s;
    }

}
