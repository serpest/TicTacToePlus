package model.base;

public class Point {

	private int x;

	private int y;



	public Point(int x, int y) {
		setX(x);
		setY(y);
	}



	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}



	private void setX(int x) {
		this.x = x;
	}

	private void setY(int y) {
		this.y = y;
	}

}