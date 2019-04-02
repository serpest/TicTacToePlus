package model;

import model.exceptions.MaximumPlayerNumberExceededException;

public class Player {

	private static final String DEFAULT_NAME = "Player";

	private static byte nPlayers = 0;



	private byte n;
	public byte getN() {
		return n;
	}
	private void setN(byte n) {
		this.n = n;
	}

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



	public Player(String name) throws MaximumPlayerNumberExceededException {
		setName(name);
		setN(nPlayers);
		try {
			setPawn(Pawn.values()[nPlayers]);
		} catch (ArrayIndexOutOfBoundsException exc) {
			throw new MaximumPlayerNumberExceededException(exc);
		}
		nPlayers += 1;
	}
	/**
	 * It is used in the creation of TicTacToeGame.DEFAULT_PLAYERS and it doesn't throw MaximumPlayerNumberExceededException.
	 * 
	 * @param nPLayer the number of the player (from 0)
	 */
	Player(byte nPLayer) {
		setName(DEFAULT_NAME + (nPLayer + 1));
		setN(nPLayer);
		setPawn(Pawn.values()[nPLayer]);
	}



	@Override
	public String toString() {
		return getName() + " (" + getPawn() + ")";
	}

}
