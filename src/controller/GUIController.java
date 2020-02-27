package controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import model.TicTacToeGame;
import model.base.Point;
import model.components.Pawn;
import model.exceptions.GridPositionOccupiedException;
import model.players.NormalVirtualPlayer;
import model.players.PerfectVirtualPlayer;
import model.players.Player;
import model.players.VirtualPlayer;
import view.GUIView;
import view.SimpleGUIView;

public class GUIController extends Controller {

	private GUIView view;

	/**
	 * The game is ready for the next new pawn (flag).
	 */
	private boolean newPawnSelectionActive;



	public GUIController() {
		view = new SimpleGUIView(this);
	}



	/**
	 * It manages the new GUI user pawn.
	 * 
	 * @param event the mouse event
	 */
	public synchronized void newPawnMouseClicked(MouseEvent event) {
		if (newPawnSelectionActive && !getGame().isGameOver()) {
			Point newPawnPoint = new Point((int) (event.getX() / (view.getGridImageDimension().getWidth() / getGame().getGrid().getXSize())), (int) ((event.getY() / (view.getGridImageDimension().getHeight() / getGame().getGrid().getYSize()))));
			try {
				getGame().addPawnToGrid(getCurrentPlayer().getPawn(), newPawnPoint);
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
		TicTacToeGame newGame = getNewTicTacToeGame(event.getActionCommand());
		setGame(newGame);
		setupGame();
		play();
	}

	@Override
	public void start() {
		//It starts the UI used to select the game mode and not the game
		view.setupGameFrame();
	}



	@Override
	protected void setupGame() {
		super.setupGame();
		view.resetGrid();
		newPawnSelectionActive = false;
	}

	@Override
	protected synchronized void placePlayerPawn() {
		//It is used ONLY when it's a VirtualPlayer's turn
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			placeVirtualPlayerPawn((VirtualPlayer) getCurrentPlayer());
			view.showGrid(getGame().getGrid());
		}
	}

	@Override
	protected boolean checkTicTacToe() {
		Point[] winnerPoints = getGame().getWinnerPoints(getCurrentPlayer().getPawn());
		if (winnerPoints != null) {
			view.showWinnerPoints(winnerPoints);
			return true;
		}
		return false;
	}

	@Override
	protected void play() {
		if (getGame().isGameOver())
			return;
		//It does a VirtualPlayer turn
		if (getCurrentPlayer() instanceof VirtualPlayer) {
			placePlayerPawn();
			finishTurn();
		}
		//It uses the play() method again if the next player is a VirtualPLayer, otherwise it allows a GUI interaction
		if (getCurrentPlayer() instanceof VirtualPlayer)
			play();
		else
			newPawnSelectionActive = true;
	}



	/**
	 * It return the game from the view description.
	 * 
	 * @param gameString the game string
	 * @return the new game
	 */
	private TicTacToeGame getNewTicTacToeGame(String gameString) {
		Player.resetPlayersNumber();
		switch (gameString) {
		case GUIView.NEW_CLASSIC_MULTIPLAYER_GAME:
			return new TicTacToeGame(new Player[] {new Player(), new Player()});
		case GUIView.NEW_NORMAL_SINGLEPLAYER_GAME:
			TicTacToeGame game = new TicTacToeGame();
			game.setPlayers(new Player[] {new Player(), new NormalVirtualPlayer(Pawn.values()[0], game.getTicTacToeNumber())});
			return game;
		case GUIView.NEW_LEGEND_SINGLEPLAYER_GAME:
			return new TicTacToeGame(new Player[] {new Player(), new PerfectVirtualPlayer()});
		default:
			throw new IllegalArgumentException('"' + gameString + "\" mode is invalid.");
		}
	}

}