/**
 Singleton pattern is used in cases where we want only a single instance of a class throughout the application.

 In this example, we want a single instance of Logger class.
 So, instead of creating Logger class instances using new keyword, we use a getInstance() method.
 getInstance() method first checks if instance is null or not. If not, it returns instance.
 Otherwise, it takes a lock on Logger class (to ensure that no parallel thread calls getInstance() at the same time),
    checks again if instance is null or not.
 If so, a new instance is created and then returned.
 **/
public class SingletonPattern {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = new Logger(); // this is a new instance

        System.out.println("Logger1: " + logger1);
        System.out.println("Logger2: " + logger2);
        System.out.println("Logger3: " + logger3);
    }
}

class Logger {
    private static Logger instance;

    public static Logger getInstance() {
        if(instance == null) {
            synchronized (Logger.class) {
                if(instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }
}