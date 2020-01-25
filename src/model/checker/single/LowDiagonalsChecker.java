package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

/**
 * It checks tic tac toe in low diagonals.
 */
public class LowDiagonalsChecker extends DiagonalsChecker {

	/**
	 * @param GRID the examined grid
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number of the game
	 * @param EXAMINED_PAWN the examined pawn
	 */
	public LowDiagonalsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(TIC_TAC_TOE_NUMBER));
		setCheckMethod((iRow)->checkDiagonalTicTacToe(new Point( 0, iRow)));
	}



	@Override
	protected int getNewChecksNumber(int TIC_TAC_TOE_NUMBER) {
		return getGRID().getContent()[0].length - TIC_TAC_TOE_NUMBER + 1;
	}

}