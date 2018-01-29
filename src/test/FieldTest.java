package test;

import org.junit.Before;
import org.junit.Test;

import game.*;
import players.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FieldTest {

	Field myField;
	String name;
	Board myBoard;
	HumanPlayer myRedPlayer;
	HumanPlayer myPurplePlayer;
	
	@Before
    public void setUp() {
		myBoard = new Board();
		myField = myBoard.getField(12); // random number
		myField.setPlayable(Color.RED); // CHECK DIT
		myField.setPlayable(Color.PURPLE);  // CHECK DIT
		myRedPlayer = new HumanPlayer("Wiljan", Color.RED, Color.YELLOW, myBoard, 2);
		myPurplePlayer = new HumanPlayer("Wouter", Color.PURPLE, Color.GREEN, myBoard, 2);
	}
	
	/** Tests if all methods return expected values on an empty field. */
	@Test
    public void testInitialisation() {
		assertEquals(null, myField.getRing(Size.TINY));
		assertEquals(null, myField.getRing(Size.SMALL));
		assertEquals(null, myField.getRing(Size.MEDIUM));
		assertEquals(null, myField.getRing(Size.LARGE));
		assertEquals(null, myField.getRing(Size.BASE));
		assertFalse(myField.hasBase());
		assertTrue(myField.isEmpty());
		assertTrue(myField.isEmptySlot(Size.TINY));
		assertFalse(myField.isFull());
		assertEquals(null, myField.owns());
		assertTrue(myField.playable(Color.RED));
		assertTrue(myField.playable(Color.PURPLE));
		assertFalse(myField.playable(Color.YELLOW));
		assertFalse(myField.playable(Color.GREEN));
	}
	
	/** Tests if you can place a ring on an empty field. */
	@Test
    public void testPlaceRingEmpty() {
		myField.placeRing(Color.RED, Size.MEDIUM, myRedPlayer);
		assertEquals(Color.RED, myField.getRing(Size.MEDIUM).getColor());
		assertEquals(Size.MEDIUM, myField.getRing(Size.MEDIUM).getSize());
		myField.placeRing(Color.PURPLE, Size.TINY, myPurplePlayer);
		assertEquals(Color.PURPLE, myField.getRing(Size.TINY).getColor());
		assertEquals(Color.RED, myField.getRing(Size.MEDIUM).getColor());
		assertTrue(myBoard.getField(11).playable(Color.RED));
		assertTrue(myBoard.getField(13).playable(Color.PURPLE));
	}
	
	/** Tests if you can place a ring on a filled field. */
	@Test
    public void testPlaceRingFilled() {
		myField.placeRing(Color.RED, Size.MEDIUM, myRedPlayer);
		myField.placeRing(Color.PURPLE, Size.MEDIUM, myPurplePlayer);
		assertEquals(Color.RED, myField.getRing(Size.MEDIUM).getColor());
		assertFalse(myBoard.getField(7).playable(Color.PURPLE));
		assertFalse(myBoard.getField(17).playable(Color.PURPLE));
	}
	
	/** Tests if you can place a ring on field with a base. */
	@Test
    public void testPlaceRingBase() {
		myField.placeRing(Color.RED, Size.BASE, myRedPlayer);
		myField.placeRing(Color.PURPLE, Size.SMALL, myPurplePlayer);
		assertEquals(Color.RED, myField.getRing(Size.BASE).getColor());
		assertEquals(null, myField.getRing(Size.SMALL));
		assertTrue(myBoard.getField(11).playable(Color.RED));
		assertFalse(myBoard.getField(13).playable(Color.PURPLE));
	}
	
	/** Tests if a starting base gets placed. */
	@Test
    public void testPlaceStart() {
		myField.placeStart();
		assertTrue(myField.hasBase());
		assertEquals(Color.START, myField.getRing(Size.BASE).getColor());
		assertTrue(myBoard.getField(11).playable(Color.RED));
		assertTrue(myBoard.getField(11).playable(Color.PURPLE));
		assertTrue(myBoard.getField(11).playable(Color.GREEN));
		assertTrue(myBoard.getField(11).playable(Color.YELLOW));
		assertTrue(myBoard.getField(13).playable(Color.RED));
		assertTrue(myBoard.getField(7).playable(Color.RED));
		assertTrue(myBoard.getField(17).playable(Color.RED));
		assertFalse(myBoard.getField(16).playable(Color.RED));
	}
	
	/** Tests if the field is legal to play on. */
	@Test
    public void testIsLegal() {
		assertTrue(myField.isLegal(Color.RED, Size.TINY, myRedPlayer));
		assertTrue(myField.isLegal(Color.PURPLE, Size.TINY, myPurplePlayer));
		assertTrue(myField.isLegal(Color.PURPLE, Size.BASE, myPurplePlayer));
		assertFalse(myField.isLegal(Color.RED, Size.TINY, myPurplePlayer));
		assertFalse(myField.isLegal(Color.PURPLE, Size.TINY, myRedPlayer));
		assertFalse(myField.isLegal(Color.YELLOW, Size.TINY, myPurplePlayer));
		assertFalse(myField.isLegal(Color.GREEN, Size.TINY, myPurplePlayer));
	}
	
	/** Tests if GetRing returns a proper ring object. */
	@Test
    public void testGetRing() {
		myField.placeRing(Color.RED, Size.LARGE, myRedPlayer);
		assertTrue(myField.getRing(Size.LARGE) instanceof Ring);
		assertEquals(myField.getRing(Size.LARGE).getClass(), 
				new Ring(Color.GREEN, Size.SMALL).getClass());
	}
	
	/** Tests if . */
	@Test
    public void test() {
		// playable
	}
	
	
	/** Tests if . */
	@Test
    public void test() {
		// setPlayable
	}
	
	
	/** Tests if . */
	@Test
    public void test() {
		// getValue
	}
	
	
	/** Tests if . */
	@Test
    public void test() {
		// setPlayable
	}
	
	
	/** Tests if . */
	@Test
    public void test() {
		// getAdjacent
	}
	
	
	/** Tests if . */
	@Test
    public void test() {
		// setAdjacent
	}
	
	
	
	/** Tests if empty and filled fields return true and false respectively. */
	@Test
    public void testIsEmpty() {
		assertTrue(myField.isEmpty());
		myField.placeRing(Color.RED, Size.LARGE, myRedPlayer);
		assertFalse(myField.isEmpty());
	}
	
	/** Tests if a field with and without base return true and false respectively. */
	@Test
    public void testHasBase() {
		assertFalse(myField.hasBase());
		myField.placeRing(Color.RED, Size.BASE, myRedPlayer);
		assertTrue(myField.hasBase());
	}
	
	/** Tests if the given slot size is used or not. */
	@Test
    public void testIsEmptySlot() {
		assertTrue(myField.isEmptySlot(Size.MEDIUM));
		myField.placeRing(Color.RED, Size.MEDIUM, myRedPlayer);
		assertTrue(myField.isEmptySlot(Size.TINY));
		assertFalse(myField.isEmptySlot(Size.MEDIUM));
		assertTrue(myField.isEmptySlot(Size.BASE));
	}
	
	/** Tests if a field filled with rings is full. */
	@Test
    public void testIsFullRings() {
		assertFalse(myField.isFull());
		myField.placeRing(Color.RED, Size.TINY, myRedPlayer);
		myField.placeRing(Color.RED, Size.SMALL, myRedPlayer);
		myField.placeRing(Color.RED, Size.MEDIUM, myRedPlayer);
		myField.placeRing(Color.RED, Size.LARGE, myRedPlayer);
		assertTrue(myField.isFull());
	}
	
	/** Tests if a field filled with a base is full. */
	@Test
    public void testIsFullBase() {
		assertFalse(myField.isFull());
		myField.placeRing(Color.RED, Size.BASE, myRedPlayer);
		assertTrue(myField.isFull());
	}
	
	/** Tests if the correct player owns a field as rings get placed. */
	@Test
    public void testOwnsRings() {
		assertEquals(null, myField.owns());
		myField.placeRing(Color.RED, Size.MEDIUM, myRedPlayer);
		assertEquals(Color.RED, myField.owns());
		myField.placeRing(Color.PURPLE, Size.TINY, myPurplePlayer);
		myField.placeRing(Color.PURPLE, Size.LARGE, myPurplePlayer);
		assertEquals(Color.PURPLE, myField.owns());
		myField.placeRing(Color.RED, Size.SMALL, myRedPlayer);
		assertEquals(null, myField.owns());
	}
	
	/** Tests if the correct player owns a field as a base gets placed. */
	@Test
    public void testOwnsBase() {
		assertEquals(null, myField.owns());
		myField.placeRing(Color.RED, Size.BASE, myRedPlayer);
		assertEquals(null, myField.owns());
	}
	
	/** Tests if the field gets reset. */
	@Test
    public void testReset() {
		myField.placeRing(Color.RED, Size.BASE, myRedPlayer);
		myField.placeRing(Color.PURPLE, Size.SMALL, myPurplePlayer);
		assertFalse(myField.isEmpty());
		myField.reset();
		assertTrue(myField.isEmpty());
	}
}
