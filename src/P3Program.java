/* Author: Jeremy Oliver
 * Date: 2-4-2018
 * 
 * Description: This program calculates the amount of time it takes to fill and increment
 * 				both arrays and ArrayLists. It prints the results out to an output file.

Limitations: - This program continues to write to the output file every time it runs without ever deleting. 
             - I can't figure out the TimeUnit.MILLISECONDS.toSeconds. I have been trying for hours. 
               Every time I try and convert it the right way, the result is 0. Infuriating.
             - If you enter a small number, the output shows the time as 0 because it is formatted to only 
               go out four decimal places. Pick a large number.
		
*/


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.util.*;

public class P3Program {
	ThreadMXBean threadTimer;
	private int[] intArray; 					// declare an int array to fill and test
	private float[] floatArray;                 // declare a float array to fill and test
	private ArrayList<Integer> intArrayList;    // declare Integer ArrayList to fill and test
	private ArrayList<Float> floatArrayList;    // declare Float ArrayList to fill and test
	private Random randomGenerator;             // declare a random generator to randomly fill arrays
	DecimalFormat realFormatter;                // declare decimal formatter to use to format output
 	double startWall,                           // start wall clock time
	        stopWall,                           // stop wall clock time
	       startCPU1,                           // start CPU time for arrays
	        stopCPU1,                           // stop CPU time for arrays
	       startCPU2,                           // start CPU time for ArrayLists
	        stopCPU2;                           // stop CPU time for ArrayLists
 	private static int run;                     // run counter for looping through
 	private int listLength;              // user length of arrays
    private Scanner keyboard;            // declare keyboard as scanner
    private static int runLength;               // user number for amount of times to loop
 	
 	BufferedWriter bw = null;                  
	FileWriter fw = null;
 	
	// Constructor
	public P3Program () {
	randomGenerator = new Random ();
	realFormatter = new DecimalFormat("0000.0000");
	run = 1;
	threadTimer = ManagementFactory.getThreadMXBean();
	keyboard = new Scanner(System.in);
	}
	
	//Method to explain program and get user input
	public void welcome() {
		System.out.println("Welcome to my program! This program will fill arrays and ArrayLists and\n"
				            + "increment them. The program times how long it takes to do this, and prints the\n"
							+ "results to an output file\n\n");
		System.out.print("Enter the number of elements you would like to fill the arrays with: ");
		listLength = keyboard.nextInt();
		System.out.print("Enter the number of times you would like to loop through the program: ");
		runLength = keyboard.nextInt();
		System.out.println("\nWorking.....");
		
	}
	
	// Method to fill arrays
	public void fillArrays() {
		
		// Initialize arrays with user input
		intArray = new int[listLength];
		floatArray = new float[listLength];
		intArrayList = new ArrayList<Integer>(listLength);
		floatArrayList = new ArrayList<Float>(listLength);
	
	try {	
		fw = new FileWriter("P3Output.txt", true);
		bw = new BufferedWriter(fw);
		
	// Get the start times of the clocks
		startWall = System.currentTimeMillis();
		startCPU1 = threadTimer.getCurrentThreadCpuTime();
	// Loop through and fill an array with 100,000 random ints
		for (int i=0; i < listLength; i++) {
			intArray[i] = randomGenerator.nextInt(10000000); 
		} 
	// Stop the clocks
		  stopWall = System.currentTimeMillis();
		  stopCPU1 = threadTimer.getCurrentThreadCpuTime();
    // Empty the array list 
		Arrays.fill(intArray, 0);
	// Print out the results 
		  bw.write("Run number: " + run + "\n"
				  + "Fill the list: Number of elements: " + listLength + "\n" 
				  + "int array - wall clock:        "
				  + realFormatter.format((stopWall - startWall) / 1000)+ " seconds\n");
	// start clock for filling ArrayList
		startWall = System.currentTimeMillis();
	    startCPU2 = threadTimer.getCurrentThreadCpuTime();
	// Loop through and fill ArrayList with 100,000 random ints
		for (int i=0; i < listLength; i++) {
			intArrayList.add(randomGenerator.nextInt(10000000));
		} 
	// Stop time for the clocks for the ArrayList  
		  stopWall = System.currentTimeMillis();
		  stopCPU2 = threadTimer.getCurrentThreadCpuTime();
    // empty the ArrayList  
		  intArrayList.clear();
    // Print out the results of filling the ArrayList, and the CPU times
		  bw.write("int ArrayList - wall clock:    "
				  + realFormatter.format((stopWall-startWall) / 1000) + " seconds"
				  + "\n\nint array - CPU time:          "
				  + realFormatter.format((stopWall-startWall) / 1000) + " seconds"
				  + "\nint ArrayList - CPU time:      "
				  + realFormatter.format((stopCPU2-startCPU2) / 1000000000) + " seconds\n\n");
   // Start time for filling float array
		startWall = System.currentTimeMillis();
		startCPU1 = threadTimer.getCurrentThreadCpuTime(); 
   // fill array with random floats
		 for (int i = 0; i < listLength; i++) {
			 floatArray[i] = randomGenerator.nextFloat();
		 } 
		  stopWall = System.currentTimeMillis();
		  stopCPU1 = threadTimer.getCurrentThreadCpuTime();
	// Empty the array list 
		Arrays.fill(floatArray, 0);
	// Print out the results of float
		bw.write("float array - wall clock:      "
				  + realFormatter.format((stopWall - startWall) / 1000) + " seconds\n");	
	// Start clock for filling ArrayList Floats
		startWall = System.currentTimeMillis();
	    startCPU2 = threadTimer.getCurrentThreadCpuTime();
	// fill ArrayList with random float
		for (int i = 0; i < listLength; i++) {
			floatArrayList.add(randomGenerator.nextFloat());
		}
		  stopWall = System.currentTimeMillis();
		  stopCPU2 = threadTimer.getCurrentThreadCpuTime();
	// empty the ArrayList  
		  floatArrayList.clear();
    // Print out remaining results of float		  
		  bw.write("float ArrayList - wall clock:  "
				  + realFormatter.format((stopWall - startWall) / 1000)  + " seconds\n"
				  + "\nfloat array - CPU time:        "
				  + realFormatter.format((stopCPU1 - startCPU1) / 1000000000) + " seconds\n"
				  + "float ArrayList - CPU time:    "
				  + realFormatter.format((stopCPU2 - startCPU2) / 1000000000) + " seconds\n\n");
} catch (IOException e) {
	e.printStackTrace();
} finally {

	try {

		if (bw != null)
			bw.close();

		if (fw != null)
			fw.close();

	} catch (IOException ex) {

		ex.printStackTrace();

	}
}
	}
	
