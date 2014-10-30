public class LineSeg {

	private Point2f P1;
	private Point2f P2;
	private float length;

	public LineSeg(Point2f P1, Point2f P2) {
		this.P1 = P1;
		this.P2 = P2;
		calculateCoefficients(P1, P2);
	}

	public Point2f getP1() {
		return P1;
	}

	public Point2f getP2() {
		return P2;
	}

	public float getLength() {
		return length;
	}

	private void calculateCoefficients(Point2f p1, Point2f p2) {
		length = (float) Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2)
				+ Math.pow((p2.getY() - p1.getY()), 2));
	}

}
