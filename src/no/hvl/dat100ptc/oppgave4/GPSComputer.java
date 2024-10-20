package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	public double totalDistance() {

		double totalDistance = 0.0;
		for (int i = 0; i < gpspoints.length - 1; i++) {
			totalDistance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}
		return totalDistance;

	}

	public double totalElevation() {

		double totalElevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			double currentElevation = gpspoints[i].getElevation();
			double nextElevation = gpspoints[i + 1].getElevation();

			if (nextElevation > currentElevation) {
				totalElevation += (nextElevation - currentElevation);
			}
		}

		return totalElevation;
	}

	public int totalTime() {

		int startTime = gpspoints[0].getTime();
		int endTime = gpspoints[gpspoints.length - 1].getTime();

		return endTime - startTime;
	}

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		for (int i = 0; i < gpspoints.length - 1; i++) {
			double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			int time = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			speeds[i] = distance / time;
		}
		return speeds;
	}

	public double maxSpeed() {
		double maxSpeed = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			int time = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			double speed = distance / time;

			if (speed > maxSpeed) {
				maxSpeed = speed;
			}
		}
		return maxSpeed;
	}

	public double averageSpeed() {

		double totalDistance = totalDistance();
		int totalTime = totalTime();

		return totalDistance / totalTime;
	}

	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4.0;
		} else if (speedmph < 12) {
			met = 6.0;
		} else if (speedmph < 14) {
			met = 8.0;
		} else if (speedmph < 16) {
			met = 10.0;
		} else if (speedmph < 20) {
			met = 12.0;
		} else {
			met = 16.0;
		}

		double hours = secs / 3600.0;
		kcal = met * weight * hours;

		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			int secs = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			double speed = distance / secs;
			totalkcal += kcal(weight, secs, speed);
		}
		return totalkcal;
	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		    double totalTime = totalTime();                     
		    double totalDistance = totalDistance() / 1000.0;     
		    double totalElevation = totalElevation();            
		    double maxSpeed = maxSpeed();                        
		    double averageSpeed = averageSpeed();                
		    double totalKcal = totalKcal(WEIGHT);                

		    String formattedTime = GPSUtils.formatTime((int) totalTime);

		    System.out.println("==============================================");
		    System.out.println("Total Time    :   " + formattedTime);
		    System.out.printf("Total distance :   ", totalDistance);
		    System.out.printf("Total elevation:  ", totalElevation);
		    System.out.printf("Max speed      :   ", maxSpeed);
		    System.out.printf("Average speed  :  ", averageSpeed);
		    System.out.printf("Energy         :  ", totalKcal);
		    System.out.println("==============================================");
		}

	}


