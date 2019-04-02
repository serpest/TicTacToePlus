package model.exceptions;

public class InvalidNumberOfPlayersException extends Exception {

	private static final long serialVersionUID = 2835750420732655204L;



	public InvalidNumberOfPlayersException() {
		super("Number of players not accepted.");
	}

}
