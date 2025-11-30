import java.util.*;

public class StartupDemo {
    
    // Simulate application startup work
    static void initializeDatabase() {
        System.out.println("[Initializing database connections...]");
        try { Thread.sleep(200); } catch (InterruptedException e) {}
    }
    
    static void loadConfiguration() {
        System.out.println("[Loading configuration...]");
        Map config = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            config.put("key" + i, "value" + i);
        }
        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }
    
    static void initializeCache() {
        System.out.println("[Initializing cache...]");
        List cache = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            cache.add("item" + i);
        }
        try { Thread.sleep(150); } catch (InterruptedException e) {}
    }
    
    static void startWebServer() {
        System.out.println("[Starting web server...]");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }
    
    public static void main(String[] args) {
        long start = System.nanoTime();
        
        System.out.println("=== Application Starting ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        try { Thread.sleep(60000); } catch (InterruptedException e) {}

        initializeDatabase();
        loadConfiguration();
        initializeCache();
        startWebServer();
        
        long duration = (System.nanoTime() - start) / 1_000_000;
        
        System.out.println("\n=== Application Ready ===");
        System.out.println("Startup time: " + duration + "ms");
    }
}
