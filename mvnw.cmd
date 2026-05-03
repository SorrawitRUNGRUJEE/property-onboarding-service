@echo off
REM Maven Wrapper script for Windows
REM Requires Maven to be installed or use: mvn wrapper:wrapper

setlocal

set MAVEN_CMD=mvn
where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Maven is not installed. Please install Maven or run: mvn -N wrapper:wrapper
    exit /b 1
)

%MAVEN_CMD% %*
