package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import no.hvl.dat100ptc.TODO;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 600;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = scale(MAPXSIZE, minlon, maxlon);
		ystep = scale(MAPYSIZE, minlat, maxlat);

		showRouteMap(MARGIN + MAPYSIZE);

		replayRoute(MARGIN + MAPYSIZE);

		showStatistics();
	}

	public double scale(int maxsize, double minval, double maxval) {

		double step = maxsize / (Math.abs(maxval - minval));

		return step;
	}

	public void showRouteMap(int ybase) {

		setColor(0, 255, 0);

		for (int i = 0; i < gpspoints.length; i++) {
			int x = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
			int y = ybase - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);

			drawCircle(x, y, 2);
		}

	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		setFont("Cambria", 12);

		String totalTime = String.format("Total Time     : %s", GPSUtils.formatTime(gpscomputer.totalTime()));
		String totalDistance = String.format("Total Distance : %.2f km", gpscomputer.totalDistance() / 1000);
		String totalElevation = String.format("Total Elevation: %.2f m", gpscomputer.totalElevation());
		String maxSpeed = String.format("Max Speed      : %.2f km/h", gpscomputer.maxSpeed());
		String averageSpeed = String.format("Average Speed  : %.2f km/h", gpscomputer.averageSpeed());
		String energy = String.format("Energy         : %.2f kcal", gpscomputer.totalKcal(gpscomputer.getWeight()));

		drawString(totalTime, MARGIN, TEXTDISTANCE);
		drawString(totalDistance, MARGIN, TEXTDISTANCE * 2);
		drawString(totalElevation, MARGIN, TEXTDISTANCE * 3);
		drawString(maxSpeed, MARGIN, TEXTDISTANCE * 4);
		drawString(averageSpeed, MARGIN, TEXTDISTANCE * 5);
		drawString(energy, MARGIN, TEXTDISTANCE * 5);

	}

	public void replayRoute(int ybase) {
		setColor(0, 0, 255);
		int radius = 5;

		int x = MARGIN + (int) ((gpspoints[0].getLongitude() - minlon) * xstep);
		int y = ybase - (int) ((gpspoints[0].getLatitude() - minlat) * ystep);

		int circle = fillCircle(x, y, radius);

		for (int i = 1; i < gpspoints.length; i++) {
			int newX = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
			int newY = ybase - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);

			moveCircle(circle, newX, newY);
			setSpeed(100);

		}

	}

}
