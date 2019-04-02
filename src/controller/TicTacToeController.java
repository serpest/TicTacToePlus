package controller;

import java.util.Random;

import model.Player;
import model.TicTacToeGame;
import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import view.View;

public class TicTacToeController {

	private View ticTacToeView;
	public View getTicTacToeView() {
		return ticTacToeView;
	}
	public void setTicTacToeView(View ticTacToeView) {
		this.ticTacToeView = ticTacToeView;
	}

	private TicTacToeGame game;
	public TicTacToeGame getGame() {
		return game;
	}
	public void setGame(TicTacToeGame game) {
		this.game = game;
	}



	public void initMenu() {
		setGame(getTicTacToeView().getTicTacToeGame());
	}

	public void play() {
		getTicTacToeView().showGameBanner();
		Player winnerPlayer;
		Random random = new Random();
		Player currentPlayer = getGame().getPlayers()[random.nextInt(getGame().getPlayers().length)];
		while (true) {
			getTicTacToeView().showGrid(getGame().getGrid());
			getTicTacToeView().showPlayerTurn(currentPlayer);
			while (true) {
				try {
					getGame().getGrid().addPawn(currentPlayer.getPawn(), getTicTacToeView().getPointNewPawn());
					break;
				} catch (GridPositionOccupiedException | GridPositionNotExistsException exc) {
					System.out.println(exc.getMessage());
				}
			}
			//Check tic tac toe
			winnerPlayer = getGame().getWinner();
			if (winnerPlayer != null) {
				getTicTacToeView().showGrid(getGame().getGrid());
				getTicTacToeView().showWinner(winnerPlayer);
				break;
			}
			if (getGame().getGrid().isFull()) {
				getTicTacToeView().showGrid(getGame().getGrid());
				getTicTacToeView().showDraw();
				break;
			}
			//Next turn
			if (currentPlayer.getN() == getGame().getPlayers().length - 1) {
				currentPlayer = getGame().getPlayers()[0];
			}
			else {
				currentPlayer = getGame().getPlayers()[currentPlayer.getN() + 1];
			}
		}
	}



	public TicTacToeController(View ticTacToeView) {
		setTicTacToeView(ticTacToeView);
	}

}
