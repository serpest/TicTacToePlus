package model;

class TicTacToeSingleChecker implements Runnable {

	private Thread thread;

	/**
	 * The main checker that creates this single checker.
	 * It's also used to get the <code>TicTacToeGame</code>.
	 */
	private final TicTacToeMainChecker MAIN_CHECKER;

	/**
	 * the method using by <code>checkLinesTicTacToe()</code> to do the check.
	 * The methods (e.g. <code>checkDiagonalTicTacToe()</code>) are standardized to receive only a parameter.
	 */
	private final Selector CHECK_METHOD;

	/**
	 * The number of lines to analyze.
	 */
	private final int CHECKS_NUMBER;



	/**
	 * It generates the <code>CHECK_METHOD</code> for the constructor.
	 * 
	 * @param mode the mode
	 * @return the new value of <code>CHECK_METHOD</code>.
	 */
	private Selector generateCHECK_METHOD(Mode mode) {
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
		case HIGH_DIAGONALS:
			return MAIN_CHECKER.getGame().getGrid().getContent().length - MAIN_CHECKER.getGame().getTicTacToeNumber() + 1;
		case ROWS:
		case LOW_DIAGONALS:
			return MAIN_CHECKER.getGame().getGrid().getContent()[0].length - MAIN_CHECKER.getGame().getTicTacToeNumber() + 1;
		default:
			assert false;
			return -1;
		}
	}

	/**
	 * It checks the lines selected using the details created by <code>initCheckDetails()</code> in the constructor.
	 */
	@Override
	public void run() {
		Pawn winnerPawn;
		for (int i = 0; i < CHECKS_NUMBER; i++) {
			if (MAIN_CHECKER.isResultFound()) {
				return;
			}
			winnerPawn = CHECK_METHOD.get(i);
			if (winnerPawn != null) {
				MAIN_CHECKER.setResult(winnerPawn);
				return;
			}
		}
		MAIN_CHECKER.addFailedSearchingAttempt();
		return;
	}

	/**
	 * It starts the thread already created.
	 */
	public void startThread() {
		thread.start();
	}

	/**
	 * It checks the row searching a tic tac toe.
	 * 
	 * @param iRow index of the row
	 * @return the winner pawn or null
	 */
	private Pawn checkRowTicTacToe(final int iRow) {
		return checkLineTicTacToe(MAIN_CHECKER.getGame().getGrid().getContent().length, (i)->MAIN_CHECKER.getGame().getGrid().getContent()[i][iRow]);
	}

	/**
	 * It checks the column searching a tic tac toe.
	 * 
	 * @param iColumn index of the column
	 * @return the winner pawn or null
	 */
	private Pawn checkColumnTicTacToe(final int iColumn) {
		return checkLineTicTacToe(MAIN_CHECKER.getGame().getGrid().getContent()[0].length, (i)->MAIN_CHECKER.getGame().getGrid().getContent()[iColumn][i]);
	}

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate it's 0)
	 * @return the winner pawn or null
	 */
	private Pawn checkDiagonalTicTacToe(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		int mainPointDimension = Integer.max(firstPoint.getX(), firstPoint.getY());
		int minGridSize = Integer.min(MAIN_CHECKER.getGame().getGrid().getContent().length, MAIN_CHECKER.getGame().getGrid().getContent()[0].length);
		int maxGridSize = Integer.max(MAIN_CHECKER.getGame().getGrid().getContent().length, MAIN_CHECKER.getGame().getGrid().getContent()[0].length);
		int lineSize = minGridSize - mainPointDimension; //The line size calculated for axes x=y
		if ((MAIN_CHECKER.getGame().getGrid().getContent().length > MAIN_CHECKER.getGame().getGrid().getContent()[0].length && mainPointIsX) || (MAIN_CHECKER.getGame().getGrid().getContent().length < MAIN_CHECKER.getGame().getGrid().getContent()[0].length && !mainPointIsX)) { //The main point axis is greater than the minimum axis
			if (mainPointDimension < maxGridSize - (minGridSize - 1)) { //The main point is before the square grid where axes x = y
				lineSize = minGridSize;
			}
			else { //The main point is in the square grid where axes x = y
				lineSize += maxGridSize - minGridSize;
			}
		}
		Pawn toRightResult = checkLineTicTacToe(lineSize, (i)->MAIN_CHECKER.getGame().getGrid().getContent()[firstPoint.getX() + i][firstPoint.getY() + i]);
		Pawn toLeftResult = checkLineTicTacToe(lineSize, (i)->MAIN_CHECKER.getGame().getGrid().getContent()[(MAIN_CHECKER.getGame().getGrid().getContent().length - 1) - firstPoint.getX() - i][firstPoint.getY() + i]);
		return (toRightResult != null) ? toRightResult : toLeftResult;
	}

	/**
	 * It checks tic tac toes in the line.
	 * The line is a row, a column or a diagonal.
	 * When it finds a tic tac toe it doesn't check if there is another.
	 * 
	 * @param lineSize the length of the line
	 * @param selector the getter of content
	 * @return the winner pawn or null
	 */
	private Pawn checkLineTicTacToe(final int lineSize, final Selector selector) {
		Pawn currentMainPawn;
		Pawn currentExaminedPawn;
		for (int i = 0; i <= lineSize - MAIN_CHECKER.getGame().getTicTacToeNumber(); i++) {
			currentMainPawn = selector.get(i);
			if (currentMainPawn == null || !currentMainPawn.equals(MAIN_CHECKER.getExaminedPawn())) {
				continue;
			}
			for (int j = 1; j < MAIN_CHECKER.getGame().getTicTacToeNumber(); j++) {
				currentExaminedPawn = selector.get(i + j);
				if (currentExaminedPawn == null || !currentMainPawn.equals(currentExaminedPawn)) {
					break;
				}
				if (j == MAIN_CHECKER.getGame().getTicTacToeNumber() - 1) {
					return currentMainPawn;
				}
			}	 
		}
		return null;
	}



	/**
	 * It creates a single checker thread and it calls the <code>initCheckDetails()</code> method.
	 * 
	 * @param MAIN_CHECKER the main checker
	 * @param mode the mode of the checker
	 */
	public TicTacToeSingleChecker(final TicTacToeMainChecker MAIN_CHECKER, Mode mode) {
		thread = new Thread(this, "TicTacToeSingleChecker");
		thread.setPriority(Thread.NORM_PRIORITY);
		this.MAIN_CHECKER = MAIN_CHECKER;
		CHECK_METHOD = generateCHECK_METHOD(mode);
		CHECKS_NUMBER = generateCHECKS_NUMBER(mode);
	}



	/**
	 * It determines the single checker mode.
	 */
	static enum Mode {
		COLUMNS, ROWS, HIGH_DIAGONALS /*The main diagonal and the ones above*/, LOW_DIAGONALS /*The main diagonal and the ones below*/;
	}

	/**
	 * It's using to:
	 * 	select the type of the single checker (e.g. <code>checkRowTicTacToe()</code>);
	 * 	get a single Pawn from the <code>gridContent</code> with custom coordinates.
	 */
	@FunctionalInterface
	private interface Selector {
		/**
		 * @param i the index
		 * @return a <code>Pawn</code>
		 */
		Pawn get(int i);
	}

}
