# PowerShell script to run page analysis
Write-Host "=== RUNNING PAGE ANALYSIS ===" -ForegroundColor Green

# Set Java path
$JAVA_HOME = "C:\Program Files\Java\jdk-24"
$JAVA_BIN = "$JAVA_HOME\bin\java.exe"
$JAVAC_BIN = "$JAVA_HOME\bin\javac.exe"

# Check if Java exists
if (-not (Test-Path $JAVA_BIN)) {
    Write-Host "Java not found at: $JAVA_BIN" -ForegroundColor Red
    Write-Host "Please install Java or update the path in this script." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Java found at: $JAVA_BIN" -ForegroundColor Green

# Try to run the standalone analyzer
Write-Host "Attempting to run StandalonePageAnalyzer..." -ForegroundColor Yellow

try {
    # First, try to compile if needed
    Write-Host "Compiling StandalonePageAnalyzer..." -ForegroundColor Yellow
    & $JAVAC_BIN -cp "target/classes;target/dependency/*" "src/main/java/com/example/insider/debug/StandalonePageAnalyzer.java"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Compilation successful!" -ForegroundColor Green
        Write-Host "Running StandalonePageAnalyzer..." -ForegroundColor Yellow
        & $JAVA_BIN -cp "target/classes;target/dependency/*" "com.example.insider.debug.StandalonePageAnalyzer"
    } else {
        Write-Host "Compilation failed. Trying alternative approach..." -ForegroundColor Yellow
        
        # Try to run with Maven if available
        Write-Host "Checking for Maven..." -ForegroundColor Yellow
        
        # Try common Maven locations
        $mavenPaths = @(
            "C:\Program Files\Apache\maven\bin\mvn.cmd",
            "C:\apache-maven\bin\mvn.cmd",
            "$env:USERPROFILE\.m2\wrapper\dists\apache-maven-*\apache-maven-*\bin\mvn.cmd"
        )
        
        $mavenFound = $false
        foreach ($mavenPath in $mavenPaths) {
            if (Test-Path $mavenPath) {
                Write-Host "Maven found at: $mavenPath" -ForegroundColor Green
                Write-Host "Running ManualInspectionTest..." -ForegroundColor Yellow
                & $mavenPath test -Dtest=ManualInspectionTest
                $mavenFound = $true
                break
            }
        }
        
        if (-not $mavenFound) {
            Write-Host "Maven not found. Please install Maven or Java dependencies." -ForegroundColor Red
            Write-Host "You can download Maven from: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
            Write-Host "Or install Java dependencies manually." -ForegroundColor Yellow
        }
    }
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Stack trace: $($_.ScriptStackTrace)" -ForegroundColor Red
}

Write-Host "=== ANALYSIS COMPLETED ===" -ForegroundColor Green
Read-Host "Press Enter to exit"
