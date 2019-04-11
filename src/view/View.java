package view;

import model.Grid;
import model.Player;
import model.Point;
import model.TicTacToeGame;

public interface View {

	TicTacToeGame getTicTacToeGame();
	
	/**
	 * It shows the game banner.
	 */
	void showGameBanner();
	
	/**
	 * It shows the grid.
	 */
	void showGrid(Grid grid);
	
	/**
	 * It shows the current turn player.
	 * 
	 * @param player the current player
	 */
	void showPlayerTurn(Player player);
	
	/**
	 * It shows the winner player.
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
