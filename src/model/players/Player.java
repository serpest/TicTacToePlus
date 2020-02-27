package model.players;

import model.components.Pawn;
import model.exceptions.MaximumPlayersNumberExceededException;

public class Player {

	private static final String DEFAULT_NAME_ROOT = "Player";

	private static int playersNumber = 0;



	public static void resetPlayersNumber() {
		playersNumber = 0;
	}



	private int serialNumber;

	private String name;

	private Pawn pawn;



	public Player(String name) throws MaximumPlayersNumberExceededException {
		setName(name);
		setSerialNumber(playersNumber);
		try {
			setPawn(Pawn.values()[playersNumber]);
		} catch (ArrayIndexOutOfBoundsException exc) {
			throw new MaximumPlayersNumberExceededException(exc);
		}
		playersNumber += 1;
	}

	public Player() throws MaximumPlayersNumberExceededException {
		this(DEFAULT_NAME_ROOT + ' ' + playersNumber);
	}



	public int getSerialNumber() {
		return serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pawn getPawn() {
		return pawn;
	}

	@Override
	public String toString() {
		return getName() + " (" + getPawn() + ")";
	}



	protected void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	protected void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

}