package model;

import model.TicTacToeSingleChecker.Mode;

/**
 * It searches in the grid a tic tac toe.
 */
class TicTacToeMainChecker {

	/**
	 * The reference of the <code>TicTacToeGame</code> instance.
	 */
	private final TicTacToeGame GAME;
	public TicTacToeGame getGame() {
		return GAME;
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
	 * The pawn obtained by the single checkers.
	 */
	private Pawn result;
	public Pawn getResult() {
		return result;
	}
	/**
	 * It sets the <code>result</code> value if it's null and it notifies the TicTacToeMainChecker.checkTicTacToe() method.
	 * 
	 * @param result the winner pawn
	 */
	public synchronized void setResult(Pawn result) {
		if (result == null) {
			return;
		}
		this.result = result;
		this.notify();
	}

	/**
	 * The pawn was found or it was not found by anyone?
	 */
	private boolean resultFound;
	public boolean isResultFound() {
		return resultFound;
	}
	public void setResultFound(boolean resultFound) {
		this.resultFound = resultFound;
	}

	private int failedSearchingAttempts;
	public int getFailedSearchingAttempts() {
		return failedSearchingAttempts;
	}
	/**
	 * It adds a failed attempt and if the check is finished it notifies the TicTacToeMainChecker.checkTicTacToe() method.
	 */
	public synchronized void addFailedSearchingAttempt() {
		this.failedSearchingAttempts += 1;
		if (failedSearchingAttempts == TicTacToeSingleChecker.Mode.values().length) {
			this.notify();
		}
	}

	/**
	 * It resets <code>result</code> and <code>resultFound</code>.
	 */
	private void resetSearching() {
		result = null;
		resultFound = false;
		failedSearchingAttempts = 0;
	}

	/**
	 * It finds a tic tac toe.
	 * If there are more than one it can see only the first tic tac toe founded.
	 * 
	 * @param examinedPawn the examined pawn
	 * @return the Pawn winner or null if there isn't a tic tac toe
	 */
	public synchronized Pawn checkTicTacToe(Pawn examinedPawn) {
		this.examinedPawn = examinedPawn; //If examinedPawn is null TicTacToeSingleChecker.checkLineTicTacToe doesn't find any false tic tac toe
		resetSearching();
		for (Mode mode : TicTacToeSingleChecker.Mode.values()) {
			new TicTacToeSingleChecker(this, mode).startThread();;
		}
		try {
			this.wait();
		} catch (InterruptedException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
		setResultFound(true);
		return getResult();
	}



	/**
	 * @param GAME the tic tac toe game
	 */
	public TicTacToeMainChecker(final TicTacToeGame GAME) {
		this.GAME = GAME;
	}

}
