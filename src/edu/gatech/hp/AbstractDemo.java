package edu.gatech.hp;


public abstract class AbstractDemo {
	
	/**
	 * @return a HeatedPlateSimulator implementation
	 */
	protected abstract HeatedPlateSimulator getImplementation();
	
	/**
	 * @return a usage message
	 */
	protected abstract String getUsageMessage();
	
	/**
	 * Performs a HeatedPlate simulation using the given input
	 * where d is a positive integer value greater than zero and
	 * l, t, r, and b are any integer between zero and one hundred, inclusive
	 * @param args -d # -l # -r # -t # -b #
	 */
	protected void simulate(String[] args) {
		
		if(args.length != 10) { // print usage
			System.out.print(getUsageMessage());
			return;
		}
		
		// initialize input
		Integer d = null;
		Integer l = null;
		Integer r = null;
		Integer t = null;
		Integer b = null;
		
		// parse arguments
		for(int i = 0; i<10; i++) {
			if("-d".equals(args[i])) {
				d = Integer.parseInt(args[++i]);
			} else if("-l".equals(args[i])) {
				l = Integer.parseInt(args[++i]);
			} else if("-r".equals(args[i])) {
				r = Integer.parseInt(args[++i]);
			} else if("-t".equals(args[i])) {
				t = Integer.parseInt(args[++i]);
			} else if("-b".equals(args[i])) {
				b = Integer.parseInt(args[++i]);
			}
		}
		
		if(d==null||l==null||r==null||t==null||b==null) { // print usage
			System.out.print(getUsageMessage());
			return;
		}
		
		if(d<=0) {
			throw new IllegalArgumentException();
		}
		
		if(l<0 || l>100) {
			throw new IllegalArgumentException();
		}
		
		if(r<0 || r>100) {
			throw new IllegalArgumentException();
		}
		
		if(t<0 || t>100) {
			throw new IllegalArgumentException();
		}
		
		if(b<0 || b>100) {
			throw new IllegalArgumentException();
		}
				
		// instantiate simulator
		HeatedPlateSimulator simulator = getImplementation();
				
		// perform simulation
		String result = simulator.execute(d, l, r, t, b);
				
		// print result
		System.out.print(result);

	}

}
