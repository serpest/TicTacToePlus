package model;

import java.util.ArrayList;
import java.util.List;

//TODO Implement multithreading checker
class TicTacToeChecker {

	private final byte TIC_TAC_TOE_NUMBER;



	private Pawn[][] gridContent;



	/**
	 * It finds the tic tac toes.
	 * If there are more than one it can see only one tic tac toe for rows, one for columns, one for the north-east diagonals and one for the south-west diagonals.
	 * 
	 * @return the Pawn winner or null if there isn't a tic tac toe
	 */
	public Pawn checkTicTacToe(Pawn[][] gridContent) {
		this.gridContent = gridContent;
		List<Pawn> results = new ArrayList<>();
		results.add(checkLinesTicTacToe((byte) gridContent.length, (iColumn)->checkColumnTicTacToe(iColumn))); //checkColumnsTicTacToe
		results.add(checkLinesTicTacToe((byte) gridContent[0].length, (iRow)->checkRowTicTacToe(iRow))); //checkRowsTicTacToe					
		results.add(checkLinesTicTacToe((byte)(gridContent.length - TIC_TAC_TOE_NUMBER + 1), (iColumn)->checkDiagonalTicTacToe(new Point(iColumn, (byte) 0)))); //checkDiagonalsTicTacToe - Point(x, 0)			
		results.add(checkLinesTicTacToe((byte)(gridContent[0].length - TIC_TAC_TOE_NUMBER + 1), (iRow)->checkDiagonalTicTacToe(new Point((byte) 0, iRow)))); //checkDiagonalsTicTacToe - Point(0, y)
		for (Pawn pawn : results) {
			if (pawn != null) {
				return pawn;
			}
		}
		return null;
	}

	private Pawn checkLinesTicTacToe(final byte checksN, final Selector selector) {
		Pawn currentPawn;
		for (byte i = 0; i < checksN; i++) {
			currentPawn = selector.get(i);
			if (currentPawn != null) {
				return currentPawn;
			}
		}
		return null;
	}

	private Pawn checkRowTicTacToe(final byte iRow) {
		return checkLineTicTacToe((byte) gridContent.length, (i)->gridContent[i][iRow]);
	}

	private Pawn checkColumnTicTacToe(final byte iColumn) {
		return checkLineTicTacToe((byte) gridContent[0].length, (i)->gridContent[iColumn][i]);
	}

	/**
	 * It checks two symmetric diagonals.
	 * 
	 * @param firstPoint of the right diagonal (minimum a coordinate it's 0)
	 * @return the winner pawn or null
	 */
	private Pawn checkDiagonalTicTacToe(final Point firstPoint) {
		boolean mainPointIsX = firstPoint.getX() >= firstPoint.getY();
		byte mainPointDimension = (byte) Integer.max(firstPoint.getX(), firstPoint.getY());
		byte minGridSize = (byte) Integer.min(gridContent.length, gridContent[0].length);
		byte maxGridSize = (byte) Integer.max(gridContent.length, gridContent[0].length);
		byte lineSize = (byte) (minGridSize - mainPointDimension); //The line size calculated for axes x=y
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
	 * 
	 * @param lineSize the length of the line
	 * @param selector the getter of content
	 * @return the winner pawn or null
	 */
	private Pawn checkLineTicTacToe(final byte lineSize, final Selector selector) {
		Pawn currentMainPawn;
		Pawn currentExaminedPawn;
		for (byte i = 0; i <= lineSize - TIC_TAC_TOE_NUMBER; i++) {
			currentMainPawn = selector.get(i);
			if (currentMainPawn == null) {
				continue;
			}
			for (byte j = 1; j < TIC_TAC_TOE_NUMBER; j++) {
				currentExaminedPawn = selector.get((byte) (i + j));
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



	public TicTacToeChecker(final byte TIC_TAC_TOE_NUMBER) {
		this.TIC_TAC_TOE_NUMBER = TIC_TAC_TOE_NUMBER;
	}



	@FunctionalInterface
	private interface Selector {
		Pawn get(byte i);
	}

}
