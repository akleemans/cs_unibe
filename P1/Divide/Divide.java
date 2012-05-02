import java.util.Scanner;

public class Divide {
    public static void main (String[] args) {
        int a, b, modulo;
        double result;
        
        Scanner scan = new Scanner (System.in);
        System.out.println ("This program will show the quotient of \nfirst number squared and the second number \n");
        
        System.out.print ("Enter the first integer number: ");
        a = scan.nextInt();

        System.out.print ("Enter the second integer number: ");
        b = scan.nextInt();

        result = (double) a * a / b;
        System.out.println ("Result in floating point numbers: " + result );
        
        modulo = a * a % b;
        System.out.println ("Integer result with shown rest: " + (a*a/b) + " rest " + modulo);        
    }
}