package test;

import org.junit.Before;
import org.junit.Test;
import game.*;
import players.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

	Player myPlayer;
	
	@Before
    public void setUp() {
		//myPlayer = new Player("name", Color.RED); //human
	}
	
	/** Tests if . */
	@Test
    public void testConstructor() {
		
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
    public void testRemoveRing() {
		//removeRing(Color color, Size size)
	}
	
}
