package controller;

import java.util.Random;

import model.Player;
import model.TicTacToeGame;
import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import view.View;

/**
 * The TicTacToeController is used to manage the game.
 */
public class TicTacToeController {

	/**
	 * It's the game UI.
	 */
	private View ticTacToeView;

	/**
	 * It's the game instance.
	 */
	private TicTacToeGame game;



	/**
	 * It starts the menu to get the game mode.
	 */
	public void initMenu() {
		game = ticTacToeView.getTicTacToeGame();
	}

	/**
	 * It starts the game process using the <code>game</code>.
	 */
	public void play() {
		ticTacToeView.showGameBanner();
		Player winnerPlayer;
		Random random = new Random();
		Player currentPlayer = game.getPlayers()[random.nextInt(game.getPlayers().length)];
		boolean gameOver = false;
		while (!gameOver) {
			ticTacToeView.showGrid(game.getGrid());
			ticTacToeView.showPlayerTurn(currentPlayer);
			boolean turnFinished = false;
			while (!turnFinished) {
				try {
					game.getGrid().addPawn(currentPlayer.getPawn(), ticTacToeView.getPointNewPawn());
					turnFinished = true;
				} catch (GridPositionOccupiedException | GridPositionNotExistsException exc) {
					System.out.println(exc.getMessage());
				}
			}
			//Check tic tac toe
			winnerPlayer = game.getWinner();
			if (winnerPlayer != null) {
				ticTacToeView.showGrid(game.getGrid());
				ticTacToeView.showWinner(winnerPlayer);
				gameOver = true;
			}
			if (game.getGrid().isFull()) {
				ticTacToeView.showGrid(game.getGrid());
				ticTacToeView.showDraw();
				gameOver = true;
			}
			//Next turn
			if (currentPlayer.getSerialNumber() == game.getPlayers().length - 1) {
				currentPlayer = game.getPlayers()[0];
			}
			else {
				currentPlayer = game.getPlayers()[currentPlayer.getSerialNumber() + 1];
			}
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
