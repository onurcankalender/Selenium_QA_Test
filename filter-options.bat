@echo off
echo Running Filter Options Test...
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

echo Maven found. Running Filter Options Test...
echo.

REM Run only the Filter Options test
mvn test -Dtest=FilterOptionsTest

echo.
echo Filter Options Test completed.
pause
