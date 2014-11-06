package model;

/**
 * This class represents a Point with two coordinates
 * 
 * @author ac332317
 * 
 */
public class Point2f {

	private float x;
	private float y;

	/**
	 * 
	 * @param x
	 *            X represents Latitude
	 * @param y
	 *            Y represents Longitude
	 */
	public Point2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

}
