#!/bin/sh

${JAVA_11_HOME}/bin/javac QuantumDemo11.java
time ${JAVA_11_HOME}/bin/java QuantumDemo11

# JDK 25
${JAVA_25_HOME}/bin/javac -cp ".:bcprov-jdk18on-1.82.jar:bcpkix-jdk18on-1.82.jar" QuantumDemo25.java
time ${JAVA_25_HOME}/bin/java -cp ".:bcprov-jdk18on-1.82.jar:bcpkix-jdk18on-1.82.jar" QuantumDemo25
