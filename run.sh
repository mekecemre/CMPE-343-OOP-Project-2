#!/bin/bash

# ==============================================================================
# CMPE-343 Project 2: Run Script
# This script runs the Contact Management System
# ==============================================================================

echo "========================================================"
echo "  CMPE-343 Contact Management System"
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

# Check if compiled classes exist
if [ ! -f "Main.class" ]; then
    echo "Error: Compiled classes not found!"
    echo "Please compile the project first using:"
    echo "  ./compile.sh"
    echo ""
    exit 1
fi

# Check if MySQL connector is present
MYSQL_JAR="mysql-connector-java.jar"
if [ ! -f "$MYSQL_JAR" ]; then
    echo "Warning: $MYSQL_JAR not found in src directory!"
    echo "Please download MySQL Connector/J and place it in the src directory."
    echo ""
    echo "Download from: https://dev.mysql.com/downloads/connector/j/"
    echo ""
    exit 1
fi

echo "Starting Contact Management System..."
echo "Press Ctrl+C to force quit if needed"
echo ""
echo "========================================================"
echo ""

# Run the application with UTF-8 encoding
java -Dfile.encoding=UTF-8 -cp ".:$MYSQL_JAR" Main

# Return to project root
cd ..

echo ""
echo "========================================================"
echo "  Application terminated"
echo "========================================================"
