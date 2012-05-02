public class B {
	public static void main(String args []) {
		// create a new object a1 of the class A (value 3)
		A a1 = new A();
		// create a new object a2 of the class A (value 3)
		A a2 = new A();
		// increment the value of a1 by 1 (new value 4)
		a1.increment();
		System.out.println(a1+"/"+a2);
		// give all the settings of a1 to a2
		a2 = a1;
		// increment the value of a2 by one (a2=a1, so the same for a1)
		a2.increment();
		System.out.println(a1+"/"+a2);
		
		// create a String s1 containing the uppercase word ROCK
		String s1 = "ROCK";
		// create a String s2 and give it the same value (ROCK)
		String s2 = s1;
		// change the word "ROCK" in s2 to "rock"
		s2 = s2.toLowerCase();
		System.out.println(s1+"/"+s2);
		
		// create an int j with the value 1
		int j=1;
		// create an int i and give it the value of j 
		int i=j;
		// increment the value of j by one
		j++;
		System.out.println(j+"/"+i);
	}
}