package model.exceptions;

import model.Point;

public class GridPositionOccupiedException extends Exception {

	private static final long serialVersionUID = -628130045077406292L;



	public GridPositionOccupiedException(Point point) {
		super("The position " + point + " of the grid is already occupied.");
	}

}
