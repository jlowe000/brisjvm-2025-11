#!/bin/sh

echo ""
echo "ConcurrencyDemo with JDK 11"
echo ""
${JAVA_11_HOME}/bin/javac ConcurrencyDemo11.java
time ${JAVA_11_HOME}/bin/java ConcurrencyDemo11
echo "
            ExecutorService executor = Executors.newFixedThreadPool(3);
            try {
                Future userF = executor.submit(() -> fetchUser(customerId));
                Future ordersF = executor.submit(() -> fetchOrders(customerId));
                Future paymentsF = executor.submit(() -> fetchPayments(customerId));
                
                String user = (String)userF.get();
                String orders = (String)ordersF.get();
                String payments = (String)paymentsF.get();
                
                if (i % 20 == 0) {
                    System.out.println("Customer " + customerId + ": " + 
                        user + ", " + orders + ", " + payments);
                }
            } finally {
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            }
"

# JDK 25
echo ""
echo "ConcurrencyDemo with JDK 25"
echo ""
time ${JAVA_25_HOME}/bin/java --enable-preview ConcurrencyDemo25.java
echo "
        try (var scope = StructuredTaskScope.open()) {
            var userT = scope.fork(() -> fetchUser(customerId));
            var ordersT = scope.fork(() -> fetchOrders(customerId));
            var paymentsT = scope.fork(() -> fetchPayments(customerId));
            
            scope.join();
            
            String user = userT.get();
            String orders = ordersT.get();
            String payments = paymentsT.get();
            
            if (i % 20 == 0) {
                IO.println("Customer " + customerId + ": " + 
                    user + ", " + orders + ", " + payments);
            }
        }
"
