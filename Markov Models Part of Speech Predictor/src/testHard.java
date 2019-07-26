
/** 
 * Hard-coded test of the Simple Test file; ran into issues and this helped solve them 
 * Issues specifically being that our backtrack was not working in viterbi. This
 * made sure to help solve the issue. Pretty sick testing if ya ask me. Also quite tedious
 * but had to be done. Ice cream failed us man, didn't give us the data we needed! smh.
 * 
 * 
 * @author Sudharsan Balasubramani and Dhruv Uppal
 * 
 */

import java.util.*;

public class testHard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner reader = new Scanner(System.in);

		HMM hard = new HMM("#");

		Map<String, Double> startTransitions = new HashMap<String, Double>();
		startTransitions.put("DET", Math.log(2. / 10));
		startTransitions.put("PRO", Math.log(7. / 10));
		startTransitions.put("N", Math.log(1. / 10));

		Map<String, Double> PTransitions = new HashMap<String, Double>();
		PTransitions.put("DET", Math.log(3. / 4));
		PTransitions.put("N", Math.log(1. / 4));

		Map<String, Double> NTransitions = new HashMap<String, Double>();
		NTransitions.put("P", Math.log(1. / 14));
		NTransitions.put("V", Math.log(6. / 14));
		NTransitions.put(".", Math.log(7. / 14));

		Map<String, Double> VTransitions = new HashMap<String, Double>();
		VTransitions.put("P", Math.log(3. / 11));
		VTransitions.put("DET", Math.log(4. / 11));
		VTransitions.put("ADJ", Math.log(2. / 11));
		VTransitions.put("PRO", Math.log(1. / 11));
		VTransitions.put(".", Math.log(1. / 11));

		Map<String, Double> DETTransitions = new HashMap<String, Double>();
		DETTransitions.put("N", Math.log(1));

		Map<String, Double> ADJTransitions = new HashMap<String, Double>();
		ADJTransitions.put(".", Math.log(1));

		Map<String, Double> PROTransitions = new HashMap<String, Double>();
		PROTransitions.put("MOD", Math.log(1. / 8));
		PROTransitions.put("V", Math.log(4. / 8));
		PROTransitions.put("N", Math.log(3. / 8));

		Map<String, Double> MODTransitions = new HashMap<String, Double>();
		MODTransitions.put("V", Math.log(1));

		Map<String, Double> PObservations = new HashMap<String, Double>();
		PObservations.put("in", Math.log(3. / 4));
		PObservations.put("for", Math.log(1. / 4));

		Map<String, Double> DETObservations = new HashMap<String, Double>();
		DETObservations.put("the", Math.log(6. / 9));
		DETObservations.put("a", Math.log(2. / 9));
		DETObservations.put("this", Math.log(1. / 9));

		Map<String, Double> NObservations = new HashMap<String, Double>();
		NObservations.put("cave", Math.log(3. / 14));
		NObservations.put("watch", Math.log(1. / 14));
		NObservations.put("work", Math.log(1. / 14));
		NObservations.put("bark", Math.log(1. / 14));
		NObservations.put("night", Math.log(2. / 14));
		NObservations.put("trains", Math.log(3. / 14));
		NObservations.put("dog", Math.log(3. / 14));

		Map<String, Double> MODObservations = new HashMap<String, Double>();
		MODObservations.put("should", Math.log(1));

		Map<String, Double> VObservations = new HashMap<String, Double>();
		VObservations.put("hide", Math.log(1. / 11));
		VObservations.put("fast", Math.log(1. / 11));
		VObservations.put("are", Math.log(1. / 11));
		VObservations.put("held", Math.log(1. / 11));
		VObservations.put("watch", Math.log(1. / 11));
		VObservations.put("work", Math.log(2. / 11));
		VObservations.put("bark", Math.log(1. / 11));
		VObservations.put("is", Math.log(2. / 11));
		VObservations.put("trains", Math.log(1. / 11));

		Map<String, Double> ADJObservations = new HashMap<String, Double>();
		ADJObservations.put("beautiful", Math.log(1. / 2));
		ADJObservations.put("fast", Math.log(1. / 2));

		Map<String, Double> PROObservations = new HashMap<String, Double>();
		PROObservations.put("your", Math.log(1. / 8));
		PROObservations.put("my", Math.log(2. / 8));
		PROObservations.put("he", Math.log(1. / 8));
		PROObservations.put("you", Math.log(1. / 8));
		PROObservations.put("we", Math.log(3. / 8));

		Map<String, Double> STOPObservations = new HashMap<String, Double>();
		STOPObservations.put(".", Math.log(1));

		hard.getTransitions().put("#", startTransitions);
		hard.getTransitions().put("P", PTransitions);
		hard.getTransitions().put("DET", DETTransitions);
		hard.getTransitions().put("N", NTransitions);
		hard.getTransitions().put("MOD", MODTransitions);
		hard.getTransitions().put("V", VTransitions);
		hard.getTransitions().put("PRO", PROTransitions);
		hard.getTransitions().put("ADJ", ADJTransitions);

		hard.getObservations().put("P", PObservations);
		hard.getObservations().put("DET", DETObservations);
		hard.getObservations().put("N", NObservations);
		hard.getObservations().put("MOD", MODObservations);
		hard.getObservations().put("V", VObservations);
		hard.getObservations().put("PRO", PROObservations);
		hard.getObservations().put("ADJ", ADJObservations);
		hard.getObservations().put(".", STOPObservations);

//		System.out.println(hard.getTransitions());
//		System.out.println(hard.getObservations());

		System.out.println(hard.viterbi("your work is beautiful ."));
		reader.close();
	}

}
