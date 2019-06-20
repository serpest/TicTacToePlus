package model.players;

import java.util.ArrayList;
import java.util.List;

import model.components.Grid;
import model.components.Pawn;
import model.base.Point;
import model.TicTacToeGame;
import model.checker.TicTacToeMainChecker;
import model.exceptions.GridSizeException;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.MaximumPlayerNumberExceededException;

/**
 * An automatic player in 3x3 grid.
 * It uses the possible winner and loser points to determinate the new point. It selects the new pawn casually.
 */
public class NormalVirtualPlayer extends VirtualPlayer {

	/**
	 * The tic tac toe checker.
	 */
	private TicTacToeMainChecker checker;

	/**
	 * The opponent's pawn.
	 */
	private Pawn opponentPawn;



	/**
	 * It creates a player with a custom name, but a fixed pawn.
	 * It works only if the grid size is 3x3 and the players are 2.
	 * 
	 * @param name the player's name
	 * @param game the <code>TicTacToeGame</code> instance 
	 * @param opponentPawn the opponent's pawn
	 * @throws MaximumPlayerNumberExceededException if the players are too many for the pawn fixed values
	 * @throws GridSizeException if the size of the grid isn't 3x3 
	 * @throws InvalidNumberOfPlayersException if the players aren't 2
	 */
	public NormalVirtualPlayer(String name, TicTacToeGame game, Pawn opponentPawn) throws MaximumPlayerNumberExceededException, GridSizeException, InvalidNumberOfPlayersException {
		super(name);
		initVirtualPlayer(game, opponentPawn);
	}

	/**
	 * It creates a player with default name and pawn.
	 * It works only if the grid size is 3x3 and the players are 2.
	 * 
	 * @param nPLayer the number of the player (from 0)
	 * @param game the <code>TicTacToeGame</code> instance 
	 * @param opponentPawn the opponent's pawn
	 * @throws MaximumPlayerNumberExceededException if the players are too many for the pawn fixed values
	 * @throws GridSizeException if the size of the grid isn't 3x3 
	 * @throws InvalidNumberOfPlayersException if the players aren't 2
	 */
	public NormalVirtualPlayer(int nPLayer, TicTacToeGame game, Pawn opponentPawn) throws MaximumPlayerNumberExceededException, GridSizeException, InvalidNumberOfPlayersException {
		super(nPLayer);
		initVirtualPlayer(game, opponentPawn);
	}



	@Override
	public Point getNewPawnPoint() {
		List<Point> emptyPoints = getEmptyPointsInGrid();
		Point winnerPoint = getWinnerPointInGrid(emptyPoints);
		if (winnerPoint != null) { //The player can win
			return winnerPoint;
		}
		List<Point> noLosingPoints = getNoLosingPointsInGrid(emptyPoints);
		if (noLosingPoints.size() == 1) { //There is only a no losing point
			return noLosingPoints.get(0);
		}
		else if (noLosingPoints.size() == 0) { //There is't no losing point.
			return emptyPoints.get((int)(Math.random()*emptyPoints.size()));
		}
		return noLosingPoints.get((int)(Math.random()*noLosingPoints.size()));
	}



	/**
	 * It return the no losing points.
	 * 
	 * @param emptyPoints the points to analyze (if a point is occupied it throws an exception)
	 * @return the list of no losing points
	 */
	private List<Point> getNoLosingPointsInGrid(List<Point> emptyPoints) {
		List<Point> noLosingPoints = new ArrayList<>();
		Grid clonedGrid = null;
		for (Point point : emptyPoints) {
			clonedGrid = getGame().getGrid().deepCopy();
			clonedGrid.addPawn(opponentPawn, point);
			if (checker.checkTicTacToe(clonedGrid, opponentPawn) != null) {
				noLosingPoints.add(point);
				return noLosingPoints;
			}
		}
		return emptyPoints;
	}

	/**
	 * It analyzes the empty points to find a winner point. When it finds one it returns it.
	 * 
	 * @param emptyPoints the points to analyze (if a point is occupied it throws an exception)
	 * @return a winner point or null
	 */
	private Point getWinnerPointInGrid(List<Point> emptyPoints) {
		Grid clonedGrid = null;
		for (Point point : emptyPoints) {
			clonedGrid = getGame().getGrid().deepCopy();
			clonedGrid.addPawn(getPawn(), point);
			if (checker.checkTicTacToe(clonedGrid, getPawn()) != null) {
				return point;
			}
		}
		//There isn't any possible tic tac toe.
		return null;
	}

	/**
	 * @return the <code>List</code> of the empty points in the grid
	 */
	private List<Point> getEmptyPointsInGrid() {
		List<Point> emptyPoints = new ArrayList<>();
		Point currentPoint;
		for (int iColumn = 0; iColumn < getGame().getGrid().getContent().length; iColumn++) {
			for (int iRow = 0; iRow < getGame().getGrid().getContent()[0].length; iRow++) {
				currentPoint = new Point(iColumn, iRow);
				if (getGame().getGrid().getPawn(currentPoint) == null) {
					emptyPoints.add(currentPoint);
				}
			}
		}
		return emptyPoints;
	}

	/**
	 * It initializes the VirtualPlayer's variables.
	 * 
	 * @throws GridSizeException if the size of the grid isn't 3x3 
	 * @throws InvalidNumberOfPlayersException if the players aren't 2
	 */
	private void initVirtualPlayer(TicTacToeGame game, Pawn opponentPawn) throws GridSizeException, InvalidNumberOfPlayersException {
		this.setGame(game);
		this.opponentPawn = opponentPawn;
		this.checker = new TicTacToeMainChecker(game.getTicTacToeNumber());
	}

}