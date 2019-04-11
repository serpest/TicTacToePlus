package model;

import java.util.ArrayList;
import java.util.List;

/**
 * It searches in the grid a tic tac toe.
 */
class TicTacToeChecker {

	/**
	 * It's the tic tac number of the current game.
	 */
	private final int TIC_TAC_TOE_NUMBER;

	/**
	 * It's the grid content of the current turn of the current game.
	 */
	private Pawn[][] gridContent;



	/**
	 * It finds the tic tac toes.
	 * If there are more than one it can see only one tic tac toe for rows, one for columns, one for the north-east diagonals and one for the south-west diagonals.
	 * 
	 * @param gridContent the grid content
	 * @return the Pawn winner or null if there isn't a tic tac toe
	 */
	public Pawn checkTicTacToe(Pawn[][] gridContent) {
		this.gridContent = gridContent;
		List<Pawn> results = new ArrayList<>();
		results.add(checkLinesTicTacToe(gridContent.length, (iColumn)->checkColumnTicTacToe(iColumn))); //checkColumnsTicTacToe
		results.add(checkLinesTicTacToe(gridContent[0].length, (iRow)->checkRowTicTacToe(iRow))); //checkRowsTicTacToe					
		results.add(checkLinesTicTacToe(gridContent.length - TIC_TAC_TOE_NUMBER + 1, (iColumn)->checkDiagonalTicTacToe(new Point(iColumn, 0)))); //checkDiagonalsTicTacToe - Point(x, 0)			
		results.add(checkLinesTicTacToe(gridContent[0].length - TIC_TAC_TOE_NUMBER + 1, (iRow)->checkDiagonalTicTacToe(new Point( 0, iRow)))); //checkDiagonalsTicTacToe - Point(0, y)
		for (Pawn pawn : results) {
			if (pawn != null) {
				return pawn;
			}
		}
		return null;
	}

	/**
	 * It checks the lines selected using the parameters.
	 * 
	 * @param checksN the number of lines to analyze
	 * @param selector the type of the single checker (e.g. <code>checkRowTicTacToe()</code>)
	 * @return the Pawn winner or null if there isn't a tic tac toe
	 */
	private Pawn checkLinesTicTacToe(final int checksN, final Selector selector) {
		Pawn currentPawn;
		for (int i = 0; i < checksN; i++) {
			currentPawn = selector.get(i);
			if (currentPawn != null) {
				return currentPawn;
			}
		}
		return null;
	}

	/**
	 * It checks the row searching a tic tac toe.
	 * 
	 * @param iRow index of the row
	 * @return the winner pawn or null
	 */
	private Pawn checkRowTicTacToe(final int iRow) {
		return checkLineTicTacToe(gridContent.length, (i)->gridContent[i][iRow]);
	}

	/**
	 * It checks the column searching a tic tac toe.
	 * 
	 * @param iColumn index of the column
	 * @return the winner pawn or null
	 */
	private Pawn checkColumnTicTacToe(final int iColumn) {
		return checkLineTicTacToe(gridContent[0].length, (i)->gridContent[iColumn][i]);
	}

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate it's 0)
	 * @return the winner pawn or null
	 */
	private Pawn checkDiagonalTicTacToe(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		int mainPointDimension = Integer.max(firstPoint.getX(), firstPoint.getY());
		int minGridSize = Integer.min(gridContent.length, gridContent[0].length);
		int maxGridSize = Integer.max(gridContent.length, gridContent[0].length);
		int lineSize = minGridSize - mainPointDimension; //The line size calculated for axes x=y
		if ((gridContent.length > gridContent[0].length && mainPointIsX) || (gridContent.length < gridContent[0].length && !mainPointIsX)) { //The main point axis is greater than the minimum axis
			if (mainPointDimension < maxGridSize - (minGridSize - 1)) { //The main point is before the square grid where axes x = y
				lineSize = minGridSize;
			}
			else { //The main point is in the square grid where axes x = y
				lineSize += maxGridSize - minGridSize;
			}
		}
		Pawn toRightResult = checkLineTicTacToe(lineSize, (i)->gridContent[firstPoint.getX() + i][firstPoint.getY() + i]);
		Pawn toLeftResult = checkLineTicTacToe(lineSize, (i)->gridContent[(gridContent.length - 1) - firstPoint.getX() - i][firstPoint.getY() + i]);
		return (toRightResult != null) ? toRightResult : toLeftResult;
	}

	/**
	 * It checks tic tac toes in the line.
	 * The line is a row, a column or a diagonal.
	 * When it finds a tic tac toe it doesn't check if there is another.
	 * 
	 * @param lineSize the length of the line
	 * @param selector the getter of content
	 * @return the winner pawn or null
	 */
	private Pawn checkLineTicTacToe(final int lineSize, final Selector selector) {
		Pawn currentMainPawn;
		Pawn currentExaminedPawn;
		for (int i = 0; i <= lineSize - TIC_TAC_TOE_NUMBER; i++) {
			currentMainPawn = selector.get(i);
			if (currentMainPawn == null) {
				continue;
			}
			for (int j = 1; j < TIC_TAC_TOE_NUMBER; j++) {
				currentExaminedPawn = selector.get(i + j);
				if (currentExaminedPawn == null || !currentMainPawn.equals(currentExaminedPawn)) {
					break;
				}
				if (j == TIC_TAC_TOE_NUMBER - 1) {
					return currentMainPawn;
				}
			}	 
		}
		return null;
	}



	/**
	 * @param TIC_TAC_TOE_NUMBER the tic tac number
	 */
	public TicTacToeChecker(final int TIC_TAC_TOE_NUMBER) {
		this.TIC_TAC_TOE_NUMBER = TIC_TAC_TOE_NUMBER;
	}



	/**
	 * It's using to:
	 * 	select the type of the single checker (e.g. <code>checkRowTicTacToe()</code>);
	 * 	get a single Pawn from the <code>gridContent</code> with custom coordinates.
	 */
	@FunctionalInterface
	private interface Selector {
		/**
		 * @param i the index
		 * @return a <code>Pawn</code>
		 */
		Pawn get(int i);
	}

}
