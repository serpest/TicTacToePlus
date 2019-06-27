package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeMainChecker;
import model.checker.TicTacToeSingleChecker;
import model.components.Grid;

/**
 * It checks tic tac toe in rows.
 */
public class RowsChecker extends TicTacToeSingleChecker {

	/**
	 * It creates the single checker.
	 * 
	 * @param MAIN_CHECKER the main checker
	 */
	public RowsChecker(final TicTacToeMainChecker MAIN_CHECKER) {
		super(MAIN_CHECKER);
		setChecksNumber(getNewChecksNumber(MAIN_CHECKER.getGrid()));
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
		return checkLineTicTacToe(getMAIN_CHECKER().getGrid().getContent().length, (i)->new Point(i,iRow));
	}

}
