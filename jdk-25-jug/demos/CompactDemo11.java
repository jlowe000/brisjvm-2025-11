public class CompactDemo11 {
    public static void main(String[] args) {
        long start = System.nanoTime();

        // Simulate some work
        int sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Result: " + sum);
        System.out.println("Execution time: " + duration + "ms");
        System.out.println("Boilerplate lines: 3 (class + static + main signature)");
    }
}
