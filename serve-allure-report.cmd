@echo off
setlocal
cd /d "%~dp0"

set "PORT=5050"
set "REPORT_DIR=%CD%\Reports\allure-report"

if not exist "%REPORT_DIR%\index.html" (
    echo Report not found. Generate it first:
    echo   mvnw-jdk21.cmd allure:report
    exit /b 1
)

echo Serving existing report at http://127.0.0.1:%PORT%
echo Press Ctrl+C to stop.
echo.

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$port = %PORT%; $root = '%REPORT_DIR%'; " ^
  "$listener = New-Object System.Net.HttpListener; " ^
  "$listener.Prefixes.Add(\"http://127.0.0.1:$port/\"); " ^
  "$listener.Start(); " ^
  "Start-Process 'http://127.0.0.1:$port/index.html'; " ^
  "while ($listener.IsListening) { " ^
  "  $context = $listener.GetContext(); " ^
  "  $path = $context.Request.Url.LocalPath.TrimStart('/'); " ^
  "  if ([string]::IsNullOrWhiteSpace($path)) { $path = 'index.html' }; " ^
  "  $file = Join-Path $root ($path -replace '/', '\'); " ^
  "  if (Test-Path $file -PathType Leaf) { " ^
  "    $bytes = [System.IO.File]::ReadAllBytes($file); " ^
  "    $ext = [System.IO.Path]::GetExtension($file).ToLower(); " ^
  "    $contentType = switch ($ext) { '.html' {'text/html'} '.js' {'application/javascript'} '.css' {'text/css'} '.json' {'application/json'} '.png' {'image/png'} '.txt' {'text/plain'} default {'application/octet-stream'} }; " ^
  "    $context.Response.ContentType = $contentType; " ^
  "    $context.Response.OutputStream.Write($bytes, 0, $bytes.Length); " ^
  "  } else { $context.Response.StatusCode = 404 }; " ^
  "  $context.Response.Close(); " ^
  "}"

endlocal
