abstract class Person25 {
    private int age;
    
    public Person25(int age) {
        // Expensive initialization
        IO.println("  [Parent init started - expensive work]");
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        this.age = age;
        IO.println("  [Parent init completed] " + age);
    }

    protected void setAge(int age) {
	this.age = age;
    }

    protected int getAge() {
	return age;
    }
}

class Employee25 extends Person25 {
    public Employee25(int age) {
	setAge(age);

        // Validate BEFORE calling super - fail fast!
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        
        super(age);  // Only called if validation passes
    }
}

void main() {
    long start = System.nanoTime();
    int created = 0;
    int failed = 0;
    
    // Test with mix of valid and invalid ages
    int[] ages = {15, 20, 70};
    
    for (int age : ages) {
        try {
            new Employee25(age);
            created++;
        } catch (IllegalArgumentException e) {
            failed++;
            IO.println("Failed (parent init skipped): age " + age);
        }
    }
    
    long duration = (System.nanoTime() - start) / 1_000_000;
    IO.println("\nTotal time: " + duration + "ms");
    IO.println("Created: " + created + ", Failed: " + failed);
    IO.println("Wasted parent inits: 0");
}
