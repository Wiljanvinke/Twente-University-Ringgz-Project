package test;

import org.junit.Before;
import org.junit.Test;

import game.*;
import players.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

	HumanPlayer player4;
	HumanPlayer player4s;
	HumanPlayer player3s;
	HumanPlayer player2s;
	
	@Before
    public void setUp() {
		Board myBoard = new Board();
		player4 = new HumanPlayer("4Player", Color.RED, myBoard);
		player4s = new HumanPlayer("4Players", Color.PURPLE, Color.GREEN, myBoard, 4);
		player3s = new HumanPlayer("3Players", Color.PURPLE, Color.GREEN, myBoard, 3);
		player2s = new HumanPlayer("2Players", Color.RED, Color.YELLOW, myBoard, 2);
	}
	
	/** Tests if . */
	@Test
    public void testConstructor() {
		assertEquals(15, player4.remainingRings());
		assertEquals(15, player4s.remainingRings());
		assertEquals(20, player3s.remainingRings());
		assertEquals(30, player2s.remainingRings());
	}
	
	/** Tests if all methods return expected values on an empty field. */
	@Test
    public void testInitialisation() {
		
	}
	
	/** Tests if . */
	@Test
    public void testGetColors() {
		//getColors()
	}
	
	/** Tests if . */
	@Test
    public void testGetRings() {
		//getRings(Color color)
	}
	
	/** Tests if . */
	@Test
    public void testRemainingRings() {
		//remainingRings()
	}
	
	/** Tests if . */
	@Test
    public void testHasRing() {
		//hasRing(Color color, Size size)
	}
	
	/** Tests if . */
	@Test
    public void hasSize() {
		//hasRing(Size size)
	}
	
	/** Tests if . */
	@Test
    public void testHasColor() {
		//hasColor(Color color)
	}
	
	/** Tests if . */
	@Test
    public void testHasMove() {
		//hasMove
	}
	
	/** Tests if . */
	@Test
    public void testRemoveRing() {
		//removeRing(Color color, Size size)
	}
	
	// makeMove
	
	// determineMove
	
	// firstMove
	
	
}
