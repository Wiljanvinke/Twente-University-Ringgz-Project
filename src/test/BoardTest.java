package test;

import org.junit.Before;
import org.junit.Test;

import game.*;
import players.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
	
	//getFields()
	
	/** Tests if the given fields return the correct number of adjacent fields. */
	@Test
    public void testAdjacentSize() {
		// Check corners
		assertTrue(myBoard.adjacent(0, 0).size() == 2);
		assertTrue(myBoard.adjacent(4, 0).size() == 2);
		assertTrue(myBoard.adjacent(0, 4).size() == 2);
		assertTrue(myBoard.adjacent(4, 4).size() == 2);
		// Check sides
		assertTrue(myBoard.adjacent(0, 2).size() == 3);
		assertTrue(myBoard.adjacent(4, 2).size() == 3);
		assertTrue(myBoard.adjacent(1, 0).size() == 3);
		assertTrue(myBoard.adjacent(3, 4).size() == 3);
		// Check middle
		assertTrue(myBoard.adjacent(1, 2).size() == 4);
		assertTrue(myBoard.adjacent(3, 3).size() == 4);
	}
	
	/** Tests if the given fields return the correct adjacent fields. */
	@Test
    public void testAdjacentField() {
		// Check corners
		assertTrue(myBoard.adjacent(0, 0).contains(myBoard.getField(0, 1)));
		assertTrue(myBoard.adjacent(0, 0).contains(myBoard.getField(1, 0)));
		assertTrue(myBoard.adjacent(4, 4).contains(myBoard.getField(4, 3)));
		assertTrue(myBoard.adjacent(4, 4).contains(myBoard.getField(3, 4)));
		assertFalse(myBoard.adjacent(4, 4).contains(myBoard.getField(3, 3)));
		// Check sides
		assertTrue(myBoard.adjacent(0, 3).contains(myBoard.getField(0, 4)));
		assertTrue(myBoard.adjacent(0, 3).contains(myBoard.getField(1, 3)));
		assertTrue(myBoard.adjacent(0, 3).contains(myBoard.getField(0, 2)));
		assertTrue(myBoard.adjacent(4, 1).contains(myBoard.getField(4, 0)));
		assertTrue(myBoard.adjacent(4, 1).contains(myBoard.getField(3, 1)));
		assertTrue(myBoard.adjacent(4, 1).contains(myBoard.getField(4, 2)));
		assertFalse(myBoard.adjacent(4, 1).contains(myBoard.getField(3, 0)));
		// Check middle
		assertTrue(myBoard.adjacent(1, 3).contains(myBoard.getField(0, 3)));
		assertTrue(myBoard.adjacent(1, 3).contains(myBoard.getField(2, 3)));
		assertTrue(myBoard.adjacent(1, 3).contains(myBoard.getField(1, 2)));
		assertTrue(myBoard.adjacent(1, 3).contains(myBoard.getField(1, 4)));
		assertFalse(myBoard.adjacent(1, 3).contains(myBoard.getField(0, 4)));
		assertFalse(myBoard.adjacent(1, 3).contains(myBoard.getField(0, 0)));
	}
	
	/** Tests if . */
	@Test
    public void testCalculateValue() {
		//calculateValue(Player player)
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
