package model;

import model.base.Point;
import model.checker.TicTacToeMainChecker;
import model.components.Grid;
import model.components.Pawn;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.InvalidTicTacToeNumberException;
import model.players.NormalVirtualPlayer;
import model.players.PerfectVirtualPlayer;
import model.players.Player;

/**
 * The integration of the objects used in the game.
 */
public class TicTacToeGame {

	/**
	 * The minimum number of players.
	 */
	public static final int MIN_NUMBER_OF_PLAYERS = 2;
	/**
	 * The maximum number of players.
	 */
	public static final int MAX_NUMBER_OF_PLAYERS = Pawn.values().length;

	/**
	 * The default players.
	 * The players are instance using a specific player constructor, because if it uses the default constructor there is a bug in the selection of the custom players. 
	 */
	public static final Player[] DEFAULT_PLAYERS = {new Player(0), new Player(1)};

	/**
	 * The minimum tic tac toe number.
	 */
	public static final int MIN_TIC_TAC_TOE_NUMBER = 2;

	/**
	 * The default tic tac toe number.
	 */
	public static final int DEFAULT_TIC_TAC_TOE_NUMBER = 3;



	/**
	 * The maximum tic tac toe number.
	 * The value must be inside the biggest grid coordinate.
	 */
	public final int MAX_TIC_TAC_TOE_NUMBER;

	/**
	 * The tic tac toe checker.
	 */
	private final TicTacToeMainChecker CHECKER;



	/**
	 * The array of the players.
	 */
	private Player[] players;

	/**
	 * The grid of the game.
	 */
	private Grid grid;

	/**
	 * The tic tac toe number.
	 */
	private int ticTacToeNumber;



	/**
	 * It creates a default multiplayer game.
	 */
	public TicTacToeGame() {
		this(DEFAULT_PLAYERS.clone());
	}

	/**
	 * It's the single-player game constructor.
	 * 
	 * @param mode the <code>VirtualPlayer</code> level
	 */
	public TicTacToeGame(SinglePlayerMode mode) {
		this(DEFAULT_PLAYERS.clone());
		initVirtualPlayer(mode);
	}

	/**
	 * A custom game constructor.
	 * 
	 * @param players the players
	 * @throws InvalidNumberOfPlayersException if the number of the players isn't accepted
	 * @throws InvalidTicTacToeNumberException if the tic tac toe number isn't accepted
	 */
	public TicTacToeGame(Player[] players) throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		this(players, new Grid(), DEFAULT_TIC_TAC_TOE_NUMBER);
	}

	/**
	 * A custom game constructor.
	 * 
	 * @param players the players
	 * @param grid the grid
	 * @param ticTacToeNumber the tic tac toe number
	 * @throws InvalidNumberOfPlayersException if the number of the players isn't accepted
	 * @throws InvalidTicTacToeNumberException if the tic tac toe number isn't accepted
	 */
	public TicTacToeGame(Player[] players, Grid grid, int ticTacToeNumber) throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		setPlayers(players);
		setGrid(grid);
		MAX_TIC_TAC_TOE_NUMBER = Integer.max(getGrid().getContent().length, getGrid().getContent()[0].length);
		setTicTacToeNumber(ticTacToeNumber);
		CHECKER = new TicTacToeMainChecker(getTicTacToeNumber());
	}



	public Player[] getPlayers() {
		return players;
	}

	public Grid getGrid() {
		return grid;
	}

	public int getTicTacToeNumber() {
		return ticTacToeNumber;
	}

	/**
	 * @param examinedPawn the examined pawn
	 * @return the winner player or null
	 */
	public Player getWinnerPlayer(Pawn examinedPawn) {
		Point[] winnerPoints = getWinnerPoints(examinedPawn);
		if (winnerPoints == null) {
			return null;
		}
		return getPlayers()[getGrid().getPawn(winnerPoints[0]).ordinal()];
	}

	/**
	 * @param examinedPawn the examined pawn
	 * @return the winner points or null
	 */
	public Point[] getWinnerPoints(Pawn examinedPawn) {
		return CHECKER.checkTicTacToe(getGrid(), examinedPawn);
	}



	private void setPlayers(Player[] players) throws InvalidNumberOfPlayersException {
		if (players.length > MAX_NUMBER_OF_PLAYERS || players.length < MIN_NUMBER_OF_PLAYERS) {
			throw new InvalidNumberOfPlayersException();
		}
		this.players = players;
	}

	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	private void setTicTacToeNumber(int ticTacToeNumber) throws InvalidTicTacToeNumberException {
		if (ticTacToeNumber < MIN_TIC_TAC_TOE_NUMBER || ticTacToeNumber > MAX_TIC_TAC_TOE_NUMBER) {
			throw new InvalidTicTacToeNumberException(MIN_TIC_TAC_TOE_NUMBER, MAX_TIC_TAC_TOE_NUMBER);
		}
		this.ticTacToeNumber = ticTacToeNumber;
	}

	/**
	 * It initializes a <code>VirtualPlayer</code>. It is the second player in the array.
	 * 
	 * @param mode the <code>VirtualPlayer</code> level
	 */
	private void initVirtualPlayer(SinglePlayerMode mode) {
		switch (mode) {
		case LEGEND:
			players[1] = new PerfectVirtualPlayer(1, this);
			break;
		case NORMAL:
			players[1] = new NormalVirtualPlayer(1, this, Pawn.values()[1]);
			break;
		default:
			assert false;
			break;
		}
	}



	/**
	 * The single-player mode used to determinate the <code>VirtualPlayer</code>.
	 */
	public enum SinglePlayerMode {

		NORMAL, LEGEND;

	}

}