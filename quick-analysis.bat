@echo off
echo Running Quick Page Analysis Test...
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

echo Maven found. Running Quick Page Analysis...
echo.

REM Run only the Quick Page Analysis test
mvn test -Dtest=QuickPageAnalysisTest

echo.
echo Quick Page Analysis completed.
pause
