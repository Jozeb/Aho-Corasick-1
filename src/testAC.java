public class testAC{
	public static void main(String args[]){
		AhoCorasick sm = new AhoCorasick();
		//String[] input = {"abcd","asdd","werew","asdasd"};
		String[] input = {"he","she","his","hers"};
		sm.createTrie(input);
		sm.getFailure();
	}
}