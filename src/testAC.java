import java.util.LinkedList;

public class testAC{
	public static void main(String args[]){
		AhoCorasick sm = new AhoCorasick(true);
		//String[] input = {"abcd","asdd","werew","asdasd"};
		String[] input = {"he","she","his","hers"};
		sm.createTrie(input);
		sm.getFailure();
		LinkedList<String> matches = sm.search("ushers");
		System.out.print("Matched " + matches + "\n");
	}
}