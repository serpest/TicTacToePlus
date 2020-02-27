package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.components.Grid;
import model.components.Pawn;
import model.base.Point;
import model.TicTacToeGame;
import model.exceptions.GridSizeException;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.InvalidTicTacToeNumberException;
import model.exceptions.MaximumPlayersNumberExceededException;
import model.players.NormalVirtualPlayer;
import model.players.PerfectVirtualPlayer;
import model.players.Player;

public class SimpleCLIView implements CLIView {

	private Scanner scanner;



	//Singleton implementation
	private static SimpleCLIView instance;
	public static SimpleCLIView getInstance() {
		if (instance == null) {
			instance = new SimpleCLIView();
		}
		return instance;
	}
	private SimpleCLIView() {}



	@Override
	public TicTacToeGame getNewTicTacToeGame() {
		scanner = new Scanner(System.in);
		Player.resetPlayersNumber();
		TicTacToeGame game = null;
		int mode;
		boolean gameSelected = false;
		while (!gameSelected) {
			System.out.print("Select a game mode:" + System.lineSeparator() +
					"\t1 - Default multiplayer" + System.lineSeparator() +
					"\t2 - Custom multiplayer" + System.lineSeparator() +
					"\t3 - Default single-player" + System.lineSeparator() +
					"> ");
			try {
				mode = scanner.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				scanner.next();
				continue;
			}
			switch (mode) {
			case 1:
				game = new TicTacToeGame(new Player[] {new Player(), new Player()});
				break;
			case 2:
				game = getTicTacToeCustomGame();
				break;
			case 3:
				game = getTicTacToeSingleGame();
				break;
			default:
				System.out.println("Number not valid");
				break;
			}
			if (game != null) {
				gameSelected = true;
			}
		}
		return game;
	}

	@Override
	public void showGameBanner() {
		////Two empty lines, ASCII art and two empty lines
		System.out.println(System.lineSeparator() +
				"  _______ _        _______           _______" + System.lineSeparator() +
				" |__   __(_)      |__   __|         |__   __|" + System.lineSeparator() +
				"    | |   _  ___     | | __ _  ___     | | ___   ___" + System.lineSeparator() +
				"    | |  | |/ __|    | |/ _` |/ __|    | |/ _ \\ / _ \\" + System.lineSeparator() +
				"    | |  | | (__     | | (_| | (__     | | (_) |  __/" + System.lineSeparator() +
				"    |_|  |_|\\___|    |_|\\__,_|\\___|    |_|\\___/ \\___|" + System.lineSeparator());
	}

	@Override
	public void showGrid(Grid grid) {
		StringBuilder sb = new StringBuilder();
		for (int iRow = 0; iRow < grid.getContent()[0].length; iRow++) {
			sb.append(System.lineSeparator()+ "-" + "----".repeat(grid.getXSize()) + System.lineSeparator() + "| ");
			for (Pawn[] column : grid.getContent()) {
				sb.append(((column[iRow] != null) ? column[iRow] : " ") + " | ");
			}
		}
		sb.append(System.lineSeparator()+ "-" + "----".repeat(grid.getXSize()) + System.lineSeparator());
		System.out.println(sb);
	}

	@Override
	public void showPlayerTurn(Player player) {
		System.out.println(player + "'s turn");
	}

	@Override
	public void showWinner(Player player) {
		System.out.println("+++ " + player + " won! +++");
	}

	@Override
	public Point getPointNewPawn() {
		scanner = new Scanner(System.in);
		int x;
		int y;
		while (true) {
			try {
			System.out.print("Enter the x position of the new pawn" + System.lineSeparator() + "> ");
			x = scanner.nextByte();
			System.out.print("Enter the y position of the new pawn" + System.lineSeparator() + "> ");
			y = scanner.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				scanner.next();
				continue;
			}
			return new Point(x, y);
		}
	}

	@Override
	public void showDraw() {
		System.out.println("+++ Draw +++");
	}



