package edu.gatech.hp;

/**
 * Provides access to the different Heated Plate simulators.
 *
 */
public interface HeatedPlateSimulator {

	int maxNumberOfIterations = 1000000;
	
	double minimumChange = 0.0001;
	
	/**
	 * Executes a simulation of a Heated Plate using the given initial conditions and returns the results as a printable String. 
	 * @param d the dimension of the square lattice
	 * @param l the left edge temperature
	 * @param r the right edge temperature
	 * @param t the top edge temperature
	 * @param b the bottom edge temperature
	 * @return the result of the simulation as a printable String
	 */
	public String execute(int d, int l, int r, int t, int b);
}
