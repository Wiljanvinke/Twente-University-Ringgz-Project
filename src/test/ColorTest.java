package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ColorTest {

	Color colorRed;
	Color colorPurple;
	Color colorGreen;
	Color colorYellow;
	Color colorStart;
	
	@Before
    public void setUp() {
		colorRed = Color.RED; 
		colorPurple = Color.PURPLE;
		colorGreen = Color.GREEN;
		colorYellow = Color.YELLOW;
		colorStart = Color.START;
	}
	
	@Test
    public void testToCharCorrect() {
		assertEquals("R", colorRed.toChar());
		assertEquals("P", colorPurple.toChar());
		assertEquals("G", colorGreen.toChar());
		assertEquals("Y", colorYellow.toChar());
		assertEquals("RGYP", colorStart.toChar());
	}
	
	@Test
    public void testToCharWrong() {
		assertFalse(colorRed.toChar().equals("r"));
		assertFalse(colorPurple.toChar().equals("purple"));
		assertFalse(colorGreen.toChar().equals("R"));
		assertFalse(colorYellow.toChar().equals("RGYP"));
		assertFalse(colorStart.toChar().equals("START"));
	}
	
	@Test
    public void testToEnumCorrect() {
		assertEquals(colorRed, Color.toEnum("R"));
		assertEquals(colorPurple, Color.toEnum("P"));
		assertEquals(colorGreen, Color.toEnum("G"));
		assertEquals(colorYellow, Color.toEnum("Y"));
		assertEquals(colorStart, Color.toEnum("RGYP"));
	}
	
	@Test
    public void testToEnumWrong() {
		assertFalse(colorRed.equals(Color.toEnum("P")));
		assertFalse(colorPurple.equals(Color.toEnum("p")));
		assertFalse(colorGreen.equals(Color.toEnum("RGYP")));
		assertFalse(colorYellow.equals(Color.toEnum("yellow")));
		assertFalse(colorStart.equals(Color.toEnum("START")));
	}
}
