package model.checker.single;

import model.base.Point;
import model.checker.TicTacToeMainChecker;

/**
 * It checks tic tac toe in low diagonals.
 */
public class LowDiagonalsChecker extends DiagonalsChecker {

	/**
	 * It creates the single checker.
	 * 
	 * @param MAIN_CHECKER the main checker
	 */
	public LowDiagonalsChecker(final TicTacToeMainChecker MAIN_CHECKER) {
		super(MAIN_CHECKER);
		setChecksNumber(getNewChecksNumber(MAIN_CHECKER));
		setCheckMethod((iRow)->checkDiagonalTicTacToe(new Point( 0, iRow)));
	}



	@Override
	protected int getNewChecksNumber(TicTacToeMainChecker MAIN_CHECKER) {
		return MAIN_CHECKER.getGrid().getContent()[0].length - MAIN_CHECKER.getTIC_TAC_TOE_NUMBER() + 1;
	}

}