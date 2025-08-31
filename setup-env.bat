@echo off
echo Setting up environment variables...

REM Set Java path
set JAVA_HOME=C:\Program Files\Java\jdk-24
set PATH=%JAVA_HOME%\bin;%PATH%

REM Check if Maven is available in common locations
if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" (
    set PATH=C:\Program Files\Apache\maven\bin;%PATH%
    echo Maven found at C:\Program Files\Apache\maven\bin
) else if exist "C:\apache-maven\bin\mvn.cmd" (
    set PATH=C:\apache-maven\bin;%PATH%
    echo Maven found at C:\apache-maven\bin
) else (
    echo Maven not found. Please install Maven first.
    echo Download from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

echo Environment setup complete!
echo Java: %JAVA_HOME%
echo Maven: mvn

REM Now run the test
echo.
echo Running Manual Inspection Test...
mvn test -Dtest=ManualInspectionTest

echo.
echo Test completed.
pause
