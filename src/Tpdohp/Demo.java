package Tpdohp;

import edu.gatech.hp.AbstractDemo;
import edu.gatech.hp.HeatedPlateSimulator;

public class Demo extends AbstractDemo {
	
	@Override
	protected HeatedPlateSimulator getImplementation() {
		return new Tpdohp();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new Demo()).simulate(args);		
	}
	
	@Override
	protected String getUsageMessage() {
		return "Usage: java Tpdohp.Demo -d 3 -t 100 -b 0 -l 75 -r 50";
	}

}
