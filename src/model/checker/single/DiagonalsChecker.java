package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeSingleChecker;
import model.components.Grid;
import model.components.Pawn;

public abstract class DiagonalsChecker extends TicTacToeSingleChecker {

	/**
	 * @param GRID the examined grid
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number of the game
	 * @param EXAMINED_PAWN the examined pawn
	 */
	public DiagonalsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
	}



	/**
	 * @param MAIN_CHECKER the main checker
	 * @return the checks number
	 */
	protected abstract int getNewChecksNumber(int TIC_TAC_TOE_NUMBER);

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate it's 0)
	 * @return the winner points or null
	 */
	protected Point[] checkDiagonalTicTacToe(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		int mainPointDimension = Integer.max(firstPoint.getX(), firstPoint.getY());
		int minGridSize = Integer.min(getGRID().getContent().length, getGRID().getContent()[0].length);
		int maxGridSize = Integer.max(getGRID().getContent().length, getGRID().getContent()[0].length);
		int lineSize = minGridSize - mainPointDimension; //The line size calculated for axes x=y
		if ((getGRID().getContent().length > getGRID().getContent()[0].length && mainPointIsX) || (getGRID().getContent().length < getGRID().getContent()[0].length && !mainPointIsX)) { //The main point axis is greater than the minimum axis
			if (mainPointDimension < maxGridSize - (minGridSize - 1)) { //The main point is before the square grid where axes x = y
				lineSize = minGridSize;
			}
			else { //The main point is in the square grid where axes x = y
				lineSize += maxGridSize - minGridSize;
			}
		}
		Point[] toRightResult = checkLineTicTacToe(lineSize, (i)->new Point(firstPoint.getX() + i, firstPoint.getY() + i));
		Point[] toLeftResult = checkLineTicTacToe(lineSize, (i)->new Point((getGRID().getContent().length - 1) - firstPoint.getX() - i, firstPoint.getY() + i));
		return (toRightResult != null) ? toRightResult : toLeftResult;
	}

}
