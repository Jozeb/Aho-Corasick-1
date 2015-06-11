import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;


public class AhoCorasick{
	protected Map <Integer,state> stateMap;
	protected Map <Integer,Integer> Failure;
	protected Map <Integer,LinkedList<String>> Output;
	protected int numStates;
	protected state zeroState;

	protected class state{
		private int id; 
		private char val;
		Map<Character,Integer> nextStates = new HashMap<Character,Integer>(); 
		public state(int id,char val){
			System.out.print("Creating state " + id + " for " + val + "\n");
			this.id = id;
			this.val = val;
		}
		public int getId(){
			return this.id;
		}
		public char getVal(){
			return this.val;
		}
	}
	
	public AhoCorasick(){
		stateMap = new HashMap<Integer,state>();
		Failure = new HashMap<Integer,Integer>();
		Output = new HashMap<Integer,LinkedList<String>>();
		numStates = 0;
	}

	protected void outputAdd(int id, String str){
			LinkedList<String> l;
			if (Output.containsKey(id)){
				l = Output.get(id);
			}
			else{
				l = new LinkedList<String>();
				Output.put(id,l);
			}
			l.add(str);

	}

	protected void outputAdd(int id, LinkedList<String> list){
		if(list == null || list.size() == 0){
			return;
		}
		LinkedList<String> l;
		if (Output.containsKey(id)){
			l = Output.get(id);
		}
		else{
			l = new LinkedList<String>();
			Output.put(id,l);
		}
		Iterator it = list.iterator();
		while(it.hasNext()){
			l.add((String) it.next());
		}
	}

	protected void createTrie(String[] input){
		zeroState = new state(0,'\0');
		stateMap.put(0, zeroState);
		state currState;
		for(int i=0; i<input.length; i++){
			currState = zeroState;
			System.out.print("New word " + input[i] + " current state " +  currState.getId() +"\n");
			int j = 0;
			while(true){
				if (j >= input[i].length()){
					break;
				}
				//System.out.print("Iterating " + j + " " + input[i].charAt(j) + " state \n");
				if (currState.nextStates.containsKey(input[i].charAt(j))){
					currState = stateMap.get(currState.nextStates.get(input[i].charAt(j)));
					j++;
				}
				else{
					break;
				}
			}
			while (j < input[i].length()){
				state nextState = new state(++numStates,input[i].charAt(j));
				stateMap.put(numStates, nextState);
				currState.nextStates.put(input[i].charAt(j), numStates);
				System.out.print(currState.getId() + " has next " + currState.nextStates.toString() + "\n");
				currState = nextState;
				j++;
			}
			outputAdd(currState.getId(),input[i]);
			System.out.print("Output" + Output.toString() + "\n");
		}
	}
	
	protected void getFailure(){
		state currState = zeroState;
		LinkedList<Integer> q = new LinkedList<Integer>();
		Iterator it = currState.nextStates.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			int cs = (Integer) pairs.getValue();
			Failure.put(cs,0);
			q.add(cs);
		}
		while(q.size() > 0){
			int cs = q.removeFirst();
			it = stateMap.get(cs).nextStates.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				int ns = (Integer) pairs.getValue();
				char nk = (Character) pairs.getKey();
				q.add(ns);
				int fs = Failure.get(cs);
				while (true){
					if (fs == 0 || stateMap.get(fs).nextStates.containsKey(nk))
						break;
					fs = Failure.get(fs);
				}
				if (fs == 0 && stateMap.get(fs).nextStates.get(nk) == null)
					Failure.put(ns,0);
				else	
					Failure.put(ns,stateMap.get(fs).nextStates.get(nk));
				outputAdd(ns,Output.get(Failure.get(ns)));
			}
		}
		System.out.print(Output.toString() + "\n");
		System.out.print(Failure.toString() + "\n");
	}
}