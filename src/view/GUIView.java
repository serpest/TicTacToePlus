package view;

import java.awt.Dimension;

import model.base.Point;

/**
 * The GUI view interface.
 */
public interface GUIView extends View {

	public static final String NEW_NORMAL_SINGLEPLAYER_GAME = "Normal Single-player";

	public static final String NEW_LEGEND_SINGLEPLAYER_GAME = "Legend Single-player";

	public static final String NEW_CLASSIC_MULTIPLAYER_GAME = "Classic Multiplayer";

	/**
	 * It setups the game frame.
	 */
	void setupFrame();

	/**
	 * It shows the winner points.
	 * 
	 * @param winnerPoints the winner points
	 */
	void showWinnerPoints(Point[] winnerPoints);

	/**
	 * @return the grid image dimension
	 */
	Dimension getGridImageDimension();

	/**
	 * It resets the grid deleting all the pawns.
	 */
	void resetGrid();

}