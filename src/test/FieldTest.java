package test;

import org.junit.Before;
import org.junit.Test;

import game.*;
import players.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FieldTest {

	private static final double DELTA = 1e-15;
	Field myField;
	String name;
	Board myBoard;
	HumanPlayer myRedPlayer;
	HumanPlayer myPurplePlayer;
	
	@Before
    public void setUp() {
		myBoard = new Board();
		myField = myBoard.getField(12); // random number
		myField.setPlayable(Color.RED);
		myField.setPlayable(Color.PURPLE);
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
	
	/** Tests if a field is playable by a color. */
	@Test
    public void testPlayable() {
		assertTrue(myField.playable(Color.RED));
		assertTrue(myField.playable(Color.PURPLE));
		assertFalse(myField.playable(Color.GREEN));
		assertFalse(myField.playable(Color.YELLOW));
	}
	
	/** Tests if a field becomes playable. */
	@Test
    public void testSetPlayable() {
		assertFalse(myField.playable(Color.YELLOW));
		myField.setPlayable(Color.YELLOW);
		assertTrue(myField.playable(Color.YELLOW));
	}
	
	/** Tests if . */
	@Test
    public void testGetValue() {
		assertEquals(0, myField.getValue(Color.RED, Size.TINY), DELTA);
		assertEquals(0, myField.getValue(Color.PURPLE, Size.BASE), DELTA);
		assertEquals(0, myField.getValue(Color.GREEN, Size.LARGE), DELTA);
		assertEquals(0, myField.getValue(Color.YELLOW, Size.MEDIUM), DELTA);
	}
	
	/** Tests if you can set and change value of the field. */
	@Test
    public void testSetValue() {
		myField.setValue(Color.RED, Size.TINY, 4.2);
		myField.setValue(Color.RED, Size.SMALL, 3.2);
		myField.setValue(Color.RED, Size.MEDIUM, 5.2);
		myField.setValue(Color.RED, Size.LARGE, 1.2);
		myField.setValue(Color.RED, Size.BASE, 9.2);
		assertEquals(4.2, myField.getValue(Color.RED, Size.TINY), DELTA);
		assertEquals(3.2, myField.getValue(Color.RED, Size.SMALL), DELTA);
		assertEquals(5.2, myField.getValue(Color.RED, Size.MEDIUM), DELTA);
		assertEquals(1.2, myField.getValue(Color.RED, Size.LARGE), DELTA);
		assertEquals(9.2, myField.getValue(Color.RED, Size.BASE), DELTA);
		myField.setValue(Color.RED, Size.MEDIUM, 1.5);
		assertEquals(1.5, myField.getValue(Color.RED, Size.MEDIUM), DELTA);
	}
	
	/** Tests if adjacent fields are linked correctly. */
	@Test
    public void testGetAdjacent() {
		assertTrue(myBoard.adjacent(2, 3).contains(myBoard.getField(1, 3)));
		assertTrue(myBoard.adjacent(2, 3).contains(myBoard.getField(3, 3)));
		assertTrue(myBoard.adjacent(2, 3).contains(myBoard.getField(2, 2)));
		assertTrue(myBoard.adjacent(2, 3).contains(myBoard.getField(2, 4)));
		assertFalse(myBoard.adjacent(2, 3).contains(myBoard.getField(2, 1)));
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
