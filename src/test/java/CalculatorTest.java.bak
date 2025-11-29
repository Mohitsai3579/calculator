import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void sqrt_test() {
        Calculator c = new Calculator("my_calc");
        double x = c.sqrt_f(4);
        // True Positve
        assertEquals(2.0,x,0.0);
        x = c.sqrt_f(5);
        assertEquals(2.23606797749979,x,0.005);

    }
    @Test
    public void factorial_test() {
        Calculator c = new Calculator("my_calc");
        int x = c.factorial(4);
        assertEquals(24,x);
        x = c.factorial(0);
        assertEquals(1,x);
        x = c.factorial(-2);
        assertEquals(0,x);
    }

    @Test
    public void power_test(){
        Calculator c = new Calculator("my_calc");

        double x = c.power(2,2);
        assertEquals(4.0,x,0.0001);
        x = c.power(10,0);
        assertEquals(1,x,0.001);
        x = c.power(5,3);
        assertEquals(125,x,0.00001);
    }

    @Test
    public void logarithmic_test(){
        Calculator c = new Calculator("my");
        double x = c.logarithm(Math.exp(4));
        assertEquals(x,4,0.005);
        x = c.logarithm(Math.exp(2));
        assertEquals(x,2,0.001);
        x = c.logarithm(1);
        assertEquals(x,0,0.0001);
        x = c.logarithm(-1);
        assertEquals(x,-1,0.001);
    }
}