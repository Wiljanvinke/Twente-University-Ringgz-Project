package players;

import java.util.Scanner;

import exceptions.*;
import extra.Protocol;
import game.*;

public class HumanPlayer extends Player implements Cloneable {
	
	private String color;
	private int size;
	private int row;
	private int column;
	private static final String HINT = "H";
	
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
		String errorMessage = "You can not make this move.";
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
			System.out.println(errorMessage);
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
	private String determineRing() {
        String prompt = "> " + getName() + ", what is your choice of color (RGYP)?"; //Opt: Prompt player specific colors & list entire inventory of rings
        boolean hint = false;
        do {
            color = readChar(prompt);
            if (color.equals(HINT)) {
            	System.out.println(getHint());
            	hint = true;
            } else {
            	hint = false;
            }
        } while (hint);
        prompt = "> " + getName() + ", what is your choice of size (01234)?";
        size = readInt(prompt);
        boolean valid = hasRing(Color.toEnum(color), Size.toEnum(size));
        while (!valid) {																//Opt: Specific color and size errors
            System.out.println("ERROR: You do not have that ring.");
            prompt = "> " + getName() + ", what is your choice of color (RGYP)?";
            color = readChar(prompt);
            prompt = "> " + getName() + ", what is your choice of size (01234)?";
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
        String prompt = "> " + getName() + ", what row do you want to place it in?";
        row = readInt(prompt);
        prompt = "> " + getName() + ", what column do you want to place it in?";
        column = readInt(prompt);
        boolean valid = board.isField(row, column);
        while (!valid) {
            System.out.println("ERROR: That field does not exist.");
            prompt = "> " + getName() + ", what row do you want to place it in?";
            row = readInt(prompt);
            prompt = "> " + getName() + ", what column do you want to place it in?";
            column = readInt(prompt);  
            valid = board.isField(row, column);
        }
        String field = row + " " + column;
        return field;
	}
	
	/**
	 * Asks the user for input of the starting base. 
	 * Also checks if that <code>Field</code> exists.
	 * @return the String containing the move arguments for the starting base
	 */
	public String firstMove() {
        String prompt = "> " + getName() + ", what row do you want to place the starting base in?";
        row = readInt(prompt);
        prompt = "> " + getName() + ", what column do you want to place the starting base in?";
        column = readInt(prompt);
        boolean valid = legalStart(row, column);
        while (!valid) {
            System.out.println("ERROR: That field does not exist.");
            prompt = "> " + getName() + ", what row do you want to place the starting base in?";
            row = readInt(prompt);
            prompt = "> " + getName() + "what column do you want to place the starting base in?";
            column = readInt(prompt);  
            valid = legalStart(row, column);
        }
        board.getField(row, column).placeStart();
        return Protocol.makeMove(row, column, Color.START.toChar(), Size.BASE.toInt());
	}
	
	/**
	 * Checks if the starting base can be placed on the given <code>Field</code>.
	 * @param startRow The row of the <code>Field</code>
	 * @param startCol The column of the <code>Field</code>
	 * @return True if both startRow and startCol are 1, 2 or 3
	 */
	private boolean legalStart(int startRow, int startCol) {
		if (startRow >= 1 && startRow <= 3 && startCol >= 1 && startCol <= 3) {
			return true;
		}
		return false;
	}
	
    /**
     * Reads an integer from the standard input.
     * @param prompt the message that is printed describing the input asked
     * @return the integer that is input in the standard input
     */
    private int readInt(String prompt) {
        int value = 0;
        Scanner line = new Scanner(System.in);
        System.out.print(prompt);

        if (line.hasNextLine()) {
        	Scanner scannerLine = new Scanner(line.nextLine());
        	if (scannerLine.hasNextInt()) {
                value = scannerLine.nextInt();
            }
        	scannerLine.close();
        } else {
        	line.close();
        }
        return value;
    }
    
    /**
     * Reads a character from the standard input.
     * @param prompt the message that is printed describing the input asked
     * @return the character indicating a color that is input in the standard input
     */
    private String readChar(String prompt) {
        String s = "";
        Scanner line = new Scanner(System.in);
        System.out.print(prompt);

        if (line.hasNextLine()) {
        	Scanner scannerLine = new Scanner(line.nextLine());
        	if (scannerLine.hasNext("[RGYP]") || scannerLine.hasNext("[rgyp]")) {
                s = scannerLine.next().toUpperCase();
            }
        	if (scannerLine.hasNext("[Hh]")) {
        		s = scannerLine.next().toUpperCase();		//hint functionality
        	}
        	scannerLine.close();

        } else {
        	line.close();
		}
		return s;
	}

	// EXPERIMENTAL
	public String getHint() {
		ComputerPlayer cpu = new ComputerPlayer(this.name, 
				this.colors[0], this.colors[1], this.board, 2);
		return cpu.determineMove();
	}
}
