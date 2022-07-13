package learn.spring.ioc.cycle;

/**
 * Logger.
 */
public class Logger {
    
    public void recordBefore(){
        System.out.println("recordBefore");
    }
    
    public void recordAfter(){
        System.out.println("recordAfter");
    }
    
}