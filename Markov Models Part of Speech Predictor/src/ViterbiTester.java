import java.util.*;

/**
 * Test class written to test viterbi. This was the ice cream scenario discussed in class.
 * This took a lot of time to do. Ok maybe not, just quite tedious.
 * @author Sudharsan Balasubramani and Dhruv Uppal.
 *
 */
public class ViterbiTester {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		String line = "";
		
		HMM iceCream = new HMM("#");
		
		Map<String, Double> startTransitions = new HashMap<String, Double>();
		startTransitions.put("hot", Math.log(5.));
		startTransitions.put("cold", Math.log(5.));
		iceCream.getTransitions().put("#", startTransitions);
		
		Map<String, Double> coldTransitions = new HashMap<String, Double>();
		Map<String, Double> coldObservations = new HashMap<String, Double>();
		coldTransitions.put("cold", Math.log(1.));
		coldTransitions.put("hot", Math.log(3.));
		coldObservations.put("one", Math.log(7.));
		coldObservations.put("two", Math.log(2.));
		coldObservations.put("three", Math.log(1.));
		iceCream.getTransitions().put("cold", coldTransitions);
		iceCream.getObservations().put("cold", coldObservations);
		
		Map<String, Double> hotTransitions = new HashMap<String, Double>();
		Map<String, Double> hotObservations = new HashMap<String, Double>();
		hotTransitions.put("hot", Math.log(7.));
		hotTransitions.put("cold", Math.log(3.));
		hotObservations.put("one", Math.log(2.));
		hotObservations.put("two", Math.log(3.));
		hotObservations.put("three", Math.log(5.));
		iceCream.getTransitions().put("hot", hotTransitions);
		iceCream.getObservations().put("hot", hotObservations);
		
		while(!line.equals("q")) {
			line = reader.nextLine();
			System.out.println("Reading: "+ line);
			
			System.out.println(iceCream.viterbi(line));
		}
		
		reader.close();
	}

}
