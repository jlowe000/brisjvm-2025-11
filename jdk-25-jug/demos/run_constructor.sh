#!/bin/sh

${JAVA_11_HOME}/bin/javac ConstructorDemo11.java
time ${JAVA_11_HOME}/bin/java ConstructorDemo11

# JDK 25
time ${JAVA_25_HOME}/bin/java ConstructorDemo25.java
