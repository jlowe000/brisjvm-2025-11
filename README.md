                          
      ____                       ____  _____
     |__  |                     |___ \|  ___|  
        | | __ ___   ____ _       __) | |___ 
        | |/ _  \ \ / / _  |     / __/|___  \ 
    /\__/ / (_| |\ V / (_| |    | |___ ___) |  
    \____/ \__,_| \_/ \__,_|    |_____|_____/      
                                               
            ____                   _  _   _____              
     # #   |__  |                 | || | |  ___|             
    #####     | | __ ___   ____ _ | || |_| |__ __   __ ___  _ __ 
     # #      | |/ _  \ \ / / _  ||__   _|  __|\ \ / // __\| '__|
    ##### /\__/ / (_| |\ V / (_| |   | | | |___ \ V / | __/| |   
     # #  \____/ \__,_| \_/ \__,_|   |_| \_____| \_/  \___||_|


## BrisJVM JUG - Java 25 Update Feature (27 Nov 2025)

### Who am I?

    \ __________________________ /
     |                          |
     | Jason Lowe               |
     | Oracle Cloud Engineering |
     | jason.lowe@oracle.com    |
     |__________________________|
    /                            \

NB: Some of the content was AI generated. All code has been compiled and was running at the point in time of the User Group event.

### Overview

Java 25 (statistics):
- was released in September 2025.
- a Long-Term Support (LTS) release with 18 JEPs.

Consider Previous Versions (Where are you at?)

| Version | Release Date |
| :------: | :------ |
| Java 24  | March, 2025  |
| Java 21  | September 2023  |
| Java 19  | September 2022  |
| Java 17  | September 2021  |
| Java 11  | September 2018 <-- (That's me) | 

---
To run the demos ... pre-requisites
- linux / cygwin environment to run shell scripts
- download of OpenJDK 11 and 25 versions (into ${ROOT_HOME})
- copy `setenv.sh.template` to `setenv.sh` and update according to your environment
- run `source setenv.sh`
- run `./run_all.sh`
---

## BrisJVM JUG - Feature Demos

### 1. Compact Source Files and Instance Main Methods (JEP 512) ✅ FINAL

**Short Description:** Write Java without class boilerplate

**Use Case:**
- eg. Quickly test an API endpoint
- eg. A one-off data transformation script.
- eg. Something small that you could do with Python or Typescript.

**Why Care At All:**
- Great for quick scripts, prototypes, and testing API integrations. Perfect for utility scripts in your dev workflow.

**Code Sample (JDK 11 vs JDK 25):**
```java
// File: TestApi.java
// OLD WAY - Full boilerplate
public class TestApi {
    public static void main(String[] args) {
        System.out.println("Testing API...");
    }
}

// NEW WAY - Compact source file
void main() {
    IO.println("Testing API...");
    var response = callApi("https://api.example.com");
    IO.println("Response: " + response);
}

String callApi(String url) {
    // Your API logic
    return "success";
}

// Run with: java TestApi.java
```

---
### 2. Module Import Declarations (JEP 511) ✅ FINAL

**Short Description:** Import entire modules with one statement

**Use Case:**
- eg. Instead of importing each class individually, import the whole module.
- eg. Simplifying code and need multiple classes eg. java.net.http.

**Why Care At All:**
- Stop writing dozens of import statements when using third-party libraries or Java modules.
- This reduces boilerplate and makes prototyping faster.
- **Warning** - @TODO What does it mean for classloading and class referencing? (Lazy Loading)

**Code Sample (JDK 11 vs JDK 25):**
```java
// ============================================
// JDK 11 WAY - Multiple individual imports
// ============================================
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.io.IOException;
import java.time.Duration;

public class ApiClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
            
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.example.com/data"))
            .GET()
            .build();
            
        HttpResponse<String> response = client.send(request, 
            BodyHandlers.ofString());
            
        System.out.println("Response: " + response.body());
    }
}


// ============================================
// JDK 25 WAY - Module import (one line!)
// ============================================
// BENEFIT: From 7 import lines to 1 import line
import module java.net.http;  // Everything from java.net.http module

public class ApiClient {
    void main() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
            
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.example.com/data"))
            .GET()
            .build();
            
        HttpResponse<String> response = client.send(request, 
            BodyHandlers.ofString());
            
        // HINT: Also notice this? System.out.println >> IO.println
        IO.println("Response: " + response.body());
    }
}
```

---
### 3. Flexible Constructor Bodies (JEP 513) ✅ FINAL

**Short Description:** Validate parameters before calling super()

**Use Case:**
- eg. You have DTOs or entities with validation rules that should be checked before calling the parent constructor.

**Why Care At All:**
- No more wasting object construction time on invalid data.
- Fail fast before expensive parent class initialisation.

**Code Sample (JDK 11 vs JDK 25):**
```java
// OLD WAY - Parent constructor runs even if validation fails
class Employee extends Person {
    Employee(String name, int age) {
        super(name, age); // Runs first, might be wasted work
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Invalid age");
        }
    }
}

// NEW WAY - Validate first, fail fast
class Employee extends Person {
    Employee(String name, int age) {
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Invalid age");
        }
        super(name, age); // Only runs if validation passes
    }
}

// Real-world usage
try {
    var emp = new Employee("John", 80); // Fails immediately
} catch (IllegalArgumentException e) {
    IO.println("Invalid employee data: " + e.getMessage());
}
```

---
### 4. Primitive Types in Patterns, instanceof, and switch (JEP 507) 🔄 THIRD PREVIEW

**Short Description:** Use primitives in switch and instanceof

**Use Case:**
- eg. Processing different types of configuration values from a properties file or API response.
- eg. Reduces the need to worry about primitives vs object types between packages.

**Why Care At All:**
- Cleaner switch statements when working with mixed primitive and object types, common in API request/response handling.

**Code Sample (JDK 11 vs JDK 25):**
```java
// Compile/run with: --enable-preview --release 25

// OLD WAY - lots of if-else and casting
Object value = config.get("timeout");
if (value instanceof Integer) {
    int timeout = (Integer) value;
    // use timeout
} else if (value instanceof Long) {
    long timeout = (Long) value;
    // use timeout
} else if (value instanceof String) {
    String timeout = (String) value;
    // parse timeout
}

// NEW WAY - clean pattern matching
Object value = config.get("timeout");
switch (value) {
    case int i -> processTimeout(i);
    case long l -> processTimeout((int)l);
    case String s -> processTimeout(Integer.parseInt(s));
    case null -> useDefaultTimeout();
    default -> throw new IllegalArgumentException("Invalid timeout");
}

// Also works with instanceof
if (value instanceof int i) {
    IO.println("Timeout is: " + i);
}
```

---
### 5. Scoped Values (JEP 506) ✅ FINAL

**Short Description:** Thread-safe data sharing without ThreadLocal hassles

**Use Case:**
- eg. Your REST controller needs to pass user context to service layers and repositories without adding context parameters everywhere.
- eg. Typical for application context ie API-key or context id for business processes

**Why Care At All:**
- Perfect for passing request context (user ID, tenant ID, trace ID) through your application layers without polluting method signatures.
- Better than ThreadLocal, especially with virtual threads.

**Code Sample:**
```java
// Define the scoped value
public class RequestContext {
    public static final ScopedValue<UserContext> CURRENT_USER = 
        ScopedValue.newInstance();
    
    public record UserContext(String userId, String tenantId, String traceId) {}
}

// In your REST controller
@RestController
public class OrderController {
    
    @PostMapping("/orders")
    public Order createOrder(@RequestBody OrderRequest request,
                            @RequestHeader("X-User-Id") String userId,
                            @RequestHeader("X-Tenant-Id") String tenantId) {
        
        var context = new UserContext(userId, tenantId, UUID.randomUUID().toString());
        
        // Bind the context for this request scope
        return ScopedValue.where(RequestContext.CURRENT_USER, context)
            .call(() -> orderService.createOrder(request));
    }
}

// In your service layer - no context parameter needed!
@Service
public class OrderService {
    public Order createOrder(OrderRequest request) {
        // Access context anywhere in the call chain
        var context = RequestContext.CURRENT_USER.get();
        
        auditLog.log("Order created by user: " + context.userId());
        
        return repository.save(request, context.tenantId());
    }
}

// In your repository - still no context parameter!
@Repository
public class OrderRepository {
    public Order save(OrderRequest request, String tenantId) {
        var context = RequestContext.CURRENT_USER.get();
        // Use trace ID for distributed tracing
        logger.info("Trace: {} - Saving order", context.traceId());
        // ... save logic
    }
}
```

---
### 6. Structured Concurrency (JEP 505) 🔄 FIFTH PREVIEW

**Short Description:** Manage related concurrent tasks as one unit

**Use Case:**
- eg. Microservice invocation to multiple back-end services.
- eg. Agentic Workflow with multiple calls and potentially multiple LLMs or Tools.
- eg. Building an API aggregation endpoint that calls multiple backend services and combines results.

**Why Care At All:**
- When you need to call multiple microservices or APIs in parallel, this ensures they all complete (or all fail) together.
- No more orphaned threads or complex error handling.

**Code Sample:**
```java
// Compile/run with: --enable-preview --release 25
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

record CustomerData(String name, String email) {}
record OrderHistory(List<Order> orders) {}
record PaymentInfo(String lastFour) {}

// Aggregate customer information from multiple services
public CustomerProfile getCustomerProfile(String customerId) {
    
    try (var scope = StructuredTaskScope.open()) {
        
        // Fork parallel calls to different services
        Subtask<CustomerData> customerTask = scope.fork(() -> 
            customerService.getCustomer(customerId));
            
        Subtask<OrderHistory> ordersTask = scope.fork(() -> 
            orderService.getOrderHistory(customerId));
            
        Subtask<PaymentInfo> paymentTask = scope.fork(() -> 
            paymentService.getPaymentInfo(customerId));
        
        // Wait for all tasks to complete
        scope.join();
        
        // If any failed, the scope handles it
        // Combine results
        return new CustomerProfile(
            customerTask.get(),
            ordersTask.get(),
            paymentTask.get()
        );
        
    } catch (Exception e) {
        // All tasks are cancelled if one fails
        throw new ServiceException("Failed to get profile", e);
    }
}

// The beauty: If one service fails, all others are automatically cancelled
// No orphaned threads, no resource leaks
```

---
### 7. Ahead-of-Time (AOT) Compilation

- Command-Line Ergonomics (JEP 514)
- Method Profiling (JEP 515)

**Short Description:** Faster startup with pre-recorded profiles

**Use Case:**
- eg. Serverless or Containerise Functions
- eg. MCP tools linked to Vibe-Coding

**Why Care At All:**
- Production apps start faster after warmup profile is created once.
- **Alternative** - GraalVM as a native image compilation (different technique)

**Code Sample (JDK 11 vs JDK 25):**
```bash
# OLD: Two steps
java -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf MyApp
java -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf
# OLD: Use profile for fast startup
java -XX:AOTMode=on -XX:AOTConfiguration=app.aotconf MyApp

# NEW: One step
java -XX:AOTCacheOutput=app.aot MyApp
# NEW: Use profile for fast startup
java -XX:AOTCache=app.aot MyApp
```

---
### 8. PEM Encodings of Cryptographic Objects (JEP 470) 🔄 PREVIEW

**Short Description:** Encode/decode certificates and keys as PEM

**Use Case:**
- eg. DevOps processes with different certificates
- eg. SaaS and Multi-Cloud Security (different certs and different connectivity)

**Why Care At All:** 
- Working with SSL certificates, API keys, and cryptographic data becomes easier.

**Code Sample (JDK 11 vs JDK 25):**
```java
// JDK 11 - Manual Base64 + Header/Footer
String pem = "-----BEGIN CERTIFICATE-----\n" +
    Base64.getEncoder().encodeToString(cert.getEncoded()) +
    "\n-----END CERTIFICATE-----";

// JDK 25 - Native PEM API
PEMEncoder encoder = PEMEncoder.of();
String pem = encoder.encodeToString(cert);
```

---
### 9. JFR CPU-Time Profiling (JEP 509) - Experimental
**Short Description:** CPU profiling for performance analysis

**Use Case:**
- eg. Finding which methods consume most CPU time in production.

**Code Sample:**
```bash
java -XX:StartFlightRecording=filename=profile.jfr,settings=profile MyApp
# Analyze in Mission Control or VisualVM
```

---
### 10. Quantum-Resistant Module-Lattice-Based Key Encapsulation Mechanism (JEP 496) 🔄 PREVIEW*

**Short Description:**
- ML-KEM (JEP 496) was is JDK 24 but not in JDK 25 ... thought __honourable mention__ ...
- Kyber (ML-KEM) is NIST-standardized (FIPS 203)
- US government mandates post-quantum by 2033
- "Harvest now, decrypt later" attacks are happening now
- JEP 496 will bring native ML-KEM support to Java

**Code Sample (JDK 11 vs JDK 25):**
```java
// This demo uses BouncyCastle's Kyber implementation to show the concept. When JEP 496 lands, the API will be similar.
// JDK 11 - RSA (vulnerable to quantum attacks)
KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
kpg.initialize(2048);
KeyPair keyPair = kpg.generateKeyPair();

// JDK 25 - ML-KEM (quantum-resistant)
// Note: ML-KEM coming in JDK 25/26 via JEP 496
// Currently use BouncyCastle for Kyber
// Note: This uses BouncyCastle as JEP 496 may not be in JDK 25 final yet
// Download: https://www.bouncycastle.org/latest_releases.html
Security.addProvider(new BouncyCastlePQCProvider());
KeyPairGenerator kpg = KeyPairGenerator.getInstance("Kyber");
kpg.initialize(KyberParameterSpec.kyber768);
```

---
## Summary: What Should You Adopt?

### **Adopt Now (Final Features):**
1. **Compact Source Files** - Great for scripts and prototypes  
2. **Module Import Declarations** - Reduces import boilerplate
3. **Flexible Constructor Bodies** - Cleaner validation logic
4. **Scoped Values** - Better than ThreadLocal for request context

### **Watch & Learn (Preview Features):**
1. **Structured Concurrency** - For parallel API calls
2. **Primitive Patterns** - Coming soon, useful for API processing
3. **PEM Encoding** - More friendly sharing keys

### **Performance Wins (Use in Production):**
1. **Compact Object Headers** - Automatic memory savings
2. **AOT Profiling** - Faster startup times
3. **JFR Enhancements** - Better production monitoring

### Migration Path
Since Java 25 is LTS. Recommended approach:

1. **Immediate:** Upgrade to Java 25, use final features where they simplify code
2. **3-6 months:** Evaluate preview features in dev/test environments  
3. **Next LTS (Java 29?):** Preview features will likely be final (next planned LTS release is Java 29 in September 2027)

---
## Resources
- OpenJDK JEPs: https://openjdk.org/projects/jdk/25/
- Oracle Release Notes: https://www.oracle.com/java/technologies/javase/25-relnote-issues.html
- Java 25 Documentation: https://docs.oracle.com/en/java/javase/25/
---
    
    
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////////////////////////////////
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////////////////////////////////
    \\\\                                                                        ////
    \\\\                                                                        ////
    \\\\                                                                        ////
    \\\\                               THANK YOU                                ////
    \\\\                         jason.lowe@oracle.com                          ////
    \\\\                                                                        ////
    \\\\                                                                        ////
    \\\\                                                                        ////
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////////////////////////////////
    \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////////////////////////////////



