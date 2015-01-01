package rbadia.voidspace.model;

import rbadia.voidspace.main.GameScreen;

/**
 * This class represents a chuck norris
 * @author Samuel
 *
 */
public class ChuckNorris extends Asteroid {

	private int chuckNorrisWidth = 48;
	private int chuckNorrisHeight = 48;
	private int chuckNorrisSpeed = 2;
	
	/**
	 * Construct a chuck norris on the screen
	 * @param screen
	 */
	public ChuckNorris(GameScreen screen) {
		super(screen);
		
		super.setSize(chuckNorrisWidth, chuckNorrisHeight);
		super.setSpeed(chuckNorrisSpeed);
	}

	private static final long serialVersionUID = 1L;

}
