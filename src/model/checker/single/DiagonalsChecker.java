package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

public abstract class DiagonalsChecker extends TicTacToeSingleChecker {

	public DiagonalsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
	}



	protected abstract int getNewChecksNumber(int TIC_TAC_TOE_NUMBER);

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate must be 0)
	 * @return the winner points or null
	 */
	protected Point[] checkDiagonalTicTacToe(final Point firstPoint) {
		int lineSize = getLineSize(firstPoint);
		Point[] toRightResult = checkLineTicTacToe(lineSize, (i)->new Point(firstPoint.getX() + i, firstPoint.getY() + i));
		Point[] toLeftResult = checkLineTicTacToe(lineSize, (i)->new Point((getGRID().getXSize() - 1) - firstPoint.getX() - i, firstPoint.getY() + i));
		return (toRightResult != null) ? toRightResult : toLeftResult;
	}

	private int getLineSize(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		int mainPointIndex = Integer.max(firstPoint.getX(), firstPoint.getY());
		int minGridSize = Integer.min(getGRID().getXSize(), getGRID().getYSize());
		int maxGridSize = Integer.max(getGRID().getXSize(), getGRID().getYSize());
		int lineSize;
		if (isMainPointAxisGreaterThanMinimumAxis(mainPointIsX)) {
			if (isMainPointBeforeTheSquareGridWhereLineSizeIsLess(mainPointIndex, maxGridSize,
					minGridSize)) {
				lineSize = minGridSize;
			}
			else {
				lineSize = maxGridSize - mainPointIndex;
			}
		}
		else {
			lineSize = minGridSize - mainPointIndex;
		}
		return lineSize;
	}

	private boolean isMainPointAxisGreaterThanMinimumAxis(boolean mainPointIsX) {
		return (mainPointIsX && getGRID().getXSize() > getGRID().getYSize()) ||
				(!mainPointIsX && getGRID().getXSize() < getGRID().getYSize());
	}

	private boolean isMainPointBeforeTheSquareGridWhereLineSizeIsLess(int mainPointIndex,
			int maxGridSize, int minGridSize) {
		return mainPointIndex < maxGridSize - (minGridSize - 1);
	}

}
