package controller;

import java.util.Random;

import model.TicTacToeGame;
import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.players.Player;
import model.players.VirtualPlayer;
import view.View;

/**
 * The TicTacToeController is used to manage the game.
 */
public class TicTacToeController {

	/**
	 * The game UI.
	 */
	private View ticTacToeView;

	/**
	 * The game instance.
	 */
	private TicTacToeGame game;

	/**
	 * The game is over?
	 */
	private boolean gameOver = false;

	/**
	 * The current player in the game.
	 */
	private Player currentPlayer;


	/**
	 * It starts the menu to get the game mode.
	 */
	public void initMenu() {
		ticTacToeView.showGameBanner();
		game = ticTacToeView.getTicTacToeGame();
	}

	/**
	 * It setups the first player randomly.
	 */
	private void setUpPlayers() {
		currentPlayer = game.getPlayers()[new Random().nextInt(game.getPlayers().length)];
	}

	/**
	 * It manages a player turn.
	 */
	private void placePlayerTile() {
		ticTacToeView.showGrid(game.getGrid());
		ticTacToeView.showPlayerTurn(currentPlayer);
		if (currentPlayer instanceof VirtualPlayer) {
			game.getGrid().addPawn(currentPlayer.getPawn(), ((VirtualPlayer) currentPlayer).getNewPawnPoint());
		}
		else {
			boolean turnFinished = false;
			while (!turnFinished) {
				try {
					game.getGrid().addPawn(currentPlayer.getPawn(), ticTacToeView.getPointNewPawn());
					turnFinished = true;
				} catch (GridPositionOccupiedException | GridPositionNotExistsException exc) {
					System.out.println(exc.getMessage());
				}
			}
		}
	}

	/**
	 * It terminates the game with a winner result if someone win the game.
	 */
	private void checkTicTacToe() {
		Player winnerPlayer = game.getWinner(currentPlayer.getPawn());
		if (winnerPlayer != null) {
			ticTacToeView.showGrid(game.getGrid());
			ticTacToeView.showWinner(winnerPlayer);
			gameOver = true;
		}
	}

	/**
	 * It terminates the game with a draw result if the grid is full.
	 */
	private void checkDraw() {
		if (game.getGrid().isFull()) {
			ticTacToeView.showGrid(game.getGrid());
			ticTacToeView.showDraw();
			gameOver = true;
		}
	}

	/**
	 * It changes the <code>currentPlayer</code> for the next turn.
	 */
	private void changePlayer() {
		if (currentPlayer.getSerialNumber() == game.getPlayers().length - 1) {
			currentPlayer = game.getPlayers()[0];
		}
		else {
			currentPlayer = game.getPlayers()[currentPlayer.getSerialNumber() + 1];
		}
	}

	/**
	 * It starts the game process using the <code>game</code>.
	 */
	public void play() {
		setUpPlayers();
		while (!gameOver) {
			placePlayerTile();
			checkTicTacToe();
			checkDraw();
			changePlayer();
		}
	}



	/**
	 * @param ticTacToeView the UI to use
	 */
	public TicTacToeController(final View ticTacToeView) {
		this.ticTacToeView = ticTacToeView;
		game = ticTacToeView.getTicTacToeGame();
	}

}
