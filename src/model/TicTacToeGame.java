package model;

import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.InvalidTicTacToeNumberException;

public class TicTacToeGame {

	public static final byte MIN_NUMBER_OF_PLAYERS = 2;
	public static final byte MAX_NUMBER_OF_PLAYERS = (byte) (Pawn.values().length);

	public static final Player[] DEFAULT_PLAYERS = {new Player((byte)0), new Player((byte)1)};

	public static final byte MIN_TIC_TAC_TOE_NUMBER = 2;

	public static final byte DEFAULT_TIC_TAC_TOE_NUMBER = 3;



	public final byte MAX_TIC_TAC_TOE_NUMBER;

	private final TicTacToeChecker CHECKER;

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

	private Grid grid;
	public Grid getGrid() {
		return grid;
	}
	private void setGrid(Grid grid) {
		this.grid = grid;
	}

	private byte ticTacToeNumber;
	public byte getTicTacToeNumber() {
		return ticTacToeNumber;
	}
	private void setTicTacToeNumber(byte ticTacToeNumber) throws InvalidTicTacToeNumberException {
		if (ticTacToeNumber < MIN_TIC_TAC_TOE_NUMBER || ticTacToeNumber > MAX_TIC_TAC_TOE_NUMBER) {
			throw new InvalidTicTacToeNumberException(MIN_TIC_TAC_TOE_NUMBER, MAX_TIC_TAC_TOE_NUMBER);
		}
		this.ticTacToeNumber = ticTacToeNumber;
	}



	public Player getWinner() {
		Pawn winnerPawn = CHECKER.checkTicTacToe(getGrid().getContent());
		if (winnerPawn == null) {
			return null;
		}
		return getPlayers()[winnerPawn.ordinal()];
	}



	public TicTacToeGame() throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		this(DEFAULT_PLAYERS);
	}
	public TicTacToeGame(Player[] players) throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		this(players, new Grid(), DEFAULT_TIC_TAC_TOE_NUMBER);
	}
	/**
	 * It's the custom game constructor.
	 * @param players
	 * @param maxPoint the size of the grid
	 * @throws InvalidNumberOfPlayersException 
	 * @throws InvalidTicTacToeNumberException 
	 */
	public TicTacToeGame(Player[] players, Grid grid, byte ticTacToeNumber) throws InvalidNumberOfPlayersException, InvalidTicTacToeNumberException {
		setPlayers(players);
		setGrid(grid);
		MAX_TIC_TAC_TOE_NUMBER = (byte) Integer.max(getGrid().getContent().length, getGrid().getContent()[0].length);
		setTicTacToeNumber(ticTacToeNumber);
		CHECKER = new TicTacToeChecker(getTicTacToeNumber());
	}

}
