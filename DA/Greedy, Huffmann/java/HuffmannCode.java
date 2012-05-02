import java.util.PriorityQueue;


public class HuffmannCode {
	
	public Node root;
	
	public static void main(String[] args) {
		HuffmannCode encoder = new HuffmannCode();
		String s1 = "In computer science and information theory, Huffmann coding is an entropy encoding algorithm used for lossless data compression.";
		String s2 = "Huffmann coding results in a prefix code that expresses the most common characters using shorter strings of bits than are used for less common source symbols.";
		encoder.printCode(s1);
		encoder.printCode(s2);
	}
	
	void prefix (String s) {
		//build priority queue
		PriorityQueue<Node> Q = new PriorityQueue<Node>();
		
		char a = '0';
		int dist = 0;
		//count freq for each sign
		while (s.length()>0) {
			a = s.charAt(0);
			Node z = new Node(a, countOccurences(s, a));
			//System.out.printf("Neuer Knoten: "+a+"\n");
			Q.add(z);
			
			//add to distinct set
			dist ++;
			
			//remove char from String
			s = removeChar(s, a);
		}
		
		//build tree
		for (int i=1; i<dist; i++) {
			Node x = Q.poll();
			Node y = Q.poll();
			Node z = new Node('0', x.freq+y.freq);
			z.left = x;
			z.right = y;
			Q.add(z);
		}
		//last element is the root
		root = Q.poll();
	}
	
	void printCode (String s) {
		//Eingabestring
		System.out.println("\nEingabestring: "+s);
		
		//construct
		prefix(s);
		
		//set codes
		setCodes(root, "");
		
		//traverse tree
		String code = "";
		for (int i=0; i<s.length(); i++) {
			code += traverse(root, s.charAt(i));
		}
		
		System.out.println(code);
	}
	
	private String setCodes(Node n, String code) {
		if (n.left!=null) setCodes(n.left, code+"0");
		if (n.right!=null) setCodes(n.right, code+"1");
		
		if (n.left==null && n.right==null) {
			n.code=code;
			System.out.println("Keine Kinder vorhanden. Setze code: "+code+" für "+n.name);
		}
		
		return null;
	}

	private String traverse(Node n, char c) {
		//if found, return
		if (n.name==c) {
			//System.out.println(c+" gefunden!");
			return n.code;
		}
			
		if (n.left==null && n.right==null) return null;
		
		String l=traverse(n.left, c), r=traverse(n.right, c);
		if (l!=null)
			return l;
		if (r!=null)
			return r;
		
		return null;
	}
	
	public static int countOccurences(String s, char b) {
	   int counter=0;
	   for (int i=0;i<s.length();i++) {
	        if (s.charAt(i) == b) counter++;
	   }
	   return counter;
	}
	
	public static String removeChar(String s, char c) {
		   String r = "";

		   for (int i=0; i<s.length(); i++) {
		      if (s.charAt(i) != c) r += s.charAt(i);
		   }

		   return r;
		}

}
