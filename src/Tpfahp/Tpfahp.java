package Tpfahp;

import edu.gatech.hp.HeatedPlateSimulator;

import java.lang.Math;

public class Tpfahp implements HeatedPlateSimulator {
	
	private int count;      // counts iterations
    private float middleOld;
    private float middleNew;
    float[][] oldPlate; 
    float[][] newPlate; 
    
    // Constructors for objects of class Tpfahp
    public Tpfahp()
    {    }

    private void createArray(int d, int l, int r, int t, int b) {
        oldPlate = new float[d + 2][d + 2];
        newPlate = new float[d + 2][d + 2];
        
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
    
        

        
    // Method for obtaining the middle value of the plates.
    // If d is even, this returns the average of the middle four plates
    // If d is odd, this returns the value of the middle plate
    private float getMiddleValue(float[][] aPlate, int d) {
        if (d % 2 == 0) {
            return (aPlate[(d/2)][(d/2)] + aPlate[(d/2) + 1][(d/2)] 
            + aPlate[(d/2)][(d/2) + 1] + aPlate[(d/2) + 1][(d/2) + 1])/4;            
        }
        
        else return aPlate[(d/2) + 1][(d/2) + 1];
    }
    
    //Method to check for loop exit conditions: too many loops or values haven't changed enough
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
    
    
    @Override
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
            return "Tpfahp";
        }
}
