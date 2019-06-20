package view;

import model.base.Point;
import model.TicTacToeGame;
import model.players.Player;

/**
 * The CLI view interface.
 */
public interface CLIView extends View {

	/**
	 * @return the selected tic tac toe game
	 */
	TicTacToeGame getTicTacToeGame();
	
	/**
	 * It shows the game banner.
	 */
	void showGameBanner();
	
	/**
	 * It shows the current turn player.
	 * 
	 * @param player the current player
	 */
	void showPlayerTurn(Player player);
	
	/**
	 * It shows the winner player.
	 * 
	 * @param player the player
	 */
	void showWinner(Player player);
	
	/**
	 * It shows the draw.
	 */
	void showDraw();
	
	/**
	 * It gets the point of the new pawn to insert it in the grid.
	 * 
	 * @return the point selected.
	 */
	Point getPointNewPawn();

}