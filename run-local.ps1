$ErrorActionPreference = "Stop"

$port = 8081
$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

Write-Host "Checking port $port..."
$connections = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue
$pids = @($connections | Select-Object -ExpandProperty OwningProcess -Unique)

foreach ($processId in $pids) {
    if (-not $processId) {
        continue
    }

    $process = Get-Process -Id $processId -ErrorAction SilentlyContinue
    if ($null -eq $process) {
        continue
    }

    if ($process.ProcessName -match "^java") {
        Write-Host "Stopping existing Java app on port $port (PID $processId)..."
        Stop-Process -Id $processId -Force
        Start-Sleep -Seconds 2
    } else {
        Write-Host "Port $port is used by PID $processId ($($process.ProcessName)). Stop it manually or change server.port."
        exit 1
    }
}

Write-Host "Starting Aurevia on http://localhost:$port/login"
Set-Location $projectRoot
mvn spring-boot:run
