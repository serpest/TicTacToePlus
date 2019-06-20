package model.checker;

import model.components.Pawn;

import java.util.concurrent.Callable;

import model.base.Point;

/**
 * It checks tic tac toe in rows, columns, high diagonals or low diagonals.
 */
class TicTacToeSingleChecker implements Callable<Point[]> {

	/**
	 * The main checker that creates this single checker.
	 * It's also used to get the <code>TicTacToeGame</code>.
	 */
	private final TicTacToeMainChecker MAIN_CHECKER;

	/**
	 * the method using by <code>checkLinesTicTacToe()</code> to do the check.
	 * The methods (e.g. <code>checkDiagonalTicTacToe()</code>) are standardized to receive only a parameter.
	 */
	private final Check CHECK_METHOD;

	/**
	 * The number of lines to analyze.
	 */
	private final int CHECKS_NUMBER;



	/**
	 * It creates a single checker thread and it calls the <code>initCheckDetails()</code> method.
	 * 
	 * @param MAIN_CHECKER the main checker
	 * @param mode the mode of the checker
	 */
	public TicTacToeSingleChecker(final TicTacToeMainChecker MAIN_CHECKER, Mode mode) {
		this.MAIN_CHECKER = MAIN_CHECKER;
		CHECK_METHOD = generateCHECK_METHOD(mode);
		CHECKS_NUMBER = generateCHECKS_NUMBER(mode);
	}



	/**
	 * It checks the lines selected using the details created by <code>initCheckDetails()</code> in the constructor and it returns the result.
	 */
	@Override
	public Point[] call() {
		Point[] winnerPoints = null;
		for (int i = 0; i < CHECKS_NUMBER; i++) {
			if (MAIN_CHECKER.isResultFound()) {
				return null;
			}
			winnerPoints = CHECK_METHOD.get(i);
			if (winnerPoints != null) {
				MAIN_CHECKER.setResultFound(true);
				break;
			}
		}
		return winnerPoints;
	}



	/**
	 * It generates the <code>CHECK_METHOD</code> for the constructor.
	 * 
	 * @param mode the mode
	 * @return the new value of <code>CHECK_METHOD</code>.
	 */
	private Check generateCHECK_METHOD(Mode mode) {
		switch (mode) {
		case COLUMNS:
			return (iColumn)->checkColumnTicTacToe(iColumn);
		case HIGH_DIAGONALS:
			return (iColumn)->checkDiagonalTicTacToe(new Point(iColumn, 0));
		case LOW_DIAGONALS:
			return (iRow)->checkDiagonalTicTacToe(new Point( 0, iRow));
		case ROWS:
			return (iRow)->checkRowTicTacToe(iRow);
		default:
			assert false;
			return null;
		
		}
	}

	/**
	 * It generates the <code>CHECKS_NUMBER</code> for the constructor.
	 * 
	 * @param mode the mode
	 * @return the new value of <code>CHECKS_NUMBER</code>.
	 */
	private int generateCHECKS_NUMBER(Mode mode) {
		switch (mode) {
		case COLUMNS:
			return MAIN_CHECKER.getGrid().getContent().length;
		case HIGH_DIAGONALS:
			return MAIN_CHECKER.getGrid().getContent().length - MAIN_CHECKER.getTIC_TAC_TOE_NUMBER() + 1;
		case ROWS:
			return MAIN_CHECKER.getGrid().getContent()[0].length;
		case LOW_DIAGONALS:
			return MAIN_CHECKER.getGrid().getContent()[0].length - MAIN_CHECKER.getTIC_TAC_TOE_NUMBER() + 1;
		default:
			assert false;
			return -1;
		}
	}

	/**
	 * It checks the row searching a tic tac toe.
	 * 
	 * @param iRow index of the row
	 * @return the winner points or null
	 */
	private Point[] checkRowTicTacToe(final int iRow) {
		return checkLineTicTacToe(MAIN_CHECKER.getGrid().getContent().length, (i)->new Point(i,iRow));
	}

