public class Node implements Comparable<Node> {
	public Node left;
	public Node right;
	public int freq;
	public char name;
	public String code;
	
	@Override
	public int compareTo(Node n) {
		return this.freq - n.freq;
	}
	
	public Node(char n, int f) {
		this.name = n;
		this.freq = f;
	}
}
