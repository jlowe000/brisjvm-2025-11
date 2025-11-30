abstract class Person11 {
    private final int age;
    
    public Person11(int age) {
        // Expensive initialization
        System.out.println("  [Parent init started - expensive work]");
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        this.age = age;
        System.out.println("  [Parent init completed] " + age);
    }
}

class Employee11 extends Person11 {
    public Employee11(int age) {
        super(age);  // MUST call first, even if validation fails
        
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
    }
}

public class ConstructorDemo11 {
    public static void main(String[] args) {
        long start = System.nanoTime();
        int created = 0;
        int failed = 0;
        
        // Test with mix of valid and invalid ages
        // int[] ages = {25, 80, 30, 15, 45, 70, 22, 10, 35, 75};
        int[] ages = {15, 20, 70};
        
        for (int age : ages) {
            try {
                new Employee11(age);
                created++;
            } catch (IllegalArgumentException e) {
                failed++;
                System.out.println("Failed (but parent init ran): age " + age);
            }
        }
        
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("\nTotal time: " + duration + "ms");
        System.out.println("Created: " + created + ", Failed: " + failed);
        System.out.println("Wasted parent inits: " + failed);
    }
}
