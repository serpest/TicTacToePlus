package model;

import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.exceptions.GridSizeException;

public class Grid {

	/**
	 * Minimum one size must be greater than or equal to this.
	 */
	private final static byte MIN_SIZE = 2;

	private final static byte DEFAULT_X_SIZE = 3;
	private final static byte DEFAULT_Y_SIZE = 3;



	/**
	 * It uses coordinates (column[x]; row[y]).
	 * 
	 * column =	Pawn[n]
	 */
	private Pawn[][] content;
	public Pawn[][] getContent() {
		return content;
	}
	private void setContent(Pawn[][] content) {
		this.content = content;
	}



	public void addPawn(Pawn pawn, Point point) throws GridPositionOccupiedException, GridPositionNotExistsException {
		if (point.getX() > content.length - 1 || point.getY() > content[0].length - 1) {
			throw new GridPositionNotExistsException(point);
		}
		if (getContent()[point.getX()][point.getY()] != null) {
			throw new GridPositionOccupiedException(point);
		}
		getContent()[point.getX()][point.getY()] = pawn;
	}
	public Pawn getPawn(Point point) {
		return getContent()[point.getX()][point.getY()];
	}

	public boolean isFull() {
		for (Pawn[] pawns : getContent()) {
			for (Pawn pawn : pawns) {
				if (pawn == null) {
					return false;
				}
			}
		}
		return true;
	}



	/**
	 * It creates a default grid.
	 */
	public Grid() {
		setContent(new Pawn[DEFAULT_X_SIZE][DEFAULT_Y_SIZE]);
	}
	/**
	 * It creates a custom grid.
	 * @param xSize
	 * @param ySize
	 * @throws GridSizeException 
	 */
	public Grid(final byte xSize, final byte ySize) throws GridSizeException {
		if ((xSize >= MIN_SIZE && ySize > 0) || (ySize >= MIN_SIZE && xSize > 0)) {
			setContent(new Pawn[xSize][ySize]);
			return;
		}
		throw new GridSizeException();
	}

}
