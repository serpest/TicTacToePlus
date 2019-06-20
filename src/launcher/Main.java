package launcher;

import controller.Controller;
import controller.CLIController;
import controller.GUIController;

/**
 * The game launcher.
 */
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



	/**
	 * @param UIString the UI to use
	 * @return the controller
	 * @throws IllegalArgumentException if the string ins't right
	 */
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

	/**
	 * It prints the help message.
	 */
	private static void printHelpMessage() {
		System.out.println("Enter an argument:" + System.lineSeparator() + "\tGUI - Graphical User Interface"+ System.lineSeparator() + "\tCLI - Command Line Interface");
	}

}