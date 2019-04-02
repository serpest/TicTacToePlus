package view;

import model.Grid;
import model.Player;
import model.Point;
import model.TicTacToeGame;

public interface View {

	TicTacToeGame getTicTacToeGame();
	
	void showGameBanner();
	
	void showGrid(Grid grid);
	
	void showPlayerTurn(Player player);
	
	void showWinner(Player player);
	
	void showDraw();
	
	Point getPointNewPawn();

}
