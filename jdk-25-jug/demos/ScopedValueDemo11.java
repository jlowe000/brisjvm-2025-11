public class ScopedValueDemo11 {
    static ThreadLocal userId = new ThreadLocal<>();
    static ThreadLocal requestId = new ThreadLocal<>();
    
    static void processOrder(int orderNum) {
        if (orderNum % 100 == 0) {
            System.out.println("Processing order " + orderNum + 
                " for user: " + userId.get() + 
                " (request: " + requestId.get() + ")");
        }
    }
    
    public static void main(String[] args) {
        long start = System.nanoTime();
        
        // Simulate 1000 requests
        for (int i = 0; i < 1000; i++) {
            userId.set("user" + i);
            requestId.set("req" + i);
            
            try {
                processOrder(i);
            } finally {
                userId.remove();  // Must clean up
                requestId.remove();
            }
        }
        
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("\nTotal time: " + duration + "ms");
        System.out.println("Pattern: ThreadLocal (manual cleanup required)");
    }
}