	/**
	 * It asks the difficult level the user and it returns the game.
	 * 
	 * @return the single game.
	 */
	private TicTacToeGame getTicTacToeSingleGame() {
		scanner = new Scanner(System.in);
		TicTacToeGame game = null;
		int mode;
		boolean gameSelected = false;
		while (!gameSelected) {
			System.out.print("Select a difficult level:" + System.lineSeparator() + "\t1 - Normal" + System.lineSeparator() + "\t2 - Legend (you can't win)" + System.lineSeparator() + "> ");
			try {
				mode = scanner.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				scanner.next();
				continue;
			}
			switch (mode) {
			case 1:
				game = new TicTacToeGame(new Player[] {new Player(), new NormalVirtualPlayer(Pawn.values()[0], game.getTicTacToeNumber())});
				break;
			case 2:
				game = new TicTacToeGame(new Player[] {new Player(), new PerfectVirtualPlayer()});
				break;
			default:
				System.out.println("Number not valid");
				break;
			}
			if (game != null) {
				gameSelected = true;
			}
		}
		return game;
	}

	/**
	 * It manages the questions to get the game details.
	 * 
	 * @return the custom game.
	 */
	private TicTacToeGame getTicTacToeCustomGame() {
		Player[] players = questionSingleCustomSetup("Do you want a custom players setup?") ?
				getTicTacToeCustomPlayers() : new Player[] {new Player(), new Player()};
		Grid grid = questionSingleCustomSetup("Do you want a custom grid?") ?
				getTicTacToeCustomGrid() : new Grid();
		int ticTacToeNumber =
				questionSingleCustomSetup("Do you want a custom tic tac toe number?") ?
						getCustomTicTacToeNumber() : TicTacToeGame.DEFAULT_TIC_TAC_TOE_NUMBER;
		try {
			return new TicTacToeGame(players, grid, ticTacToeNumber);
		} catch (InvalidNumberOfPlayersException | InvalidTicTacToeNumberException exc) {
			assert false;
			return getTicTacToeCustomGame();
		}
	}

	/**
	 * It questions to the user if he/she want a specific custom game option.
	 * 
	 * @param question the question string
	 * @return the answer of the user
	 */
	private boolean questionSingleCustomSetup(String question) {
		scanner = new Scanner(System.in);
		while (true) {
			System.out.print(question + " [y/n]" + System.lineSeparator() + "> ");
			String answer = scanner.nextLine().toLowerCase();
			if ("y".equals(answer)) {
				return true;
			}
			else if ("n".equals(answer)) {
				return false;
			}
			System.out.println("Enter \"y\" or \"n\"");
		}
	}

	private Player[] getTicTacToeCustomPlayers() {
		scanner = new Scanner(System.in);
		List<Player> players = new ArrayList<>();
		String currentPlayerName;
		System.out.println("Enter the players name (the number of player must be from " + TicTacToeGame.MIN_NUMBER_OF_PLAYERS + " to " + TicTacToeGame.MAX_NUMBER_OF_PLAYERS + "):");
		for (int i = 0; i < TicTacToeGame.MAX_NUMBER_OF_PLAYERS; i++) {
			currentPlayerName = scanner.nextLine();
			if ("".equals(currentPlayerName)) {
				break;
			}
			try {
				players.add(new Player(currentPlayerName));
			} catch (MaximumPlayersNumberExceededException exc) {
				exc.printStackTrace();
				assert false : "The for statement isn't finish at the right moment.";
			}
		}
		return players.toArray(new Player[players.size()]);
	}

	/**
	 * It get from the user the dimensions of the custom grid and it instance it.
	 * 
	 * @return the custom grid
	 */
	private Grid getTicTacToeCustomGrid() {
		scanner = new Scanner(System.in);
		int xSize;
		int ySize;
		while (true) {
			try {
			System.out.print("Enter the x size of the grid" + System.lineSeparator() + "> ");
			xSize = scanner.nextByte();
			System.out.print("Enter the y size of the grid" + System.lineSeparator() + "> ");
			ySize = scanner.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				scanner.next();
				continue;
			}
			try {
				return new Grid(xSize, ySize);
			} catch (GridSizeException exc) {
				System.out.println(exc.getMessage());
			}
		}
	}

	private int getCustomTicTacToeNumber() {
		scanner = new Scanner(System.in);
		int ticTacToeNumber;
		while (true) {
			try {
			System.out.print("Enter the tic tac toe number (equal to or greater than " + TicTacToeGame.MIN_TIC_TAC_TOE_NUMBER + ")" + System.lineSeparator() + "> ");
			ticTacToeNumber = scanner.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				scanner.next();
				continue;
			}
			return ticTacToeNumber;
		}
	}

}