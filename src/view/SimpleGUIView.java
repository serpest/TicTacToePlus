package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.GUIController;
import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

/**
 * A simple implementation of <code>GUIView</code>.
 * It works only in default grids.
 */
public class SimpleGUIView implements GUIView {

	/**
	 * The grid thickness pixels number.
	 */
	private static final int GRID_THICKNESS = 22;



	/**
	 * The game controller.
	 */
	private GUIController controller;

	/**
	 * The GUI frame.
	 */
	private JFrame frame;

	/**
	 * The grid image label.
	 */
	private JLabel gridLabel = new JLabel();

	/**
	 * The grid image.
	 */
	private BufferedImage gridImage;

	/**
	 * The last shown grid.
	 */
	private Grid lastShownGrid = new Grid();



	/**
	 * It setups the frame using the <code>setupFrame()</code> method and it instance the controller.
	 * 
	 * @param controller the game controller
	 */
	public SimpleGUIView(GUIController controller) {
		this.controller = controller;
	}



	@Override
	public Dimension getGridImageDimension() {
		return gridLabel.getSize();
	}

	@Override
	public void resetGrid() {
		lastShownGrid = new Grid();
		frame.remove(gridLabel);
		File gridImageFile = new File("res/game/grid.png");
		try {
			gridImage = ImageIO.read(gridImageFile);
		} catch (IOException exc) {
			System.err.println(gridImageFile + " doesn't exist.");
			System.exit(-2);
		}
		gridLabel = new JLabel(new ImageIcon(gridImage));
		gridLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event) {
				controller.newPawnMouseClicked(event);
			}

			@Override
			public void mousePressed(MouseEvent event) {}

			@Override
			public void mouseReleased(MouseEvent event) {}

			@Override
			public void mouseEntered(MouseEvent event) {}

			@Override
			public void mouseExited(MouseEvent event) {}

		});
		frame.add(gridLabel);
		frame.pack();
	}

	@Override
	public void showGrid(Grid grid) {
		Point[] pointsToShow = getLastShownGridDifferences(grid);
		showPawnsOnGrid(pointsToShow, "black");
		lastShownGrid = grid.deepCopy();
	}

	@Override
	public void showWinnerPoints(Point[] winnerPoints) {
		showPawnsOnGrid(winnerPoints, "green");
	}

	@Override
	public void setupFrame() {
		//Frame creation
		frame = new JFrame("TicTacToePlus");
		frame.getContentPane().setBackground(Color.decode("#999999"));
		File iconFile = new File("res/icon.png");
		try {
			frame.setIconImage(ImageIO.read(iconFile));
		} catch (IOException exc) {
			System.err.println(iconFile + " doesn't exist.");
			System.exit(-2);
		}
		//North panel creation
		Button[] menuButtons = {new Button(NEW_NORMAL_SINGLEPLAYER_GAME), new Button(NEW_LEGEND_SINGLEPLAYER_GAME), new Button(NEW_CLASSIC_MULTIPLAYER_GAME)};
		Panel menuPanel = new Panel();
		menuPanel.setBackground(Color.decode("#00ff00"));
		for (Button button : menuButtons) {
			button.setBackground(Color.WHITE);
			button.addActionListener(controller::newGameActionPerformed);
			menuPanel.add(button);
		}
		frame.add(menuPanel, BorderLayout.NORTH);
		//Visualization
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}



	/**
	 * It shows the selected points using the selected color on the grid.
	 * 
	 * @param points the points to show
	 * @param filePrefix the pawns color
	 */
	private void showPawnsOnGrid(Point[] points, String filePrefix) {
		Pawn currentPawn;
		File currentPawnFile;
		BufferedImage currentPawnImage = null;
		Graphics gridGraphics = gridImage.getGraphics();
		Dimension placeSize = new Dimension((int) ((getGridImageDimension().getWidth() - GRID_THICKNESS * (controller.getGame().getGrid().getContent().length - 1)) / 3), (int) ((getGridImageDimension().getHeight() - GRID_THICKNESS * (controller.getGame().getGrid().getContent()[1].length - 1)) / 3));
		Dimension marginSize;
		for (Point point : points) {
			currentPawn = controller.getGame().getGrid().getPawn(point);
			if (currentPawn == null) {
				continue;
			}
			currentPawnFile = new File("res/game/pawns/" + filePrefix + "_" + currentPawn + ".png");
			try {
				currentPawnImage = ImageIO.read(currentPawnFile);
			} catch (IOException exc) {
				System.err.println(currentPawnFile + " doesn't exist.");
				System.exit(-2);
			}
			marginSize = new Dimension((int) ((placeSize.getWidth() - currentPawnImage.getWidth()) / 2), (int) ((placeSize.getHeight() - currentPawnImage.getHeight()) / 2));
			gridGraphics.drawImage(currentPawnImage, (int) (point.getX() * (placeSize.getWidth() + GRID_THICKNESS) + marginSize.getWidth()), (int) (point.getY() * (placeSize.getHeight() + GRID_THICKNESS) + marginSize.getHeight()), null);
		}
		gridGraphics.dispose();
		gridLabel.repaint();
	}

	/**
	 * It returns the differences' points between the <code>lastShownGrid</code> and the parameter.
	 * 
	 * @param grid the other grid
	 * @return the differences' point
	 */
	private Point[] getLastShownGridDifferences(Grid grid) {
		List<Point> differencesPoints = new ArrayList<>();
		Point currentPoint;
		for (byte x = 0; x < grid.getContent().length; x++) {
			for (byte y = 0; y < grid.getContent().length; y++) {
				currentPoint = new Point(x, y);
				if (grid.getPawn(currentPoint) != lastShownGrid.getPawn(currentPoint)) {
					differencesPoints.add(currentPoint);
				}
			}
		}
		return differencesPoints.toArray(new Point[differencesPoints.size()]);
	}
}