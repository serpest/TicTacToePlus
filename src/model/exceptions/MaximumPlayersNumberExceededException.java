package model.exceptions;

public class MaximumPlayersNumberExceededException extends RuntimeException {

	private static final long serialVersionUID = 4459554044361936317L;



	public MaximumPlayersNumberExceededException(Throwable cause) {
		super("Maximum players number exceeded - " + cause.getMessage() + ".");
	}

}