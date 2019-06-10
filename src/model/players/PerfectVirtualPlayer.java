package model.players;

import java.util.HashMap;
import java.util.Map;

import model.TicTacToeGame;
import model.base.Point;
import model.components.Grid;
import model.components.Pawn;
import model.exceptions.GridSizeException;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.MaximumPlayerNumberExceededException;

/**
 * An automatic and perfect player in 3x3 grid.
 * It uses the following trees:
 * 	First player - https://en.wikipedia.org/wiki/File:Tictactoe-X.svg;
 * 	Second player - https://en.wikipedia.org/wiki/File:Tictactoe-O.svg.
 */
public class PerfectVirtualPlayer extends VirtualPlayer {

	/**
	 * The new point for all the configurations.
	 * VirtualPlayer is "X" and the opponent is "O".
	 * There are 272 values, so I'll move it to a text file.
	 */
	private static final Map<String, Point> PERFECT_POINTS = new HashMap<>() {

		private static final long serialVersionUID = -2528085884556986379L;



		{
			put("XOXOOXO__", new Point(2, 2));
			put("O___X_O__", new Point(0, 1));
			put("O___X___O", new Point(1, 0));
			put("XOOOOX_X_", new Point(0, 2));
			put("XX_OO____", new Point(2, 0));
			put("_X_OXOXOO", new Point(2, 0));
			put("XXO_X_O_O", new Point(1, 2));
			put("X__OX_O_O", new Point(1, 2));
			put("_X_OX_O_O", new Point(1, 2));
			put("X__OXO___", new Point(2, 2));
			put("O_OXX_O__", new Point(2, 1));
			put("XXOOO_X__", new Point(2, 1));
			put("XO_OX_O__", new Point(2, 2));
			put("X_X_XOO_O", new Point(1, 0));
			put("X__OXOO__", new Point(2, 2));
			put("XOO_O_X__", new Point(0, 1));
			put("OX_OX_XOO", new Point(2, 0));
			put("XXO_X_OO_", new Point(2, 2));
			put("X__OXO__O", new Point(2, 0));
			put("XXOOO_XO_", new Point(2, 1));
			put("_________", new Point(0, 0));
			put("X__OX__OO", new Point(0, 2));
			put("X__OX_OO_", new Point(2, 2));
			put("XXO_XOO__", new Point(1, 2));
			put("X_O_OOX__", new Point(0, 1));
			put("XOXOX__O_", new Point(0, 2));
			put("OOX_X__O_", new Point(0, 2));
			put("X_XO___O_", new Point(1, 0));
			put("XOXOO_OX_", new Point(2, 1));
			put("XOOOXO_X_", new Point(2, 2));
			put("XOXOO__XO", new Point(2, 1));
			put("X_O_O_XO_", new Point(0, 1));
			put("_X_OXO__O", new Point(1, 2));
			put("XO_XX_O_O", new Point(2, 1));
			put("X_X___O_O", new Point(1, 0));
			put("XXOOOX__O", new Point(0, 2));
			put("XOXOX_OO_", new Point(2, 2));
			put("OOXOXO_X_", new Point(0, 2));
			put("X__O_____", new Point(1, 0));
			put("_XOOX_XOO", new Point(2, 1));
			put("XOXOXOO__", new Point(2, 2));
			put("XOXOXO__O", new Point(0, 2));
			put("XX____OO_", new Point(2, 0));
			put("_X_OX__OO", new Point(0, 2));
			put("OOX_X___O", new Point(0, 2));
			put("OOX_X_O__", new Point(0, 1));
			put("X_XO____O", new Point(1, 0));
			put("O___X__O_", new Point(2, 1));
			put("XX___OO__", new Point(2, 0));
			put("XO_OX_XOO", new Point(2, 0));
			put("XXOOOX_O_", new Point(0, 2));
			put("O________", new Point(1, 1));
			put("O___XO___", new Point(1, 2));
			put("XOXOX_O_O", new Point(1, 2));
			put("XOX___XOO", new Point(0, 1));
			put("OO__X____", new Point(2, 0));
			put("O_O_XX_O_", new Point(0, 1));
			put("OOX_XO___", new Point(0, 2));
			put("XOX__OX_O", new Point(0, 1));
			put("X__OXOXOO", new Point(2, 0));
			put("XO_X____O", new Point(0, 2));
			put("XO_X__O__", new Point(1, 1));
			put("X_O_O____", new Point(0, 2));
			put("XX_OO__O_", new Point(2, 0));
			put("XX____O_O", new Point(2, 0));
			put("OXO_XO___", new Point(1, 2));
			put("XXOOX__O_", new Point(2, 2));
			put("XX_OOXOO_", new Point(2, 0));
			put("XX_OOX_OO", new Point(2, 0));
			put("X_OOOXXO_", new Point(1, 0));
			put("__OOX____", new Point(1, 2));
			put("O_OOX_X__", new Point(1, 0));
			put("X_OXO____", new Point(0, 2));
			put("XXOOXO___", new Point(1, 2));
			put("XOOOOXX__", new Point(1, 2));
			put("X_OXX_O_O", new Point(2, 1));
			put("XOOOX_OX_", new Point(2, 2));
			put("XOOOX__XO", new Point(2, 1));
			put("XO_X___O_", new Point(0, 2));
			put("OXO_X__O_", new Point(0, 1));
			put("OXOOX_XO_", new Point(2, 1));
			put("X_OXXOO__", new Point(2, 2));
			put("XO_OOXOX_", new Point(2, 0));
			put("XO_OOX_XO", new Point(2, 0));
			put("X_OXOO___", new Point(0, 2));
			put("X_O_O_X_O", new Point(0, 1));
			put("XO_XO_OXO", new Point(2, 0));
			put("OXOOXOX__", new Point(1, 2));
			put("XX_OOXO_O", new Point(2, 0));
			put("O__OX____", new Point(0, 2));
			put("X_OOOXX_O", new Point(1, 0));
			put("X_OOX_OXO", new Point(1, 0));
			put("X_OXX_OO_", new Point(2, 1));
			put("XO_XOO_XO", new Point(0, 2));
			put("O_O_XO_X_", new Point(1, 0));
			put("OXO_X___O", new Point(1, 2));
			put("OXO_X_O__", new Point(1, 2));
			put("OXOOX_X_O", new Point(1, 2));
			put("XO_XOOOX_", new Point(2, 0));
			put("XO_OXO___", new Point(2, 2));
			put("X_____O__", new Point(1, 0));
			put("X_______O", new Point(2, 0));
			put("XO_OX__O_", new Point(2, 2));
			put("XOX_O_O__", new Point(1, 2));
			put("XOX_O___O", new Point(1, 2));
			put("____O____", new Point(0, 0));
			put("X_X_XO_OO", new Point(1, 0));
			put("O__OXOX__", new Point(2, 0));
			put("X_X_O__O_", new Point(1, 0));
			put("O__OX_XO_", new Point(2, 0));
			put("XOX_X_OO_", new Point(2, 2));
			put("XOX_X__OO", new Point(0, 2));
			put("X__OXO_O_", new Point(2, 2));
			put("X_XOOXO_O", new Point(1, 0));
			put("X_X_OO__O", new Point(1, 0));
			put("X_X_OOO__", new Point(1, 0));
			put("OOXXX_OO_", new Point(2, 1));
			put("XOOXX_O__", new Point(2, 1));
			put("OX_XXOO_O", new Point(1, 2));
			put("XOOOO_XX_", new Point(2, 2));
			put("XOX_XO__O", new Point(0, 2));
			put("X_X_O__OO", new Point(1, 0));
			put("X_X_O_OO_", new Point(1, 0));
			put("OOXXXOO__", new Point(1, 2));
			put("X______O_", new Point(2, 0));
			put("XXO___O__", new Point(1, 1));
			put("XXOOX_O__", new Point(1, 2));
			put("XXOOX___O", new Point(1, 2));
			put("X____O___", new Point(1, 1));
			put("O__OX_X_O", new Point(2, 0));
			put("X_XOOXOO_", new Point(1, 0));
			put("OOXXX_O_O", new Point(2, 1));
			put("OX__XOXOO", new Point(2, 0));
			put("XO__O__XO", new Point(0, 1));
			put("XO__O_OX_", new Point(2, 0));
			put("OX_XXOOO_", new Point(2, 2));
			put("XOX_XO_O_", new Point(0, 2));
			put("OO_OX_X__", new Point(2, 0));
			put("X__OO____", new Point(2, 1));
			put("XO__OO_X_", new Point(0, 1));
			put("XXO_O____", new Point(0, 2));
			put("OXOXX_OO_", new Point(2, 1));
			put("OXOXX__OO", new Point(2, 1));
			put("XOO_OOXX_", new Point(0, 1));
			put("_OXOXO___", new Point(0, 2));
			put("_OXOX__O_", new Point(0, 2));
			put("OX__X_O_O", new Point(1, 2));
			put("XOOXOO_X_", new Point(0, 2));
			put("XO_______", new Point(0, 1));
			put("OX__XO__O", new Point(1, 2));
			put("O__OXX_O_", new Point(0, 2));
			put("X__OXOOXO", new Point(1, 0));
			put("XOO_O_XXO", new Point(0, 1));
			put("XX_O__O__", new Point(2, 0));
			put("XX_O____O", new Point(2, 0));
			put("X_X_O___O", new Point(1, 0));
			put("OOXOX____", new Point(0, 2));
			put("XXO_O__O_", new Point(0, 2));
			put("XX__OO_O_", new Point(2, 0));
			put("OX__X__OO", new Point(0, 2));
			put("XOOXO__XO", new Point(0, 2));
			put("X_X_O_O_O", new Point(1, 0));
			put("XO_OX_OXO", new Point(2, 0));
			put("___OX__O_", new Point(0, 0));
			put("XX__O__O_", new Point(2, 0));
			put("X__XOO__O", new Point(0, 2));
			put("X__XOOO__", new Point(2, 0));
			put("__OOXO_X_", new Point(1, 0));
			put("OO_OXXXO_", new Point(2, 0));
			put("XX__OO___", new Point(2, 0));
			put("___OXO___", new Point(0, 0));
			put("OXOOX____", new Point(1, 2));
			put("XX_O___O_", new Point(2, 0));
			put("_OOOX__X_", new Point(0, 0));
			put("XX__O_OO_", new Point(2, 0));
			put("XX__O__OO", new Point(2, 0));
			put("XX_O_O___", new Point(2, 0));
			put("O___XO_XO", new Point(1, 0));
			put("O___XOOX_", new Point(1, 0));
			put("X_XXOOOO_", new Point(1, 0));
			put("X_OOOX___", new Point(0, 2));
			put("XO_XO____", new Point(0, 2));
			put("_O_OX____", new Point(2, 0));
			put("XOO_O__X_", new Point(0, 2));
			put("OXOXXO_O_", new Point(2, 2));
			put("___OX_O__", new Point(0, 0));
			put("___OX___O", new Point(1, 0));
			put("XX__O_O__", new Point(2, 0));
			put("XX__O___O", new Point(2, 0));
			put("OOX_XOOX_", new Point(0, 1));
			put("OOX_XO_XO", new Point(0, 2));
			put("XO_X_O___", new Point(0, 2));
			put("O__OXO_X_", new Point(1, 0));
			put("XOXXOOO__", new Point(1, 2));
			put("OXO_X_XOO", new Point(2, 1));
			put("X_O_XO___", new Point(2, 2));
			put("X_O______", new Point(0, 1));
			put("XOX_O_X_O", new Point(0, 1));
			put("___O_____", new Point(1, 1));
			put("_OXOX_O__", new Point(0, 0));
			put("_OXOX___O", new Point(0, 2));
			put("X_XXOOO_O", new Point(1, 0));
			put("OXOXXOO__", new Point(1, 2));
			put("_XOOX___O", new Point(1, 2));
			put("O__XX_O_O", new Point(2, 1));
			put("X_X__O_O_", new Point(1, 0));
			put("XOXO__X_O", new Point(1, 1));
			put("XO_OOX___", new Point(1, 2));
			put("X_XOXOO_O", new Point(1, 0));
			put("X_XOO___O", new Point(1, 0));
			put("X_XOO_O__", new Point(1, 0));
			put("X_OOXO___", new Point(2, 2));
			put("XXOO_____", new Point(1, 1));
			put("X___O___O", new Point(2, 0));
			put("XO_XXOO__", new Point(2, 2));
			put("XO_XOO___", new Point(0, 2));
			put("X_OOX__O_", new Point(2, 2));
			put("O___XXOO_", new Point(0, 1));
			put("O___XX_OO", new Point(0, 1));
			put("XXOOOXXO_", new Point(2, 2));
			put("O__OXXXOO", new Point(2, 0));
			put("X__OOX_O_", new Point(1, 0));
			put("XOX____O_", new Point(1, 1));
			put("X_XOXO__O", new Point(1, 0));
			put("X___O_O__", new Point(2, 0));
			put("X__XOO_O_", new Point(0, 2));
			put("O__XX_OO_", new Point(2, 1));
			put("X_X__O__O", new Point(1, 0));
			put("XO_XX_OO_", new Point(2, 1));
			put("O__XXOO__", new Point(1, 0));
			put("OO__XO_X_", new Point(2, 0));
			put("X_XOXO_OO", new Point(1, 0));
			put("X_X___OO_", new Point(1, 0));
			put("X_X____OO", new Point(1, 0));
			put("X_OOX_O__", new Point(2, 2));
			put("XOX_____O", new Point(0, 2));
			put("__OOX__XO", new Point(1, 0));
			put("__OOX_OX_", new Point(1, 0));
			put("X_X_____O", new Point(1, 0));
			put("XXO_O_XOO", new Point(0, 1));
			put("XO_OO__X_", new Point(2, 1));
			put("X_OX___O_", new Point(0, 2));
			put("XO__O____", new Point(1, 2));
			put("X___XOO__", new Point(2, 2));
			put("X___XO__O", new Point(2, 0));
			put("XOX_O_OXO", new Point(0, 1));
			put("X_OX_O___", new Point(0, 2));
			put("XXO_O_X_O", new Point(0, 1));
			put("OO_XX_O__", new Point(2, 1));
			put("XOOX_____", new Point(0, 2));
			put("X___O____", new Point(1, 0));
			put("O_OOX__X_", new Point(1, 0));
			put("O_O_X____", new Point(1, 0));
			put("OX_OX___O", new Point(1, 2));
			put("OO__XX_O_", new Point(0, 1));
			put("XXO_OOXO_", new Point(0, 1));
			put("X_OX____O", new Point(0, 2));
			put("X_OX__O__", new Point(1, 1));
			put("X___XO_O_", new Point(2, 2));
			put("X___O__O_", new Point(1, 0));
			put("O_OOXXXO_", new Point(1, 0));
			put("XXO_O_XO_", new Point(0, 1));
			put("XXOOOXX_O", new Point(1, 2));
			put("X_OOO_X__", new Point(2, 1));
			put("X___OO___", new Point(0, 1));
			put("X__OOX__O", new Point(1, 0));
			put("X__OOXO__", new Point(2, 0));
			put("XOX_OO_XO", new Point(0, 1));
			put("XOX_OOOX_", new Point(0, 1));
			put("XXO_OOX__", new Point(0, 1));
			put("XO__XO___", new Point(2, 2));
			put("X_OOX_XOO", new Point(2, 1));
		}

	};



