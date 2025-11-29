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

	@Test
    public void add_test(){
        Calculator c = new Calculator("test");
        assertEquals(15.0, c.add(10, 5), 0.0);
        assertEquals(-5.0, c.add(-10, 5), 0.0);
    }

    @Test
    public void subtract_test(){
        Calculator c = new Calculator("test");
        assertEquals(5.0, c.subtract(10, 5), 0.0);
        assertEquals(-15.0, c.subtract(-10, -5), 0.0);
    }

    @Test
    public void multiply_test(){
        Calculator c = new Calculator("test");
        assertEquals(50.0, c.multiply(10, 5), 0.0);
        assertEquals(0.0, c.multiply(10, 0), 0.0);
    }

    @Test
    public void divide_test(){
        Calculator c = new Calculator("test");
        assertEquals(2.0, c.divide(10, 5), 0.0);
        assertEquals(Double.NaN, c.divide(10, 0), 0.0); // Test division by zero
    
    }
}