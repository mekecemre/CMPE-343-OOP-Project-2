@echo off
REM ==============================================================================
REM CMPE-343 Project 2: Compilation Script for Windows
REM This script compiles all Java source files for the Contact Management System
REM ==============================================================================

echo ========================================================
echo   CMPE-343 Contact Management System - Compilation
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

echo Cleaning previous compilation...
del /s /q *.class 2>nul

echo Compiling Java source files...
echo.

REM Check if MySQL connector is present
set MYSQL_JAR=mysql-connector-java.jar
if not exist "%MYSQL_JAR%" (
    echo Error: %MYSQL_JAR% not found in src directory!
    echo.
    echo The MySQL Connector JAR should be included in the repository.
    echo If you're seeing this error:
    echo   1. Make sure you cloned the complete repository
    echo   2. Check that src\mysql-connector-java.jar exists
    echo   3. Re-clone the repository if the file is missing
    echo.
    echo If you need to download it manually:
    echo   https://dev.mysql.com/downloads/connector/j/
    echo.
    pause
    cd ..
    exit /b 1
)

set CLASSPATH=.;%MYSQL_JAR%

REM Compile all Java files
javac -encoding UTF-8 -cp "%CLASSPATH%" Main.java models\*.java managers\*.java utils\*.java roles\*.java

REM Check compilation result
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================================
    echo   Compilation successful!
    echo ========================================================
    echo.
    echo To run the application, use:
    echo   run.bat
    echo.
    echo Or manually:
    echo   cd src
    echo   java -Dfile.encoding=UTF-8 -cp ".;%MYSQL_JAR%" Main
    echo.
) else (
    echo.
    echo ========================================================
    echo   Compilation failed!
    echo ========================================================
    echo.
    echo Please check the error messages above and fix any issues.
    pause
    cd ..
    exit /b 1
)

cd ..
pause