	/**
	 * It's the value using to determinate a equivalence class and to introduce the fortuity.
	 * It's the number of rotation to right to convert a <code>currentRotatedGrid</code> point to an original grid point.
	 * For the whole game is the same.
	 * The initial value is -1 because 0 is a valid value.
	 * Values: 0 - 3
	 */
	private int rotateValue = -1;

	/**
	 * The rotated grid (it's a equivalence class).
	 * It's valid only in the current turn.
	 */
	private Grid currentRotatedGrid;



	/**
	 * It update the <code>currentRotatedGrid</code> using the game grid and the <code>rotateValue<code>.
	 */
	private void updateCurrentRotatedGrid() {
		if (rotateValue == 0) { //Fast method
			currentRotatedGrid = getGame().getGrid();
			return;
		}
		currentRotatedGrid = getRotatetedGrid(getGame().getGrid(), 4 - rotateValue);
	}

	/**
	 * It converts the point in the rotated grid to the point in the original grid.
	 * 
	 * @param point the point in the rotated grid
	 * @return the point in the original grid
	 */
	private Point getOriginalPointFromCurrentRotatedGridPoint(Point point) {
		return getRotatedPoint(point, rotateValue);
	}

	/**
	 * It rotates to right the <code>point</code> [<code>rotateValue</code>] times.
	 * 
	 * @param point the original point
	 * @param rotateValue number of times to rotate the point to right
	 * @return the new rotated point
	 */
	private Point getRotatedPoint(Point point, int rotateValue) {
		Point newPoint = point;
		for (byte i = 0; i < rotateValue; i++) {
			newPoint = getRotatedPointToRight(newPoint);
		}
		return newPoint;
	}

