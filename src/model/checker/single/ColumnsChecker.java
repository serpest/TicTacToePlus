package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeSingleChecker;
import model.components.Grid;
import model.components.Pawn;

/**
 * It checks tic tac toe in columns.
 */
public class ColumnsChecker extends TicTacToeSingleChecker  {

	/**
	 * @param GRID the examined grid
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number of the game
	 * @param EXAMINED_PAWN the examined pawn
	 */
	public ColumnsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(GRID));
		setCheckMethod(this::checkColumnTicTacToe);
	}



	/**
	 * @param grid the grid
	 * @return the checks number
	 */
	private int getNewChecksNumber(Grid grid) {
		return grid.getContent().length;
	}

	/**
	 * It checks the column searching a tic tac toe.
	 * 
	 * @param iColumn index of the column
	 * @return the winner points or null
	 */
	private Point[] checkColumnTicTacToe(final int iColumn) {
		return checkLineTicTacToe(getGRID().getContent()[0].length, (i)->new Point(iColumn,i));
	}

}
