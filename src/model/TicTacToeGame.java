package model;

import model.base.Point;
import model.checker.TicTacToeMainChecker;
import model.components.Grid;
import model.components.Pawn;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.InvalidTicTacToeNumberException;
import model.players.Player;

public class TicTacToeGame {

	public static final int MIN_NUMBER_OF_PLAYERS = 2;
	public static final int MAX_NUMBER_OF_PLAYERS = Pawn.values().length;

	public static final int MIN_TIC_TAC_TOE_NUMBER = Grid.MIN_SIZE;

	public static final int DEFAULT_TIC_TAC_TOE_NUMBER = 3;



	public final int MAX_TIC_TAC_TOE_NUMBER; //It depends on the grid size.

	private final TicTacToeMainChecker CHECKER;

	private Player[] players;

	private Grid grid;

	private int ticTacToeNumber;

	private boolean gameOver;



	/*
	 * It doesn't setup the players.
	 */
	public TicTacToeGame() {
		this(new Grid(), DEFAULT_TIC_TAC_TOE_NUMBER);
	}

	public TicTacToeGame(Player[] players)
			throws InvalidNumberOfPlayersException {
		this(players, new Grid(), DEFAULT_TIC_TAC_TOE_NUMBER);
	}

	public TicTacToeGame(Player[] players, Grid grid, int ticTacToeNumber)
			throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		this(grid, ticTacToeNumber);
		setPlayers(players);
	}



	/*
	 * It doesn't setup the players.
	 */
	private TicTacToeGame(Grid grid, int ticTacToeNumber)
			throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		MAX_TIC_TAC_TOE_NUMBER = Integer.max(grid.getXSize(), grid.getYSize());
		CHECKER = new TicTacToeMainChecker(ticTacToeNumber);
		gameOver = false;
		setGrid(grid);
		setTicTacToeNumber(ticTacToeNumber);
	}



	public Player[] getPlayers() {
		return players;
	}

	public int getPlayersNumber() {
		return players.length;
	}

	/**
	 * @return the original grid if the game isn't over, otherwise a cloned grid
	 */
	public Grid getGrid() {
		if (isGameOver())
			return grid.clone();
		return grid;
	}

	public int getTicTacToeNumber() {
		return ticTacToeNumber;
	}

	public void addPawnToGrid(Pawn pawn, Point point) {
		getGrid().addPawn(pawn, point);
	}

	public Player getWinnerPlayer(Pawn examinedPawn) {
		Point[] winnerPoints = getWinnerPoints(examinedPawn);
		if (winnerPoints != null) {
			return getPlayerFromGridPawn(winnerPoints[0]);
		}
		return null;
	}

	public Point[] getWinnerPoints(Pawn examinedPawn) {
		Point[] winnerPoints = CHECKER.checkTicTacToe(getGrid(), examinedPawn);
		if (winnerPoints != null)
			setGameOver();
		return winnerPoints;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver() {
		this.gameOver = true;
	}

	public boolean checkDraw() {
		if (getGrid().isFull()) {
			setGameOver();
			return true;
		}
		return false;
	}

	public void setPlayers(Player[] players) throws InvalidNumberOfPlayersException {
		if (players.length > MAX_NUMBER_OF_PLAYERS || players.length < MIN_NUMBER_OF_PLAYERS) {
			throw new InvalidNumberOfPlayersException();
		}
		this.players = players;
	}



	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	private void setTicTacToeNumber(int ticTacToeNumber) throws InvalidTicTacToeNumberException {
		if (!isTicTacToeNumberValid(ticTacToeNumber)) {
			throw new InvalidTicTacToeNumberException(MIN_TIC_TAC_TOE_NUMBER, MAX_TIC_TAC_TOE_NUMBER);
		}
		this.ticTacToeNumber = ticTacToeNumber;
	}

	private boolean isTicTacToeNumberValid(int ticTacToeNumber) {
		return ticTacToeNumber >= MIN_TIC_TAC_TOE_NUMBER && ticTacToeNumber <= MAX_TIC_TAC_TOE_NUMBER;
	}

	private Player getPlayerFromGridPawn(Point point) {
		return getPlayers()[getGrid().getPawn(point).ordinal()];
	}

}