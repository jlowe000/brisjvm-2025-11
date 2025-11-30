#!/bin/sh

# ============================================
# JDK 11 - Execution Time Sampling
# ============================================

read -p "Start JDK11 Profiling ..."

# Start recording with standard profiling
${JAVA_11_HOME}/bin/javac JFRDemo.java
${JAVA_11_HOME}/bin/java -XX:StartFlightRecording:settings=profile,filename=jdk11-recording.jfr JFRDemo

# View hot methods (execution time based)
${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk11-recording.jfr > jdk11-samples.txt

# Or view summary
${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk11-recording.jfr | head -50

# Count samples
echo "Sample count:"
${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk11-recording.jfr | grep -c "stackTrace"

# ============================================
# JDK 25 - CPU Time Profiling (Linux only)
# ============================================

read -p "Start JDK25 Profiling ..."

# Standard execution sampling (works everywhere)
${JAVA_25_HOME}/bin/javac JFRDemo.java
${JAVA_25_HOME}/bin/java -XX:StartFlightRecording:settings=profile,filename=jdk25-execution.jfr JFRDemo

# NEW: CPU-time profiling (Linux only, experimental)
${JAVA_25_HOME}/bin/java -XX:StartFlightRecording:jdk.CPUTimeSample#enabled=true,filename=jdk25-cputime.jfr JFRDemo

# View CPU-time hot methods (JDK 25 only)
${JAVA_25_HOME}/bin/jfr view cpu-time-hot-methods jdk25-cputime.jfr

# Compare both event types
echo "=== Execution Samples (time-based) ==="
${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk25-execution.jfr | grep "cpuIntensiveTask" | wc -l

echo "=== CPU Time Samples (cycle-based, Linux only) ==="
${JAVA_25_HOME}/bin/jfr print --events jdk.CPUTimeSample jdk25-cputime.jfr | grep "cpuIntensiveTask" | wc -l

# ============================================
# Advanced Analysis
# ============================================

# JDK 25: View flame graph data
# ${JAVA_25_HOME}/bin/jfr view flame-graph jdk25-cputime.jfr > flamegraph.html

# Compare execution time vs CPU time
# ${JAVA_25_HOME}/bin/jfr view hot-methods jdk25-execution.jfr > execution-hotmethods.txt
# ${JAVA_25_HOME}/bin/jfr view cpu-time-hot-methods jdk25-cputime.jfr > cputime-hotmethods.txt

# echo "Comparing profiles:"
# echo "Execution sampling shows time spent (including I/O wait)"
# echo "CPU-time sampling shows actual CPU cycles consumed"
# echo ""
# echo "Expected difference:"
# echo "- ioIntensiveTask: High in execution time, LOW in CPU time"
# echo "- cpuIntensiveTask: High in BOTH execution and CPU time"

# ============================================
# Performance Overhead Test
# ============================================

read -p "Start JFR Profiling Overhead ..."

echo "=== Testing JFR Overhead ==="

# No JFR
echo "=== No JFR ==="
time ${JAVA_25_HOME}/bin/java JFRDemo

# With execution sampling (JDK 11 & 25)
echo "=== JFR Execution Sampling ==="
time ${JAVA_25_HOME}/bin/java -XX:StartFlightRecording:settings=profile,filename=overhead-exec.jfr JFRDemo

# With CPU-time profiling (JDK 25, Linux only)
echo "=== JFR Execution Sampling with CPU-time Profiling ==="
time ${JAVA_25_HOME}/bin/java -XX:StartFlightRecording:jdk.CPUTimeSample#enabled=true,filename=overhead-cpu.jfr JFRDemo

echo "Compare the 'real' times above to see JFR overhead"
echo "CPU-time profiling typically has similar overhead to execution sampling"
