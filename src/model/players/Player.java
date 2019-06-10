package model.players;

import model.components.Pawn;
import model.exceptions.MaximumPlayerNumberExceededException;

/**
 * It's an simple tic tac toe player.
 */
public class Player {

	/**
	 * it's the root word of the default players.
	 */
	private static final String DEFAULT_NAME = "Player";

	/**
	 * It's the current serial number.
	 */
	private static int nPlayers = 0;



	/**
	 * It's the serial number of the player generated when the player was created.
	 * It's used from the controller to change the player turn.
	 */
	private int serialNumber;
	public int getSerialNumber() {
		return serialNumber;
	}
	private void setSerialNumber(int n) {
		this.serialNumber = n;
	}

	/**
	 * It's the player's name.
	 */
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * It is always used the same in the content array of Grid.
	 */
	private Pawn pawn;
	public Pawn getPawn() {
		return pawn;
	}
	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}



	/**
	 * It creates a player with a custom name, but a fixed pawn.
	 * 
	 * @param name the player's name
	 * @throws MaximumPlayerNumberExceededException if the players are too many for the pawn fixed values
	 */
	public Player(String name) throws MaximumPlayerNumberExceededException {
		setName(name);
		setSerialNumber(nPlayers);
		try {
			setPawn(Pawn.values()[nPlayers]);
		} catch (ArrayIndexOutOfBoundsException exc) {
			throw new MaximumPlayerNumberExceededException(exc);
		}
		nPlayers += 1;
	}

	/**
	 * It creates a player with default name and pawn.
	 * It is used in the creation of TicTacToeGame.DEFAULT_PLAYERS and it doesn't throw MaximumPlayerNumberExceededException.
	 * 
	 * @param nPLayer the number of the player (from 0)
	 */
	public Player(int nPLayer) {
		setName(DEFAULT_NAME + (nPLayer + 1));
		setSerialNumber(nPLayer);
		setPawn(Pawn.values()[nPLayer]);
	}



	@Override
	public String toString() {
		return getName() + " (" + getPawn() + ")";
	}

}
