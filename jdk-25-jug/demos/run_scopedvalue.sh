#!/bin/sh

echo ""
echo "ScopedValueDemo with JDK 11"
echo ""
${JAVA_11_HOME}/bin/javac ScopedValueDemo11.java
time ${JAVA_11_HOME}/bin/java ScopedValueDemo11
echo ""
head -n 25 ScopedValueDemo11.java

# JDK 25
echo ""
echo "ScopedValueDemo with JDK 11"
echo ""
time ${JAVA_25_HOME}/bin/java --enable-preview ScopedValueDemo25.java
echo ""
head -n 25 ScopedValueDemo25.java
