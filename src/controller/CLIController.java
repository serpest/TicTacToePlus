package controller;

import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.players.Player;
import model.players.VirtualPlayer;
import view.CLIView;
import view.SimpleCLIView;

public class CLIController extends Controller {

	private CLIView view;

	public CLIController() {
		this.view = SimpleCLIView.getInstance();
	}

	@Override
	public void start() {
		setGame(view.getNewTicTacToeGame());
		setupGame();
		play();
	}

	@Override
	protected void play() {
		while (!getGame().isGameOver()) {
			placePlayerPawn();
			finishTurn();
		}
		start(); //It starts a new game
	}

	@Override
	protected void placePlayerPawn() {
		view.showGrid(getGame().getGrid());
		view.showPlayerTurn(getCurrentPlayer());
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			placeVirtualPlayerPawn((VirtualPlayer) getCurrentPlayer());
		}
		else {
			boolean turnFinished = false;
			while (!turnFinished) {
				try {
					getGame().addPawnToGrid(getCurrentPlayer().getPawn(), view.getPointNewPawn());
					turnFinished = true;
				} catch (GridPositionOccupiedException | GridPositionNotExistsException exc) {
					System.out.println(exc.getMessage());
				}
			}
		}
	}

	@Override
	protected boolean checkTicTacToe() {
		Player winnerPlayer = getGame().getWinnerPlayer(getCurrentPlayer().getPawn());
		if (winnerPlayer != null) {
			view.showGrid(getGame().getGrid());
			view.showWinner(winnerPlayer);
			return true;
		}
		return false;
	}

	@Override
	protected boolean checkDraw() {
		if (super.checkDraw()) {
			view.showGrid(getGame().getGrid());
			view.showDraw();
			return true;
		}
		return false;
	}

}