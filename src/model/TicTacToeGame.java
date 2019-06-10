package model;

import model.checker.TicTacToeMainChecker;
import model.components.Grid;
import model.components.Pawn;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.InvalidTicTacToeNumberException;
import model.players.NormalVirtualPlayer;
import model.players.PerfectVirtualPlayer;
import model.players.Player;

/**
 * It's the integration of the objects used in the game.
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
	 * It's the array of the players.
	 */
	private Player[] players;
	public Player[] getPlayers() {
		return players;
	}
	private void setPlayers(Player[] players) throws InvalidNumberOfPlayersException {
		if (players.length > MAX_NUMBER_OF_PLAYERS || players.length < MIN_NUMBER_OF_PLAYERS) {
			throw new InvalidNumberOfPlayersException();
		}
		this.players = players;
	}

	/**
	 * It's the grid of the game.
	 */
	private Grid grid;
	public Grid getGrid() {
		return grid;
	}
	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * It's the tic tac toe number.
	 */
	private int ticTacToeNumber;
	public int getTicTacToeNumber() {
		return ticTacToeNumber;
	}
	private void setTicTacToeNumber(int ticTacToeNumber) throws InvalidTicTacToeNumberException {
		if (ticTacToeNumber < MIN_TIC_TAC_TOE_NUMBER || ticTacToeNumber > MAX_TIC_TAC_TOE_NUMBER) {
			throw new InvalidTicTacToeNumberException(MIN_TIC_TAC_TOE_NUMBER, MAX_TIC_TAC_TOE_NUMBER);
		}
		this.ticTacToeNumber = ticTacToeNumber;
	}



	/**
	 * @param examinedPawn the examined pawn
	 * @return the player winner or null using <code>CHECKER</code>.
	 */
	public Player getWinner(Pawn examinedPawn) {
		Pawn winnerPawn = CHECKER.checkTicTacToe(getGrid(), examinedPawn);
		if (winnerPawn == null) {
			return null;
		}
		return getPlayers()[winnerPawn.ordinal()];
	}

	/**
	 * It initializes a <code>VirtualPlayer</code>. It is the second player in the array.
	 * 
	 * @param mode the <code>VirtualPlayer</code> level
	 */
	private void initVirtualPlayer(SINGLE_PLAYER_MODE mode) {
		switch (mode) {
		case LEGEND:
			players[1] = new PerfectVirtualPlayer(1, this);
			break;
		case NORMAL:
			players[1] = new NormalVirtualPlayer(1, this, Pawn.values()[0]);
			break;
		default:
			assert false;
			break;
		}
	}



	/**
	 * It creates a default single-player game.
	 */
	public TicTacToeGame() {
		this(DEFAULT_PLAYERS);
	}

	/**
	 * It's the default game constructor.
	 * 
	 * @param mode the <code>VirtualPlayer</code> level
	 */
	public TicTacToeGame(SINGLE_PLAYER_MODE mode) {
		this(DEFAULT_PLAYERS);
		initVirtualPlayer(mode);
	}

	/**
	 * It's a custom game constructor.
	 * 
	 * @param players the players
	 * @throws InvalidNumberOfPlayersException if the number of the players isn't accepted
	 * @throws InvalidTicTacToeNumberException if the tic tac toe number isn't accepted
	 */
	public TicTacToeGame(Player[] players) throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		this(players, new Grid(), DEFAULT_TIC_TAC_TOE_NUMBER);
	}

	/**
	 * It's a custom game constructor.
	 * 
	 * @param players the players of the game
	 * @param grid the grid of the game
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



	/**
	 * The single-player mode used to determinate the <code>VirtualPlayer</code>.
	 */
	public enum SINGLE_PLAYER_MODE {

		NORMAL, LEGEND;

	}

}
