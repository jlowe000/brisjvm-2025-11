#!/bin/sh

echo ""
echo "PEMDemo with JDK 11"
echo ""
${JAVA_11_HOME}/bin/javac PEMDemo11.java
time ${JAVA_11_HOME}/bin/java PEMDemo11
echo "
        // Split into 64-character lines (PEM standard)
        for (int i = 0; i < base64.length(); i += 64) {
            int end = Math.min(i + 64, base64.length());
            pem.append(base64.substring(i, end)).append("\n");
        }
"

# JDK 25
echo ""
echo "PEMDemo with JDK 25"
echo ""
${JAVA_25_HOME}/bin/javac --enable-preview --release 25 PEMDemo25.java
time ${JAVA_25_HOME}/bin/java --enable-preview PEMDemo25
echo "
    // Encode to PEM - simple one-liner!
    PEMEncoder encoder = PEMEncoder.of();
    String publicKeyPEM = encoder.encodeToString(keyPair.getPublic());
    String privateKeyPEM = encoder.encodeToString(keyPair.getPrivate());
"
