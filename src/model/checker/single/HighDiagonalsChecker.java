package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

public class HighDiagonalsChecker extends DiagonalsChecker {

	public HighDiagonalsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(TIC_TAC_TOE_NUMBER));
		setCheckMethod((iColumn)->checkDiagonalTicTacToe(new Point(iColumn, 0)));
	}



	@Override
	protected int getNewChecksNumber(int TIC_TAC_TOE_NUMBER) {
		return getGRID().getXSize() - TIC_TAC_TOE_NUMBER + 1;
	}

}
