import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class JFRDemo {
    
    // CPU-intensive method
    static long cpuIntensiveTask() {
        long result = 0;
        for (int i = 0; i < 10_000_000; i++) {
            result += Math.sqrt(i) * Math.sin(i);
        }
        return result;
    }
    
    // I/O-intensive method (waits but uses little CPU)
    static void ioIntensiveTask() {
        try {
            Thread.sleep(100); // Simulates I/O wait
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Mixed workload
    static void mixedWorkload() {
        for (int i = 0; i < 5; i++) {
            cpuIntensiveTask();  // High CPU usage
            ioIntensiveTask();   // Low CPU, high wait time
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== JFR Profiling Demo ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("\nRunning workload for 10 seconds...");
        System.out.println("(JFR recording in progress)\n");
        
        long startTime = System.currentTimeMillis();
        int iterations = 0;
        
        // Run for 10 seconds
        while (System.currentTimeMillis() - startTime < 10_000) {
            mixedWorkload();
            iterations++;
            
            if (iterations % 5 == 0) {
                System.out.print(".");
            }
        }
        
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("\n\nCompleted " + iterations + " iterations in " + 
            duration + "ms");
        System.out.println("\nJFR recording saved. Use 'jfr' tool to analyze.");
    }
}
