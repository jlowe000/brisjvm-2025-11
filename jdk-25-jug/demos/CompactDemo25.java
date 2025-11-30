void main() {
    long start = System.nanoTime();
    
    // Simulate some work
    int sum = 0;
    for (int i = 0; i < 1000000; i++) {
        sum += i;
    }
    
    long duration = (System.nanoTime() - start) / 1_000_000;
    IO.println("Result: " + sum);
    IO.println("Execution time: " + duration + "ms");
    IO.println("Boilerplate lines: 0");
}
