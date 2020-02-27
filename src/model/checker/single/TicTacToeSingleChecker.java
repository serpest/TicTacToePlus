package model.checker.single;

import model.components.Grid;
import model.components.Pawn;

import java.util.concurrent.Callable;

import model.base.Point;

public abstract class TicTacToeSingleChecker implements Callable<Point[]> {

	private final Grid GRID;

	private final int TIC_TAC_TOE_NUMBER;

	private final Pawn EXAMINED_PAWN;

	/**
	 * the method using by <code>checkLinesTicTacToe()</code> to do the check.
	 * The methods are standardized to receive only a parameter.
	 */
	private Checker checkMethod;

	/**
	 * The number of lines to analyze.
	 */
	private int checksNumber;



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

	protected void setCheckMethod(Checker checkMethod) {
		this.checkMethod = checkMethod;
	}

	protected void setChecksNumber(int checksNumber) {
		this.checksNumber = checksNumber;
	}

	/**
	 * It checks tic tac toes in the line.
	 * When it finds a tic tac toe it doesn't check if there is another.
	 * 
	 * @param lineSize the length of the line
	 * @param getter the getter of content
	 * @return the winner points or null
	 */
	protected Point[] checkLineTicTacToe(final int lineSize, final PawnGetter getter) {
		Point[] winnerPoints;
		Pawn currentExaminedPawn;
		int maximumIndexOfTheTicTacToeFirstPawn = lineSize - TIC_TAC_TOE_NUMBER;
		for (int i = 0; i <= maximumIndexOfTheTicTacToeFirstPawn; i++) {
			winnerPoints = new Point[TIC_TAC_TOE_NUMBER];
			for (int j = 0; j < TIC_TAC_TOE_NUMBER; j++) {
				winnerPoints[j] = getter.get(i + j);
				currentExaminedPawn = GRID.getPawn(winnerPoints[j]);
				if (isPawnIrrelevant(currentExaminedPawn)) {
					break;
				}
				if (j == TIC_TAC_TOE_NUMBER - 1) {
					return winnerPoints;
				}
			}	 
		}
		return null;
	}



	private boolean isPawnIrrelevant(Pawn pawn) {
		return pawn == null || !pawn.equals(EXAMINED_PAWN);
	}



	/**
	 * It's used to select the type of the single checker.
	 */
	@FunctionalInterface
	protected interface Checker {

		/**
		 * @param i the index
		 * @return the winner points or null
		 */
		Point[] get(int i);

	}



	/**
	 * It's used to get a single Pawn from the <code>gridContent</code> with custom coordinates.
	 */
	@FunctionalInterface
	protected interface PawnGetter {

		Point get(int i);

	}

}