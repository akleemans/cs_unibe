public class StackTest {
	public static void main(String[] args) {
		Stack stack = new Stack();
		stack.push("!");
		stack.push("Welt");
		stack.push("Hallo");
		try {
			System.out.println("initial stack: "+stack);
			System.out.println("result of pop(): "+stack.pop());
			System.out.println("stack: "+stack);
			System.out.println("result of pop(): "+stack.pop());
			System.out.println("stack: "+stack);
			System.out.println("result of pop(): "+stack.pop());
			System.out.println("stack: "+stack);
			System.out.println("result of pop(): "+stack.pop());
			System.out.println("stack: "+stack);
		}catch(java.util.EmptyStackException e){
			System.out.println("pop() is not possible! Stack is empty!");
		}
	}
}