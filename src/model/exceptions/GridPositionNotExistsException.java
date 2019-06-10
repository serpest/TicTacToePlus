package model.exceptions;

import model.base.Point;

public class GridPositionNotExistsException extends RuntimeException {

	private static final long serialVersionUID = 816326998914811391L;



	public GridPositionNotExistsException(Point point) {
		super("The position " + point + " doesn't exist.");
	}

}
