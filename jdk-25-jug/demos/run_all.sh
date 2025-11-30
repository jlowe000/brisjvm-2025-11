echo ""
echo "This Test - Compact and Instance Main"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_compact.sh
echo ""
echo "This Test - Module Import"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_module.sh
echo ""
echo "This Test - Flexible Constructors"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_constructor.sh
echo ""
echo "This Test - Scoped Values"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_scopedvalue.sh
echo ""
echo "This Test - Concurrency"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_concurrency.sh
# echo ""
# echo "This Test - Compact Memory Headers"
# Pause and wait for Enter key
# read -p "Press Enter to continue..."
# ./run_memory.sh
echo ""
echo "This Test - AOT Startup"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_startup.sh
echo ""
echo "This Test - PEM Encoding"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_pem.sh
echo ""
echo "This Test - Java Flight Recording - CPU Profiling"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./run_jfr.sh
echo ""
echo "This Test - Java Flight Recording - Analyse That"
# Pause and wait for Enter key
read -p "Press Enter to continue..."
./analyse_jfr.sh
echo ""
# echo "This Test - Post Quantum Safe Encoding"
# Pause and wait for Enter key
# read -p "Press Enter to continue..."
# ./run_quantum.sh
