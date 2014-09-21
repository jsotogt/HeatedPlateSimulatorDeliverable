package Tpdohp;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.hp.HeatedPlateSimulator;

/**
 * Performs a Heated Plate simulation using a grid of LatticePoint objects.
 */
public class Tpdohp implements HeatedPlateSimulator {
	
	// edge temperatures
	private LatticePoint topEdge;
	private LatticePoint bottomEdge;
	private LatticePoint rightEdge;
	private LatticePoint leftEdge;
	
	// points to first lattice in the grid
	private List<LatticePoint> firstColumn;
	
	// dimension of the square lattice
	private Integer gridDimension;
	
	// performed iterations total
	private Integer numberOfIterations;
	
	public Tpdohp() {

	}
	
	
	/**
	 * @return a List with lattice points of the first row
	 */
	private List<LatticePoint> createFirstRow() {
		List<LatticePoint> firstRow = new ArrayList<LatticePoint>(gridDimension);
		
		for(int i=0; i<gridDimension; i++) {
			// construct lattice
			LatticePoint latticePoint = new LatticePoint();			
			if(i>0 && i<gridDimension - 1) { // middle column
				latticePoint.leftNeighbor = firstRow.get(i-1);
				latticePoint.topNeighbor = topEdge;
				latticePoint.rightNeighbor = null;
				latticePoint.bottomNeighbor = null;
				firstRow.get(i-1).rightNeighbor = latticePoint;
			} else if(i==0 && gridDimension == 1) { // first lattice & and only lattice
				latticePoint.leftNeighbor = leftEdge;
				latticePoint.topNeighbor = topEdge;
				latticePoint.rightNeighbor = rightEdge;
				latticePoint.bottomNeighbor = bottomEdge;
			}else if(i==0) { // first lattice
				latticePoint.leftNeighbor = leftEdge;
				latticePoint.topNeighbor = topEdge;
				latticePoint.rightNeighbor = null;
				latticePoint.bottomNeighbor = null;
			} else if(i==gridDimension - 1) { // last column
				latticePoint.leftNeighbor = firstRow.get(i-1);
				latticePoint.topNeighbor = topEdge;
				latticePoint.rightNeighbor = rightEdge;
				latticePoint.bottomNeighbor = null;
				firstRow.get(i-1).rightNeighbor = latticePoint;
			}
			firstRow.add(latticePoint);
		}
		return firstRow;
	}
	
	/**
	 * @return a List with lattice points of a middle row
	 */
	private List<LatticePoint> createMiddleRow(List<LatticePoint> previousRow) {
		List<LatticePoint> currentRow = new ArrayList<LatticePoint>(gridDimension);
		
		for(int i=0; i<gridDimension; i++) {
			// construct lattice
			LatticePoint latticePoint = new LatticePoint();			
			if(i>0 && i<gridDimension - 1) { // middle column
				latticePoint.leftNeighbor = currentRow.get(i-1);
				latticePoint.topNeighbor = previousRow.get(i);
				latticePoint.rightNeighbor = null;
				latticePoint.bottomNeighbor = null;
				currentRow.get(i-1).rightNeighbor = latticePoint;				
				previousRow.get(i).bottomNeighbor = latticePoint;
			} else if(i==0) { // first column
				latticePoint.leftNeighbor = leftEdge;
				latticePoint.topNeighbor = previousRow.get(i);
				latticePoint.rightNeighbor = null;
				latticePoint.bottomNeighbor = null;
				previousRow.get(i).bottomNeighbor = latticePoint;
			} else if(i==gridDimension - 1) { // last column
				latticePoint.leftNeighbor = currentRow.get(i-1);
				latticePoint.topNeighbor = previousRow.get(i);
				latticePoint.rightNeighbor = rightEdge;
				latticePoint.bottomNeighbor = null;
				currentRow.get(i-1).rightNeighbor = latticePoint;				
				previousRow.get(i).bottomNeighbor = latticePoint;				
			}
			currentRow.add(latticePoint);
		}
		return currentRow;
	}
	
