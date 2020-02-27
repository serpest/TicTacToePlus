package model.checker;

import java.util.ArrayList;
import java.util.Collection;
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
import model.checker.single.TicTacToeSingleChecker;
import model.components.Grid;
import model.components.Pawn;

/**
 * It searches in the grid a tic tac toe.
 */
public class TicTacToeMainChecker {

	private static final int SINGLE_CHECKERS_NUMBER = 4;



	private final int TIC_TAC_TOE_NUMBER;

	private ExecutorService executorService;




	public TicTacToeMainChecker(final int TIC_TAC_TOE_NUMBER) {
		this.TIC_TAC_TOE_NUMBER = TIC_TAC_TOE_NUMBER;
		 initExecutorService();
	}




	/**
	 * It searches a tic tac toe.
	 * If there are more than one tic tac toe it will find only the first one.
	 * 
	 * @param grid the examined grid
	 * @param examinedPawn the examined pawn
	 * @return the winner points or null if there isn't a tic tac toe
	 */
	public Point[] checkTicTacToe(Grid grid, Pawn examinedPawn) {
		Collection<TicTacToeSingleChecker> tasks = new ArrayList<>(SINGLE_CHECKERS_NUMBER);
		tasks.add(new ColumnsChecker(grid, TIC_TAC_TOE_NUMBER, examinedPawn));
		tasks.add(new RowsChecker(grid, TIC_TAC_TOE_NUMBER, examinedPawn));
		tasks.add(new HighDiagonalsChecker(grid, TIC_TAC_TOE_NUMBER, examinedPawn));
		tasks.add(new LowDiagonalsChecker(grid, TIC_TAC_TOE_NUMBER, examinedPawn));
		Point[] winnerPoints = null;
		try {
			List<Future<Point[]>> winnerPointsFutures = executorService.invokeAll(tasks);
			for (Future<Point[]> future : winnerPointsFutures) {
				winnerPoints = future.get();
				if (winnerPoints != null) {
					break;
				}
			}
		} catch (InterruptedException | ExecutionException exc) {
			exc.printStackTrace();
			System.exit(1);
		}
		return winnerPoints;
	}



	private void initExecutorService() {
		executorService = Executors.newFixedThreadPool(SINGLE_CHECKERS_NUMBER);
	}

}