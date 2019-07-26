import java.io.*;
import java.util.*;
/**
 * This class is responsible for training the Markov Model and performing viterbi on files and sentences.
 * Also includes a file tester accuracy method and console testing method. This is the heart of it all bois.
 * @author Sudharsan Balasubramani and Dhruv Uppal
 *
 */
public class HMM {

	//Unseen word penalty, string for start.
	private final double U = -100.0;
	private String start;

	//Maps for training
	private Map<String, Map<String, Double>> transitions;
	private Map<String, Map<String, Double>> observations;

	/**
	 * Model constructor
	 * @param start		String to start (usually #)
	 */
	public HMM(String start) {
		this.start = start;
		transitions = new HashMap<String, Map<String, Double>>();
		observations = new HashMap<String, Map<String, Double>>();
	}

	/**
	 * Viterbi algorithm. Pseudocode taken from Professor Pierson. Uses probabilities of observations
	 * and transitions to find the most probable hidden state. Uses a backtrack to find path.
	 * @param line		line to decode
	 * @return			list of hidden states
	 */
	public List<String> viterbi(String line) {

		Set<String> currStates = new HashSet<String>();
		Map<String, Double> currScores = new HashMap<String, Double>();
		List<Map<String, String>> predecessors = new ArrayList<Map<String, String>>();
		List<String> tagPath = new ArrayList<String>();

		currStates.add(start);
		currScores.put(start, 0.);

		String[] words = line.split(" ");

		for (int i = 0; i < words.length; i++) {
			Set<String> nextStates = new HashSet<String>();
			Map<String, Double> nextScores = new HashMap<String, Double>();

			for (String currState : currStates) {
				if (transitions.containsKey(currState)) {
					for (String nextState : transitions.get(currState).keySet()) {
						nextStates.add(nextState);
						double nextScore;
						if (observations.get(nextState) != null && observations.get(nextState).containsKey(words[i])) {
							nextScore = currScores.get(currState) + transitions.get(currState).get(nextState)
									+ observations.get(nextState).get(words[i]);
						} else {
							nextScore = currScores.get(currState) + transitions.get(currState).get(nextState) + U;
						}

						if (!nextScores.containsKey(nextState) || nextScore > nextScores.get(nextState)) {
							nextScores.put(nextState, nextScore);
							if(predecessors.size() == i) {
								Map<String, String> pred = new HashMap<String, String>();
								pred.put(nextState, currState);
								predecessors.add(pred);
							}
							else {
								predecessors.get(i).put(nextState, currState);
							}
							
						}

					}
					
				}
			}

			currStates = nextStates;
			currScores = nextScores;
		}
		
		//Find max state
		String maxState = "";
		int i = 0;
		for (String currState : currScores.keySet()) {
			if (i == 0) {
				maxState = currState;
				i++;
			} else {
				if (currScores.get(currState) > currScores.get(maxState)) {
					maxState = currState;
				}
			}
		}

		tagPath.add(maxState);
		String pred = maxState;
		
		for(int j = predecessors.size()-1; j > 0; j--) {
			tagPath.add(0, predecessors.get(j).get(pred));
			pred = predecessors.get(j).get(pred);
			
		}
		return tagPath;
	}

	
	/**
	 * Gets transition map
	 * @return		transitions
	 */
	public Map<String, Map<String, Double>> getTransitions() {
		return transitions;
	}

