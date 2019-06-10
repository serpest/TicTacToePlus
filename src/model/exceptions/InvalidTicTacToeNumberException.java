package model.exceptions;

public class InvalidTicTacToeNumberException extends RuntimeException {

	private static final long serialVersionUID = 4962762730091672890L;



	public InvalidTicTacToeNumberException(final int MIN_TIC_TAC_TOE_NUMBER, final int MAX_TIC_TAC_TOE_NUMBER) {
		super("Invalid tic tac toe number, it must be from " + MIN_TIC_TAC_TOE_NUMBER + " to " + MAX_TIC_TAC_TOE_NUMBER + ".");
	}

}
