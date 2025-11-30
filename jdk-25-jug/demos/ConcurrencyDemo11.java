import java.util.concurrent.*;

public class ConcurrencyDemo11 {
    
    static String fetchUser(int id) throws Exception {
        Thread.sleep(100);
        return "User" + id;
    }
    
    static String fetchOrders(int id) throws Exception {
        Thread.sleep(150);
        return "Orders" + id;
    }
    
    static String fetchPayments(int id) throws Exception {
        Thread.sleep(120);
        return "Payments" + id;
    }
    
    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();
        
        // Process 50 customers in parallel
        for (int i = 0; i < 50; i++) {
            final int customerId = i;
            
            ExecutorService executor = Executors.newFixedThreadPool(3);
            try {
                Future userF = executor.submit(() -> fetchUser(customerId));
                Future ordersF = executor.submit(() -> fetchOrders(customerId));
                Future paymentsF = executor.submit(() -> fetchPayments(customerId));
                
                String user = (String)userF.get();
                String orders = (String)ordersF.get();
                String payments = (String)paymentsF.get();
                
                if (i % 10 == 0) {
                    System.out.println("Customer " + customerId + ": " + 
                        user + ", " + orders + ", " + payments);
                }
            } finally {
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            }
        }
        
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("\nTotal time: " + duration + "ms");
        System.out.println("Pattern: ExecutorService (manual management)");
    }
}
