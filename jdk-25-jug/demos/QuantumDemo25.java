// Note: This uses BouncyCastle as JEP 496 may not be in JDK 25 final yet
// Download: https://www.bouncycastle.org/latest_releases.html
// Compile with: javac -cp ".:bcprov-jdk18on-*.jar:bcpkix-jdk18on-*.jar" QuantumDemo25.java
// Run with: java -cp ".:bcprov-jdk18on-*.jar:bcpkix-jdk18on-*.jar" QuantumDemo25

import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.*;
import javax.crypto.*;
import java.security.*;

public class QuantumDemo25 {
    
    public static void main(String[] args) throws Exception {
        // Add BouncyCastle PQC provider
        Security.addProvider(new BouncyCastlePQCProvider());
        
        long start = System.nanoTime();
        
        System.out.println("=== JDK 25: Post-Quantum Kyber (Quantum-Safe) ===\n");
        
        // Generate Kyber key pair - resistant to quantum attacks
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Kyber", "BCPQC");
        kpg.initialize(KyberParameterSpec.kyber768, new SecureRandom());
        
        long keyGenStart = System.nanoTime();
        KeyPair keyPair = kpg.generateKeyPair();
        long keyGenTime = (System.nanoTime() - keyGenStart) / 1_000_000;
        
        System.out.println("Kyber-768 Key Pair Generated");
        System.out.println("Key Generation Time: " + keyGenTime + "ms");
        System.out.println("Public Key Size: " + keyPair.getPublic().getEncoded().length + " bytes");
        System.out.println("Private Key Size: " + keyPair.getPrivate().getEncoded().length + " bytes");
        
        System.out.println("\n--- Key Encapsulation (Sender) ---");
        
        // Sender: Encapsulate a shared secret using receiver's public key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("Kyber", "BCPQC");
        KEMGenerateSpec kemGenSpec = new KEMGenerateSpec(keyPair.getPublic(), "AES");
        keyGenerator.init(kemGenSpec, new SecureRandom());
        
        long encapStart = System.nanoTime();
        SecretKeyWithEncapsulation secretKeyWithEncap = 
            (SecretKeyWithEncapsulation) keyGenerator.generateKey();
        long encapTime = (System.nanoTime() - encapStart) / 1_000;
        
        byte[] encapsulation = secretKeyWithEncap.getEncapsulation();
        byte[] sharedSecretSender = secretKeyWithEncap.getEncoded();
        
        System.out.println("Encapsulation Size: " + encapsulation.length + " bytes");
        System.out.println("Encapsulation Time: " + encapTime + "µs");
        System.out.println("Shared Secret (sender): " + 
            bytesToHex(sharedSecretSender).substring(0, 32) + "...");
        
        System.out.println("\n--- Key Decapsulation (Receiver) ---");
        
        // Receiver: Decapsulate using private key
        KeyGenerator keyGen2 = KeyGenerator.getInstance("Kyber", "BCPQC");
        KEMExtractSpec kemExtSpec = new KEMExtractSpec(
            keyPair.getPrivate(), encapsulation, "AES");
        keyGen2.init(kemExtSpec, new SecureRandom());
        
        long decapStart = System.nanoTime();
        SecretKeyWithEncapsulation secretKeyReceiver = 
            (SecretKeyWithEncapsulation) keyGen2.generateKey();
        long decapTime = (System.nanoTime() - decapStart) / 1_000;
        
        byte[] sharedSecretReceiver = secretKeyReceiver.getEncoded();
        
        System.out.println("Decapsulation Time: " + decapTime + "µs");
        System.out.println("Shared Secret (receiver): " + 
            bytesToHex(sharedSecretReceiver).substring(0, 32) + "...");
        System.out.println("Secrets Match: " + 
            MessageDigest.isEqual(sharedSecretSender, sharedSecretReceiver));
        
        long totalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("\n=== Summary ===");
        System.out.println("Total Time: " + totalTime + "ms");
        System.out.println("Algorithm: Kyber-768 (ML-KEM)");
        System.out.println("Quantum Safe: YES ✅");
        System.out.println("Security Level: ~AES-192 equivalent");
        System.out.println("Based on: Module Learning With Errors (lattice cryptography)");
        System.out.println("\nNote: Kyber is resistant to quantum attacks using Shor's algorithm.");
        System.out.println("NIST standardized as FIPS 203 (ML-KEM) in August 2024.");
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
