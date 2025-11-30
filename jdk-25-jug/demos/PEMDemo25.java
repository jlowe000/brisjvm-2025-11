// Compile with: javac --enable-preview --release 25 PEMDemo25.java
// Run with: java --enable-preview PEMDemo25

import java.security.*;

void main() throws Exception {
    long start = System.nanoTime();
    
    IO.println("=== JDK 25: Native PEM API ===\n");
    
    // Generate RSA key pair
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(2048);
    KeyPair keyPair = keyGen.generateKeyPair();
    
    // Encode to PEM - simple one-liner!
    PEMEncoder encoder = PEMEncoder.of();
    String publicKeyPEM = encoder.encodeToString(keyPair.getPublic());
    String privateKeyPEM = encoder.encodeToString(keyPair.getPrivate());
    
    IO.println("Public Key (PEM):");
    IO.println(publicKeyPEM);
    IO.println("Private Key (PEM - first 300 chars):");
    IO.println(privateKeyPEM.substring(0, 300) + "...\n");
    
    // Decode from PEM - automatic type detection!
    PEMDecoder decoder = PEMDecoder.of();
    PublicKey decodedPublicKey = decoder.decode(publicKeyPEM, PublicKey.class);
    
    // Verify round-trip
    boolean matches = keyPair.getPublic().equals(decodedPublicKey);
    IO.println("Round-trip successful: " + matches);
    
    // Bonus: Encrypted PEM (password-protected private key)
    String password = "MySecurePassword123";
    String encryptedPEM = encoder.withEncryption(password.toCharArray())
        .encodeToString(keyPair.getPrivate());
    
    IO.println("\nEncrypted Private Key (PEM - first 200 chars):");
    IO.println(encryptedPEM.substring(0, 200) + "...");
    
    // Decrypt
    PrivateKey decryptedKey = decoder.withDecryption(password.toCharArray())
        .decode(encryptedPEM, PrivateKey.class);
    IO.println("Decryption successful: " + 
        keyPair.getPrivate().equals(decryptedKey));
    
    long duration = (System.nanoTime() - start) / 1_000_000;
    IO.println("\nExecution time: " + duration + "ms");
    IO.println("Lines of code for encode/decode: ~4 lines");
    IO.println("Manual header/footer management: Not needed");
    IO.println("Algorithm detection: Automatic");
}