	/**
	 * @return a List with lattice points of the last row
	 */
	private List<LatticePoint> createLastRow(List<LatticePoint> previousRow) {
		List<LatticePoint> lastRow = new ArrayList<LatticePoint>(gridDimension);
		
		for(int i=0; i<gridDimension; i++) {
			// construct lattice
			LatticePoint latticePoint = new LatticePoint();			
			if(i>0 && i<gridDimension - 1) { // middle column
				latticePoint.leftNeighbor = lastRow.get(i-1);
				latticePoint.topNeighbor = previousRow.get(i);
				latticePoint.rightNeighbor = null;
				latticePoint.bottomNeighbor = bottomEdge;
				lastRow.get(i-1).rightNeighbor = latticePoint;				
				previousRow.get(i).bottomNeighbor = latticePoint;
				
			} else if(i==0) { // first column
				latticePoint.leftNeighbor = leftEdge;
				latticePoint.topNeighbor = previousRow.get(i);
				latticePoint.rightNeighbor = null;
				latticePoint.bottomNeighbor = bottomEdge;
				previousRow.get(i).bottomNeighbor = latticePoint;
			} else if(i==gridDimension - 1) { // last column
				latticePoint.leftNeighbor = lastRow.get(i-1);
				latticePoint.topNeighbor = previousRow.get(i);
				latticePoint.rightNeighbor = rightEdge;
				latticePoint.bottomNeighbor = bottomEdge;
				lastRow.get(i-1).rightNeighbor = latticePoint;				
				previousRow.get(i).bottomNeighbor = latticePoint;				
			}
			
			lastRow.add(latticePoint);
		}
		return lastRow;
	}
	
	/**
	 * Creates the lattice grid based on the gridDimension value.
	 */
	private void createGrid() {
		// used to create rows
		List<LatticePoint> currentRow = null;
		List<LatticePoint> previousRow = null;
		
		// create rows
		for(int i=0; i<gridDimension; i++) {
			if(i>0 && i<gridDimension - 1) {
				currentRow = createMiddleRow(previousRow);
			} else if (i == 0) {
				currentRow = createFirstRow();
			} else if(i == gridDimension - 1) {
				currentRow = createLastRow(previousRow);
			}
			// store reference to first column
			firstColumn.add(currentRow.get(0));
			// move to next row
			previousRow = currentRow;			
		}
	}
	
	/**
	 * Recalculates temperature value of each lattice in the grid based on the
	 * temperature value of it's four neighbors.
	 */
	private void performIteration() {
		for(LatticePoint latticePoint : firstColumn) {
			for(int i = 0; i < gridDimension; i++) {
				latticePoint.calculateNewTemperatureValue();
				latticePoint = latticePoint.rightNeighbor;
			}
		}
	}
	
	/**
	 * Stores the current temperature value in the previous temperature value for all the
	 * lattice points in the grid.
	 */
	private void persistSimulation() {
		for(LatticePoint latticePoint : firstColumn) {
			for(int i = 0; i < gridDimension; i++) {
				latticePoint.persistCurrentTemperature();
				latticePoint = latticePoint.rightNeighbor;
			}
		}
	}
	
	/**
	 * @return the result of the simulation as a formatted string
	 */
	private String getResult() {
		String result = "";
		
		for(LatticePoint latticePoint : firstColumn) {
			for(int i = 0; i < gridDimension; i++) {
				String temperature = latticePoint.getFormattedTemperature(); 
				result += (i < gridDimension - 1)? temperature + "\t" : temperature + "\n";
				latticePoint = latticePoint.rightNeighbor;
			}
		}
		
		return result;
	}
	
	/**
	 * @return true once the simulation is done, false otherwise
	 */
	private boolean done() {
		
		if(numberOfIterations < 1) {
			return false;
		}
		
		if(numberOfIterations >= maxNumberOfIterations) {
			return true;
		}
		
		for(LatticePoint latticePoint : firstColumn) {
			for(int i = 0; i < gridDimension; i++) {
				if(latticePoint.temperatureChange >= minimumChange) {
					return false;
				}
				latticePoint = latticePoint.rightNeighbor;
			}
		}
		
		return true;
	}
	

	@Override
	public synchronized String execute(int d, int l, int r, int t, int b) {
		// define grid dimension
		gridDimension = d;
		
		// define edge values
		leftEdge = new LatticePoint(l);
		topEdge = new LatticePoint(t);
		rightEdge = new LatticePoint(r);
		bottomEdge = new LatticePoint(b);
		
		// simulations done of previous values
		leftEdge.persistCurrentTemperature();
		topEdge.persistCurrentTemperature();
		rightEdge.persistCurrentTemperature();
		bottomEdge.persistCurrentTemperature();
		
                // reset counter
                numberOfIterations = 0;
                
		// first column reference
		firstColumn = new ArrayList<LatticePoint>(gridDimension);
		
		// generate grid
		createGrid();
		
		// perform simulation
		while(!done()) {			
			performIteration();
			persistSimulation();
			numberOfIterations++;
		}
		
		// get result
		String result = getResult();
		
		// return result
		return result;
	}
        
        @Override
        public String toString() {
            return "Tpdohp";
        }

}

