package model;

import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.exceptions.GridSizeException;

/**
 * It's a tic tac toe grid with default or custom sizes.
 */
public class Grid {

	/**
	 * Minimum one size must be greater than or equal to this.
	 */
	private final static int MIN_SIZE = 2;

	/**
	 * The default x size of the classic game.
	 */
	private final static int DEFAULT_X_SIZE = 3;
	/**
	 * The default y size of the classic game.
	 */
	private final static int DEFAULT_Y_SIZE = 3;



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



	/**
	 * It adds the pawn in a specific position of the grid.
	 * 
	 * @param pawn the player pawn
	 * @param point the point of the grid
	 * @throws GridPositionOccupiedException if the position is already occupied
	 * @throws GridPositionNotExistsException if the point ins't inside the grid
	 */
	public void addPawn(Pawn pawn, Point point) throws GridPositionOccupiedException, GridPositionNotExistsException {
		checkPositionExistenceInGrid(point);
		if (getContent()[point.getX()][point.getY()] != null) {
			throw new GridPositionOccupiedException(point);
		}
		getContent()[point.getX()][point.getY()] = pawn;
	}

	/**
	 * @param point the point of the grid
	 * @return the content of the grid in point position
	 * @throws GridPositionNotExistsException if the point ins't inside the grid
	 */
	public Pawn getPawn(Point point) throws GridPositionNotExistsException {
		checkPositionExistenceInGrid(point);
		return getContent()[point.getX()][point.getY()];
	}

	/**
	 * It launches an exception if the point ins't inside the grid.
	 * 
	 * @param point the point of the grid
	 * @throws GridPositionNotExistsException if the point ins't inside the grid
	 */
	private void checkPositionExistenceInGrid(Point point) throws GridPositionNotExistsException {
		if (point.getX() > content.length - 1 || point.getY() > content[0].length - 1) {
			throw new GridPositionNotExistsException(point);
		}
	}

	/**
	 * @return the grid is full
	 */
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
	 * 
	 * @param xSize the size of x
	 * @param ySize the size of y
	 * @throws GridSizeException if the grid size isn't accepted.
	 */
	public Grid(final int xSize, final int ySize) throws GridSizeException {
		if ((xSize >= MIN_SIZE && ySize > 0) || (ySize >= MIN_SIZE && xSize > 0)) {
			setContent(new Pawn[xSize][ySize]);
			return;
		}
		throw new GridSizeException();
	}

}
