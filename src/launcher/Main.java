package launcher;

import controller.TicTacToeController;
import view.CLIView;

public class Main {

	/**
	 * It plays the game in an infinite loop.
	 * 
	 * @param args not required
	 */
	public static void main(String[] args) {
		TicTacToeController ticTacToeController;
		while (true) {
			ticTacToeController = new TicTacToeController(CLIView.getInstance());
			ticTacToeController.play();
		}
	}

}
