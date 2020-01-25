package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

/**
 * It checks tic tac toe in high diagonals.
 */
public class HighDiagonalsChecker extends DiagonalsChecker {

	/**
	 * @param GRID the examined grid
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number of the game
	 * @param EXAMINED_PAWN the examined pawn
	 */
	public HighDiagonalsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(TIC_TAC_TOE_NUMBER));
		setCheckMethod((iColumn)->checkDiagonalTicTacToe(new Point(iColumn, 0)));
	}



	@Override
	protected int getNewChecksNumber(int TIC_TAC_TOE_NUMBER) {
		return getGRID().getContent().length - TIC_TAC_TOE_NUMBER + 1;
	}

}
