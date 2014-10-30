import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class Control extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTextField urlTF;

	JButton goBtn;

	ArrayList<Point2f> worldVertices;

	String urlStr;

	float minLong, maxLong, maxLat, minLat;

	Canvas theCanvas;

	public Control() {
		urlTF = new JTextField();
		urlTF.setColumns(40);
		add(urlTF);
		goBtn = new JButton("GO");
		goBtn.addActionListener(this);
		worldVertices = new ArrayList<Point2f>();
		add(goBtn);
		setLayout(new FlowLayout());
	}

	public Control(Canvas theCanvas) {
		this();
		this.theCanvas = theCanvas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// urlStr = urlTF.getText();
			urlStr = "http://www.albany.edu/faculty/" + "jmower/geog/gog692/"
					+ "IllinoisVertices.csv";
			System.out.println("test");
			URL url = new URL(urlStr);
			downloadVertices(url);
			findMBR();
			normalizedVertices();
			theCanvas.repaint();
		} catch (Exception error) {

		}
	}

	public void downloadVertices(URL url) {
		try {
			URLConnection uc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String longitudeString = inputLine.split(",")[0];
				String latitudeString = inputLine.split(",")[1];

				float latitudeFloat = Float.parseFloat(latitudeString);
				float longitudeFloat = Float.parseFloat(longitudeString);
				this.worldVertices.add(new Point2f(longitudeFloat,
						latitudeFloat));
			}

		} catch (Exception e) {

		}
	}

	public void findMBR() {
		this.minLat = Float.MAX_VALUE;
		this.minLong = Float.MAX_VALUE;

		this.maxLat = Float.MAX_VALUE * (-1);
		this.maxLong = Float.MAX_VALUE * (-1);

		for (Point2f p : this.worldVertices) {
			if (p.getX() < minLong) {
				minLong = p.getX();
			}
			if (p.getX() > maxLong) {
				maxLong = p.getX();
			}
			if (p.getY() < minLat) {
				minLat = p.getY();
			}
			if (p.getY() > maxLat) {
				maxLat = p.getY();
			}

		}
	}

	public void normalizedVertices() {
		float longitudeRange = this.maxLong - this.minLong;
		float latitudeRange = this.maxLat - this.minLat;

		for (Point2f p : this.worldVertices) {
			float normX = (this.minLong - p.getX()) / longitudeRange;
			float normY = (this.minLat - p.getY()) / latitudeRange;
			this.theCanvas.normalizedVertices.add(new Point2f(normX, normY));
			System.out.println("Long: " + normX + "\tLat: " + normY);
		}
		this.theCanvas.setLatitudeRange(latitudeRange);
		this.theCanvas.setLongitudeRange(longitudeRange);
		this.theCanvas.setMaxLong(maxLong);
		this.theCanvas.setMaxLat(maxLat);
		this.theCanvas.setMinLat(minLat);
		this.theCanvas.setMinLong(minLong);
	}

}
