package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeMainChecker;
import model.checker.TicTacToeSingleChecker;
import model.components.Grid;

/**
 * It checks tic tac toe in columns.
 */
public class ColumnsChecker extends TicTacToeSingleChecker  {

	/**
	 * It creates the single checker.
	 * 
	 * @param MAIN_CHECKER the main checker
	 */
	public ColumnsChecker(final TicTacToeMainChecker MAIN_CHECKER) {
		super(MAIN_CHECKER);
		setChecksNumber(getNewChecksNumber(MAIN_CHECKER.getGrid()));
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
		return checkLineTicTacToe(getMAIN_CHECKER().getGrid().getContent()[0].length, (i)->new Point(iColumn,i));
	}

}
