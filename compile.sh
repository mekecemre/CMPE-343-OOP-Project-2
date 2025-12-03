#!/bin/bash

# ==============================================================================
# CMPE-343 Project 2: Compilation Script
# This script compiles all Java source files for the Contact Management System
# ==============================================================================

echo "========================================================"
echo "  CMPE-343 Contact Management System - Compilation"
echo "========================================================"
echo ""

# Check if we're in the right directory
if [ ! -d "src" ]; then
    echo "Error: src directory not found!"
    echo "Please run this script from the project root directory."
    exit 1
fi

# Navigate to src directory
cd src

echo "Cleaning previous compilation..."
find . -name "*.class" -type f -delete

echo "Compiling Java source files..."
echo ""

# Check if MySQL connector is present
MYSQL_JAR="mysql-connector-java.jar"
if [ ! -f "$MYSQL_JAR" ]; then
    echo "Error: $MYSQL_JAR not found in src directory!"
    echo ""
    echo "The MySQL Connector JAR should be included in the repository."
    echo "If you're seeing this error:"
    echo "  1. Make sure you cloned the complete repository"
    echo "  2. Check that src/mysql-connector-java.jar exists"
    echo "  3. Re-clone the repository if the file is missing"
    echo ""
    echo "If you need to download it manually:"
    echo "  https://dev.mysql.com/downloads/connector/j/"
    echo ""
    cd ..
    exit 1
fi

CLASSPATH=".:$MYSQL_JAR"

# Compile all Java files
javac -encoding UTF-8 -cp "$CLASSPATH" \
    Main.java \
    models/*.java \
    managers/*.java \
    utils/*.java \
    roles/*.java

# Check compilation result
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================================"
    echo "  ✓ Compilation successful!"
    echo "========================================================"
    echo ""
    echo "To run the application, use:"
    echo "  ./run.sh"
    echo ""
    echo "Or manually:"
    echo "  cd src"
    echo "  java -Dfile.encoding=UTF-8 -cp .:$MYSQL_JAR Main"
    echo ""
else
    echo ""
    echo "========================================================"
    echo "  ✗ Compilation failed!"
    echo "========================================================"
    echo ""
    echo "Please check the error messages above and fix any issues."
    exit 1
fi

cd ..
