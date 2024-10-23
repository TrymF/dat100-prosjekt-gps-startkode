package no.hvl.dat100ptc.oppgave5;

import no.hvl.dat100ptc.TODO;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static final int MARGIN = 50; // margin on the sides

	private static final int MAXBARHEIGHT = 500; // assume no height above 500 meters

	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn (uten .csv): ");
		GPSComputer gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + 3 * N, 2 * MARGIN + MAXBARHEIGHT);

		// top margin + height of drawing area
		showHeightProfile(MARGIN + MAXBARHEIGHT);
	}

	public void showHeightProfile(int ybase) {

		int x = MARGIN; // første høyde skal tegnes ved MARGIN

		
		for (GPSPoint point : gpspoints) {
			int elevation = (int) point.getElevation();
			
			if(elevation > 0) {
				elevation = 0;
			}
			
			if (elevation > MAXBARHEIGHT) {
				elevation = MAXBARHEIGHT;
			}
			
			int x1 = x;
			int y1 = ybase;
			int x2 = x;
			int y2 = ybase - elevation;
			
			drawLine(x1, ybase, x, y2);
			
			x += 3;
		}
	}
}
		
		/*
		double maxAltitude = findMaxAltitude();
		
		for (int i = 0; i < gpspoints.length; i++) {
			double altitude = gpspoints[i].getElevation();

			if (altitude < 0) {
				altitude = 0;
			}

			int barHeight = (int) ( (altitude / maxAltitude) * MAXBARHEIGHT);
			
			int y = ybase - barHeight;
		

			drawLine(x, ybase, x, y);

			x += 3;
		}
	*/
	/*
	}
	
	private double findMaxAltitude() {
		double maxAltitude = 0;
		
		for (GPSPoint point : gpspoints) {
			double elevation = point.getElevation();
			
			if (elevation > maxAltitude) {
				maxAltitude = elevation;
			}
		
		
		for (GPSPoint point : gpspoints) {
			if (point.getElevation() > maxAltitude) {
				maxAltitude = (int) point.getElevation();
			} 
		}
		return maxAltitude > MAXBARHEIGHT ? MAXBARHEIGHT : maxAltitude;
	}
	*/
	


