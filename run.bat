@echo off
REM ==============================================================================
REM CMPE-343 Project 2: Run Script for Windows
REM This script runs the Contact Management System
REM ==============================================================================

echo ========================================================
echo   CMPE-343 Contact Management System
echo ========================================================
echo.

REM Check if we're in the right directory
if not exist "src" (
    echo Error: src directory not found!
    echo Please run this script from the project root directory.
    pause
    exit /b 1
)

REM Navigate to src directory
cd src

REM Check if compiled classes exist
if not exist "Main.class" (
    echo Error: Compiled classes not found!
    echo Please compile the project first using:
    echo   compile.bat
    echo.
    pause
    cd ..
    exit /b 1
)

REM Check if MySQL connector is present
set MYSQL_JAR=mysql-connector-java.jar
if not exist "%MYSQL_JAR%" (
    echo Warning: %MYSQL_JAR% not found in src directory!
    echo Please download MySQL Connector/J and place it in the src directory.
    echo.
    echo Download from: https://dev.mysql.com/downloads/connector/j/
    echo.
    pause
    cd ..
    exit /b 1
)

echo Starting Contact Management System...
echo Press Ctrl+C to force quit if needed
echo.
echo ========================================================
echo.

REM Run the application with UTF-8 encoding
java -Dfile.encoding=UTF-8 -cp ".;%MYSQL_JAR%" Main

REM Return to project root
cd ..

echo.
echo ========================================================
echo   Application terminated
echo ========================================================
pause
