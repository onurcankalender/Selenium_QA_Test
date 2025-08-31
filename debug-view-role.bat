@echo off
echo Running View Role Button Debug Test...
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

echo Maven found. Running View Role debug test...
echo.

REM Run only the View Role debug test
mvn test -Dtest=ViewRoleDebugTest

echo.
echo View Role debug test completed.
pause
