package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeMainChecker;

/**
 * It checks tic tac toe in high diagonals.
 */
public class HighDiagonalsChecker extends DiagonalsChecker {

	/**
	 * It creates the single checker.
	 * 
	 * @param MAIN_CHECKER the main checker
	 */
	public HighDiagonalsChecker(final TicTacToeMainChecker MAIN_CHECKER) {
		super(MAIN_CHECKER);
		setChecksNumber(getNewChecksNumber(MAIN_CHECKER));
		setCheckMethod((iColumn)->checkDiagonalTicTacToe(new Point(iColumn, 0)));
	}



	@Override
	protected int getNewChecksNumber(TicTacToeMainChecker MAIN_CHECKER) {
		return MAIN_CHECKER.getGrid().getContent().length - MAIN_CHECKER.getTIC_TAC_TOE_NUMBER() + 1;
	}

}
