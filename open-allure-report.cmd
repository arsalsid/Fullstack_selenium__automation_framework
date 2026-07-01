@echo off
setlocal
cd /d "%~dp0"

set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo.
echo ============================================================
echo  Allure Report Server
echo ============================================================
echo.
echo  Do NOT open Reports\allure-report\index.html directly.
echo  Chrome blocks local file JSON requests (404 / loading forever).
echo.
echo  Starting server at: http://127.0.0.1:5050
echo  Keep the "Allure Server" window open while viewing the report.
echo  Press Ctrl+C in that window to stop the server.
echo.
echo ============================================================
echo.

start "Allure Server" cmd /k "cd /d %~dp0 && set JAVA_HOME=%JAVA_HOME% && set PATH=%JAVA_HOME%\bin;%PATH% && mvnw.cmd allure:serve"

echo Waiting for server to start...
timeout /t 12 /nobreak >nul

where chrome >nul 2>&1
if %errorlevel%==0 (
    start "" chrome "http://127.0.0.1:5050"
) else (
    start "" "http://127.0.0.1:5050"
)

echo.
echo If the report is still loading, wait a few seconds and refresh the browser.
endlocal
