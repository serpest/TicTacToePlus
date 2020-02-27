package model.checker.single;

import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

public class ColumnsChecker extends TicTacToeSingleChecker  {

	public ColumnsChecker(final Grid GRID, final int TIC_TAC_TOE_NUMBER, final Pawn EXAMINED_PAWN) {
		super(GRID, TIC_TAC_TOE_NUMBER, EXAMINED_PAWN);
		setChecksNumber(getNewChecksNumber(GRID));
		setCheckMethod(this::checkColumnTicTacToe);
	}



	private int getNewChecksNumber(Grid grid) {
		return grid.getXSize();
	}

	private Point[] checkColumnTicTacToe(final int iColumn) {
		return checkLineTicTacToe(getGRID().getYSize(), (i)->new Point(iColumn,i));
	}

}
