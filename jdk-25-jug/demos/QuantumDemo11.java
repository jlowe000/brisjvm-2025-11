import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;

public class QuantumDemo11 {
    
    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();
        
        System.out.println("=== JDK 11: Traditional RSA (NOT Quantum-Safe) ===\n");
        
        // Generate RSA key pair - vulnerable to quantum attacks
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048, new SecureRandom());
        
        long keyGenStart = System.nanoTime();
        KeyPair keyPair = kpg.generateKeyPair();
        long keyGenTime = (System.nanoTime() - keyGenStart) / 1_000_000;
        
        System.out.println("RSA Key Pair Generated");
        System.out.println("Key Generation Time: " + keyGenTime + "ms");
        System.out.println("Public Key Size: " + keyPair.getPublic().getEncoded().length + " bytes");
        System.out.println("Private Key Size: " + keyPair.getPrivate().getEncoded().length + " bytes");
        
        // Encrypt a symmetric key using RSA
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey aesKey = keyGen.generateKey();
        
        System.out.println("\n--- Encrypting AES Key with RSA ---");
        
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.WRAP_MODE, keyPair.getPublic());
        
        long encryptStart = System.nanoTime();
        byte[] encryptedKey = cipher.wrap(aesKey);
        long encryptTime = (System.nanoTime() - encryptStart) / 1_000;
        
        System.out.println("Encrypted Key Size: " + encryptedKey.length + " bytes");
        System.out.println("Encryption Time: " + encryptTime + "µs");
        
        // Decrypt the symmetric key
        cipher.init(Cipher.UNWRAP_MODE, keyPair.getPrivate());
        
        long decryptStart = System.nanoTime();
        SecretKey decryptedKey = (SecretKey) cipher.unwrap(
            encryptedKey, "AES", Cipher.SECRET_KEY);
        long decryptTime = (System.nanoTime() - decryptStart) / 1_000;
        
        System.out.println("Decryption Time: " + decryptTime + "µs");
        System.out.println("Keys Match: " + 
            MessageDigest.isEqual(aesKey.getEncoded(), decryptedKey.getEncoded()));
        
        long totalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("\n=== Summary ===");
        System.out.println("Total Time: " + totalTime + "ms");
        System.out.println("Algorithm: RSA-2048");
        System.out.println("Quantum Safe: NO ❌");
        System.out.println("Vulnerable to: Shor's algorithm on large quantum computers");
        System.out.println("\nNote: RSA will be broken by sufficiently powerful quantum computers.");
        System.out.println("Current recommendation: Transition to post-quantum algorithms.");
    }
}
