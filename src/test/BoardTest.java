package test;

import org.junit.Before;
import org.junit.Test;

import game.*;
import players.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	Board myBoard;
	
	@Before
    public void setUp() {
		myBoard = new Board();
	}
	
	/** Tests if all methods return expected values on an empty board. */
	@Test
    public void testInitialisation() {
		
	}
	
	/** Tests if . */
	@Test
    public void testIndex() {
		//index(int row, int col)
	}
	
	/** Tests if . */
	@Test
    public void testIsFieldIndex() {
		//isField(int index)
	}
	
	/** Tests if . */
	@Test
    public void testIsFieldRowCol() {
		//isField(int row, int col)
	}
	
	/** Tests if . */
	@Test
    public void testGetFieldIndex() {
		//getField(int i)
	}
	
	/** Tests if . */
	@Test
    public void testGetFieldRowCol() {
		//getField(int row, int col)
	}
	
	/** Tests if . */
	@Test
    public void testSetFieldIndex() {
		//setField(int index, Color color, Size size, Player player)
	}
	
	/** Tests if . */
	@Test
    public void testSetFieldRowCol() {
		//setField(int row, int col, Color color, Size size, Player player)
	}
	
	/** Tests if . */
	@Test
    public void testReset() {
		//reset()
	}
	
	@Test
    public void testToString() {
		System.out.println(myBoard.toString());
	}

}
