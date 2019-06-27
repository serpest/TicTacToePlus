package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeMainChecker;
import model.checker.TicTacToeSingleChecker;

public abstract class DiagonalsChecker extends TicTacToeSingleChecker {

	/**
	 * It creates the single checker.
	 * 
	 * @param MAIN_CHECKER the main checker
	 */
	public DiagonalsChecker(TicTacToeMainChecker MAIN_CHECKER) {
		super(MAIN_CHECKER);
	}



	/**
	 * @param MAIN_CHECKER the main checker
	 * @return the checks number
	 */
	protected abstract int getNewChecksNumber(TicTacToeMainChecker MAIN_CHECKER);

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate it's 0)
	 * @return the winner points or null
	 */
	protected Point[] checkDiagonalTicTacToe(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		int mainPointDimension = Integer.max(firstPoint.getX(), firstPoint.getY());
		int minGridSize = Integer.min(getMAIN_CHECKER().getGrid().getContent().length, getMAIN_CHECKER().getGrid().getContent()[0].length);
		int maxGridSize = Integer.max(getMAIN_CHECKER().getGrid().getContent().length, getMAIN_CHECKER().getGrid().getContent()[0].length);
		int lineSize = minGridSize - mainPointDimension; //The line size calculated for axes x=y
		if ((getMAIN_CHECKER().getGrid().getContent().length > getMAIN_CHECKER().getGrid().getContent()[0].length && mainPointIsX) || (getMAIN_CHECKER().getGrid().getContent().length < getMAIN_CHECKER().getGrid().getContent()[0].length && !mainPointIsX)) { //The main point axis is greater than the minimum axis
			if (mainPointDimension < maxGridSize - (minGridSize - 1)) { //The main point is before the square grid where axes x = y
				lineSize = minGridSize;
			}
			else { //The main point is in the square grid where axes x = y
				lineSize += maxGridSize - minGridSize;
			}
		}
		Point[] toRightResult = checkLineTicTacToe(lineSize, (i)->new Point(firstPoint.getX() + i, firstPoint.getY() + i));
		Point[] toLeftResult = checkLineTicTacToe(lineSize, (i)->new Point((getMAIN_CHECKER().getGrid().getContent().length - 1) - firstPoint.getX() - i, firstPoint.getY() + i));
		return (toRightResult != null) ? toRightResult : toLeftResult;
	}

}
