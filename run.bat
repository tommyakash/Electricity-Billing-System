@echo off
REM ===========================
REM Electricity Billing System
REM Compile & Run JavaFX App
REM ===========================

REM Set UTF-8 encoding
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

REM Compile the Java file
javac --module-path "javafx-sdk-24.0.2\lib" --add-modules javafx.controls,javafx.fxml ElectricityBillingSystem.java
IF %ERRORLEVEL% NEQ 0 (
    echo Compilation failed.
    pause
    exit /b
)

REM Run the application
java --module-path "javafx-sdk-24.0.2\lib" --add-modules javafx.controls,javafx.fxml ElectricityBillingSystem

pause
