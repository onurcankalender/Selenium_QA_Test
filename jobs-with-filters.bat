@echo off
echo Running Jobs Page With Filters Test...
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

echo Maven found. Running Jobs Page With Filters Test...
echo.

REM Run only the Jobs Page With Filters test
mvn test -Dtest=JobsPageWithFiltersTest

echo.
echo Jobs Page With Filters Test completed.
pause
