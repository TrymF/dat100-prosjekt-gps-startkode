package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.TODO;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
		
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		int length = gpspoints.length;
		double[] latitudes = new double[length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		int length = gpspoints.length;
		double[] longitudes = new double[length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		return longitudes;
	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());
		
		double deltaphi = latitude2 - latitude1;
		double deltadelta = longitude2 - longitude1;

		double a = compute_a(latitude1, latitude2, deltaphi, deltadelta);
		double c = compute_c(a);
		
		return R * c;
	}
	
	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {
	
		return Math.sin(deltaphi / 2) * Math.sin(deltaphi / 2)
	            + Math.cos(phi1) * Math.cos(phi2)
	            * Math.sin(deltadelta / 2) * Math.sin(deltadelta / 2);
	}

	private static double compute_c(double a) {

		return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	}

	
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		double distance = distance(gpspoint1, gpspoint2);
		
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		
		speed = distance / secs;
		
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = "%02d:%02d:%02d";

		int hrs = secs / 3600;
		int minutes = (secs % 3600)/60;
		long sec = secs % 60;
		timestr = String.format(TIMESEP, hrs, minutes, sec);
		
		return String.format("%10s", timestr);
		
	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		
		throw new UnsupportedOperationException(TODO.method());
		
		// TODO
		
	}
}
