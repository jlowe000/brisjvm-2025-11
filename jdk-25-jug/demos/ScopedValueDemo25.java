import java.util.concurrent.StructuredTaskScope;

void main() {
    ScopedValue userId = ScopedValue.newInstance();
    ScopedValue requestId = ScopedValue.newInstance();
    
    long start = System.nanoTime();
    
    // Simulate 1000 requests
    for (int i = 0; i < 1000; i++) {
        final int orderNum = i;
        
        ScopedValue.where(userId, "user" + i)
            .where(requestId, "req" + i)
            .run(() -> {
                if (orderNum % 100 == 0) {
                    IO.println("Processing order " + orderNum + 
                        " for user: " + userId.get() + 
                        " (request: " + requestId.get() + ")");
                }
            });
        // Auto-cleanup!
    }
    
    long duration = (System.nanoTime() - start) / 1_000_000;
    IO.println("\nTotal time: " + duration + "ms");
    IO.println("Pattern: ScopedValue (automatic cleanup)");
}
