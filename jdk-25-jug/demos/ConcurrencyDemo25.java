import java.util.concurrent.StructuredTaskScope;

void main() throws Exception {
    long start = System.nanoTime();
    
    // Process 50 customers in parallel
    for (int i = 0; i < 50; i++) {
        final int customerId = i;
        
        try (var scope = StructuredTaskScope.open()) {
            var userT = scope.fork(() -> fetchUser(customerId));
            var ordersT = scope.fork(() -> fetchOrders(customerId));
            var paymentsT = scope.fork(() -> fetchPayments(customerId));
            
            scope.join();
            
            String user = userT.get();
            String orders = ordersT.get();
            String payments = paymentsT.get();
            
            if (i % 10 == 0) {
                IO.println("Customer " + customerId + ": " + 
                    user + ", " + orders + ", " + payments);
            }
        }
    }
    
    long duration = (System.nanoTime() - start) / 1_000_000;
    IO.println("\nTotal time: " + duration + "ms");
    IO.println("Pattern: StructuredTaskScope (automatic management)");
}

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
