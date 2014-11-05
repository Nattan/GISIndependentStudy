package app.view;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JComponent;

import app.model.Point2f;

public class Canvas extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;

	private ArrayList<Point2f> normalizedVertices;

	private Point2f clikedPoint;
	private Point2f clikedPoint2;

	private final int RADIUS = 5;

	private int x2, y2, y1, x1;

	private float longitudeRange, latitudeRange, maxLong, minLong, maxLat, minLat;
	
    public static final double R = 6372.8; // In kilometers

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
			x2 = (int) this.clikedPoint2.getX();
			y2 = (int) this.clikedPoint2.getY();

			g2.fillArc(x2 - RADIUS, y2 - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360);
			g2.drawLine(x1, y1, x2, y2);
			g2.drawString(this.getLongLat(x2, y2), x2 - 20, y2 - 20);
			Main.distanceLabel.setText(this.haversineFormula(clikedPoint, clikedPoint2));;
			
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
		float originalX = getLongitudeRange() * (normX / getWidth())
				+ getMinLong();
		float originalY = getLatitudeRange() * (normY / getHeight())
				+ getMinLat();

		DecimalFormat df = new DecimalFormat("###.##");

		return df.format(originalX) + ", " + df.format(originalY);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		System.out.println("TA PRECIONANDO");
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		System.out.println("TA RELEASED");

	}

	public String haversineFormula(Point2f p1, Point2f p2) {
		DecimalFormat df = new DecimalFormat(".####");
		double dLat = Math.toRadians(p2.getX() - p1.getX());
		double dLon = Math.toRadians(p2.getY() - p2.getY());
		double lat1 = Math.toRadians(p1.getX());
		double lat2 = Math.toRadians(p2.getY());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return "Distance is " + df.format((R * c)) + "Km";
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
	
	public ArrayList<Point2f> getNormalizedVertices() {
		return normalizedVertices;
	}

	public void setNormalizedVertices(ArrayList<Point2f> normalizedVertices) {
		this.normalizedVertices = normalizedVertices;
	}
	
}
