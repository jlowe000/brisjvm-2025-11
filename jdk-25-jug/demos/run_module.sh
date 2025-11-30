#!/bin/sh

echo ""
echo "ModuleImportDemo with JDK 11"
echo ""
${JAVA_11_HOME}/bin/javac ModuleImportDemo11.java
time ${JAVA_11_HOME}/bin/java ModuleImportDemo11
echo ""
head ModuleImportDemo11.java

# JDK 25
echo ""
echo "ModuleImportDemo with JDK 25"
echo ""
time ${JAVA_25_HOME}/bin/java ModuleImportDemo25.java
echo ""
head ModuleImportDemo25.java
