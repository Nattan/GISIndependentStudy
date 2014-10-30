import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Canvas extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;

	ArrayList<Point2f> normalizedVertices;

	Point2f clikedPoint;
	Point2f clikedPoint2;

	private final int RADIUS = 5;

	double length;

	int x2, y2, y1, x1;

	float longitudeRange, latitudeRange, maxLong, minLong, maxLat, minLat;

	public Canvas() {
		normalizedVertices = new ArrayList<Point2f>();
		addMouseListener(this);
		setLayout(new FlowLayout());
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (normalizedVertices.size() != 0) {
			Point2f start = null;

			for (Point2f v : normalizedVertices) {
				Point2f vS = new Point2f(v.getX() * getWidth(), v.getY()
						* getWidth());
				System.out.println("Long " + (int) vS.getX() * -1 + " Lat: "
						+ (int) vS.getY());
				if (start != null) {
					g2.drawLine((int) start.getX() * -1,
							(int) (getHeight() - (start.getY() * -1)),
							(int) vS.getX() * -1,
							(int) (getHeight() - (vS.getY() * -1)));
				}
				start = vS;
			}
		}

		if (this.clikedPoint != null) {
			x1 = (int) this.clikedPoint.getX();
			y1 = (int) this.clikedPoint.getY();
			g2.fillArc(x1 - RADIUS, y1 - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360);
			g2.drawString(this.getLongLat(x1, y1), x1 - 20, y1 - 20);
		}

		if (this.clikedPoint2 != null) {
			//LineSeg line = new LineSeg(this.clikedPoint, this.clikedPoint2);

			x2 = (int) this.clikedPoint2.getX();
			y2 = (int) this.clikedPoint2.getY();

			g2.fillArc(x2 - RADIUS, y2 - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360);
			g2.drawLine(x1, y1, x2, y2);

			
			g2.drawString(this.getLongLat(x2, y2), x2 - 20, y2 - 20);
		}

		if (this.clikedPoint != null && this.clikedPoint2 != null) {
			this.clikedPoint = null;
			this.clikedPoint2 = null;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.clikedPoint == null) {
			this.clikedPoint = new Point2f(e.getX(), e.getY());
		} else {
			this.clikedPoint2 = new Point2f(e.getX(), e.getY());
		}
		repaint();
	}

	private String getLongLat(float normX, float normY) {
		// longitude = LONGITUDE_RANGE * ( normX / getWidth() ) + minLong
		// latitude = latitudeRange * ( normY / getHeight() ) + minLat
		float originalX = getLongitudeRange() * (normX / getWidth())
				+ getMinLong();
		float originalY = getLatitudeRange() * (normY / getHeight())
				+ getMinLat();

		DecimalFormat df = new DecimalFormat("###.##");

		return df.format(originalX) + ", " + df.format(originalY);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public float getLongitudeRange() {
		return longitudeRange;
	}

	public void setLongitudeRange(float longitudeRange) {
		this.longitudeRange = longitudeRange;
	}

	public float getLatitudeRange() {
		return latitudeRange;
	}

	public void setLatitudeRange(float latitudeRange) {
		this.latitudeRange = latitudeRange;
	}

	public float getMaxLong() {
		return maxLong;
	}

	public void setMaxLong(float maxLong) {
		this.maxLong = maxLong;
	}

	public float getMinLong() {
		return minLong;
	}

	public void setMinLong(float minLong) {
		this.minLong = minLong;
	}

	public float getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(float maxLat) {
		this.maxLat = maxLat;
	}

	public float getMinLat() {
		return minLat;
	}

	public void setMinLat(float minLat) {
		this.minLat = minLat;
	}

}
