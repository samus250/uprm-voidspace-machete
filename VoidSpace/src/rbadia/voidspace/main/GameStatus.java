package rbadia.voidspace.main;

import java.util.ArrayList;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.EnemyShip;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;
	
	// status variables
	private boolean newShip;
	private long asteroidsDestroyed = 0;
	private long enemyShipsDestroyed = 0;
	private long points = 0;
	private int shipsLeft;
	private long bulletsFired = 0;
	private long bulletsRemaining;
	private int level = 1;
	
	// the game logic
	private GameLogic gameLogic;
	
	public GameStatus(){
		
	}
	
	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}
	
	/**
	 * Sets the status of the game.
	 * @param gameStarted - should be true if the game started, false otherwise
	 */
	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}
	
	/**
	 * Sets the status of the game (if its starting).
	 * @param gameStarting - should be true if the game is starting, false otherwise
	 */
	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}
	
	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * Sets the status of the game, if the game is over.
	 * @param gameOver - should be true if the game is over, false otherwise
	 */
	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	/**
	 * Sets the status of the player's ship.
	 * @param newShip - should be true if the player's ship is new, false otherwise
	 */
	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}
	
	/**
	 * Returns the number of enemy ships destroyed.
	 * @return the number of enemy ships destroyed
	 */
	public synchronized long getEnemyShipsDestroyed() {
		return enemyShipsDestroyed;
	}
	
	/**
	 * Returns the number of targets destroyed.
	 * @return the number of targets destroyed
	 */
	public synchronized long getTargetsDestroyed() {
		return asteroidsDestroyed + enemyShipsDestroyed;
	}
	
	/**
	 * Returns the current hit/miss ratio.
	 * @return the current hit/miss ratio
	 */
	public synchronized float getHitMissRatio() {
		if(bulletsFired > 0) {
			return ((float)getTargetsDestroyed() / (float)bulletsFired) * 100;
		} else {
			return 0.0F;
		}
	}

	/**
	 * Sets the number of asteroids that have been destroyed.
	 * @param asteroidsDestroyed - the number of asteroids that have been destroyed
	 */
	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		long newDestroyedAsteroids = asteroidsDestroyed - this.asteroidsDestroyed;
		this.asteroidsDestroyed = asteroidsDestroyed;
		setPoints(getPoints() + GameSettings.ASTEROID_POINTS * newDestroyedAsteroids);
	}
	
	/**
	 * Sets the number of enemy ships that have been destroyed.
	 * @param enemyShipsDestroyed - the number of enemy ships that have been destroyed
	 */
	public synchronized void setEnemyShipsDestroyed(long enemyShipsDestroyed) {
		long newEnemyShipsDestroyed = enemyShipsDestroyed - this.enemyShipsDestroyed;
		this.enemyShipsDestroyed = enemyShipsDestroyed;
		setPoints(getPoints() + GameSettings.ENEMY_SHIP_POINTS * newEnemyShipsDestroyed);
	}
	
	/**
	 * Returns the number of bullets fired.
	 * @return the number of bullets fired
	 */
	public synchronized long getBulletsFired() {
		return bulletsFired;
	}
	
	/**
	 * Sets the number of bullets fired.
	 * @param bulletsFired - the number of bullets fired.
	 */
	public synchronized void setBulletsFired(long bulletsFired) {
		this.bulletsFired = bulletsFired;
	}
	
	/**
	 * Returns the number of bullets remaining.
	 * @return the number of bullets remaining
	 */
	public synchronized long getBulletsRemaining() {
		return bulletsRemaining;
	}
	
	/**
	 * Sets the number of bullets remaining.
	 * @param bulletsRemaining - the number of bullets remaining
	 */
	public synchronized void setBulletsRemaining(long bulletsRemaining) {
		if(bulletsRemaining == 0 && getShipsLeft() > 1) {
			setShipsLeft(getShipsLeft() - 1);
			this.bulletsRemaining = GameSettings.BULLETS_PER_LIVES;
		} else {
			this.bulletsRemaining = bulletsRemaining;
		}
		
	}

	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	/**
	 * Sets the number of the player's ships left.
	 * @param shipsLeft - the number of the player's ships left
	 */
	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}
	
	/**
	 * Returns the number of points the player has.
	 * @return the number of points the player has
	 */
	public synchronized long getPoints() {
		return points;
	}

	/**
	 * Sets the number of points the player has.
	 * @param points the number of points the player has
	 */
	public synchronized void setPoints(long points) {
		this.points = points;
		updateLevel();
	}
	
	/**
	 * Returns the level the player is in.
	 * @return the level the player is in
	 */
	public synchronized int getLevel() {
		return this.level;
	}
	
	/**
	 * Updates the quantity of bullets remaining, depending on the level
	 * @param level - the level the player is in
	 */
	public synchronized void updateBullets(int level) {
		if(!gameStarting && level != 1 && level % GameSettings.LEVELS_FOR_GAINING_BULLETS == 0)
			this.bulletsRemaining += GameSettings.BULLET_GAIN_PER_LEVELS;
	}
	
	/**
	 * Updates the number of ships the player has left, depending on the level
	 * @param level - the level the player is in
	 */
	public synchronized void updateShips(int level) {
		if(!gameStarting && level != 1 && level % GameSettings.LEVELS_FOR_EXTRA_SHIPS == 0)
			this.shipsLeft += GameSettings.EXTRA_SHIPS_PER_LEVELS;
	}
	
	/**
	 * Updates the number of asteroids that are on the screen at a given time depending on the level.
	 * @param level - the level the player is in
	 */
	public synchronized void updateAsteroidCount(int level) {
		// change asteroid count if it is the right time
		ArrayList<Asteroid> asteroids = gameLogic.getAsteroidList();
		if(level == GameSettings.LEVEL_FOR_ASTEROIDS_L2
				&& asteroids.size() < GameSettings.NUM_ASTEROIDS_L2){
			// add correct number of new asteroids
			int asteroidsToAdd = GameSettings.NUM_ASTEROIDS_L2 - asteroids.size();
			for(int i = 0; i < asteroidsToAdd; i++)
				asteroids.add(gameLogic.newAsteroid(gameLogic.getGameScreen()));
		} 
		else if(level == GameSettings.LEVEL_FOR_ASTEROIDS_L3
				&& asteroids.size() < GameSettings.NUM_ASTEROIDS_L3) {
			int asteroidsToAdd = GameSettings.NUM_ASTEROIDS_L3 - asteroids.size();
			for(int i = 0; i < asteroidsToAdd; i++)
				asteroids.add(gameLogic.newAsteroid(gameLogic.getGameScreen()));
		}
	}
	
	/**
	 * Updates the number of enemy ships in the screen in a given time, depending on the level.
	 * @param level - the level the player is in
	 */
	public synchronized void updateEnemyShipCount(int level) {
		// change enemyShip count if it is the right time
		ArrayList<EnemyShip> enemyShips = gameLogic.getEnemyShipList();
		if(level == GameSettings.LEVEL_FOR_ENEMY_SHIPS_L2
				&& enemyShips.size() < GameSettings.NUM_ENEMY_SHIPS_L2){
			// add correct number of new asteroids
			int enemyShipsToAdd = GameSettings.NUM_ENEMY_SHIPS_L2 - enemyShips.size();
			for(int i = 0; i < enemyShipsToAdd; i++)
				enemyShips.add(gameLogic.newEnemyShip(gameLogic.getGameScreen()));
		} 
		else if(level == GameSettings.LEVEL_FOR_ENEMY_SHIPS_L3
				&& enemyShips.size() < GameSettings.NUM_ENEMY_SHIPS_L3) {
			int enemyShipsToAdd = GameSettings.NUM_ENEMY_SHIPS_L3 - enemyShips.size();
			for(int i = 0; i < enemyShipsToAdd; i++)
				enemyShips.add(gameLogic.newEnemyShip(gameLogic.getGameScreen()));
		}
	}
	
	/**
	 * Sets the level the player is in.
	 * @param level - the level the player is in
	 */
	public synchronized void setLevel(int level) {
		// update only if level changed
		if(level != this.level) {
			updateShips(level);
			updateBullets(level);
			updateAsteroidCount(level);
			updateEnemyShipCount(level);
			this.level = level;
		}
	}
	
	/**
	 * Updates the level number depending on the player's game status (targets destroyed, points, etc.)
	 */
	public synchronized void updateLevel() {
		if(GameSettings.CHANGE_LEVEL_WITH_POINTS)
			setLevel((int)(getPoints() / GameSettings.POINTS_TO_NEXT_LEVEL + 1));
		else
			setLevel((int)(getEnemyShipsDestroyed() + getAsteroidsDestroyed())/GameSettings.TARGETS_TO_NEXT_LEVEL + 1);
	}
	
	/**
	 * Sets the game logic for use in the class
	 * @param gameLogic - the game logic that will be used
	 */
	public synchronized void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
	

}
