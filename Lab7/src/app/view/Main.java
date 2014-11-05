package app.view;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import app.model.Point2f;

/**
 * Main class
 * @author ac332317
 *
 */

public class Main {

	private JFrame frmIndependentStudy;
	private JTextField textField;
	
	private JTextField urlTF;

	private ArrayList<Point2f>  worldVertices = new ArrayList<Point2f>();

	private String urlStr;

	private float minLong, maxLong, maxLat, minLat;
	
	private Canvas theCanvas = new Canvas();
	
	public static JLabel distanceLabel = new JLabel("");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmIndependentStudy.setVisible(true);
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
		frmIndependentStudy = new JFrame();
		frmIndependentStudy.setTitle("Independent Study - GIS");
		frmIndependentStudy.setBounds(100, 100, 1175, 737);
		frmIndependentStudy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIndependentStudy.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(294, 27, 500, 20);
		frmIndependentStudy.getContentPane().add(textField);
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
		frmIndependentStudy.getContentPane().add(btnNewButton);
		
	
		theCanvas.setBounds(47, 148, 507, 529);
		frmIndependentStudy.getContentPane().add(theCanvas);
		theCanvas.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("URL:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(242, 27, 42, 20);
		frmIndependentStudy.getContentPane().add(lblNewLabel);
		
		distanceLabel.setBounds(272, 123, 215, 14);
		frmIndependentStudy.getContentPane().add(distanceLabel);
		
		
		JTextPane txtpnDsfsdf = new JTextPane();
		txtpnDsfsdf.setText("The haversine formula is an important equantion in navigation, giving great-circle distances between two points on a sphere from their longitudes and latitudes. We have changed the formula so that the user can see"
				+ "the final result in Kilometers.");
		txtpnDsfsdf.setBounds(578, 182, 571, 57);
		frmIndependentStudy.getContentPane().add(txtpnDsfsdf);
		try{
		JLabel imgLabel = new JLabel("New label");
		imgLabel.setBounds(578, 266, 571, 119);
		frmIndependentStudy.getContentPane().add(imgLabel);
		
		Image img = ImageIO.read(new File("C:/Users/ac332317/git/Repository/Lab7/haversine.png"));
		Image resizedImage = 
			    img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_FAST);
		
		imgLabel.setIcon(new ImageIcon(resizedImage));
		
		
		
		}catch(Exception e){
			
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
			this.theCanvas.getNormalizedVertices().add(new Point2f(normX, normY));
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
