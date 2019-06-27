package model.checker;

import model.components.Pawn;

import java.util.concurrent.Callable;

import model.base.Point;

/**
 * It checks tic tac toe in rows, columns, high diagonals or low diagonals.
 */
public abstract class TicTacToeSingleChecker implements Callable<Point[]> {

	/**
	 * The main checker that creates this single checker.
	 * It's used to get the grid, the <code>resultFound</code> boolean and the <code>examinedPawn</code>.
	 */
	private final TicTacToeMainChecker MAIN_CHECKER;

	/**
	 * the method using by <code>checkLinesTicTacToe()</code> to do the check.
	 * The methods are standardized to receive only a parameter.
	 */
	private Check checkMethod;

	/**
	 * The number of lines to analyze.
	 */
	private int checksNumber;



	/**
	 * It creates a single checker.
	 * 
	 * @param MAIN_CHECKER the main checker
	 */
	public TicTacToeSingleChecker(final TicTacToeMainChecker MAIN_CHECKER) {
		this.MAIN_CHECKER = MAIN_CHECKER;
	}



	public TicTacToeMainChecker getMAIN_CHECKER() {
		return MAIN_CHECKER;
	}

	/**
	 * It checks the lines selected and it returns the result.
	 */
	@Override
	public Point[] call() {
		Point[] winnerPoints = null;
		for (int i = 0; i < checksNumber; i++) {
			if (MAIN_CHECKER.isResultFound()) {
				return null;
			}
			winnerPoints = checkMethod.get(i);
			if (winnerPoints != null) {
				MAIN_CHECKER.setResultFound(true);
				break;
			}
		}
		return winnerPoints;
	}



	protected void setCheckMethod(Check checkMethod) {
		this.checkMethod = checkMethod;
	}

	protected void setChecksNumber(int checksNumber) {
		this.checksNumber = checksNumber;
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
	protected Point[] checkLineTicTacToe(final int lineSize, final Selector selector) {
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
	 * It's using to select the type of the single checker (e.g. <code>checkRowTicTacToe()</code>);
	 */
	@FunctionalInterface
	protected interface Check {

		/**
		 * @param i the index
		 * @return the winner points or null
		 */
		Point[] get(int i);

	}



	/**
	 * It's using to get a single Pawn from the <code>gridContent</code> with custom coordinates.
	 */
	@FunctionalInterface
	protected interface Selector {

		/**
		 * @param i the index
		 * @return a <code>Point</code>
		 */
		Point get(int i);

	}

}