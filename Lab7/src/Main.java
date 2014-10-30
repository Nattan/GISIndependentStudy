import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.awt.FlowLayout;


public class Main {

	private JFrame frame;
	private JTextField textField;
	
	private JTextField urlTF;

	private JButton goBtn;

	private ArrayList<Point2f>  worldVertices = new ArrayList<Point2f>();

	private String urlStr;

	private float minLong, maxLong, maxLat, minLat;
	
	private Canvas theCanvas = new Canvas();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 737);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(294, 27, 500, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					urlStr = "http://www.albany.edu/faculty/" + "jmower/geog/gog692/"
							+ "IllinoisVertices.csv";
					System.out.println("test");
					URL url = new URL(urlStr);
					downloadVertices(url);
					findMBR();
					normalizedVertices();
					theCanvas.setVisible(true);
					theCanvas.repaint();
				}
				catch(Exception e){
					System.out.print(e.getMessage());
				}
			}
		});
		btnNewButton.setBounds(496, 59, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
	
		theCanvas.setBounds(294, 117, 507, 529);
		frame.getContentPane().add(theCanvas);
		
		theCanvas.setVisible(false);
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
