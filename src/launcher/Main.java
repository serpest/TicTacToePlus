package launcher;

import controller.Controller;
import controller.CLIController;
import controller.GUIController;

public class Main {

	/**
	 * It starts the game.
	 * 
	 * @param args required, the first one determinates the UI
	 */
	public static void main(String[] args) {
		Controller controller = null;
		try {
			controller = getController(args[0]);
		} catch (IllegalArgumentException | IndexOutOfBoundsException exc) {
			printHelpMessage();
			System.exit(-1);
		}
		controller.start();
	}

	private static Controller getController(String UIString) throws IllegalArgumentException {
		switch (UIString.toUpperCase()) {
		case "GUI":
			return new GUIController();
		case "CLI":
			return new CLIController();
		default:
			throw new IllegalArgumentException();
		}
	}

	private static void printHelpMessage() {
		System.out.println("TicTacToePlus requires one of these arguments:" + System.lineSeparator() + "\tGUI - Graphical User Interface"+ System.lineSeparator() + "\tCLI - Command Line Interface");
	}

}