package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

public class RowsChecker extends TicTacToeSingleChecker {

	public RowsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(getGRID()));
		setCheckMethod(this::checkRowTicTacToe);
	}



	private int getNewChecksNumber(Grid grid) {
		return grid.getYSize();
	}

	private Point[] checkRowTicTacToe(final int iRow) {
		return checkLineTicTacToe(getGRID().getXSize(), (i)->new Point(i,iRow));
	}

}