	/**
	 * It checks the column searching a tic tac toe.
	 * 
	 * @param iColumn index of the column
	 * @return the winner points or null
	 */
	private Point[] checkColumnTicTacToe(final int iColumn) {
		return checkLineTicTacToe(MAIN_CHECKER.getGrid().getContent()[0].length, (i)->new Point(iColumn,i));
	}

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate it's 0)
	 * @return the winner points or null
	 */
	private Point[] checkDiagonalTicTacToe(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		int mainPointDimension = Integer.max(firstPoint.getX(), firstPoint.getY());
		int minGridSize = Integer.min(MAIN_CHECKER.getGrid().getContent().length, MAIN_CHECKER.getGrid().getContent()[0].length);
		int maxGridSize = Integer.max(MAIN_CHECKER.getGrid().getContent().length, MAIN_CHECKER.getGrid().getContent()[0].length);
		int lineSize = minGridSize - mainPointDimension; //The line size calculated for axes x=y
		if ((MAIN_CHECKER.getGrid().getContent().length > MAIN_CHECKER.getGrid().getContent()[0].length && mainPointIsX) || (MAIN_CHECKER.getGrid().getContent().length < MAIN_CHECKER.getGrid().getContent()[0].length && !mainPointIsX)) { //The main point axis is greater than the minimum axis
			if (mainPointDimension < maxGridSize - (minGridSize - 1)) { //The main point is before the square grid where axes x = y
				lineSize = minGridSize;
			}
			else { //The main point is in the square grid where axes x = y
				lineSize += maxGridSize - minGridSize;
			}
		}
		Point[] toRightResult = checkLineTicTacToe(lineSize, (i)->new Point(firstPoint.getX() + i, firstPoint.getY() + i));
		Point[] toLeftResult = checkLineTicTacToe(lineSize, (i)->new Point((MAIN_CHECKER.getGrid().getContent().length - 1) - firstPoint.getX() - i, firstPoint.getY() + i));
		return (toRightResult != null) ? toRightResult : toLeftResult;
	}

	/**
	 * It checks tic tac toes in the line.
	 * The line is a row, a column or a diagonal.
	 * When it finds a tic tac toe it doesn't check if there is another.
	 * 
	 * @param lineSize the length of the line
	 * @param selector the getter of content
	 * @return the winner points or null
	 */
	private Point[] checkLineTicTacToe(final int lineSize, final Selector selector) {
		Point[] winnerPoints;
		Pawn currentMainPawn;
		Pawn currentExaminedPawn;
		for (int i = 0; i <= lineSize - MAIN_CHECKER.getTIC_TAC_TOE_NUMBER(); i++) {
			winnerPoints = new Point[MAIN_CHECKER.getTIC_TAC_TOE_NUMBER()];
			winnerPoints[0] = selector.get(i);
			currentMainPawn = MAIN_CHECKER.getGrid().getPawn(winnerPoints[0]);
			if (currentMainPawn == null || !currentMainPawn.equals(MAIN_CHECKER.getExaminedPawn())) {
				continue;
			}
			for (int j = 1; j < MAIN_CHECKER.getTIC_TAC_TOE_NUMBER(); j++) {
				winnerPoints[j] = selector.get(i + j);
				currentExaminedPawn = MAIN_CHECKER.getGrid().getPawn(winnerPoints[j]);
				if (currentExaminedPawn == null || !currentMainPawn.equals(currentExaminedPawn)) {
					break;
				}
				if (j == MAIN_CHECKER.getTIC_TAC_TOE_NUMBER() - 1) {
					return winnerPoints;
				}
			}	 
		}
		return null;
	}



	/**
	 * It determines the single checker mode.
	 */
	static enum Mode {

		COLUMNS, ROWS, HIGH_DIAGONALS /*The main diagonal and the ones above*/, LOW_DIAGONALS /*The main diagonal and the ones below*/;

	}



	/**
	 * It's using to get a single Pawn from the <code>gridContent</code> with custom coordinates.
	 */
	@FunctionalInterface
	private interface Selector {

		/**
		 * @param i the index
		 * @return a <code>Point</code>
		 */
		Point get(int i);

	}

	/**
	 * It's using to select the type of the single checker (e.g. <code>checkRowTicTacToe()</code>);
	 */
	@FunctionalInterface
	private interface Check {

		/**
		 * @param i the index
		 * @return the winner points or null
		 */
		Point[] get(int i);

	}

}