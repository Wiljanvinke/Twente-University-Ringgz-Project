package test;

import org.junit.Before;
import org.junit.Test;
import game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	Board myBoard;
	
	@Before
    public void setUp() {
		myBoard = new Board();
	}
	
	@Test
    public void testToString() {
		System.out.println(myBoard.toString());
	}

}