	// Method to increment arrays
	public void incrementArrays() {
		
	// Initialize arrays with user input
		intArray = new int[listLength];
		floatArray = new float[listLength];
		intArrayList = new ArrayList<Integer>(listLength);
		floatArrayList = new ArrayList<Float>(listLength);
		
		try {	
			fw = new FileWriter("P3Output.txt", true);
			bw = new BufferedWriter(fw);
	// Get the start times of the clocks
			startWall = System.currentTimeMillis();
			startCPU1 = threadTimer.getCurrentThreadCpuTime();
	// Loop through and fill an array with 100,000 random ints
				for (int i=0; i < listLength; i++) {
					intArray[i] = randomGenerator.nextInt(10000000);
					intArray[i] += 1; 
				} 
	// Stop the clocks
			stopWall = System.currentTimeMillis();
			stopCPU1 = threadTimer.getCurrentThreadCpuTime();
	// Empty the array list 
			Arrays.fill(intArray, 0);
	// Print out the results 
			bw.write("Increment elements in the list: \n" 
						  + "int array - wall clock:        "
						  + realFormatter.format((stopWall - startWall) / 1000) + " seconds\n");
	// start clock for filling ArrayList
		startWall = System.currentTimeMillis();
		startCPU2 = threadTimer.getCurrentThreadCpuTime();
	// Loop through and fill ArrayList with 100,000 random ints
			    for (int i=0; i < listLength; i++) {
					intArrayList.add(randomGenerator.nextInt(10000000));
					Integer value = intArrayList.get(i);
					value += 1;
					intArrayList.set(i, value);
			    }
	// Stop time for the clocks for the ArrayList  
		stopWall = System.currentTimeMillis();
		stopCPU2 = threadTimer.getCurrentThreadCpuTime();
	// empty the ArrayList  
		intArrayList.clear();
	// Print out the results of filling the ArrayList, and the CPU times
				  bw.write("int ArrayList - wall clock:    "
						  + realFormatter.format((stopWall - startWall) / 1000) + " seconds\n"
						  + "\nint array - CPU time:          "
						  + realFormatter.format((stopCPU1 - startCPU1) / 1000000000) + " seconds\n"
						  + "int ArrayList - CPU time:      "
						  + realFormatter.format((stopCPU2 - startCPU2) / 1000000000) + " seconds\n\n");
	// Start time for filling float array
		startWall = System.currentTimeMillis();
		startCPU1 = threadTimer.getCurrentThreadCpuTime(); 
	// fill array with random floats
				 for (int i = 0; i < listLength; i++) {
					 floatArray[i] = randomGenerator.nextFloat();
					 floatArray[i] += 1;
				 } 
	// stop time for filling float array
		stopWall = System.currentTimeMillis();
		stopCPU1 = threadTimer.getCurrentThreadCpuTime();
	// Empty the array list 
		Arrays.fill(floatArray, 0);
	// Print out the results of float
		bw.write("float array - wall clock:      "
				+ realFormatter.format((stopWall - startWall) / 1000) + " seconds\n");	
	// Start clock for filling ArrayList Floats
		startWall = System.currentTimeMillis();
		startCPU2 = threadTimer.getCurrentThreadCpuTime();
	// fill ArrayList with random float
				for (int i = 0; i < listLength; i++) {
					floatArrayList.add(randomGenerator.nextFloat());
					Float value = floatArrayList.get(i);
					value += 1;
					floatArrayList.set(i, value);
				}
	// stop time for filling array with float
		stopWall = System.currentTimeMillis();
		stopCPU2 = threadTimer.getCurrentThreadCpuTime();
	// empty the ArrayList  
		floatArrayList.clear();
	// Print out remaining results of float		  
				  bw.write("float ArrayList - wall clock:  "
						  + realFormatter.format((stopWall - startWall) / 1000) + " seconds\n"
						  + "\nfloat array - CPU time:        "
						  + realFormatter.format((stopCPU1 - startCPU1) / 1000000000) + " seconds\n"
						  + "float ArrayList - CPU time:    "
						  + realFormatter.format((stopCPU2 - startCPU2) / 1000000000) + " seconds\n\n");
		
	}
	 catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
			}

	public static void main(String[] args) {
		
	P3Program efficiency = new P3Program();
	
	// Print the welcome message, get user inputs
	efficiency.welcome();
	
	// Run the methods equal to the number of times the user entered
	while (run <= runLength) {	
		efficiency.fillArrays();
		efficiency.incrementArrays();
	run++;
	
	}
	// Print out a message when the program is done running
	System.out.println("\nAll done! Check your output file!");
	
 } // End main
} // End class
