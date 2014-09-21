package Tpdohp;

public class LatticePoint {

	// neighbors
	protected LatticePoint topNeighbor;
	protected LatticePoint bottomNeighbor;
	protected LatticePoint rightNeighbor;
	protected LatticePoint leftNeighbor;
	
	// simulation values
	protected double currentTemperature = 0;
	protected double previousTemperature = 0;
	protected double temperatureChange = 0;
	
	protected LatticePoint() {
		
	}
	
	protected LatticePoint(double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}
	
	/**
	 * Calculates a new temperature value by averaging the temperature
	 * of the four neighbor lattice points.
	 */
	protected void calculateNewTemperatureValue() {
		currentTemperature = (leftNeighbor.previousTemperature + topNeighbor.previousTemperature 
				+ rightNeighbor.previousTemperature + bottomNeighbor.previousTemperature) / 4.0;
	}
	
	/**
	 * Stores current temperature in previous temperature value.
	 */
	protected void persistCurrentTemperature() {
		temperatureChange = Math.abs(currentTemperature - previousTemperature);
		previousTemperature = currentTemperature;
	}
	
	/**
	 * @return the current temperature as a two decimal String
	 */
	protected String getFormattedTemperature() {
		return String.format("%.2f", currentTemperature);
	}
}
