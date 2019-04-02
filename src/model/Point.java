package model;

public class Point {

	private byte x;
	public byte getX() {
		return x;
	}
	private void setX(byte x) {
		this.x = x;
	}

	private byte y;
	public byte getY() {
		return y;
	}
	private void setY(byte y) {
		this.y = y;
	}



	public Point(byte x, byte y) {
		setX(x);
		setY(y);
	}



	@Override
	public String toString() {
		return "(" + getX() + "; " + getY() + ")";
	}

}
