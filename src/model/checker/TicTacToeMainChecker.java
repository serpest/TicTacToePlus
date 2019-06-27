package model.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.base.Point;
import model.checker.single.ColumnsChecker;
import model.checker.single.HighDiagonalsChecker;
import model.checker.single.LowDiagonalsChecker;
import model.checker.single.RowsChecker;
import model.components.Grid;
import model.components.Pawn;

/**
 * It searches in the grid a tic tac toe.
 */
public class TicTacToeMainChecker {

	/**
	 * The game's tic tac toe number.
	 */
	private final int TIC_TAC_TOE_NUMBER;

	/**
	 * The examined grid.
	 */
	private Grid grid;

	/**
	 * The type of the pawn examined.
	 * It's helpful if the <code>ticTacToeNumber</code> is long.
	 */
	private Pawn examinedPawn;

	/**
	 * The single checkers' <code>ExecutorService</code>.
	 */
	private ExecutorService executorService;

	/**
	 * The pawn was found or it was not found by anyone?
	 */
	private volatile boolean resultFound;



	/**
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number
	 */
	public TicTacToeMainChecker(final int TIC_TAC_TOE_NUMBER) {
		this.TIC_TAC_TOE_NUMBER = TIC_TAC_TOE_NUMBER;
		 initExecutorService();
	}



	public int getTIC_TAC_TOE_NUMBER() {
		return TIC_TAC_TOE_NUMBER;
	}

	public Grid getGrid() {
		return grid;
	}

	/**
	 * It finds a tic tac toe.
	 * If there are more than one it can see only the first tic tac toe founded.
	 * 
	 * @param grid the examined grid
	 * @param examinedPawn the examined pawn
	 * @return the winner points or null if there isn't a tic tac toe
	 */
	public Point[] checkTicTacToe(Grid grid, Pawn examinedPawn) {
		resetSearching();
		this.grid = grid;
		this.examinedPawn = examinedPawn; //If examinedPawn is null TicTacToeSingleChecker.checkLineTicTacToe doesn't find any false tic tac toe
		List<Future<Point[]>> singleCheckersFutures = new ArrayList<>();
		singleCheckersFutures.add(executorService.submit(new ColumnsChecker(this)));
		singleCheckersFutures.add(executorService.submit(new RowsChecker(this)));
		singleCheckersFutures.add(executorService.submit(new HighDiagonalsChecker(this)));
		singleCheckersFutures.add(executorService.submit(new LowDiagonalsChecker(this)));
		Point[] winnerPoints = null;
		for (Future<Point[]> future : singleCheckersFutures) {
			try {
				winnerPoints = future.get();
			} catch (InterruptedException | ExecutionException exc) {
				exc.printStackTrace();
			}
			if (winnerPoints != null) {
				break;
			}
		}
		return winnerPoints;
	}



	Pawn getExaminedPawn() {
		return examinedPawn;
	}

	boolean isResultFound() {
		return resultFound;
	}

	void setResultFound(boolean resultFound) {
		this.resultFound = resultFound;
	}



	/**
	 * It resets <code>resultFound</code>.
	 */
	private void resetSearching() {
		resultFound = false;
	}

	/**
	 * It initializes the the single checkers' executorService.
	 */
	private void initExecutorService() {
		executorService = Executors.newFixedThreadPool(4);
	}

}