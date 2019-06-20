package model.exceptions;

public class GridSizeException extends RuntimeException {

	private static final long serialVersionUID = 5449456579183548356L;



	public GridSizeException() {
		super("Grid size not accepted.");
	}

}