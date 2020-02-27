package view;

import model.base.Point;
import model.TicTacToeGame;
import model.players.Player;

public interface CLIView extends View {

	TicTacToeGame getNewTicTacToeGame();

	void showGameBanner();
	
	void showPlayerTurn(Player player);
	
	void showWinner(Player player);
	
	void showDraw();

	Point getPointNewPawn();

}