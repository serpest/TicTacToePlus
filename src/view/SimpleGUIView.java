package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GUIController;
import model.base.Point;
import model.components.Grid;
import model.components.Pawn;

/*
 * It works only in default grids.
 */
public class SimpleGUIView implements GUIView {

	/**
	 * The grid thickness pixels number.
	 */
	private static final int GRID_THICKNESS = 22;



	private GUIController controller;

	private JFrame frame;

	private JLabel gridLabel = new JLabel();
	private BufferedImage gridImage;

	private Grid lastShownGrid = new Grid();



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
			System.exit(2);
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
		lastShownGrid = grid.clone();
	}

	@Override
	public void showWinnerPoints(Point[] winnerPoints) {
		showPawnsOnGrid(winnerPoints, "green");
	}

	@Override
	public void setupGameFrame() {
		createFrame();
		createNorthPanel();
		showFrame();
	}



	private void createFrame() {
		frame = new JFrame("TicTacToePlus");
		frame.getContentPane().setBackground(Color.decode("#999999"));
		File iconFile = new File("res/icon.png");
		try {
			frame.setIconImage(ImageIO.read(iconFile));
		} catch (IOException exc) {
			System.err.println(iconFile + " doesn't exist.");
			System.exit(-2);
		}
	}

	private void createNorthPanel() {
		JButton[] menuButtons = {new JButton(NEW_NORMAL_SINGLEPLAYER_GAME), new JButton(NEW_LEGEND_SINGLEPLAYER_GAME), new JButton(NEW_CLASSIC_MULTIPLAYER_GAME)};
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(Color.decode("#00ff00"));
		for (JButton button : menuButtons) {
			button.setBackground(Color.WHITE);
			button.addActionListener(controller::newGameActionPerformed);
			menuPanel.add(button);
		}
		frame.add(menuPanel, BorderLayout.NORTH);
	}

	private void showFrame() {
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
		Dimension placeSize = new Dimension((int) ((getGridImageDimension().getWidth() - GRID_THICKNESS * (controller.getGame().getGrid().getXSize() - 1)) / 3), (int) ((getGridImageDimension().getHeight() - GRID_THICKNESS * (controller.getGame().getGrid().getContent()[1].length - 1)) / 3));
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
				System.exit(2);
			}
			marginSize = new Dimension((int) ((placeSize.getWidth() - currentPawnImage.getWidth()) / 2), (int) ((placeSize.getHeight() - currentPawnImage.getHeight()) / 2));
			gridGraphics.drawImage(currentPawnImage, (int) (point.getX() * (placeSize.getWidth() + GRID_THICKNESS) + marginSize.getWidth()), (int) (point.getY() * (placeSize.getHeight() + GRID_THICKNESS) + marginSize.getHeight()), null);
		}
		gridGraphics.dispose();
		gridLabel.repaint();
	}

	/**
	 * It returns the points where there are differences between the <code>lastShownGrid</code> and the parameter.
	 * 
	 * @param grid the other grid
	 * @return the points where there are differences
	 */
	private Point[] getLastShownGridDifferences(Grid grid) {
		List<Point> differencesPoints = new ArrayList<>();
		Point currentPoint;
		for (byte x = 0; x < grid.getXSize(); x++) {
			for (byte y = 0; y < grid.getXSize(); y++) {
				currentPoint = new Point(x, y);
				if (grid.getPawn(currentPoint) != lastShownGrid.getPawn(currentPoint)) {
					differencesPoints.add(currentPoint);
				}
			}
		}
		return differencesPoints.toArray(new Point[differencesPoints.size()]);
	}
}