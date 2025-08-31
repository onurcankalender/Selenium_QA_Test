@echo off
echo Running Jobs Page Debug Test...
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

echo Maven found. Running Jobs page debug test...
echo.

REM Run only the Jobs page debug test
mvn test -Dtest=JobsPageDebugTest

echo.
echo Jobs page debug test completed.
pause
