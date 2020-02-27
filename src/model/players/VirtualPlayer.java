package model.players;

import model.base.Point;
import model.components.Grid;
import model.exceptions.GridSizeException;
import model.exceptions.MaximumPlayersNumberExceededException;

public abstract class VirtualPlayer extends Player {

	private Grid currentOriginalGrid;



	public VirtualPlayer(String name) throws MaximumPlayersNumberExceededException {
		super(name);
	}

	public VirtualPlayer() throws MaximumPlayersNumberExceededException {
		super();
	}



	public Grid getCurrentOriginalGrid() {
		return currentOriginalGrid;
	}

	public void setCurrentOriginalGrid(Grid currentOriginalGrid) {
		if (!isGridValid(currentOriginalGrid))
			throw new GridSizeException();
		this.currentOriginalGrid = currentOriginalGrid;
	}

	public abstract Point getNewPawnPoint(Grid grid);



	protected boolean isGridValid(Grid grid) {
		return grid.getXSize() == 3 && grid.getYSize() == 3;
	}

}