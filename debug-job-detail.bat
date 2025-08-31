@echo off
echo Running Job Detail Page Debug Test...
echo.

REM Check if Maven is available
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH.
    echo Please install Maven and add it to your PATH.
    echo Download from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

echo Maven found. Running Job Detail debug test...
echo.

REM Run only the Job Detail debug test
mvn test -Dtest=JobDetailDebugTest

echo.
echo Job Detail debug test completed.
pause
