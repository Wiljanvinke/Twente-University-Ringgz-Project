package game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A class to keep players in the game
 * There are different players: human and computer
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Player {

	private Set<Ring> rings;
	
	
	/**
	 * Constructs a <code>Player</code> in a four-player game.
	 * @param color The color the <code>Player</code> gets.
	 */
	public Player(Color color) {
		rings = new HashSet<>();
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 3; j++) {
				rings.add(new Ring(color, Size.toEnum(i)));
			}
		}
	}
	
	/**
	 * Constructs a new <code>Player</code>. If less than 4 <code>Player</code>s have been specified, an extra set
	 * of <code>Ring</code>s will be given with a different <code>Color</code>. The size of the extra set depends
	 * on the number of <code>Player</code>s.
	 * @param color1 the <code>Color</code> of the first set of <code>Ring</code>s
	 * @param color2 the <code>Color</code> of the second set of <code>Ring</code>s
	 * @param numberplayers the number of total <code>Player</code>s who will be playing the game.
	 */
	public Player(Color color1, Color color2, int numberplayers) {
		assert(numberplayers < 2 && numberplayers > 4);
		Player p = new Player(color1);
		if(numberplayers == 2) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 3; j++) {
					p.rings.add(new Ring(color2, Size.toEnum(i)));
				}
			}
		} else {
			if(numberplayers == 3) {
				for(int i = 0; i < 5; i++) {
					p.rings.add(new Ring(color2, Size.toEnum(i)));
					
				}
			}
		}
	}
	
	
	/**
	 * Return all the <code>Ring</code>s this <code>Player</code> has.
	 * @return a Set of <code>Ring</code>s
	 */
	public Set<Ring> getRings(){
		return rings;
	}
	
	
	/**
	 * Checks if a this <code>Player</code> has a <code>Ring</code> with a specific <code>Color</code> and <code>Size</code>.
	 * @param color the <code>Color</code> of the <code>Ring</code> to check
	 * @param size the <code>Size</code> of the <code>Ring</code> to check
	 * @return true if the <code>Player</code> has this Ring
	 */
	public boolean hasRing(Color color, Size size) {
		Ring result = null;
		Iterator<Ring> iterator = getRings().iterator();
		while (iterator.hasNext()) {
			if(result.getColor() == color && result.getSize() == size) {
				return true;
			}
			result = iterator.next();
		}
		return false;
	}
	
	/**
	 * Checks if a this <code>Player</code> has a specific <code>Ring</code>.
	 * @param ring the <code>Ring</code> to check
	 * @return true if the <code>Player</code> has this Ring
	 */
	public boolean hasRing(Ring ring) {
		Ring result = null;
		Iterator<Ring> iterator = getRings().iterator();
		while (iterator.hasNext()) {
			if(result == ring) {
				return true;
			}
			result = iterator.next();
		}
		return false;
	}
	
	
	
	
}
