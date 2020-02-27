package view;

import java.awt.Dimension;

import model.base.Point;

public interface GUIView extends View {

	public static final String NEW_NORMAL_SINGLEPLAYER_GAME = "Normal Single-player";

	public static final String NEW_LEGEND_SINGLEPLAYER_GAME = "Legend Single-player";

	public static final String NEW_CLASSIC_MULTIPLAYER_GAME = "Classic Multiplayer";



	void setupGameFrame();

	void showWinnerPoints(Point[] winnerPoints);

	Dimension getGridImageDimension();

	void resetGrid();

}