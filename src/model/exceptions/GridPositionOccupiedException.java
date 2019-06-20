package model.exceptions;

import model.base.Point;

public class GridPositionOccupiedException extends RuntimeException {

	private static final long serialVersionUID = -628130045077406292L;



	public GridPositionOccupiedException(Point point) {
		super("The position " + point + " of the grid is already occupied.");
	}

}