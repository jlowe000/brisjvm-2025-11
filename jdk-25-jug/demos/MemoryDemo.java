import java.util.*;

public class MemoryDemo {
    
    static class Customer {
        String name;
        int age;
        String email;
    }
    
    public static void main(String[] args) {
        System.out.println("Java version: " + System.getProperty("java.version"));
        
        Runtime runtime = Runtime.getRuntime();
        List<Customer> customers = new ArrayList<Customer>();
        
        // Force GC and measure
        runtime.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        
        long start = System.nanoTime();
        
        // Create 1 million customers
        for (int i = 0; i < 1_000_000; i++) {
            Customer c = new Customer();
            c.name = "Customer" + i;
            c.age = 30 + (i % 40);
            c.email = "customer" + i + "@example.com";
            customers.add(c);
        }
        
        long duration = (System.nanoTime() - start) / 1_000_000;
        
        // Force GC and measure again
        runtime.gc();
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        long memAfter = runtime.totalMemory() - runtime.freeMemory();
        
        long memUsed = (memAfter - memBefore) / 1_000_000;
        
        System.out.println("\nCreated: " + customers.size() + " customers");
        System.out.println("Creation time: " + duration + "ms");
        System.out.println("Memory used: ~" + memUsed + " MB");
        System.out.println("Per object: ~" + (memUsed * 1000 / customers.size()) + " bytes");
        System.out.println("\nExpected per object:");
        System.out.println("  JDK 11: ~38-40 bytes");
        System.out.println("  JDK 25: ~30-32 bytes");
    }
}
