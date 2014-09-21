package Tpdahp;

import edu.gatech.hp.HeatedPlateSimulator;

import java.lang.Math;

public class Tpdahp implements HeatedPlateSimulator {
	
    private int count;      // counts iterations
    private double middleOld;
    private double middleNew;
    double[][] oldPlate; 
    double[][] newPlate;
    
    // Constructors for objects of class Tpdahp
    public Tpdahp()    {    }
       
    /**
	 * Creates an array to hold the plate temperatures.
	 */
    private void createArray(int d, int l, int r, int t, int b)
    {
        oldPlate = new double[d + 2][d + 2];
        newPlate = new double[d + 2][d + 2];
        
        //initialize plates
        for (int i = 1; i < (d + 1); i++) {
            oldPlate[i][0] = t;
            oldPlate[0][i] = l;
            oldPlate[i][d + 1] = b;
            oldPlate[d + 1][i] = r;
            newPlate[i][0] = t;
            newPlate[0][i] = l;
            newPlate[i][d + 1] = b;
            newPlate[d + 1][i] = r;
        }
    }
    
    /**
     * @param a plate, the plate dimensions
	 * @return the temperature of the middle of the plate
	 */
    private double getMiddleValue(double[][] aPlate, int d) {
        if (d % 2 == 0) {
            return (aPlate[(d/2)][(d/2)] + aPlate[(d/2) + 1][(d/2)] 
            + aPlate[(d/2)][(d/2) + 1] + aPlate[(d/2) + 1][(d/2) + 1])/4;            
        }
        
        else return aPlate[(d/2) + 1][(d/2) + 1];
    }
    
    /**
     * @param the plate dimensions
	 * @return true once the simulation is done, false otherwise
	 */
    private boolean done(int d) {
        // Stops loop at a fixed count
        if (count >= maxNumberOfIterations) {
            return true;
        }
               
        if ((Math.abs(middleNew - middleOld) < minimumChange) && (count > (d*d/2))){
            return true;
        }
        
        else return false;
    }
    
    /**
     * @param the plate dimensions
	 * @return the result of the simulation as a formatted string
	 */
    private String getResult(int d) {
        String result = "";

        for (int j = 1; j <= d; j++) {
            for (int i = 1; i <= d; i++) {

                result = result + String.format("%1$,.2f", newPlate[i][j]) + "  ";  
                
            }
            result += "\n";         
        }
        
        return result;
    }
    
    public synchronized String execute(int d, int l, int r, int t, int b) {
        count = 0;

        createArray(d, l, r, t, b);
        
        while (!done(d)) {
        // Modify newPlate
        for (int i = 1; i <= d; i++) {
          for (int j = 1; j <= d; j++) {
            newPlate[i][j] = (oldPlate[i + 1][j] + oldPlate[i - 1][j] +
                              oldPlate[i][j + 1] + oldPlate[i][j - 1]) / 4;         
          }
        }
        
        //Get the middle values of the plates to check if they have changed enough to continue
        middleOld = getMiddleValue(oldPlate, d);
        middleNew = getMiddleValue(newPlate, d);
        
        // Makes oldPlate = newPlate (the swap method)
        for (int i = 1; i <= d; i++) {
          for (int j = 1; j <= d; j++) {
            oldPlate[i][j] = newPlate[i][j];           
          }         
        }
        count++;  //counts iterations
        }
        
        return getResult(d);
    }
    
    @Override
    public String toString() {
        return "Tpdahp";
    }
}
