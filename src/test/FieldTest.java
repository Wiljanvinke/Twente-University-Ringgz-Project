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
	
	@Before
    public void setUp() {
		myField = new Field();
		name = "Wiljan";
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
	}
	
	/** Tests if you can place a ring on an empty field. */
	@Test
    public void testPlaceRingEmpty() {
		myField.placeRing(Color.RED, Size.MEDIUM, new HumanPlayer(name, Color.RED));
		assertEquals(Color.RED, myField.getRing(Size.MEDIUM).getColor());
		assertEquals(Size.MEDIUM, myField.getRing(Size.MEDIUM).getSize());
		myField.placeRing(Color.PURPLE, Size.TINY, new HumanPlayer(name, Color.PURPLE));
		assertEquals(Color.PURPLE, myField.getRing(Size.TINY).getColor());
		assertEquals(Color.RED, myField.getRing(Size.MEDIUM).getColor());
	}
	
	/** Tests if you can place a ring on a filled field. */
	@Test
    public void testPlaceRingFilled() {
		myField.placeRing(Color.RED, Size.MEDIUM, new HumanPlayer(name, Color.RED));
		myField.placeRing(Color.PURPLE, Size.MEDIUM, new HumanPlayer(name, Color.PURPLE));
		assertEquals(Color.RED, myField.getRing(Size.MEDIUM).getColor());
	}
	
	/** Tests if you can place a ring on field with a base. */
	@Test
    public void testPlaceRingBase() {
		myField.placeRing(Color.RED, Size.BASE, new HumanPlayer(name, Color.RED));
		myField.placeRing(Color.PURPLE, Size.SMALL, new HumanPlayer(name, Color.PURPLE));
		assertEquals(Color.RED, myField.getRing(Size.BASE).getColor());
		assertEquals(null, myField.getRing(Size.SMALL));
	}
	
	/** Tests if GetRing returns a proper ring object. */
	@Test
    public void testGetRing() {
		myField.placeRing(Color.RED, Size.LARGE, new HumanPlayer(name, Color.RED));
		assertTrue(myField.getRing(Size.LARGE) instanceof Ring);
		assertEquals(myField.getRing(Size.LARGE).getClass(), 
				new Ring(Color.GREEN, Size.SMALL).getClass());
	}
	
	/** Tests if empty and filled fields return true and false respectively. */
	@Test
    public void testIsEmpty() {
		assertTrue(myField.isEmpty());
		myField.placeRing(Color.RED, Size.LARGE, new HumanPlayer(name, Color.RED));
		assertFalse(myField.isEmpty());
	}
	
	/** Tests if a field with and without base return true and false respectively. */
	@Test
    public void testHasBase() {
		assertFalse(myField.hasBase());
		myField.placeRing(Color.RED, Size.BASE, new HumanPlayer(name, Color.RED));
		assertTrue(myField.hasBase());
	}
	
	/** Tests if the given slot size is used or not. */
	@Test
    public void testIsEmptySlot() {
		assertTrue(myField.isEmptySlot(Size.MEDIUM));
		myField.placeRing(Color.RED, Size.MEDIUM, new HumanPlayer(name, Color.RED));
		assertTrue(myField.isEmptySlot(Size.TINY));
		assertFalse(myField.isEmptySlot(Size.MEDIUM));
		assertTrue(myField.isEmptySlot(Size.BASE));
	}
	
	/** Tests if a field filled with rings is full. */
	@Test
    public void testIsFullRings() {
		Player myPlayer = new HumanPlayer(name, Color.RED);
		assertFalse(myField.isFull());
		myField.placeRing(Color.RED, Size.TINY, myPlayer);
		myField.placeRing(Color.RED, Size.SMALL, myPlayer);
		myField.placeRing(Color.RED, Size.MEDIUM, myPlayer);
		myField.placeRing(Color.RED, Size.LARGE, myPlayer);
		assertTrue(myField.isFull());
	}
	
	/** Tests if a field filled with a base is full. */
	@Test
    public void testIsFullBase() {
		assertFalse(myField.isFull());
		myField.placeRing(Color.RED, Size.BASE, new HumanPlayer(name, Color.RED));
		assertTrue(myField.isFull());
	}
	
	/** Tests if the correct player owns a field as rings get placed. */
	@Test
    public void testOwnsRings() {
		assertEquals(null, myField.owns());
		myField.placeRing(Color.RED, Size.MEDIUM, new HumanPlayer(name, Color.RED));
		assertEquals(Color.RED, myField.owns());
		myField.placeRing(Color.YELLOW, Size.TINY, new HumanPlayer(name, Color.YELLOW));
		myField.placeRing(Color.YELLOW, Size.LARGE, new HumanPlayer(name, Color.YELLOW));
		assertEquals(Color.YELLOW, myField.owns());
		myField.placeRing(Color.RED, Size.SMALL, new HumanPlayer(name, Color.RED));
		assertEquals(null, myField.owns());
	}
	
	/** Tests if the correct player owns a field as a base gets placed. */
	@Test
    public void testOwnsBase() {
		assertEquals(null, myField.owns());
		myField.placeRing(Color.RED, Size.BASE, new HumanPlayer(name, Color.RED));
		assertEquals(null, myField.owns());
	}
	
	/** Tests if the field gets reset. */
	@Test
    public void testReset() {
		myField.placeRing(Color.RED, Size.BASE, new HumanPlayer(name, Color.RED));
		myField.placeRing(Color.PURPLE, Size.SMALL, new HumanPlayer(name, Color.PURPLE));
		assertFalse(myField.isEmpty());
		myField.reset();
		assertTrue(myField.isEmpty());
	}
}
