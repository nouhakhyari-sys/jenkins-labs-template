/**
 * A very small calculator, built and tested by Jenkins.
 */
public class Calculator {

    public int add(int a, int b) {
        // Jenkins can switch on a fake bug with the SIMULATE_BUG parameter.
        if (Boolean.getBoolean("simulateBug")) {
            return a + b + 1;
        }
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("Calculator is running!");
        System.out.println("2 + 3 = " + calc.add(2, 3));
        System.out.println("4 x 5 = " + calc.multiply(4, 5));
    }
}
