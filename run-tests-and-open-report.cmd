@echo off
setlocal
cd /d "%~dp0"

set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Running tests...
call "%~dp0mvnw.cmd" clean test
if errorlevel 1 (
    echo Tests finished with failures. Opening report anyway...
)

call "%~dp0open-allure-report.cmd"
endlocal
