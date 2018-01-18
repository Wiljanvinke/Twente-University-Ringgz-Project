package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

	Player myPlayer;
	
	@Before
    public void setUp() {
		myPlayer = new Player(Color.RED);
	}
	
	/** Tests if all methods return expected values on an empty field. */
	@Test
    public void testInitialisation() {
		
	}
	
	
}
