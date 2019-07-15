package controller;

import java.util.Random;

import model.TicTacToeGame;
import model.players.Player;

/**
 * The tic tac toe controller.
 */
public abstract class Controller {

	/**
	 * The game instance.
	 */
	private TicTacToeGame game;

	/**
	 * The game is over.
	 */
	private boolean gameOver;

	/**
	 * The current player in the game.
	 */
	private Player currentPlayer;



	public TicTacToeGame getGame() {
		return game;
	}
	public void setGame(TicTacToeGame game) {
		this.game = game;
	}

	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * It starts the game.
	 */
	public abstract void start();



	/**
	 * It setups the first player randomly and set the <code>gameOver</code> boolean to false.
	 */
	void setupGame() {
		setCurrentPlayer(getGame().getPlayers()[new Random().nextInt(getGame().getPlayers().length)]);
		setGameOver(false);
	}

	/**
	 * It changes the <code>currentPlayer</code> for the next turn.
	 */
	void changePlayer() {
		if (getCurrentPlayer().getSerialNumber() == getGame().getPlayers().length - 1) { //The player is the last one generated
			setCurrentPlayer(getGame().getPlayers()[0]);
		}
		else {
			setCurrentPlayer(getGame().getPlayers()[getCurrentPlayer().getSerialNumber() + 1]);
		}
	}

	/**
	 * It terminates the game with a draw result if the grid is full.
	 * 
	 * @return there is a draw
	 */
	boolean checkDraw() {
		if (getGame().getGrid().isFull()) {
			setGameOver(true);
			return true;
		}
		return false;
	}

	/**
	 * It finished a game turn.
	 */
	void finishTurn() {
		checkTicTacToe();
		checkDraw();
		changePlayer();
	}

	/**
	 * It manages a player turn.
	 */
	abstract void placePlayerTile();

	/**
	 * It terminates the game with a winner result if someone win the game.
	 */
	abstract void checkTicTacToe();

	/**
	 * It starts the game process using the <code>game</code>.
	 */
	abstract void play();

}