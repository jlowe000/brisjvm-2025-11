#!/bin/sh

echo ""
echo "CompactDemo with JDK 11"
echo ""
${JAVA_11_HOME}/bin/javac CompactDemo11.java
time ${JAVA_11_HOME}/bin/java CompactDemo11
echo ""
cat CompactDemo11.java

# JDK 25
echo ""
echo "CompactDemo with JDK 25"
echo ""
time ${JAVA_25_HOME}/bin/java CompactDemo25.java
echo ""
cat CompactDemo25.java
