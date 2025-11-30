#!/bin/sh

echo ""
echo "StartupDemo with JDK 11"
echo ""
${JAVA_11_HOME}/bin/javac StartupDemo.java
time ${JAVA_11_HOME}/bin/java StartupDemo
echo ""
echo "\${JAVA_11_HOME}/bin/javac StartupDemo.java"
echo "time \${JAVA_11_HOME}/bin/java StartupDemo"

# JDK 25
echo ""
echo "StartupDemo with JDK 25"
echo ""
${JAVA_25_HOME}/bin/javac StartupDemo.java
${JAVA_25_HOME}/bin/java -XX:AOTCacheOutput=startup.aot StartupDemo
time ${JAVA_25_HOME}/bin/java -XX:AOTCache=startup.aot StartupDemo
time ${JAVA_25_HOME}/bin/java -XX:AOTCache=startup.aot StartupDemo
echo ""
echo "\${JAVA_25_HOME}/bin/javac StartupDemo.java"
echo "\${JAVA_25_HOME}/bin/java -XX:AOTCacheOutput=startup.aot StartupDemo"
echo "time ${JAVA_25_HOME}/bin/java -XX:AOTCache=startup.aot StartupDemo"
