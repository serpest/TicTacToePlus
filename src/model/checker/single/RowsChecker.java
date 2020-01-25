package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeSingleChecker;
import model.components.Grid;
import model.components.Pawn;

/**
 * It checks tic tac toe in rows.
 */
public class RowsChecker extends TicTacToeSingleChecker {

	/**
	 * @param GRID the examined grid
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number of the game
	 * @param EXAMINED_PAWN the examined pawn
	 */
	public RowsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(getGRID()));
		setCheckMethod(this::checkRowTicTacToe);
	}



	/**
	 * @param grid the grid
	 * @return the checks number
	 */
	private int getNewChecksNumber(Grid grid) {
		return grid.getContent()[0].length;
	}

	/**
	 * It checks the row searching a tic tac toe.
	 * 
	 * @param iRow index of the row
	 * @return the winner points or null
	 */
	private Point[] checkRowTicTacToe(final int iRow) {
		return checkLineTicTacToe(getGRID().getContent().length, (i)->new Point(i,iRow));
	}

}
