public class Problem { 
    public static int a = 17;
    public static void main(String[] args) {
        int b = 24;
        double c = 3.41;
        System.out.println("a = " + a);
        
        a = a + b;
        System.out.println("a = " + a);
       
        b = (int) c/2;
        System.out.println("b = " + b);
    }
}