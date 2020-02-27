package model.players;

import java.util.ArrayList;
import java.util.List;

import model.components.Grid;
import model.components.Pawn;
import model.base.Point;
import model.checker.TicTacToeMainChecker;
import model.exceptions.GridSizeException;
import model.exceptions.InvalidNumberOfPlayersException;
import model.exceptions.MaximumPlayersNumberExceededException;

/**
 * An automatic player in 3x3 grid. The players must be 2.
 * It uses the possible winner and loser points to determinate the new point.
 */
public class NormalVirtualPlayer extends VirtualPlayer {

	private List<Point> lastEmptyPointsInGrid;

	private TicTacToeMainChecker checker;

	private Pawn opponentPawn;



	public NormalVirtualPlayer(String name, Pawn opponentPawn, int ticTacToeNumber) throws MaximumPlayersNumberExceededException, GridSizeException, InvalidNumberOfPlayersException {
		super(name);
		init(opponentPawn, ticTacToeNumber);
	}

	public NormalVirtualPlayer(Pawn opponentPawn, int ticTacToeNumber) throws MaximumPlayersNumberExceededException, GridSizeException, InvalidNumberOfPlayersException {
		super();
		init(opponentPawn, ticTacToeNumber);
	}



	@Override
	public Point getNewPawnPoint(Grid grid) {
		setCurrentOriginalGrid(grid);
		if (lastEmptyPointsInGrid == null) //It's called only in the first turn for the initialization
			initLastEmptyPointsInGrid();
		updateEmptyPointsInGrid();
		Point winnerPoint = getWinnerPointInGrid(lastEmptyPointsInGrid);
		if (winnerPoint != null) { //The player can win
			return winnerPoint;
		}
		List<Point> noLosingPoints = getNoLosingPointsInGrid(lastEmptyPointsInGrid);
		return noLosingPoints.get((int)(Math.random()*noLosingPoints.size()));
	}

	public void init(Pawn opponentPawn, int ticTacToeNumber) throws GridSizeException, InvalidNumberOfPlayersException {
		this.opponentPawn = opponentPawn;
		this.checker = new TicTacToeMainChecker(ticTacToeNumber);
	}



	/*
	 * If the opponent has two possible winning points,
	 * this method will return a point that avoids only one tic tac toe.
	 * In this case it isn't a no losing point indeed.
	 */
	private List<Point> getNoLosingPointsInGrid(List<Point> emptyPoints) {
		List<Point> noLosingPoints = new ArrayList<>();
		Grid clonedGrid = null;
		for (Point point : emptyPoints) {
			clonedGrid = getCurrentOriginalGrid().clone();
			clonedGrid.addPawn(opponentPawn, point);
			if (checker.checkTicTacToe(clonedGrid, opponentPawn) != null) {
				noLosingPoints.add(point);
				return noLosingPoints;
			}
		}
		return emptyPoints;
	}

	private Point getWinnerPointInGrid(List<Point> emptyPoints) {
		Grid clonedGrid = null;
		for (Point point : emptyPoints) {
			clonedGrid = getCurrentOriginalGrid().clone();
			clonedGrid.addPawn(getPawn(), point);
			if (checker.checkTicTacToe(clonedGrid, getPawn()) != null) {
				return point;
			}
		}
		//There isn't any possible tic tac toe.
		return null;
	}

	private void updateEmptyPointsInGrid() {
		for (int i = 0; i < lastEmptyPointsInGrid.size(); i++) {
			if (getCurrentOriginalGrid().getPawn(lastEmptyPointsInGrid.get(i)) != null) {
				lastEmptyPointsInGrid.remove(i);
				i--;
			}
		}
	}

	private void initLastEmptyPointsInGrid() {
		int xSize = getCurrentOriginalGrid().getXSize();
		int ySize = getCurrentOriginalGrid().getYSize();
		lastEmptyPointsInGrid = new ArrayList<Point>(xSize * ySize);
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				lastEmptyPointsInGrid.add(new Point(x, y));
			}
		}
	}

}