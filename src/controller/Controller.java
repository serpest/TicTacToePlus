package controller;

import java.util.Random;

import model.TicTacToeGame;
import model.players.Player;
import model.players.VirtualPlayer;

public abstract class Controller {

	private TicTacToeGame game;

	private Player currentPlayer;



	public abstract void start();

	public TicTacToeGame getGame() {
		return game;
	}



	protected void setGame(TicTacToeGame game) {
		this.game = game;
	}

	protected Player getCurrentPlayer() {
		return currentPlayer;
	}

	protected void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	protected void setupGame() {
		setCurrentPlayer(getGame().getPlayers()[new Random().nextInt(getGame().getPlayers().length)]);
	}

	protected void changeNextPlayer() {
		if (isLastPlayerInTheTurn(getCurrentPlayer())) {
			setCurrentPlayer(getGame().getPlayers()[0]);
		}
		else {
			setCurrentPlayer(getGame().getPlayers()[getCurrentPlayer().getSerialNumber() + 1]);
		}
	}

	protected boolean checkDraw() {
		return getGame().checkDraw();
	}

	/**
	 * It finished a game turn.
	 */
	protected void finishTurn() {
		checkTicTacToe();
		checkDraw();
		changeNextPlayer();
	}

	protected void placeVirtualPlayerPawn(VirtualPlayer virtualPlayer) {
		getGame().addPawnToGrid(virtualPlayer.getPawn(),
				virtualPlayer.getNewPawnPoint(getGame().getGrid()));
	}

	/**
	 * It manages a player turn.
	 */
	protected abstract void placePlayerPawn();

	/**
	 * It terminates the game with a winner result if someone win the game.
	 */
	protected abstract boolean checkTicTacToe();

	/**
	 * It starts the game process using the <code>game</code>.
	 */
	protected abstract void play();



	private boolean isLastPlayerInTheTurn(Player player) {
		return player.getSerialNumber() == getGame().getPlayers().length - 1;
	}

}