package model.exceptions;

public class MaximumPlayerNumberExceededException extends Exception {

	private static final long serialVersionUID = 4459554044361936317L;



	public MaximumPlayerNumberExceededException(Throwable cause) {
		super("Maximum player number exceeded - " + cause.getMessage() + ".");
	}

}
