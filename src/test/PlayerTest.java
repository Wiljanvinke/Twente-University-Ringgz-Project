package test;

import org.junit.Before;
import org.junit.Test;

import game.*;
import players.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
	
	/** Tests if the players get constructed properly. */
	@Test
    public void testConstructor() {
		assertEquals(15, player4.remainingRings());
		assertEquals(15, player4s.remainingRings());
		assertEquals(20, player3s.remainingRings());
		assertEquals(30, player2s.remainingRings());
	}
	
	/** Tests if the correct colors are stored. */
	@Test
    public void testGetColors() {
		assertEquals(1, player4.getColors().length);
		assertEquals(2, player4s.getColors().length);
		assertEquals(2, player3s.getColors().length);
		assertEquals(2, player2s.getColors().length);
		assertEquals(Color.PURPLE, player4s.getColors()[0]);
		assertEquals(Color.GREEN, player4s.getColors()[1]);
		assertEquals(Color.RED, player4.getColors()[0]);
	}
	
	/** Tests if players get the right number of rings. */
	@Test
    public void testGetRings() {
		assertEquals(5, player4.getRings(Color.RED).length);
		assertEquals(null, player4.getRings(Color.PURPLE));
		assertEquals(5, player4s.getRings(Color.PURPLE).length);
		assertEquals(5, player3s.getRings(Color.PURPLE).length);
		assertEquals(5, player2s.getRings(Color.YELLOW).length);
		assertEquals(null, player2s.getRings(Color.GREEN));
		for (int i = 0; i < 5; i++) {
			assertEquals(3, player4.getRings(Color.RED)[i]);
			assertEquals(3, player4s.getRings(Color.PURPLE)[i]);
			assertEquals(0, player4s.getRings(Color.GREEN)[i]);
			assertEquals(3, player3s.getRings(Color.PURPLE)[i]);
			assertEquals(1, player3s.getRings(Color.GREEN)[i]);
			assertEquals(3, player2s.getRings(Color.RED)[i]);
			assertEquals(3, player2s.getRings(Color.YELLOW)[i]);
		}
	}
	
	/** Tests if HasRing returns expected values. */
	@Test
    public void testHasRing() {
		assertTrue(player4.hasRing(Color.RED, Size.TINY));
		assertTrue(player3s.hasRing(Color.GREEN, Size.TINY));
		assertTrue(player3s.hasRing(Color.PURPLE, Size.TINY));
		assertTrue(player3s.hasRing(Color.PURPLE, Size.BASE));
		assertFalse(player3s.hasRing(Color.RED, Size.TINY));
		assertFalse(player3s.hasRing(Color.YELLOW, Size.BASE));
		// Playing out rings
		player3s.removeRing(Color.PURPLE, Size.TINY);
		player3s.removeRing(Color.PURPLE, Size.TINY);
		player3s.removeRing(Color.PURPLE, Size.TINY);
		assertFalse(player3s.hasRing(Color.PURPLE, Size.TINY));
	}
	
	/** Tests if hasSize returns expected values. */
	@Test
    public void hasSize() {
		assertTrue(player4.hasSize(Size.TINY));
		assertTrue(player4.hasSize(Size.BASE));
		assertTrue(player3s.hasSize(Size.TINY));
		assertTrue(player3s.hasSize(Size.SMALL));
		assertTrue(player3s.hasSize(Size.MEDIUM));
		assertTrue(player3s.hasSize(Size.LARGE));
		assertTrue(player3s.hasSize(Size.BASE));
		// Playing out rings
		player4.removeRing(Color.RED, Size.TINY);
		player4.removeRing(Color.RED, Size.TINY);
		player4.removeRing(Color.RED, Size.TINY);
		assertFalse(player4.hasSize(Size.TINY));
	}
	
	/** Tests if a player has the given color. */
	@Test
    public void testHasColor() {
		assertFalse(player3s.hasColor(Color.RED));
		assertFalse(player3s.hasColor(Color.YELLOW));
		assertTrue(player3s.hasColor(Color.GREEN));
		assertTrue(player3s.hasColor(Color.PURPLE));
	}
	
	/** Tests if the player has a move available to him. */
	@Test
    public void testHasMove() {
		//Hard to write out a whole game just to find a point where you have no moves left
	}
	
	/** Tests if rings get removed from the player. */
	@Test
    public void testRemoveRing() {
		assertEquals(3, player3s.getRings(Color.PURPLE)[0]);
		player3s.removeRing(Color.PURPLE, Size.TINY);
		assertEquals(19, player3s.remainingRings());
		assertEquals(2, player3s.getRings(Color.PURPLE)[0]);
		player3s.removeRing(Color.PURPLE, Size.TINY);
		assertEquals(1, player3s.getRings(Color.PURPLE)[0]);
		player3s.removeRing(Color.PURPLE, Size.TINY);
		assertEquals(0, player3s.getRings(Color.PURPLE)[0]);
		player3s.removeRing(Color.PURPLE, Size.TINY);
		assertEquals(0, player3s.getRings(Color.PURPLE)[0]);
		assertEquals(17, player3s.remainingRings());
	}
	
	// makeMove
	
	// determineMove
	
	// firstMove
	
	
}
