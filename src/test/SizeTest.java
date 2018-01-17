package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SizeTest {

	Size sizeTiny;
	Size sizeSmall;
	Size sizeMedium;
	Size sizeLarge;
	Size sizeBase;
	
	@Before
    public void setUp() {
		sizeTiny = Size.TINY;
		sizeSmall = Size.SMALL;
		sizeMedium = Size.MEDIUM;
		sizeLarge = Size.LARGE;
		sizeBase = Size.BASE;
	}
	
	@Test
    public void testToIntCorrect() {
		assertTrue(sizeTiny.toInt() == 0);
		assertTrue(sizeSmall.toInt() == 1);
		assertTrue(sizeMedium.toInt() == 2);
		assertTrue(sizeLarge.toInt() == 3);
		assertTrue(sizeBase.toInt() == 4);
	}
	
	@Test
    public void testToIntWrong() {
		assertFalse(sizeTiny.toInt() == 1);
		assertFalse(sizeSmall.toInt() == 0);
		assertFalse(sizeMedium.toInt() == 3);
		assertFalse(sizeLarge.toInt() == 0);
		assertFalse(sizeBase.toInt() == 2);
	}
	
	@Test
    public void testToEnumCorrect() {
		assertEquals(sizeTiny, Size.toEnum(0));
		assertEquals(sizeSmall, Size.toEnum(1));
		assertEquals(sizeMedium, Size.toEnum(2));
		assertEquals(sizeLarge, Size.toEnum(3));
		assertEquals(sizeBase, Size.toEnum(4));
	}
	
	@Test
    public void testToEnumWrong() {
		assertFalse(sizeTiny.equals(Size.toEnum(1)));
		assertFalse(sizeSmall.equals(Size.toEnum(0)));
		assertFalse(sizeMedium.equals(Size.toEnum(3)));
		assertFalse(sizeLarge.equals(Size.toEnum(4)));
		assertFalse(sizeBase.equals(Size.toEnum(0)));
	}
}
