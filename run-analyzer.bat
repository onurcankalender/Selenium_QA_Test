@echo off
echo Running Page Analyzer...
echo.

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed or not in PATH.
    echo Please install Java and add it to your PATH.
    pause
    exit /b 1
)

echo Java found. Compiling and running Page Analyzer...
echo.

REM Compile the project
echo Compiling project...
javac -cp "target/classes;target/dependency/*" src/main/java/com/example/insider/debug/PageAnalyzer.java

if %errorlevel% neq 0 (
    echo Compilation failed. Trying to compile with Maven first...
    mvn compile
    if %errorlevel% neq 0 (
        echo Maven compilation also failed.
        pause
        exit /b 1
    )
)

REM Run the analyzer
echo Running Page Analyzer...
java -cp "target/classes;target/dependency/*" com.example.insider.debug.PageAnalyzer

echo.
echo Page Analyzer completed.
pause
