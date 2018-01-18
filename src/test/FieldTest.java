package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class FieldTest {

	Field myField1;
	Field myField2;
	
	@Before
    public void setUp() {
		myField1 = new Field();
		myField2 = new Field();
	}
	
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
	
	@Test
    public void testPlaceRingEmpty() {
		// Checks if you can place a ring on an empty field
		assertEquals(null, myField1.getRing(Size.MEDIUM));
		myField1.placeRing(Color.RED, Size.MEDIUM, new Player(Color.RED));
		assertEquals(Color.RED, myField1.getRing(Size.MEDIUM).getColor());
		assertEquals(Size.MEDIUM, myField1.getRing(Size.MEDIUM).getSize());

		
		
		
		
	}
	
	@Test
    public void testPlaceRingFilled() {
		// Checks if you can place a ring on a filled field
		
	}
	
	@Test
    public void testPlaceRingBase() {
		// Checks if you can place a ring on field with a base
		
	}
	
	@Test
    public void testGetRing() {
		getRing(Size size)
	}
	
	@Test
    public void testIsEmpty() {
		isEmpty()
	}
	
	@Test
    public void testHasBase() {
		hasBase()
	}
	
	@Test
    public void testIsEmptySlot() {
		isEmptySlot(Size size)
	}
	
	@Test
    public void testIsFull() {
		isFull() 
	}
	
	@Test
    public void testOwns() {
		owns()
	}
	
	
	
}
