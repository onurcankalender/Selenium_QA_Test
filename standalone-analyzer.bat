@echo off
echo Running Standalone Page Analyzer...
echo.

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed or not in PATH.
    echo Please install Java and add it to your PATH.
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

echo Java found. Compiling and running Standalone Page Analyzer...
echo.

REM Try to compile using javac first
echo Compiling StandalonePageAnalyzer...
javac -cp "target/classes;target/dependency/*" src/main/java/com/example/insider/debug/StandalonePageAnalyzer.java
if %errorlevel% neq 0 (
    echo javac failed, trying to download dependencies and compile...
    echo.
    echo Please make sure you have the following dependencies in your classpath:
    echo - Selenium WebDriver
    echo - WebDriverManager
    echo - ChromeDriver
    echo.
    echo You may need to download these JAR files manually and add them to the classpath.
    pause
    exit /b 1
)

REM Run the analyzer
echo Running Standalone Page Analyzer...
java -cp "target/classes;target/dependency/*" com.example.insider.debug.StandalonePageAnalyzer

echo.
echo Standalone Page Analyzer completed.
pause
