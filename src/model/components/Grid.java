package model.components;

import model.base.Point;
import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.exceptions.GridSizeException;
import utils.Utils;

public class Grid implements Cloneable {

	public final static int MIN_SIZE = 1; //It must be valid for minimum one axis

	public final static int DEFAULT_X_SIZE = 3;
	public final static int DEFAULT_Y_SIZE = 3;



	private Pawn[][] content; //[column][row]



	public Grid() {
		this(DEFAULT_X_SIZE, DEFAULT_Y_SIZE);
	}

	public Grid(int xSize, int ySize) throws GridSizeException {
		initContent(xSize, ySize);
	}



	public Pawn[][] getContent() {
		return content;
	}

	public void addPawn(Pawn pawn, Point point) throws GridPositionOccupiedException, GridPositionNotExistsException {
		checkPositionExistenceInGrid(point);
		checkPointOccupied(point);
		getContent()[point.getX()][point.getY()] = pawn;
	}

	public Pawn getPawn(Point point) throws GridPositionNotExistsException {
		checkPositionExistenceInGrid(point);
		return getContent()[point.getX()][point.getY()];
	}

	public boolean isFull() {
		return isEveryPawnFilter((pawn)->pawn != null);
	}

	public boolean isEmpty() {
		return isEveryPawnFilter((pawn)->pawn == null);
	}

	public int getXSize() {
		return getContent().length;
	}

	public int getYSize() {
		return getContent()[0].length;
	}

	@Override
	public Grid clone() {
		Grid clonedGrid = new Grid();
		Pawn[][] clonedGridContent = Utils.clone(getContent());
		clonedGrid.setContent(clonedGridContent);
		return clonedGrid;
	}



	private void setContent(Pawn[][] content) {
		this.content = content;
	}

	private void checkPositionExistenceInGrid(Point point) throws GridPositionNotExistsException {
		if (point.getX() > content.length - 1 || point.getY() > content[0].length - 1) {
			throw new GridPositionNotExistsException(point);
		}
	}

	private void initContent(int xSize, int ySize) throws GridSizeException {
		if (areGridDimensionsCorrect(xSize, ySize))
			setContent(new Pawn[xSize][ySize]);
		else
			throw new GridSizeException();
	}

	private boolean areGridDimensionsCorrect(final int xSize, final int ySize) {
		return (xSize >= MIN_SIZE && ySize >= MIN_SIZE);
	}

	private void checkPointOccupied(Point point) throws GridPositionOccupiedException {
		if (getContent()[point.getX()][point.getY()] != null) {
			throw new GridPositionOccupiedException(point);
		}
	}

	private boolean isEveryPawnFilter(PawnFilter filter) {
		for (Pawn[] pawns : getContent()) {
			for (Pawn pawn : pawns) {
				if (!filter.filter(pawn)) {
					return false;
				}
			}
		}
		return true;
	}



	@FunctionalInterface
	private interface PawnFilter {
		boolean filter(Pawn pawn);
	}

}