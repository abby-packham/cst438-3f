package cst438hw2.domain;

public class TempAndTime {
	public double temp;
	public long time;
	public int timezone;
	
	public TempAndTime(double temp, long time, int timezone){
		this.temp = temp;
		this.time = time;
		this.timezone = timezone;
	}
	
	public double getTemp() {
		return this.temp;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public int getTimezone() {
		return this.timezone;
	}
 }
