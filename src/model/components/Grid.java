package model.components;

import model.base.Point;
import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.exceptions.GridSizeException;

/**
 * The tic tac toe grid with default or custom sizes.
 */
public class Grid implements Cloneable {

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
		if (areGridDimensionsCorrect(xSize, ySize)) {
			setContent(new Pawn[xSize][ySize]);
			return;
		}
		throw new GridSizeException();
	}



	/**
	 * It uses coordinates (column[x]; row[y]).
	 * 
	 * column =	Pawn[n]
	 */
	private Pawn[][] content;



	public Pawn[][] getContent() {
		return content;
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
		checkPointOccupied(point);
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
	 * @return the grid is full?
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
	 * @return the grid is empty?
	 */
	public boolean isEmpty() {
		for (Pawn[] pawns : getContent()) {
			for (Pawn pawn : pawns) {
				if (pawn != null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * It returns a new independent grid.
	 * 
	 * @return a copied grid
	 */
	public Grid deepCopy() {
		try {
			Grid clonedGrid = (Grid) super.clone();
			Pawn[][] clonedGridContent = getContent().clone();
			for (byte i = 0; i < getContent().length; i++) {
				clonedGridContent[i] = clonedGridContent[i].clone();
			}
			clonedGrid.setContent(clonedGridContent);
			return clonedGrid;
		} catch (CloneNotSupportedException exc) {
			exc.printStackTrace();
		}
		return null;
	}



	private void setContent(Pawn[][] content) {
		this.content = content;
	}

	/**
	 * @param point the point of the grid
	 * @throws GridPositionNotExistsException if the point ins't inside the grid
	 */
	private void checkPositionExistenceInGrid(Point point) throws GridPositionNotExistsException {
		if (point.getX() > content.length - 1 || point.getY() > content[0].length - 1) {
			throw new GridPositionNotExistsException(point);
		}
	}

	/**
	 * It check the correction of the grid size.
	 * It is used from the constructor to verify the parameters.
	 * 
	 * @param xSize the size of y
	 * @param ySize the size of y
	 * @return grid dimensions are correct?
	 */
	private boolean areGridDimensionsCorrect(final int xSize, final int ySize) {
		return (xSize >= MIN_SIZE && ySize > 0) || (ySize >= MIN_SIZE && xSize > 0);
	}

	/**
	 * It throws an exception if the parameter point is already occupied in the grid.
	 * 
	 * @param point the point
	 * @throws GridPositionNotExistsException if the point ins't inside the grid
	 */
	private void checkPointOccupied(Point point) throws GridPositionOccupiedException {
		if (getContent()[point.getX()][point.getY()] != null) {
			throw new GridPositionOccupiedException(point);
		}
	}

}