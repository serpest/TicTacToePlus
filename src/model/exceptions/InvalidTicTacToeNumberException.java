package model.exceptions;

public class InvalidTicTacToeNumberException extends Exception {

	private static final long serialVersionUID = 4962762730091672890L;



	public InvalidTicTacToeNumberException(final byte MIN_TIC_TAC_TOE_NUMBER, final byte MAX_TIC_TAC_TOE_NUMBER) {
		super("Invalid tic tac toe number, it must be from " + MIN_TIC_TAC_TOE_NUMBER + " to " + MAX_TIC_TAC_TOE_NUMBER + ".");
	}

}