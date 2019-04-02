package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Grid;
import model.Pawn;
import model.Player;
import model.Point;
import model.TicTacToeGame;
import model.exceptions.GridSizeException;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.InvalidTicTacToeNumberException;
import model.exceptions.MaximumPlayerNumberExceededException;

public class CLIView implements View {

	private final static byte DEFAULT_PLAY = 1;
	private final static byte CUSTOM_PLAY = 2;



	private Scanner sc;



	@Override
	public TicTacToeGame getTicTacToeGame() {
		sc = new Scanner(System.in);
		TicTacToeGame game = null;
		byte mode;
		while (true) {
			System.out.print("Select a game mode:" + System.lineSeparator() + "\t" + DEFAULT_PLAY + " - Default play" + System.lineSeparator() + "\t" + CUSTOM_PLAY + " - Custom play" + System.lineSeparator() + "> ");
			try {
				mode = sc.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				sc.next();
				continue;
			}
			switch (mode) {
			case DEFAULT_PLAY:
				try {
					game = new TicTacToeGame();
				} catch (InvalidNumberOfPlayersException | InvalidTicTacToeNumberException e) {
					e.printStackTrace();
				}
				break;
			case CUSTOM_PLAY:
				game = getTicTacToeCustomGame();
				break;
			default:
				System.out.println("Number not valid");
				break;
			}
			if (game != null) {
				break;
			}
		}
		return game;
	}

	private TicTacToeGame getTicTacToeCustomGame() {
		Player[] players = questionSingleCustomSetup("Do you want a custom players setup?") ? getTicTacToeCustomPlayers() : TicTacToeGame.DEFAULT_PLAYERS;
		Grid grid = questionSingleCustomSetup("Do you want a custom grid?") ? getTicTacToeCustomGrid() : new Grid();
		byte ticTacToeNumber = questionSingleCustomSetup("Do you want a custom tic tac toe number?") ? getCustomTicTacToeNumber() : TicTacToeGame.DEFAULT_TIC_TAC_TOE_NUMBER;
		try {
			return new TicTacToeGame(players, grid, ticTacToeNumber);
		} catch (InvalidNumberOfPlayersException | InvalidTicTacToeNumberException exc) {
			System.out.println(exc.getMessage());
			return getTicTacToeCustomGame();
		}
	}

	private boolean questionSingleCustomSetup(String question) {
		sc = new Scanner(System.in);
		while (true) {
			System.out.print(question + " [y/n]" + System.lineSeparator() + "> ");
			String answer = sc.nextLine().toLowerCase();
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
		sc = new Scanner(System.in);
		List<Player> players = new ArrayList<>();
		String currentPlayerName;
		while (true) {
			System.out.println("Enter the players name (the number of player must be from " + TicTacToeGame.MIN_NUMBER_OF_PLAYERS + " to " + TicTacToeGame.MAX_NUMBER_OF_PLAYERS + "):");
			for (byte i = 0; i < TicTacToeGame.MAX_NUMBER_OF_PLAYERS; i++) {
				currentPlayerName = sc.nextLine();
				if ("".equals(currentPlayerName)) {
					break;
				}
				try {
					players.add(new Player(currentPlayerName));
				} catch (MaximumPlayerNumberExceededException e) {
					e.printStackTrace();
					assert false : "The for statement isn't finish at the right moment.";
				}
			}
			return players.toArray(new Player[players.size()]);
		}
	}

	private Grid getTicTacToeCustomGrid() {
		sc = new Scanner(System.in);
		byte xSize;
		byte ySize;
		while (true) {
			try {
			System.out.print("Enter the x size of the grid" + System.lineSeparator() + "> ");
			xSize = sc.nextByte();
			System.out.print("Enter the y size of the grid" + System.lineSeparator() + "> ");
			ySize = sc.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				sc.next();
				continue;
			}
			try {
				return new Grid(xSize, ySize);
			} catch (GridSizeException exc) {
				System.out.println(exc.getMessage());
			}
		}
	}

	private byte getCustomTicTacToeNumber() {
		sc = new Scanner(System.in);
		byte ticTacToeNumber;
		while (true) {
			try {
			System.out.print("Enter the tic tac toe number (equal to or greater than " + TicTacToeGame.MIN_TIC_TAC_TOE_NUMBER + ")" + System.lineSeparator() + "> ");
			ticTacToeNumber = sc.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				sc.next();
				continue;
			}
			return ticTacToeNumber;
		}
	}

	@Override
	public void showGameBanner() {
		////Two "empty" line, ASCII art and two "empty" line
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
		for (byte iRow = 0; iRow < grid.getContent()[0].length; iRow++) {
			sb.append(System.lineSeparator()+ "-" + "----".repeat(grid.getContent().length) + System.lineSeparator() + "| ");
			for (Pawn[] column : grid.getContent()) {
				sb.append(((column[iRow] != null) ? column[iRow] : " ") + " | ");
			}
		}
		sb.append(System.lineSeparator()+ "-" + "----".repeat(grid.getContent().length) + System.lineSeparator());
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
		sc = new Scanner(System.in);
		byte x;
		byte y;
		while (true) {
			try {
			System.out.print("Enter the x position of the new pawn" + System.lineSeparator() + "> ");
			x = sc.nextByte();
			System.out.print("Enter the y position of the new pawn" + System.lineSeparator() + "> ");
			y = sc.nextByte();
			} catch (InputMismatchException exc) {
				System.out.println("Enter a number");
				sc.next();
				continue;
			}
			return new Point(x, y);
		}
	}

	@Override
	public void showDraw() {
		System.out.println("+++ Draw +++");
	}



	/*
	 * Singleton implementation
	 */
	private static CLIView instance;
	public static CLIView getInstance() {
		if (instance == null) {
			instance = new CLIView();
		}
		return instance;
	}
	private CLIView() {}

}
