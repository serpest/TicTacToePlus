package launcher;

import controller.TicTacToeController;
import model.exceptions.GridPositionOccupiedException;
import model.exceptions.InvalidNumberOfPlayersException;
import view.CLIView;

public class Main {

	public static void main(String[] args) throws InvalidNumberOfPlayersException, GridPositionOccupiedException {
		TicTacToeController ticTacToeController = new TicTacToeController(CLIView.getInstance());
		while (true) {
			ticTacToeController.initMenu();
			ticTacToeController.play();
		}
	}

}
