package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
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
	
	/** Tests if sizes are correctly converted to an integer. */
	@Test
    public void testToIntCorrect() {
		assertEquals(0, sizeTiny.toInt());
		assertEquals(1, sizeSmall.toInt());
		assertEquals(2, sizeMedium.toInt());
		assertEquals(3, sizeLarge.toInt());
		assertEquals(4, sizeBase.toInt());
	}
	
	/** Tests if the correct integer is being used. */
	@Test
    public void testToIntWrong() {
		assertFalse(sizeTiny.toInt() == 1);
		assertFalse(sizeSmall.toInt() == 0);
		assertFalse(sizeMedium.toInt() == 3);
		assertFalse(sizeLarge.toInt() == 0);
		assertFalse(sizeBase.toInt() == 2);
	}
	
	/** Tests if integers are correctly converted to sizes. */
	@Test
    public void testToEnumCorrect() {
		assertEquals(sizeTiny, Size.toEnum(0));
		assertEquals(sizeSmall, Size.toEnum(1));
		assertEquals(sizeMedium, Size.toEnum(2));
		assertEquals(sizeLarge, Size.toEnum(3));
		assertEquals(sizeBase, Size.toEnum(4));
	}
	
	/** Tests if the correct integer is being used. */
	@Test
    public void testToEnumWrong() {
		assertFalse(sizeTiny.equals(Size.toEnum(1)));
		assertFalse(sizeSmall.equals(Size.toEnum(0)));
		assertFalse(sizeMedium.equals(Size.toEnum(3)));
		assertFalse(sizeLarge.equals(Size.toEnum(4)));
		assertFalse(sizeBase.equals(Size.toEnum(0)));
	}
	
}
