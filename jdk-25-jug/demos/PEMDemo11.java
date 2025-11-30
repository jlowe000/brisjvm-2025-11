import java.security.*;
import java.security.cert.*;
import java.util.Base64;
import java.io.*;

public class PEMDemo11 {
    
    // Manual PEM encoding - tedious and error-prone
    static String encodePublicKeyToPEM(PublicKey publicKey) throws Exception {
        byte[] encoded = publicKey.getEncoded();
        String base64 = Base64.getEncoder().encodeToString(encoded);
        
        // Manually add PEM header/footer
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN PUBLIC KEY-----\n");
        
        // Split into 64-character lines (PEM standard)
        for (int i = 0; i < base64.length(); i += 64) {
            int end = Math.min(i + 64, base64.length());
            pem.append(base64.substring(i, end)).append("\n");
        }
        
        pem.append("-----END PUBLIC KEY-----\n");
        return pem.toString();
    }
    
    static String encodePrivateKeyToPEM(PrivateKey privateKey) throws Exception {
        byte[] encoded = privateKey.getEncoded();
        String base64 = Base64.getEncoder().encodeToString(encoded);
        
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN PRIVATE KEY-----\n");
        
        for (int i = 0; i < base64.length(); i += 64) {
            int end = Math.min(i + 64, base64.length());
            pem.append(base64.substring(i, end)).append("\n");
        }
        
        pem.append("-----END PRIVATE KEY-----\n");
        return pem.toString();
    }
    
    // Manual PEM decoding - complex parsing
    static PublicKey decodePublicKeyFromPEM(String pem) throws Exception {
        // Remove header and footer
        String base64 = pem
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s", ""); // Remove all whitespace
        
        byte[] decoded = Base64.getDecoder().decode(base64);
        
        // Try different key algorithms (trial and error!)
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(
                new java.security.spec.X509EncodedKeySpec(decoded));
        } catch (Exception e) {
            // Try EC if RSA fails
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePublic(
                new java.security.spec.X509EncodedKeySpec(decoded));
        }
    }
    
    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();
        
        System.out.println("=== JDK 11: Manual PEM Encoding ===\n");
        
        // Generate RSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        
        // Encode to PEM - manual process
        String publicKeyPEM = encodePublicKeyToPEM(keyPair.getPublic());
        String privateKeyPEM = encodePrivateKeyToPEM(keyPair.getPrivate());
        
        System.out.println("Public Key (PEM):");
        System.out.println(publicKeyPEM);
        System.out.println("Private Key (PEM - first 300 chars):");
        System.out.println(privateKeyPEM.substring(0, 300) + "...\n");
        
        // Decode from PEM - complex parsing
        PublicKey decodedPublicKey = decodePublicKeyFromPEM(publicKeyPEM);
        
        // Verify round-trip
        boolean matches = keyPair.getPublic().equals(decodedPublicKey);
        System.out.println("Round-trip successful: " + matches);
        
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Execution time: " + duration + "ms");
        System.out.println("Lines of code for encode/decode: ~60 lines");
        System.out.println("Manual header/footer management: Required");
        System.out.println("Algorithm detection: Trial and error");
    }
}
