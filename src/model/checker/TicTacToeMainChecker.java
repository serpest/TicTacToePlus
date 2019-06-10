package model.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.components.Grid;
import model.components.Pawn;

/**
 * It searches in the grid a tic tac toe.
 */
public class TicTacToeMainChecker {

	/**
	 * The examined grid.
	 */
	private  Grid grid;
	public Grid getGrid() {
		return grid;
	}

	/**
	 * The game's tic tac toe number.
	 */
	private final int TIC_TAC_TOE_NUMBER;
	public int getTIC_TAC_TOE_NUMBER() {
		return TIC_TAC_TOE_NUMBER;
	}

	/**
	 * The type of the pawn examined.
	 * It's helpful if the <code>ticTacToeNumber</code> is long.
	 */
	private Pawn examinedPawn;
	public Pawn getExaminedPawn() {
		return examinedPawn;
	}

	/**
	 * The pawn was found or it was not found by anyone?
	 */
	private volatile boolean resultFound;
	public boolean isResultFound() {
		return resultFound;
	}
	public void setResultFound(boolean resultFound) {
		this.resultFound = resultFound;
	}

	/**
	 * It resets <code>resultFound</code>.
	 */
	private void resetSearching() {
		resultFound = false;
	}

	/**
	 * It finds a tic tac toe.
	 * If there are more than one it can see only the first tic tac toe founded.
	 * 
	 * @param grid the examined grid
	 * @param examinedPawn the examined pawn
	 * @return the Pawn winner or null if there isn't a tic tac toe
	 */
	public Pawn checkTicTacToe(Grid grid, Pawn examinedPawn) {
		resetSearching();
		this.grid = grid;
		this.examinedPawn = examinedPawn; //If examinedPawn is null TicTacToeSingleChecker.checkLineTicTacToe doesn't find any false tic tac toe
		List<Future<Pawn>> results = new ArrayList<>();
		ExecutorService es = Executors.newFixedThreadPool(TicTacToeSingleChecker.Mode.values().length);
		for (TicTacToeSingleChecker.Mode mode : TicTacToeSingleChecker.Mode.values()) {
			results.add(es.submit(new TicTacToeSingleChecker(this, mode)));
		}
		Pawn winnerPawn = null;
		for (Future<Pawn> future : results) {
			try {
				winnerPawn = future.get();
			} catch (InterruptedException | ExecutionException exc) {
				exc.printStackTrace();
			}
			if (winnerPawn != null) {
				break;
			}
		}
		es.shutdown();
		return winnerPawn;
	}



	/**
	 * @param TIC_TAC_TOE_NUMBER the tic tac toe number
	 */
	public TicTacToeMainChecker(final int TIC_TAC_TOE_NUMBER) {
		this.TIC_TAC_TOE_NUMBER = TIC_TAC_TOE_NUMBER;
	}

}
