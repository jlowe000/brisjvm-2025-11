#!/bin/sh

${JAVA_11_HOME}/bin/javac MemoryDemo.java
time ${JAVA_11_HOME}/bin/java -Xmx2g MemoryDemo

# JDK 25
time ${JAVA_25_HOME}/bin/java -Xmx2g MemoryDemo.java
