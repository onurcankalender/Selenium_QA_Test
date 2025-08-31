@echo off
echo Running Simple Jobs Page Analysis Test...
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

echo Maven found. Running Simple Jobs Page Analysis...
echo.

REM Run only the Simple Jobs Page Analysis test
mvn test -Dtest=SimpleJobsPageTest

echo.
echo Simple Jobs Page Analysis completed.
pause
