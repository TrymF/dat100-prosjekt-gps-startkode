package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	
	public static void main(String[] args) {
		
		GPSPoint point1 = new GPSPoint(1800, 60.3881, 5.33185, 1.6);
		GPSPoint point2 = new GPSPoint(3600, 60.317211, 5.353570, 22.3);
		
		GPSData gpsData = new GPSData(2);
		
		gpsData.insertGPS(point1);
		gpsData.insertGPS(point2);
		
		gpsData.print();

		
	}
}