	/**
	 * It rotates a point to right and it returns the new point.
	 * 
	 * @param point the original point
	 * @return the new point rotated to right
	 */
	private Point getRotatedPointToRight(Point point) {
		return new Point(point.getY(), 2 -point.getX());
	}

	/**
	 * It rotates to right the <code>grid</code> [<code>rotateValue</code>] times.
	 * 
	 * @param grid the original grid
	 * @param rotateValue number of times to rotate the grid to right
	 * @return the new rotated grid
	 */
	private Grid getRotatetedGrid(Grid grid, int rotateValue) {
		Grid rotatedGrid = new Grid();
		Point currentOriginalPoint;
		for(int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				currentOriginalPoint = new Point(x, y);
				rotatedGrid.addPawn(grid.getPawn(currentOriginalPoint), getRotatedPoint(currentOriginalPoint, rotateValue));
			}
		}
		return rotatedGrid;
	}

	/**
	 * It creates the <code>rotateValue</code>.
	 */
	private void createRotateValue() {
		if (getGame().getGrid().isEmpty() || getGame().getGrid().getContent()[1][1] != null) {
			rotateValue = (int) (Math.random()*4);
		}
		else {
			final Point[] BOTTOM_POINTS = {new Point(0, 0), new Point(0, 2), new Point(2, 2), new Point(2, 0)};
			final Point[] LATERAL_POINTS = {new Point(0, 1), new Point(0, 1), new Point(2, 1), new Point(2, 1)};
			createRotateValueFromOccupiedGrid(BOTTOM_POINTS);
			createRotateValueFromOccupiedGrid(LATERAL_POINTS);
		}
		
	}

	/**
	 * It create the <code>rotateValue</code> when the opponent starts the game.
	 * It assigns the point's index to the <code>rotateValue</code> when it finds an occupied point.
	 * 
	 * @param POINTS the examined points
	 */
	private void createRotateValueFromOccupiedGrid(final Point[] POINTS) {
		for (byte i = 0; i < POINTS.length; i++) {
			if (getGame().getGrid().getPawn(POINTS[i]) != null) {
				rotateValue = i;
				break;
			}
		}
	}

	/**
	 * It creates a string grid representation using "X" for this player and "O" for the other player.
	 * 
	 * @param grid the grid
	 * @return a simple grid representation
	 */
	public String gridToString(Grid grid) {
		StringBuilder sb = new StringBuilder();
		Pawn currentPawn;
		for (byte iRow = 0; iRow < grid.getContent()[0].length; iRow++) {
			for (byte iColumn = 0; iColumn < grid.getContent().length; iColumn++) {
				currentPawn = grid.getContent()[iColumn][iRow];
				sb.append((currentPawn != null) ? ((currentPawn.equals(this.getPawn()) ? "X" : "O")) : "_");
			}
		}
		return sb.toString();
	}



	/**
	 * It creates a player with a custom name, but a fixed pawn.
	 * It works only if the grid size is 3x3 and the players are 2.
	 * 
	 * @param name the player's name
	 * @param game the <code>TicTacToeGame</code> instance 
	 * @throws MaximumPlayerNumberExceededException if the players are too many for the pawn fixed values
	 * @throws GridSizeException if the size of the grid isn't 3x3 
	 * @throws InvalidNumberOfPlayersException if the players aren't 2
	 */
	public PerfectVirtualPlayer(String name, TicTacToeGame game) throws MaximumPlayerNumberExceededException, GridSizeException, InvalidNumberOfPlayersException {
		super(name);
		setGame(game);
	}

	/**
	 * It creates a player with default name and pawn.
	 * It works only if the grid size is 3x3 and the players are 2.
	 * 
	 * @param nPLayer the number of the player (from 0)
	 * @param game the <code>TicTacToeGame</code> instance 
	 * @throws MaximumPlayerNumberExceededException if the players are too many for the pawn fixed values
	 * @throws GridSizeException if the size of the grid isn't 3x3 
	 * @throws InvalidNumberOfPlayersException if the players aren't 2
	 */
	public PerfectVirtualPlayer(int nPLayer, TicTacToeGame game) throws MaximumPlayerNumberExceededException, GridSizeException, InvalidNumberOfPlayersException {
		super(nPLayer);
		setGame(game);
	}



	@Override
	public Point getNewPawnPoint() {
		if (rotateValue == -1) {
			createRotateValue();
		}
		updateCurrentRotatedGrid();
		Point currentRotatedGridNewPoint = PERFECT_POINTS.get(gridToString(currentRotatedGrid));
		assert currentRotatedGridNewPoint != null : "Situation not provided by PERFECT_POINTS.";
		return getOriginalPointFromCurrentRotatedGridPoint(currentRotatedGridNewPoint);
	}

}
