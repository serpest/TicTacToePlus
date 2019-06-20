package controller;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import model.TicTacToeGame;
import model.base.Point;
import model.exceptions.GridPositionOccupiedException;
import model.players.VirtualPlayer;
import view.GUIView;
import view.SimpleGUIView;

/**
 * The GUI controller used to manage the game.
 */
public class GUIController extends Controller {

	/**
	 * The game UI.
	 */
	private GUIView view;

	/**
	 * The game is ready for the next new pawn (flag).
	 */
	private boolean newPawnSelectionActive;



	/**
	 * It gets the view.
	 */
	public GUIController() {
		view = new SimpleGUIView(this);
	}



	/**
	 * It manages the new GUI user pawn.
	 * 
	 * @param event the mouse event
	 */
	public synchronized void newPawnMouseClicked(MouseEvent event) {
		if (newPawnSelectionActive && !isGameOver()) {
			Point newPawnPoint = new Point((int) (event.getX() / (view.getGridImageDimension().getWidth() / getGame().getGrid().getContent().length)), (int) ((event.getY() / (view.getGridImageDimension().getHeight() / getGame().getGrid().getContent()[0].length))));
			try {
				getGame().getGrid().addPawn(getCurrentPlayer().getPawn(), newPawnPoint);
			} catch (GridPositionOccupiedException exc) {
				return;
			}
			view.showGrid(getGame().getGrid());
			finishTurn();
			newPawnSelectionActive = false;
			play();
		}
	}

	/**
	 * It is called to start a new game when the current game doesn't terminate.
	 * It works only if the source of the event is a button.
	 * 
	 * @param event the event
	 */
	public synchronized void newGameActionPerformed(ActionEvent event) {
		Button button = (Button) event.getSource();
		TicTacToeGame newGame = getNewTicTacToeGame(button.getLabel());
		setGame(newGame);
		setupGame();
		play();
	}

	@Override
	public void start() {
		view.setupFrame();
	}



	@Override
	void setupGame() {
		super.setupGame();
		view.resetGrid();
		newPawnSelectionActive = false;
	}

	@Override
	synchronized void placePlayerTile() {
		//It is used ONLY when there is a VirtualPlayer's turn
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			getGame().getGrid().addPawn(getCurrentPlayer().getPawn(), ((VirtualPlayer) getCurrentPlayer()).getNewPawnPoint());
			view.showGrid(getGame().getGrid());
		}
	}

	@Override
	void checkTicTacToe() {
		Point[] winnerPoints = getGame().getWinnerPoints(getCurrentPlayer().getPawn());
		if (winnerPoints != null) {
			setGameOver(true);
			view.showWinnerPoints(winnerPoints);
		}
	}

	@Override
	void play() {
		//It does a VirtualPlayer turn
		if (isGameOver()) {
			return;
		}
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			placePlayerTile();
			finishTurn();
		}
		//It use the play() method again if the next player is a VirtualPLayer, otherwise it allows a GUI interaction.
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			play();
		}
		else {
			newPawnSelectionActive = true;
		}
	}



	/**
	 * It return the game from the view description.
	 * 
	 * @param gameString the game string
	 * @return the new game
	 */
	private TicTacToeGame getNewTicTacToeGame(String gameString) {
		switch (gameString) {
		case GUIView.NEW_CLASSIC_MULTIPLAYER_GAME:
			return new TicTacToeGame();
		case GUIView.NEW_NORMAL_SINGLEPLAYER_GAME:
			return new TicTacToeGame(TicTacToeGame.SinglePlayerMode.NORMAL);
		case GUIView.NEW_LEGEND_SINGLEPLAYER_GAME:
			return new TicTacToeGame(TicTacToeGame.SinglePlayerMode.LEGEND);
		default:
			assert false;
			return null;
		}
	}

}