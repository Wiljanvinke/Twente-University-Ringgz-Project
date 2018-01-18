package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FieldTest {

	Field myField1;
	
	@Before
    public void setUp() {
		myField1 = new Field();
	}
	
	/** Tests if all methods return expected values on an empty field. */
	@Test
    public void testInitialisation() {
		assertEquals(null, myField1.getRing(Size.TINY));
		assertEquals(null, myField1.getRing(Size.SMALL));
		assertEquals(null, myField1.getRing(Size.MEDIUM));
		assertEquals(null, myField1.getRing(Size.LARGE));
		assertEquals(null, myField1.getRing(Size.BASE));
		assertFalse(myField1.hasBase());
		assertTrue(myField1.isEmpty());
		assertTrue(myField1.isEmptySlot(Size.TINY));
		assertFalse(myField1.isFull());
		assertEquals(null, myField1.owns());
	}
	
	/** Tests if you can place a ring on an empty field. */
	@Test
    public void testPlaceRingEmpty() {
		myField1.placeRing(Color.RED, Size.MEDIUM, new Player(Color.RED));
		assertEquals(Color.RED, myField1.getRing(Size.MEDIUM).getColor());
		assertEquals(Size.MEDIUM, myField1.getRing(Size.MEDIUM).getSize());
		myField1.placeRing(Color.PURPLE, Size.TINY, new Player(Color.PURPLE));
		assertEquals(Color.PURPLE, myField1.getRing(Size.TINY).getColor());
		assertEquals(Color.RED, myField1.getRing(Size.MEDIUM).getColor());
	}
	
	/** Tests if you can place a ring on a filled field. */
	@Test
    public void testPlaceRingFilled() {
		myField1.placeRing(Color.RED, Size.MEDIUM, new Player(Color.RED));
		myField1.placeRing(Color.PURPLE, Size.MEDIUM, new Player(Color.PURPLE));
		assertEquals(Color.RED, myField1.getRing(Size.MEDIUM).getColor());
	}
	
	/** Tests if you can place a ring on field with a base. */
	@Test
    public void testPlaceRingBase() {
		myField1.placeRing(Color.RED, Size.BASE, new Player(Color.RED));
		myField1.placeRing(Color.PURPLE, Size.SMALL, new Player(Color.PURPLE));
		assertEquals(Color.RED, myField1.getRing(Size.BASE).getColor());
		assertEquals(null, myField1.getRing(Size.SMALL));
	}
	
	/** Tests if GetRing returns a proper ring object. */
	@Test
    public void testGetRing() {
		myField1.placeRing(Color.RED, Size.LARGE, new Player(Color.RED));
		assertTrue(myField1.getRing(Size.LARGE) instanceof Ring);
		assertEquals(myField1.getRing(Size.LARGE).getClass(), 
				new Ring(Color.GREEN, Size.SMALL).getClass());
	}
	
	/** Tests if empty and filled fields return true and false respectively. */
	@Test
    public void testIsEmpty() {
		assertTrue(myField1.isEmpty());
		myField1.placeRing(Color.RED, Size.LARGE, new Player(Color.RED));
		assertFalse(myField1.isEmpty());
	}
	
	/** Tests if a field with and without base return true and false respectively. */
	@Test
    public void testHasBase() {
		assertFalse(myField1.hasBase());
		myField1.placeRing(Color.RED, Size.BASE, new Player(Color.RED));
		assertTrue(myField1.hasBase());
	}
	
	/** Tests if the given slot size is used or not. */
	@Test
    public void testIsEmptySlot() {
		assertTrue(myField1.isEmptySlot(Size.MEDIUM));
		myField1.placeRing(Color.RED, Size.MEDIUM, new Player(Color.RED));
		assertTrue(myField1.isEmptySlot(Size.TINY));
		assertFalse(myField1.isEmptySlot(Size.MEDIUM));
		assertTrue(myField1.isEmptySlot(Size.BASE));
	}
	
	/** Tests if a field filled with rings is full. */
	@Test
    public void testIsFullRings() {
		Player myPlayer = new Player(Color.RED);
		assertFalse(myField1.isFull());
		myField1.placeRing(Color.RED, Size.TINY, myPlayer);
		myField1.placeRing(Color.RED, Size.SMALL, myPlayer);
		myField1.placeRing(Color.RED, Size.MEDIUM, myPlayer);
		myField1.placeRing(Color.RED, Size.LARGE, myPlayer);
		assertTrue(myField1.isFull());
	}
	
	/** Tests if a field filled with a base is full. */
	@Test
    public void testIsFullBase() {
		assertFalse(myField1.isFull());
		myField1.placeRing(Color.RED, Size.BASE, new Player(Color.RED));
		assertTrue(myField1.isFull());
	}
	
	/** Tests if the correct player owns a field as rings get placed. */
	@Test
    public void testOwnsRings() {
		assertEquals(null, myField1.owns());
		myField1.placeRing(Color.RED, Size.MEDIUM, new Player(Color.RED));
		assertEquals(Color.RED, myField1.owns());
		myField1.placeRing(Color.YELLOW, Size.TINY, new Player(Color.YELLOW));
		myField1.placeRing(Color.YELLOW, Size.LARGE, new Player(Color.YELLOW));
		assertEquals(Color.YELLOW, myField1.owns());
		myField1.placeRing(Color.RED, Size.SMALL, new Player(Color.RED));
		assertEquals(null, myField1.owns());
	}
	
	/** Tests if the correct player owns a field as a base gets placed. */
	@Test
    public void testOwnsBase() {
		assertEquals(null, myField1.owns());
		myField1.placeRing(Color.RED, Size.BASE, new Player(Color.RED));
		assertEquals(null, myField1.owns());
	}
	
}
