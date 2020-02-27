package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

public class LowDiagonalsChecker extends DiagonalsChecker {

	public LowDiagonalsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(TIC_TAC_TOE_NUMBER));
		setCheckMethod((iRow)->checkDiagonalTicTacToe(new Point( 0, iRow)));
	}



	@Override
	protected int getNewChecksNumber(int TIC_TAC_TOE_NUMBER) {
		return getGRID().getYSize() - TIC_TAC_TOE_NUMBER + 1;
	}

}