import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        
        System.out.println("Starting Calculator...");

        while(flag) {
            // Updated Menu
            System.out.println("\nSelect Operation:");
            System.out.println("1. Square Root  2. Factorial  3. Logarithm  4. Power");
            System.out.println("5. Add          6. Subtract   7. Multiply   8. Divide");
            System.out.println("0. Exit");
            
            int option = sc.nextInt();
            Calculator cal = new Calculator("my_calc");
            double a, b; // reusable variables for inputs

            switch (option) {
                case 0:
                    System.out.println("Bye");
                    flag = false;
                    break;
                case 1: // Sqrt
                    System.out.println("Enter number:");
                    System.out.println(cal.sqrt_f(sc.nextDouble()));
                    break;
                case 2: // Factorial
                    System.out.println("Enter number:");
                    System.out.println(cal.factorial(sc.nextInt()));
                    break;
                case 3: // Log
                    System.out.println("Enter number:");
                    System.out.println(cal.logarithm(sc.nextDouble()));
                    break;
                case 4: // Power
                    System.out.println("Enter base and exponent:");
                    System.out.println(cal.power(sc.nextDouble(), sc.nextInt()));
                    break;
                // --- NEW CASES ---
                case 5: // Add
                    System.out.println("Enter two numbers:");
                    System.out.println(cal.add(sc.nextDouble(), sc.nextDouble()));
                    break;
                case 6: // Subtract
                    System.out.println("Enter two numbers:");
                    System.out.println(cal.subtract(sc.nextDouble(), sc.nextDouble()));
                    break;
                case 7: // Multiply
                    System.out.println("Enter two numbers:");
                    System.out.println(cal.multiply(sc.nextDouble(), sc.nextDouble()));
                    break;
                case 8: // Divide
                    System.out.println("Enter two numbers:");
                    System.out.println(cal.divide(sc.nextDouble(), sc.nextDouble()));
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}