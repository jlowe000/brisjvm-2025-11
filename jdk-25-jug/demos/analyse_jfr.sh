#!/bin/sh

echo "=========================================="
echo "JFR Analysis: JDK 11 vs JDK 25"
echo "=========================================="

if [ -f "jdk11-recording.jfr" ]; then
    echo ""
    echo "=== JDK 11: Execution Sampling ==="
    echo "Total samples:"
    ${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk11-recording.jfr | grep -c "stackTrace"
    
    echo "Samples in cpuIntensiveTask:"
    ${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk11-recording.jfr | grep -c "cpuIntensiveTask"
    
    echo "Samples in ioIntensiveTask:"
    ${JAVA_25_HOME}/bin/jfr print --events jdk.ExecutionSample jdk11-recording.jfr | grep -c "ioIntensiveTask"
fi

if [ -f "jdk25-cputime.jfr" ]; then
    echo ""
    echo "=== JDK 25: CPU-Time Profiling ==="
    echo "Total CPU-time samples:"
    ${JAVA_25_HOME}/bin/jfr print --events jdk.CPUTimeSample jdk25-cputime.jfr | grep -c "stackTrace"
    
    echo "CPU samples in cpuIntensiveTask:"
    ${JAVA_25_HOME}/bin/jfr print --events jdk.CPUTimeSample jdk25-cputime.jfr | grep -c "cpuIntensiveTask"
    
    echo "CPU samples in ioIntensiveTask:"
    ${JAVA_25_HOME}/bin/jfr print --events jdk.CPUTimeSample jdk25-cputime.jfr | grep -c "ioIntensiveTask"
    
    echo ""
    echo "Key Difference:"
    echo "- Execution sampling: Shows ioIntensiveTask (includes wait time)"
    echo "- CPU-time sampling: Fewer ioIntensiveTask samples (only CPU time)"
    echo "- This helps identify true CPU bottlenecks vs I/O waits"
fi

echo ""
echo "=========================================="