	/**
	 * Gets observations map
	 * @return		observations
	 */
	public Map<String, Map<String, Double>> getObservations() {
		return observations;
	}

	
	/**
	 * Trains the model with training files. First takes care of transitions,
	 * normalizes probabilities. Then does the same with observations.
	 * 
	 * @param observationFile		sentence file
	 * @param stateFile				Tag file
	 * @throws Exception			if file not found
	 */
	public void train(String observationFile, String stateFile) throws Exception {

		BufferedReader obs = null;
		BufferedReader state = null;

		String lineO;
		String lineS;

		//Transition Training
		try {
			obs = new BufferedReader(new FileReader(observationFile));
			state = new BufferedReader(new FileReader(stateFile));
		} catch (Exception e) {
			if (obs == null)
				System.err.println("Observations Training File Not Found!");
			else
				System.err.println("States Training File Not Found!");
		}

		Map<String, Double> startTags = new HashMap<String, Double>();

		Map<String, Double> transCount = new HashMap<String, Double>();

		while ((lineS = state.readLine()) != null) {
			String[] tags = lineS.split(" ");

			if (!startTags.containsKey(tags[0])) {
				startTags.put(tags[0], 1.);
				if (!transCount.containsKey(start)) {
					transCount.put(start, 1.);
				} else {
					transCount.put(start, transCount.get(start) + 1);
				}

			} else {
				startTags.put(tags[0], startTags.get(tags[0]) + 1);
				transCount.put(start, transCount.get(start) + 1);

			}

			for (int i = 0; i < tags.length - 1; i++) {
				if (!transitions.containsKey(tags[i])) {
					Map<String, Double> trans = new HashMap<String, Double>();
					trans.put(tags[i + 1], 1.);
					transitions.put(tags[i], trans);
					transCount.put(tags[i], 1.);
				} else {
					if (!transitions.get(tags[i]).containsKey(tags[i + 1])) {
						transitions.get(tags[i]).put(tags[i + 1], 1.);
						transCount.put(tags[i], transCount.get(tags[i]) + 1);
					} else {
						Double transScore = transitions.get(tags[i]).get(tags[i + 1]);
						transitions.get(tags[i]).put(tags[i + 1], transScore + 1);
						transCount.put(tags[i], transCount.get(tags[i]) + 1);
					}

				}
			}

			transitions.put(start, startTags);

		}

		//Normalization w/ Probabilities
		for (String tag : transCount.keySet()) {
			for (String trans : transitions.get(tag).keySet()) {
				transitions.get(tag).put(trans, Math.log(transitions.get(tag).get(trans) / transCount.get(tag)));
			}
		}

		state.close();

		//Observation Training
		try {
			state = new BufferedReader(new FileReader(stateFile));
		} catch (Exception e) {
			System.err.println("States Training File Not Found!");
		}

		Map<String, Double> obsCount = new HashMap<String, Double>();
		while ((lineO = obs.readLine()) != null && (lineS = state.readLine()) != null) {
			String[] tags = lineS.split(" ");
			String[] words = lineO.split(" ");

			for (int i = 0; i < tags.length; i++) {
				if (!observations.containsKey(tags[i])) {
					Map<String, Double> score = new HashMap<String, Double>();
					score.put(words[i], 1.);
					observations.put(tags[i], score);
					obsCount.put(tags[i], 1.);
				} else {
					if (!observations.get(tags[i]).containsKey(words[i])) {
						observations.get(tags[i]).put(words[i], 1.);
						obsCount.put(tags[i], obsCount.get(tags[i]) + 1);
					} else {
						observations.get(tags[i]).put(words[i], observations.get(tags[i]).get(words[i]) + 1);
						obsCount.put(tags[i], obsCount.get(tags[i]) + 1);
					}

				}
			}

		}
		//Normalization w/ probabilities
		for (String tag : obsCount.keySet()) {
			for (String o : observations.get(tag).keySet()) {
				observations.get(tag).put(o, Math.log(observations.get(tag).get(o) / obsCount.get(tag)));
			}
		}

	}

	/**
	 * Console test method, takes in a line, returns tags
	 * @param reader	A scanner object for reading lines
	 */
	public void inputTest(Scanner reader) {
		String line = "";
		reader.nextLine();

		while (!line.equals("q")) {
			System.out.println("Enter a line to decode, or 'q' to quit: ");
			line = reader.nextLine();
			if (line.equals("")) {
				System.out.println("Invalid, try again!");
			} else if (!line.equals("q")) {
				System.out.println(viterbi(line));
			}
		}
	}
	
	/**
	 * Finds the accuracy of viterbi given a file of sentences and tags to compare viterbi to.
	 * @param sentenceFile		file of sentences
	 * @param tagFile			tags corresponding to sentences
	 * @throws Exception		if files not found
	 */
	public void fileTest(String sentenceFile, String tagFile) throws Exception {
		BufferedReader sentences = null;
		BufferedReader tagList = null;
		String sentence = "";
		String tags = "";
		int total = 0;
		int fails = 0;
		
		try {
			sentences = new BufferedReader(new FileReader(sentenceFile));
			tagList = new BufferedReader(new FileReader(tagFile));
		}
		catch(Exception e){
			System.err.println("sentences or tags file not found");
		}
		
		while((tags = tagList.readLine()) != null) {
			sentence = sentences.readLine().toLowerCase();
			
			String[] tag = tags.split(" ");
			
			List<String> experimental = viterbi(sentence);
			
			for(int i = 0; i < tag.length; i++) {
				total += 1;
				
				if(!experimental.get(i).equals(tag[i])) {
					fails += 1;
				}
			}
		}
		
		tagList.close();
		sentences.close();
		
		String accuracy = ""+((double)(total-fails)/total)*100;
		accuracy = accuracy.substring(0, accuracy.indexOf(".")+3);
		
		System.out.println("Out of a total of "+ total + " word-tag pairs, viterbi made "+ fails + " mistakes." +
							"This is about a " + accuracy + "% success rate.");		
	}
	
	/**
	 * Resets the maps so the model can be retrained with another model
	 */
	public void resetModel() {
		transitions = new HashMap<String, Map<String, Double>>();
		observations = new HashMap<String, Map<String, Double>>();
	}
}
