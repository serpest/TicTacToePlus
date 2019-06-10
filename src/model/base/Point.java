package model.base;

/**
 * It's a simple point P(x, y).
 */
public class Point {

	/**
	 * It's the x coordinate.
	 */
	private int x;
	public int getX() {
		return x;
	}
	private void setX(int x) {
		this.x = x;
	}

	/**
	 * It's the y coordinate.
	 */
	private int y;
	public int getY() {
		return y;
	}
	private void setY(int y) {
		this.y = y;
	}



	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Point(int x, int y) {
		setX(x);
		setY(y);
	}



	@Override
	public String toString() {
		return "(" + getX() + "; " + getY() + ")";
	}

}
