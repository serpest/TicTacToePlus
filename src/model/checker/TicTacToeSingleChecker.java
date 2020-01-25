package model.checker;

import model.components.Grid;
import model.components.Pawn;

import java.util.concurrent.Callable;

import model.base.Point;

/**
 * It checks tic tac toe in rows, columns, high diagonals or low diagonals.
 */
public abstract class TicTacToeSingleChecker implements Callable<Point[]> {

	/**
	 * The examined grid.
	 */
	private final Grid GRID;

	/**
	 * The tic tac toe number of the game.
	 */
	private final int TIC_TAC_TOE_NUMBER;

	/**
	 * The examined pawn.
	 */
	private final Pawn EXAMINED_PAWN;

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
	 * @param GRID the examined grid
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number of the game
	 * @param EXAMINED_PAWN the examined pawn
	 */
	public TicTacToeSingleChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		this.GRID = GRID;
		this.TIC_TAC_TOE_NUMBER = TIC_TAC_TOE_NUMBER;
		this.EXAMINED_PAWN = EXAMINED_PAWN;
	}



	/**
	 * It checks the lines selected and it returns the result.
	 */
	@Override
	public Point[] call() {
		Point[] winnerPoints = null;
		for (int i = 0; i < checksNumber; i++) {
			winnerPoints = checkMethod.get(i);
			if (winnerPoints != null) {
				break;
			}
		}
		return winnerPoints;
	}



	protected Grid getGRID() {
		return GRID;
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
		for (int i = 0; i <= lineSize - TIC_TAC_TOE_NUMBER; i++) {
			winnerPoints = new Point[TIC_TAC_TOE_NUMBER];
			winnerPoints[0] = selector.get(i);
			currentMainPawn = GRID.getPawn(winnerPoints[0]);
			if (currentMainPawn == null || !currentMainPawn.equals(EXAMINED_PAWN)) {
				continue;
			}
			for (int j = 1; j < TIC_TAC_TOE_NUMBER; j++) {
				winnerPoints[j] = selector.get(i + j);
				currentExaminedPawn = GRID.getPawn(winnerPoints[j]);
				if (currentExaminedPawn == null || !currentMainPawn.equals(currentExaminedPawn)) {
					break;
				}
				if (j == TIC_TAC_TOE_NUMBER - 1) {
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