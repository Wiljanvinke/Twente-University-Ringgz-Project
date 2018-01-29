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
	
	/** Tests if rows and columns get converted to an index. */
	@Test
    public void testIndex() {
		assertEquals(0, myBoard.index(0, 0));
		assertEquals(3, myBoard.index(0, 3));
		assertEquals(11, myBoard.index(2, 1));
	}
	
	/** Tests if the fields with given index are on the board. */
	@Test
    public void testIsFieldIndex() {
		assertTrue(myBoard.isField(0));
		assertTrue(myBoard.isField(13));
		assertTrue(myBoard.isField(24));
		assertFalse(myBoard.isField(-1));
		assertFalse(myBoard.isField(25));
	}
	
	/** Tests if fields with given row and column are on the board. */
	@Test
    public void testIsFieldRowCol() {
		assertTrue(myBoard.isField(0, 0));
		assertTrue(myBoard.isField(4, 4));
		assertFalse(myBoard.isField(-1, 0));
		assertFalse(myBoard.isField(2, 5));
	}
	
	/** Tests if getField gives a field. */
	@Test
    public void testGetFieldRowCol() {
		assertTrue(myBoard.getField(2, 3) instanceof Field);
	}
	
	/** Tests if getFields gives an array of fields. */
	@Test
    public void testGetFields() {
		assertTrue(myBoard.getFields() instanceof Field[]);
		assertTrue(myBoard.getFields().length == 25);
		assertTrue(myBoard.getFields()[0] != null);
		assertTrue(myBoard.getFields()[0] instanceof Field);
	}
	
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
		// HOLY SHIT HARD TEST
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
