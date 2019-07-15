package controller;

import model.exceptions.GridPositionNotExistsException;
import model.exceptions.GridPositionOccupiedException;
import model.players.Player;
import model.players.VirtualPlayer;
import view.CLIView;
import view.SimpleCLIView;

/**
 * The CLI controller used to manage the game.
 */
public class CLIController extends Controller {

	/**
	 * The game UI.
	 */
	private CLIView view;

 

	/**
	 * It gets the view.
	 */
	public CLIController() {
		this.view = SimpleCLIView.getInstance();
	}



	@Override
	public void start() {
		setGame(view.getTicTacToeGame());
		setupGame();
		play();
	}



	@Override
	void play() {
		while (!isGameOver()) {
			placePlayerTile();
			finishTurn();
		}
		start(); //It starts a new game
	}

	@Override
	void placePlayerTile() {
		view.showGrid(getGame().getGrid());
		view.showPlayerTurn(getCurrentPlayer());
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			getGame().getGrid().addPawn(getCurrentPlayer().getPawn(), ((VirtualPlayer) getCurrentPlayer()).getNewPawnPoint());
		}
		else {
			boolean turnFinished = false;
			while (!turnFinished) {
				try {
					getGame().getGrid().addPawn(getCurrentPlayer().getPawn(), view.getPointNewPawn());
					turnFinished = true;
				} catch (GridPositionOccupiedException | GridPositionNotExistsException exc) {
					System.out.println(exc.getMessage());
				}
			}
		}
	}

	@Override
	void checkTicTacToe() {
		Player winnerPlayer = getGame().getWinnerPlayer(getCurrentPlayer().getPawn());
		if (winnerPlayer != null) {
			setGameOver(true);
			view.showGrid(getGame().getGrid());
			view.showWinner(winnerPlayer);
		}
	}

	@Override
	boolean checkDraw() {
		if (super.checkDraw()) {
			view.showGrid(getGame().getGrid());
			view.showDraw();
			return true;
		}
		return false;
	}

}