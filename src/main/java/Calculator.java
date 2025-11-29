import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.Math;
public class Calculator {
    String name;
    private final static Logger logger = LogManager.getLogger();

    Calculator(String name){
        this.name = name;
        logger.info("[CALCULATOR "+this.name+" CREATED]");
    }

    public double sqrt_f(double x){
        logger.info("[SQRT_FN] : "+x);
        double answer = 0.0;
	if(x>=0)
		answer = Math.sqrt(x);
        logger.info("[SQRT_FN - RESULT] : "+answer);
        return answer;
    }
    public double logarithm(double x){
        logger.info("[LOG] : "+x);
        if(x<=0){
            logger.info("[LOG - RESULT] : INVALID_INPUT");
            return -1;
        }
        logger.info("[LOG - RESULT] : "+Math.log(x));
        return Math.log(x);
    }
    public double power(double x,int y){
        logger.info("[POWER] : "+x+", "+y);
        double answer =1.0;
        if(y==0) {
            logger.info("[POWER - RESULT] : "+answer);
            return answer;
        }
        // Simplified power for negative exponents
        if(y < 0){
             answer = Math.pow(x,y);
             logger.info("[POWER - RESULT] : "+answer);
             return answer;
        }
        for(int i=0;i<y;i++)
            answer *= x;
        logger.info("[POWER - RESULT] : "+answer);
        return answer;
    }
    public int factorial(int x){
        int fact = 1;
        logger.info("[FACTORIAL] : "+x);
        if(x<0){
            logger.info("[FACTORIAL - RESULT] : 0");
            return 0;
        }
        if(x<=1) {
            logger.info("[FACTORIAL - RESULT] : "+fact);
            return fact;
        }
        for(int i=2;i<=x;i++)
            fact*=i;
        logger.info("[FACTORIAL - RESULT] : "+fact);
        return fact;
    }
    
    // --- New Arithmetic Operations ---
    public double add(double x, double y){
        logger.info("[ADD] : "+x+", "+y);
        double answer = x + y;
        logger.info("[ADD - RESULT] : "+answer);
        return answer;
    }
    
    public double subtract(double x, double y){
        logger.info("[SUBTRACT] : "+x+", "+y);
        double answer = x - y;
        logger.info("[SUBTRACT - RESULT] : "+answer);
        return answer;
    }
    
    public double multiply(double x, double y){
        logger.info("[MULTIPLY] : "+x+", "+y);
        double answer = x * y;
        logger.info("[MULTIPLY - RESULT] : "+answer);
        return answer;
    }
    
    public double divide(double x, double y){
        logger.info("[DIVIDE] : "+x+", "+y);
        if(y == 0.0){
            logger.info("[DIVIDE - RESULT] : DIVIDE_BY_ZERO");
            return Double.NaN; 
        }
        double answer = x / y;
        logger.info("[DIVIDE - RESULT] : "+answer);
        return answer;
    }
}